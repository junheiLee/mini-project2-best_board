package com.bestteam.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bestteam.dto.ModifyPostDto;
import com.bestteam.service.PostService;
import com.bestteam.service.ValidationService;

/**
 * 게시물 수정 요청을 처리하는 서블릿
 * 
 * 게시물 수정 페이지 요청을 받으면 doGet 메서드를 실행해 게시물 수정 폼을 보여주고,
 * 게시물 수정 요청을 받으면 doPost 메서드를 실행해 게시물을 수정한 후, 해당 게시물 상세 보기 폼을 보여준다.
 * @author 이준희, kangdonghee
 */
@WebServlet("/modify")
public class PostModifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final PostService postService = PostService.getInstance();
	private final ValidationService validationService = ValidationService.getInstance();
	
	/**
	 * 다음의 순서로 게시물 수정 페이지 요청 처리
	 * 1. 해당 게시글의 해당 유저의 게시글인지 검증
	 * 2. 해당 게시물 ID의 수정 가능 정보를 DB에서 가져온다.
	 * 3. 수정 가능 정보를 request에 담는다.
	 * 4. 수정 폼으로 이동
	 * 
	 * forward: "/post/modifyPost.jsp"
	 * 
	 * @param postId	게시물 아이디
	 * @param title		게시물 제목
	 * @param content	게시물 내용
	 * @param postGrade	게시물 등급(등급 이상만 열람 가능)
	 * 
	 * @author 이준희, kangdonghee
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postId = Integer.parseInt(request.getParameter("postId"));
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		ModifyPostDto currentPostDto = postService.findById(postId);
		
		if (userId == null) {
			response.sendError(401, "접근 권한이 없습니다.");
			return;
		}
		
		if (!validationService.isWriter(userId, currentPostDto.getWriter())) {
			response.sendError(401, "접근 권한이 없습니다.");
			return;
		}
		
		request.setAttribute("postId", postId);
		request.setAttribute("title", currentPostDto.getTitle());
		request.setAttribute("content", currentPostDto.getContent());
		request.setAttribute("postGrade", currentPostDto.getPostGrade());
		request.getRequestDispatcher("/post/modifyPost.jsp").forward(request, response);
	}
	
	/**
	 * 게시물 수정 정보로 posts DB의 해당 게시물 수정
	 * 
	 * @param postId	게시물 아이디
	 * @param title		게시물 제목
	 * @param content	게시물 내용
	 * @param postGrade	게시물 등급(등급 이상만 열람 가능)
	 * @param writer	게시물 작성자
	 * 
	 * @author 이준희, kangdonghee
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		int postId = Integer.parseInt(request.getParameter("postId"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int postGrade = Integer.parseInt(request.getParameter("postGrade"));
		String userId = (String) request.getSession().getAttribute("userId");
		
		ModifyPostDto modifyDto = new ModifyPostDto(postId, title, content, postGrade, userId);
		postService.update(modifyDto);
		
		response.sendRedirect("detail?postId=" + postId);
	}
}
