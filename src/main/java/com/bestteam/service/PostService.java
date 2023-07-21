package com.bestteam.service;

import java.util.List;

import com.bestteam.dto.ListDto;
import com.bestteam.dto.ModifyPostDto;
import com.bestteam.dto.PostDetailDto;
import com.bestteam.dto.WritePostDto;
import com.bestteam.repository.PostRepository;

public class PostService {

	private static final PostService INSTANCE = new PostService();

	private final PostRepository postRepository = PostRepository.getInstance();

	private PostService() {
	}

	public static PostService getInstance() {
		return INSTANCE;
	}

	/**
	 * 게시글 저장 등록일, 수정일도 함께 저장
	 * 
	 * @param writePostDto 작성 게시글 DTO(userId, title, content, postGrade)
	 * @author kangdonghee
	 */
	public void save(WritePostDto writePostDto) {
		postRepository.save(writePostDto);
	}

	/**
	 * 게시글 아이디를 받아 해당 게시글의 정보를 수정 폼에서 활용 가능하도록 반환
	 * 
	 * @param postId 게시글 아이디
	 * @return ModifyPostDto 수정할 게시글의 수정 전 정보
	 */
	public ModifyPostDto findById(int postId) {
		return postRepository.findById(postId);
	}

	/**
	 * 수정 게시글 정보를 받아 해당 게시글 정보 수정
	 * 
	 * @param ModifyPostDto 수정할 게시글의 수정 전 정보
	 */
	public void update(ModifyPostDto modifyDto) {
		postRepository.update(modifyDto);
	}

	/**
	 * 해당 게시글을 조회한다
	 * 
	 * @param postId
	 * @return 게시글 조회
	 * @author 명원식
	 */
	public PostDetailDto viewByPostId(int postId) {
		return postRepository.viewByPostId(postId);
	}

	/**
	 * 해당 게시글의 조회수를 상승시킨다
	 * 
	 * @param postId
	 * @return 게시글 조회
	 * @author 명원식
	 */
	public void addHit(int postId) {
		postRepository.addHit(postId);
	}

	/**
	 * 유저의 세션과 게시글의 작성자를 비교
	 * 
	 * @param userId 유저의 세션 아이디
	 * @param writer 게시글의 작성자
	 * @return 두 값이 일치하면 false, 일치하지 않으면 true를 반환
	 * @author kangdonghee
	 */
	public boolean isBadUser(String userId, String writer) {
		return !userId.equals(writer);
	}
	
	/**
	 * 해당 게시글을 삭제시킨다
	 * 
	 * @param postId 게시글의 ID
	 * @author 명원식
	 */
	public void delete(int postId) {
		postRepository.delete(postId);
	}
	
	/**
	 * 게시판 목록 조회 기능
	 * 
	 * @author 김훈호
	 * 
	 * @param listDto 
	 * 
	 * @return 게시글 목록을 반환
	 */
	public List<ListDto> selectList(ListDto listDto) {
		return postRepository.selectList(listDto);
	}
	
	/**
	 * 게시클 총 갯수 조회 기능
	 * 
	 * @author 김훈호
	 * 
	 * @return 게시글 갯수를 반환
	 */
	public int selectCount(ListDto listDto) {
		return postRepository.selectCount(listDto);
	}

	public int getTotalPage(ListDto listDto) {
		return (selectCount(listDto) - 1) / 10 + 1;
	}
}
