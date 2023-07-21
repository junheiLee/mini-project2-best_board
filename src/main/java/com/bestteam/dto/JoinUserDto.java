package com.bestteam.dto;

public class JoinUserDto {

	private final String userName;
	private final String userId;
	private final String userPw;
	private final String email;
	
	public JoinUserDto(String userName, String userId, String userPw, String email) {
		this.userName = userName;
		this.userId = userId;
		this.userPw = userPw;
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public String getEmail() {
		return email;
	}
}
