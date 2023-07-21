package com.bestteam.service;

import com.bestteam.repository.PostRepository;
import com.bestteam.repository.UserRepository;

public class ValidationService {

	private static final ValidationService INSTANCE = new ValidationService();
	private static final int ADMIN_GRADE = 0;
	private static final int NON_APPROVOED_GRADE = 5;

	private final UserRepository userRepository = UserRepository.getInstance();
	private final PostRepository postRepository = PostRepository.getInstance();

	private ValidationService() {
	}

	public static ValidationService getInstance() {
		return INSTANCE;
	}

	/**
	 * 사용자 등급이 게시글 등급보다 높아 게시글 상세 화면을 볼 수 있는지 확인
	 * 
	 * @param postGrade 게시글 권한 등급
	 * @param userGrade 사용자 등급
	 * @return boolean 사용자 등급이 게시글을 등급보다 높거나 같으면 true
	 * @author 이준희
	 */
	public boolean canOpen(int postGrade, int userGrade) {
		return userGrade <= postGrade;
	}

	/**
	 * 사용자 등급을 확인해 admin 계정인지 확인
	 * 
	 * @param userGrade 사용자 등급
	 * @return boolean 사용자 등급이 0, admin 계정이면 true
	 * @author 이준희
	 */
	public boolean isAdmin(int userGrade) {
		return userGrade == ADMIN_GRADE;
	}
	
	public boolean isAdmin(String userId) {
		return userRepository.findGradeById(userId) == ADMIN_GRADE;
	}

	/**
	 * 사용자 아이디 null 체크
	 * 사용자 등급이 관리자 등급인지 체크
	 * 
	 * @param userId	사용자 아이디
	 * @param userGrade 사용자 등급
	 * @return 사용자가 관리자라면 true, 사용자가 회원이 아니거나 관리자가 아니라면 false를 반환
	 * @author kangdonghee
	 */
	public boolean isAdminNullCheck(String userId, int userGrade) {
		return !(userId == null) && isAdmin(userGrade);
	}

	/**
	 * 사용자 아이디와 게시글 작성자인지 확인
	 * 
	 * @param userId     사용자 아이디
	 * @param postWirter 게시글 작성 아이디
	 * @return boolean 사용자가 게시글 작성을 했으면 true
	 * @author 이준희
	 */
	public boolean isWriter(String userId, String postWirter) {
		return userId.equals(postWirter);
	}
	
	public boolean isWriter(String userId, int postId) {
		return userId.equals(postRepository.findWriterById(postId));
	}

	/**
	 * 로그인 정보가 일치하는지 확인하는 메서드
	 * 
	 * @param userId	사용자 아이디
	 * @param userPw	사용자 비밀번호
	 * @return 로그인 정보가 기저장된 정보이면 true, 아니면 false를 반환
	 * @author kangdonghee
	 */
	public boolean login(String userId, String userPw) {
		if (userRepository.isNotExist(userId)) {
			System.out.println("없는 아이디");
			return false;
		}
		
		if (isWrongPw(userId, userPw)) {
			System.out.println("비밀번호 불일치");
			return false;
		}
		
		if (isNonApprovedUser(userId)) {
			System.out.println("승인되지 않은 유저");
			return false;
		}
		
		return true;
	}
	
	private boolean isNonApprovedUser(String userId) {
		return userRepository.findGradeById(userId) == NON_APPROVOED_GRADE;
	}

	private boolean isWrongPw(String userId, String userPw) {
		String findPw = userRepository.findPwById(userId);
		return !findPw.equals(userPw);
	}

	public boolean canDeletePost(String userId, int postId) {
		if (userId == null) return false;
		return isAdmin(userId) || isWriter(userId, postId);
	}
}
