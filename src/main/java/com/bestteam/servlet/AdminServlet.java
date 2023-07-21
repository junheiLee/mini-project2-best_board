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
 * 회원 관리 페이지 요청 처리 서블릿
 * 
 * session으로 userId 등급 확인 후 admin 계정이 맞으면, 회원 관리 페이지 이동 요청 시, doGet 메서드를 실행해 회원
 * 관리 폼 보여줌
 * 
 * 일반 사용자는 401 Error
 * 
 * @author 이준희
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private final UserService userService = UserService.getInstance();
	private final ValidationService validateionService = ValidationService.getInstance();

	/**
	 * session의 userId가 null이거나, 관리 등급이 아니면 401 Error
	 * 
	 * session으로 userId 등급 확인 후, 회원 관리 페이지로 이동 forward: "admin/manage.jsp"
	 * 
	 * @author 이준희
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");

		if (userId == null) {
			response.sendError(401, "접근 권한이 없습니다.");
			return;
		}
		
		if (validateionService.isAdmin(userId)) {
			request.setAttribute("userList", userService.findAll());
			request.getRequestDispatcher("admin/manage.jsp").forward(request, response);
		} else {
			response.sendError(401, "접근 권한이 없습니다.");
		}
	}
}