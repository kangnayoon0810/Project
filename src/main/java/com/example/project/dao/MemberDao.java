package com.example.project.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.project.dto.Member;

@Mapper
public interface MemberDao {

	@Insert("""
			INSERT INTO `member`
			 	SET regDate = NOW()
			     	, updateDate = NOW()
			     	, `name` = #{name}
			     	, sex = #{sex}
			     	, nickName = #{nickName}
			     	, phoneNumber = #{phoneNumber}
			     	, loginId = #{loginId}
			     	, loginPw = #{loginPw}
			     	, eMail = #{eMail}
			     	, authLevel = #{authLevel}
			""")
	void signupMember(String name, int sex, String nickName, int phoneNumber, String loginId, String loginPw, String eMail, int authLevel);

	@Select("""
			SELECT *
			FROM `member`
			WHERE nickName = #{nickName}
			""")
	Member getMemberByNickName(String nickName);

	@Select("""
			SELECT *
			FROM `member`
			WHERE phoneNumber = #{phoneNumber}
			""")
	Member getMemberByPhoneNumber(int phoneNumber);

	@Select("""
			SELECT *
				FROM `member`
				WHERE loginId = #{loginId}
			""")
	Member getMemberByLoginId(String loginId);

	@Select("""
			SELECT *
				FROM `member`
				WHERE eMail = #{eMail}
			""")
	Member getMemberByEMail(String eMail);

	@Select("""
			SELECT nickName
				FROM `member`
				WHERE id = #{id}
			""")
	String getLoginId(int id);
	
	@Select("""
			SELECT *
				FROM `member`
				WHERE authLevel = #{authLevel}
			""")
	Member getMemberByAuthLevel(int authLevel);

	@Select("""
			SELECT nickName
				FROM `member`
				WHERE nickName = #{nickName}
			""")
	String getNickName(String nickName);

	@Select("""
			SELECT m.*, p.profileImageId 
			 	FROM `member` m
			 	INNER JOIN `profile`p
			 	ON m.id = p.memberId
			 	WHERE m.id = #{id};
			""")
	Member getMemberById(int id);

}