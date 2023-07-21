package com.bestteam.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bestteam.service.PostService;
import com.bestteam.service.ValidationService;

/**
 * 게시글 삭제 처리하는 서블릿
 * 
 * 관리자 아니면 해당 유저한테 게시글 삭제 요청을 받으면 doGet 메서드를 실행하여 게시글을 삭제한다
 *  
 * doGet 메서드 실행 완료시 ("best_board?pageNum=1") 로 redirect 시킨다
 * 
 * @author 명원식
 *
 */
@WebServlet("/delete")
public class PostDeleteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private final PostService postservice = PostService.getInstance();
	private final ValidationService validationService = ValidationService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int postId = Integer.parseInt(request.getParameter("postId"));
    	String userId = (String) request.getSession().getAttribute("userId");
    	
    	if (userId == null) {
			response.sendError(401, "접근 권한이 없습니다.");
			return;
		}
		
		if (!validationService.canDeletePost(userId, postId)) {
			response.sendError(401, "접근 권한이 없습니다.");
			return;
		}
    		
    	postservice.delete(postId);
    	
		response.sendRedirect("board?pageNum=1");
	}
}
