package com.bestteam.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bestteam.dto.JoinUserDto;
import com.bestteam.service.UserService;

/**
 * 회원 가입 요청을 처리하는 서블릿
 * 
 * 회원 가입 페이지를 요청 받으면 doGet 메서드를 실행하여 회원 가입 폼을 보여주고,
 * 회원 가입 요청을 받으면 doPost 메서드를 실행하여 회원 가입을 진행한 후 처음 페이지로 돌아간다.
 * 
 * @author kangdonghee
 */
@WebServlet("/join")
public class JoinUserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private final UserService userService = UserService.getInstance();
	
	/**
	 * 회원 가입 폼으로 이동
	 * forward: "register.jsp"
	 * 
	 * @author kangdonghee
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Call JoinMemberServlet doGet()");
		request.setAttribute("doubled",	false);
		request.getRequestDispatcher("user/join.jsp").forward(request, response);
	}

	/**
     * 다음의 순서로 회원 가입 요청을 처리함
     * <p>
     * 1. 아이디 중복 검사(중복 시 사용자에게 알림 출력)
     * <p>
     * 2. 유저의 정보를 users DB에 저장
     * <p>
     * 3. 처음 페이지로 이동
     * 
     * if login is successful, redirect: "board?pageNum=1",
     * otherwise, forward: "register.jsp"
     * 
     * @author kangdonghee
     */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Call JoinMemberServlet doPost()");
		request.setCharacterEncoding("UTF-8");
		
		String userName = request.getParameter("userName");
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String email = request.getParameter("email");
		JoinUserDto dto = new JoinUserDto(userName, userId, userPw, email);
		
		if (userService.join(dto)) {
			System.out.println("join success");
			response.sendRedirect("board?pageNum=1");
		} else {
			System.out.println("join fail");
			setAttributesIfUserIdDoubled(request, dto);
			request.setAttribute("doubled", true);
			request.getRequestDispatcher("user/join.jsp").forward(request, response);
		}
	}
	
	private void setAttributesIfUserIdDoubled(HttpServletRequest request, JoinUserDto dto) {
		request.setAttribute("userName", dto.getUserName());
		request.setAttribute("userId", dto.getUserId());
		request.setAttribute("userPw", dto.getUserPw());
		request.setAttribute("email", dto.getEmail());
	}
}