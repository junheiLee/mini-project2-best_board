package com.bestteam.dto;

public class UserListDto {

	private final String userId;
	private final String userName;
	private final String email;
	private final int userGrade;
	private final boolean approved;

	public UserListDto(String userId, String userName, String email, int userGrade, boolean approved) {
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.userGrade = userGrade;
		this.approved = approved;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public int getUserGrade() {
		return userGrade;
	}

	public boolean isApproved() {
		return approved;
	}
}
