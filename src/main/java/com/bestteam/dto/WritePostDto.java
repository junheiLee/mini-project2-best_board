package com.bestteam.dto;

public class WritePostDto {

	private final String userId;
	private final String title;
	private final String content;
	private final int postGrade;
	
	public WritePostDto(String userId, String title, String content, int postGrade) {
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.postGrade = postGrade;
	}

	public String getUserId() {
		return userId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
	public int getPostGrade() {
		return postGrade;
	}
}
