package com.ds.bbs.model.member.dto;

import java.util.Date;

public class MemberDTO {
	private String userId;
	private String userName;
	private String userNikname;
	private String userPwd;
	private int verify;
	private Date joinDate;
	
	public MemberDTO() {}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserNikname() {
		return userNikname;
	}

	public void setUserNikname(String userNikname) {
		this.userNikname = userNikname;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	public int getVerify() {
		return verify;
	}

	public void setVerify(int verify) {
		this.verify = verify;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	@Override
	public String toString() {
		return "MemberDTO [userId=" + userId + ", userName=" + userName + ", userNikname=" + userNikname + ", userPwd="
				+ userPwd + ", verify=" + verify + ", joinDate=" + joinDate + "]";
	}
}
