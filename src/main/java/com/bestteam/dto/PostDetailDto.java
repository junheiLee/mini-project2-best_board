package com.bestteam.dto;

public class PostDetailDto {

	private final int postId;
	private final String title;
	private final String writer;
	private final String content;
	private final int postGrade;
	private final String modDate;
	private final int hit;
		
	public PostDetailDto(int postId, String title, String writer, String content, int postGrade, String modDate, int hit) {
		this.postId = postId;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.postGrade = postGrade;
		this.modDate = modDate;
		this.hit = hit;
	}

	public int getPostId() {
		return postId;
	}

	public String getTitle() {
		return title;
	}

	public String getWriter() {
		return writer;
	}

	public String getContent() {
		return content;
	}

	public int getPostGrade() {
		return postGrade;
	}

	public String getModDate() {
		return modDate;
	}

	public int getHit() {
		return hit;
	}

}
