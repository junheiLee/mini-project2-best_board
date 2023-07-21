package com.bestteam.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bestteam.service.UserService;
import com.bestteam.service.ValidationService;

/**
 * 로그인 버튼을 클릭한 뒤 등급을 확인해서
 * 관리자/유저 페이지로 보내는 서블릿
 * 
 * @param userId 유저 아이디
 * @param userPw 유저 비밀번호
 * @param userGrade 유저 등급
 * 
 * @author 김훈호
 */
@WebServlet("/logCheck")
public class LogCheckServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private final ValidationService validationService = ValidationService.getInstance();
	private final UserService userService = UserService.getInstance();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) request.getParameter("userId");
		String userPw = (String) request.getParameter("userPw");
		
		if (validationService.login(userId, userPw)) {
			System.out.println("로그인 성공!");
			session.setAttribute("userId", userId);
			session.setAttribute("userGrade", userService.getGrade(userId));
			response.sendRedirect("board");
		} else {
			// todo 팝업창
			response.sendRedirect("board");
		}
	}
}
