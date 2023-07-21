package com.bestteam.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bestteam.dto.WritePostDto;
import com.bestteam.service.PostService;

/**
 * 게시글 작성 요청을 처리하는 서블릿
 * 
 * 게시글 작성 페이지를 요청 받으면 doGet 메서드를 실행하여 게시글 작성 폼을 보여주고,
 * 게시글 작성 요청을 받으면 doPost 메서드를 실행하여 게시글을 DB에 저장한 후 처음 페이지로 돌아간다.
 * 
 * @author kangdonghee
 */
@WebServlet("/write")
public class WritePostServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private final PostService postService = PostService.getInstance();

	/**
	 * 게시글 작성 폼으로 이동
	 * 작성자 세션 체크, 로그인 되어 있지 않으면 401 오류 페이지로 보냄
	 * forward: "writePost.jsp"
	 * 
	 * @author kangdonghee
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Call WritePostServlet doGet()");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		System.out.println(userId);

		if (userId == null) {
			System.out.println("왜 여기로 안 오냐고");
			response.sendError(401, "접근 권한이 없습니다.");
			return;
		}
		
		int userGrade = (int) session.getAttribute("userGrade");
		
		request.setAttribute("userId", userId);
		request.setAttribute("userGrade", userGrade);
		request.getRequestDispatcher("post/writePost.jsp").forward(request, response);
	}

	/**
	 * 게시글을 DB에 저장 후 처음 페이지로 이동
	 * redirect: "board/pageNum=1"
	 * 
	 * @author kangdonghee
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Call WritePostServlet doPost()");
		request.setCharacterEncoding("UTF-8");
		
		String userId = (String) request.getSession().getAttribute("userId");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int postGrade = Integer.parseInt(request.getParameter("postGrade"));
		WritePostDto dto = new WritePostDto(userId, title, content, postGrade);
		
		postService.save(dto);
		
		response.sendRedirect("board?pageNum=1");
	}
}
