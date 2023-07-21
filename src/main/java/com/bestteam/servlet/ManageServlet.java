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
 * 회원 관리 서블릿 - 가입 승인, 회원 퇴출 및 가입 거절, 등급 수정 등 회원 관리 담당 session으로 userId 등급 확인 후
 * admin 계정이 맞으면 해당 로직 실행 admin 계정이 아니면, 401 Error
 * 
 * 회원 삭제, 퇴출(approval=false) 요청 받으면 doGet 메서드를 실행해 회원 삭제 후, 회원 관리 페이지로 redirect
 * 가입 승인(approval=true) 요청 받으면 doGet 메서드를 실행해 회원 가입 여부를 수정 후, 회원 관리 페이지로 redirect
 * 회원 등급 수정 요청 받으면 doPost 실행 후 회원 관리 페이지로 redirect
 * 
 * @author 이준희
 */
@WebServlet("/manage")
public class ManageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private final UserService userService = UserService.getInstance();
	private final ValidationService validationService = ValidationService.getInstance();

	/**
	 * session의 userId가 null이거나, 관리 등급이 아니면 401 Error
	 *
	 * session으로 userId 등급 확인 후,
	 * 1. 가입 승인 여부 확인
	 * 2_1. 승인 - 유저 approved(가입) DB에 update
	 * 2_2. 거절 - 유저 DB에서 삭제 3. 회원 관리 페이지 보여주기
	 * 
	 * @param userId   유저 아이디
	 * @param approval 가입 승인 여부(승인: true/거절, 퇴출: false)
	 * 
	 * @author 이준희
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String adminId = (String) session.getAttribute("userId");
		
		if (adminId == null) {
			response.sendError(401, "접근 권한이 없습니다.");
			return;
		}
		
		if (validationService.isAdmin(adminId)) {
			String userId = (String) request.getParameter("userId");
			boolean approval = Boolean.parseBoolean(request.getParameter("approval"));
			userService.approve(userId, approval);
			response.sendRedirect("admin");
		} else {
			response.sendError(401, "접근 권한이 없습니다.");
		}
	}
	
	/**
	 * session의 userId가 null이거나, 관리 등급이 아니면 401 Error
	 *
	 * session으로 userId 등급 확인 후,
	 * 1. 유저 등급 정보 DB에서 수정
	 * 2. 회원 관리 페이지 보여주기
	 * 
	 * @param userId   유저 아이디
	 * @param approval 가입 승인 여부(거절, 퇴출: false)
	 * 
	 * @author 이준희
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String adminId = (String) session.getAttribute("userId");
		int adminGrade = (int) session.getAttribute("userGrade");

		if (validationService.isAdminNullCheck(adminId, adminGrade)) {
			String userId = (String) request.getParameter("userId");
			int userGrade = Integer.parseInt(request.getParameter("userGrade"));
			userService.updateUserGrade(userId, userGrade);
			response.sendRedirect("admin");
		} else {
			response.sendError(401, "접근 권한이 없습니다.");
		}
	}
}