package com.example.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project.dto.Article;
import com.example.project.dto.Board;
import com.example.project.dto.Req;
import com.example.project.service.ArticleService;
import com.example.project.service.BoardService;
import com.example.project.service.FavoriteTrainerService;
import com.example.project.service.MemberService;
import com.example.project.util.Util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsrArticleController {

	private ArticleService articleService;
	private MemberService memberService;
	private BoardService boardService;
	private FavoriteTrainerService favoriteTrainerService;
	private Req req;

	public UsrArticleController(ArticleService articleService, MemberService memberService, BoardService boardService, FavoriteTrainerService favoriteTrainerService, Req req) {
		this.articleService = articleService;
		this.memberService = memberService;
		this.boardService = boardService;
		this.favoriteTrainerService = favoriteTrainerService;
		this.req = req;
	}

	@GetMapping("/usr/article/write")
	public String write(Model model, int boardId, int memberCategory) {
		model.addAttribute("boardId", boardId);
		model.addAttribute("memberCategory", memberCategory);
		return "usr/article/write";
	}

	@PostMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(String content, int boardId, int memberCategory) {

		this.articleService.writeArticle(content, this.req.getLoginedMember().getId(), boardId, memberCategory);

		int id = this.articleService.getLastArticleId();

		return Util.jsReplace("게시물이 등록되었습니다", String.format("detail?id=%d", id));
	}
	
	
	@GetMapping("/usr/article/list")
	public String list(Model model, int boardId, @RequestParam int memberCategory, @RequestParam(defaultValue = "1") int cPage, @RequestParam(defaultValue = "") String keyWord, @RequestParam(defaultValue = "latest") String sortType) {
		
		int articlesInPage = 10;
		int limitFrom = (cPage - 1) * articlesInPage;
		
		int articlesCnt = this.articleService.getArticlesCnt(boardId, keyWord);
		
		int pagesCnt = (int) Math.ceil(articlesCnt / (double) articlesInPage);
		
		int startPage = ((cPage - 1) / 10) * 10 + 1;
		int endPage = (((cPage - 1) / 10) + 1 ) * 10;
		
		if (endPage > pagesCnt) {
			endPage = pagesCnt;
		}
		
		Board board = this.boardService.getBoard(boardId);
		List<Article> articles = this.articleService.getArticles(keyWord, boardId, articlesInPage, limitFrom, sortType);
		
		 // ✅ 여기 추가!!!
	    if (req.isLogined() && req.getLoginedMember().getAuthLevel() == 1) {
	        int loginedMemberId = req.getLoginedMember().getId();

	        for (Article article : articles) {
	            boolean favorited = favoriteTrainerService.isFavorited(loginedMemberId, article.getMemberId());
	            article.setFavorited(favorited);
	        }
	    }

		
		model.addAttribute("memberCategory", memberCategory);
		model.addAttribute("keyWord", keyWord);
		model.addAttribute("cPage", cPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("articles", articles);
		model.addAttribute("board", board);
		model.addAttribute("articlesCnt", articlesCnt);
		model.addAttribute("pagesCnt", pagesCnt);
		model.addAttribute("sortType", sortType);

		return "usr/article/list";
	}
	
	@GetMapping("/usr/article/detail")
	public String detail(HttpServletRequest request, HttpServletResponse response, Model model, int id) {

		Cookie[] cookies = request.getCookies();
		boolean isViewed = false;
		
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("viewedArticle_" + id)) {
					isViewed = true;
					break;
				}
			}
		}
		
		if (!isViewed) {
			this.articleService.increaseViewCnt(id);
			Cookie cookie = new Cookie("viewedArticle_" + id, "true");
			cookie.setMaxAge(60 * 30);
			response.addCookie(cookie);
		}
		
		Article article = this.articleService.getArticleById(id);
		
		// ✅ 즐겨찾기 상태 추가
		if (req.isLogined() && req.getLoginedMember().getAuthLevel() == 1) {
	        int loginedMemberId = req.getLoginedMember().getId();
	        boolean favorited = favoriteTrainerService.isFavorited(loginedMemberId, article.getMemberId());
	        article.setFavorited(favorited);
	    }
		
		model.addAttribute("article", article);

		return "usr/article/detail";
	}
	
	@GetMapping("/usr/article/modify")
	public String modify(Model model, int id) {

		Article article = this.articleService.getArticleById(id);

		model.addAttribute("article", article);

		return "usr/article/modify";
	}

	@PostMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String content) {

		this.articleService.modifyArticle(id, content);

		return Util.jsReplace(String.format("%d번 게시물이 수정되었습니다", id), String.format("detail?id=%d", id));
	}

	@GetMapping("/usr/article/delete")
	@ResponseBody
	public String delete(int id, int boardId, int memberCategory) {

		this.articleService.deleteArticle(id);

		return Util.jsReplace(String.format("%d번 게시물이 삭제되었습니다", id), String.format("list?boardId=%d&memberCategory=%d", boardId, memberCategory));
	}
}