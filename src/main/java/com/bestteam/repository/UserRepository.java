package com.bestteam.repository;

import static com.bestteam.repository.DBConnectionUtil.close;
import static com.bestteam.repository.DBConnectionUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bestteam.dto.JoinUserDto;
import com.bestteam.dto.UserListDto;

public class UserRepository {

	private static final UserRepository INSTANCE = new UserRepository();

	private UserRepository() {
	}

	public static UserRepository getInstance() {
		return INSTANCE;
	}

	/**
	 * DB에 저장된 모든 유저(승인 요청 대기인 유저 포함)들의 아이디를 반환
	 * 
	 * @return 모든 유저들의 아이디를 반환
	 * @author kangdonghee
	 */
	public Set<String> findIdAll() {
		Set<String> result = new HashSet<>();

		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String query = "select user_id from users";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				result.add(rs.getString("user_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, stmt, rs);
		}

		return result;
	}

	/**
	 * 유저의 정보를 DB에 저장. 유저의 등급은 4, 승인 여부는 false로 저장된다.
	 * 
	 * @param dto 회원 가입 정보
	 * @author kangdonghee
	 */
	public void save(JoinUserDto dto) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;

		try {
			String query = "insert into users (user_id, user_name, user_pw, email, join_date) values (?, ?, ?, ?, now())";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserName());
			pstmt.setString(3, dto.getUserPw());
			pstmt.setString(4, dto.getEmail());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, null);
		}
	}

	/**
	 * users DB에 저장된 회원 관리에 필요한 모든 유저(승인 요청 대기인 유저 포함) 정보 반환
	 * 
	 * @return 모든 유저 정보
	 * 
	 * @author 이준희
	 */
	public List<UserListDto> findAll() {
		List<UserListDto> userList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(
					"select user_id, user_name, email, user_grade, approved from users where user_grade != 0 order by join_date desc");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				userList.add(new UserListDto(rs.getString("user_id"), rs.getString("user_name"), rs.getString("email"),
						rs.getInt("user_grade"), rs.getBoolean("approved")));
			}

		} catch (SQLException e) {
			System.out.println("UserRepository.findAll() Error-> " + e.getMessage());
		} finally {
			close(conn, pstmt, rs);
		}
		return userList;
	}

	/**
	 * 회원 아이디를 받아 users DB 에서 해당 회원 승인 여부를 update(가입 성공)
	 * 
	 * @param userId 사용자 아이디
	 * 
	 * @author 이준희
	 */
	public void approveById(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("update users set approved=1, user_grade=4 where user_id=?");
			pstmt.setString(1, userId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("UserRepository.approveById() Error-> " + e.getMessage());
		} finally {
			close(conn, pstmt, null);
		}
	}

	/**
	 * 회원 아이디를 받아 users DB 에서 해당 회원 삭제(가입 실패, 회원 퇴출)
	 * 
	 * @param userId 사용자 아이디
	 * 
	 * @author 이준희
	 */
	public void deleteById(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("delete from users where user_id=?");
			pstmt.setString(1, userId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("UserRepository.approveById() Error-> " + e.getMessage());
		} finally {
			close(conn, pstmt, null);
		}
	}

	/**
	 * 회원 아이디와 수정 등급을 받아 users DB 에서 해당 회원 등급 수정
	 * 
	 * @param userId    사용자 아이디
	 * @param userGrade 사용자 등급
	 * 
	 * @author 이준희
	 */
	public void updateUserGrade(String userId, int userGrade) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("update users set	user_grade=? where user_id=?");
			pstmt.setInt(1, userGrade);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("UserRepository.updateUserGrade() Error-> " + e.getMessage());
		} finally {
			close(conn, pstmt, null);
		}
	}

	/**
	 * 사용자 id를 받아 사용자 등급 받아오는 기능
	 * 
	 * @param userId 사용자 아이디
	 * @return userGrade 사용자 등급
	 * @author 이준희
	 */
	public int findGradeById(String userId) {
		int userGrade = -1;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select user_grade from users where user_id=?");
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				userGrade = rs.getInt("user_grade");
			}
		} catch (SQLException e) {
			System.out.println("UserRepository.findGradeById() Error-> " + e.getMessage());
		} finally {
			close(conn, pstmt, null);
		}

		return userGrade;
	}

	/**
	 * 유저 아이디가 DB에 저장되어 있는지 확인하는 메서드
	 * 
	 * @param userId 유저 아이디
	 * @return 유저 아이디가 DB에 저장되어 있으면 true, 저장되어 있지 않으면 false를 반환
	 * @author kangdonghee
	 */
	public boolean isNotExist(String userId) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement("select user_name from users where user_id = ?");
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			return !rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		
		return true;
	}

	/**
	 * 유저 아이디에 해당하는 비밀번호를 반환하는 메서드
	 * 
	 * @param userId	유저 아이디
	 * @return	유저 아이디에 해당하는 비밀번호를 반환
	 * @author kangdonghee
	 */
	public String findPwById(String userId) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement("select user_pw from users where user_id = ?");
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return rs.getString("user_pw");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		
		return "";
	}
}
