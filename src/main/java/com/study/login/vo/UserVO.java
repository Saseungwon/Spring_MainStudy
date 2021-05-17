package com.study.login.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserVO {
	private String userId;
	private String userName;
	private String userPass;
	private String userRole;

	// 생성자
	public UserVO() {

	}

	public UserVO(String userId, String userName, String userPass, String userRole) {

		this.userId = userId;
		this.userName = userName;
		this.userPass = userPass;
		this.userRole = userRole;
	}

	// toString() 구현
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	// 맴버필드의 get/set 메서드 생성

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

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}
