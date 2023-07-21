package com.bestteam.dto;

public class ModifyPostDto {

	private final int postId;
	private final String title;
	private final String content;
	private final int postGrade;
	private final String writer;
	
	public ModifyPostDto(int postId, String title, String content, int postGrade, String writer) {
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.postGrade = postGrade;
		this.writer = writer;
	}

	public int getPostId() {
		return postId;
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
	
	public String getWriter() {
		return writer;
	}

	@Override
	public String toString() {
		return "ModifyPostDto [postId=" + postId + ", title=" + title + ", content=" + content + ", postGrade="
				+ postGrade + ", writer=" + writer + "]";
	}
}
