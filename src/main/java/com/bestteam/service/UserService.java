package com.bestteam.service;

import java.util.List;
import java.util.Set;

import com.bestteam.dto.JoinUserDto;
import com.bestteam.dto.UserListDto;
import com.bestteam.repository.UserRepository;

public class UserService {

	private static final UserService INSTANCE = new UserService();

	private final UserRepository userRepository = UserRepository.getInstance();

	private UserService() {
	}

	public static UserService getInstance() {
		return INSTANCE;
	}

	/**
	 * 유저 회원가입 진행. 가입에 성공하면 true, 실패하면 false를 반환
	 *
	 * @param dto 유저 회원가입 정보
	 * @return 회원 가입 성공 시 true, 실패 시 false를 반환
	 * @author kangdonghee
	 */
	public boolean join(JoinUserDto dto) {
		if (isIdDoubled(dto.getUserId())) return false;

		userRepository.save(dto);
		return true;
	}

	/**
	 * 유저 아이디 중복 체크
	 * 
	 * @param userId 유저 아이디
	 * @return 유저 아이디가 중복이라면 true, 중복이 아니라면 false를 반환
	 * @author kangdonghee
	 */
	private boolean isIdDoubled(String userId) {
		Set<String> userIdSet = userRepository.findIdAll();
		return userIdSet.add(userId) ? false : true;
	}

	/**
	 * @return 모든 유저 정보
	 * @author 이준희
	 */
	public List<UserListDto> findAll() {
		return userRepository.findAll();
	}

	/**
	 * 해당 아이디의 승인 여부에 따라 users DB에서 삭제 혹은, 권한 변경 true -> 가입 여부 true로 변경 false -> 삭제
	 * 
	 * @param userId
	 * @param approval
	 * @author 이준희
	 */
	public void approve(String userId, boolean approval) {
		if (approval) {
			userRepository.approveById(userId);
		} else {
			userRepository.deleteById(userId);
		}
	}

	/**
	 * 해당 아이디의 유저 등급 변경
	 * 
	 * @param userId    유저 아이디
	 * @param userGrade 유저 등급
	 * @author 이준희
	 */
	public void updateUserGrade(String userId, int userGrade) {
		userRepository.updateUserGrade(userId, userGrade);
	}

	/**
	 * 해당 유저의 등급을 반환하는 메서드
	 * 
	 * @param userId	유저 아이디
	 * @return	유저의 등급을 반환
	 * @author kangdonghee
	 */
	public int getGrade(String userId) {
		return userRepository.findGradeById(userId);
	}
}
