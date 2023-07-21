package com.bestteam.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bestteam.dto.PostDetailDto;
import com.bestteam.service.ValidationService;
import com.bestteam.service.PostService;

/**
 * 게시물 상세 페이지 요청을 처리
 * 
 * 게시물 상세 페이지 요청을 받으면 doGet 메서드를 실행해 열람 가능한지 검증 후 , 해당 페이지를 보여준다. 1. 로그인이 되지 않은
 * 사용자: 권람 불가 페이지 2. 로그인 된 사용자의 등급이 게시글 등급보다 높을 때: 조회 수 증가 2-a. 작성자일 때: 수정, 삭제
 * 버튼이 있는 페이지 forward:"/user/detailPost.jsp" 2-b. admin 계정일 때: 삭제 버튼이 있는 페이지
 * forward:"/admin/detailPost.jsp" 2-c. 그 외 열람 가능 사용자: 수정, 삭제 불가 페이지
 * forward:"/detailPost.jsp" 3. 사용자 등급이 게시글 등급보다 낮을 때: 열람 불가 페이지 forward:
 * "/noPermission.jsp"
 * 
 * @author 이준희
 */
@WebServlet("/detail")
public class PostDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final PostService postService = PostService.getInstance();
	private final ValidationService validationService = ValidationService.getInstance();

	/**
	 * 열람한 등급을 확인 후 게시물 상세 페이지로 이동
	 * 
	 * 1. session이 null인지 확인
	 * 2. parameter로 넘어온 postId로 게시물 로드
	 * 3. 검증 후 해당 권한의 상세 페이지로 이동
	 * 
	 * @author 이준희
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("userGrade") == null) {
			response.sendError(401, "접근 권한이 없습니다.");
		} else {
			int postId = Integer.parseInt(request.getParameter("postId")); // 해당 게시글의 아이디
			PostDetailDto postDetailDto = postService.viewByPostId(postId);
			
			setPostDetailDto(request, postDetailDto);
			checkGradeIsOver(request, response, postDetailDto);
		}
	}

	private void setPostDetailDto(HttpServletRequest request, PostDetailDto postDetailDto) {
		request.setAttribute("postId", postDetailDto.getPostId());
		request.setAttribute("title", postDetailDto.getTitle());
		request.setAttribute("writer", postDetailDto.getWriter());
		request.setAttribute("content", postDetailDto.getContent());
		request.setAttribute("postGrade", postDetailDto.getPostGrade());
		request.setAttribute("hit", postDetailDto.getHit());
		request.setAttribute("modDate", postDetailDto.getModDate());
	}

	private void checkGradeIsOver(HttpServletRequest request, HttpServletResponse response, PostDetailDto postDetailDto) throws ServletException, IOException {
		int userGrade = (int) request.getSession().getAttribute("userGrade"); // 현재 열람을 시도하고 있는 사용자의 등급

		if (validationService.canOpen(postDetailDto.getPostGrade(), userGrade)) {
			postService.addHit(postDetailDto.getPostId());
			checkGradeEtc(request, response, postDetailDto);
		} else {
			response.sendError(401, "접근 권한이 없습니다.");
		}
	}

	private void checkGradeEtc(HttpServletRequest request, HttpServletResponse response, PostDetailDto postDetailDto) throws ServletException, IOException {
		String userId = (String) request.getSession().getAttribute("userId"); // 현재 열람을 시도하고 있는 사용자의 아이디
		int userGrade = (int) request.getSession().getAttribute("userGrade"); // 현재 열람을 시도하고 있는 사용자의 등급

		if (validationService.isWriter(userId, postDetailDto.getWriter())) {
			request.getRequestDispatcher("/user/writerDetailPost.jsp").forward(request, response);
		} else if (validationService.isAdmin(userGrade)) {
			request.getRequestDispatcher("/admin/detailPost.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/user/detailPost.jsp").forward(request, response);
		}
	}
}
