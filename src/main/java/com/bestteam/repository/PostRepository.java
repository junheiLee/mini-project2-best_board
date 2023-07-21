package com.bestteam.repository;

import static com.bestteam.repository.DBConnectionUtil.close;
import static com.bestteam.repository.DBConnectionUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bestteam.dto.ListDto;
import com.bestteam.dto.ModifyPostDto;
import com.bestteam.dto.PostDetailDto;
import com.bestteam.dto.WritePostDto;

public class PostRepository {

	private static final PostRepository INSTANCE = new PostRepository();

	private PostRepository() {
	}

	public static PostRepository getInstance() {
		return INSTANCE;
	}

	/**
	 * 게시글 저장 등록일, 수정일도 함께 저장
	 * 
	 * @param dto 작성 게시글 DTO(userId, title, content, postGrade)
	 * @author kangdonghee
	 */
	public void save(WritePostDto dto) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;

		try {
			String query = "insert into posts (title, writer, content, post_grade, reg_date, mod_date) values (?, ?, ?, ?, now(), now())";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getPostGrade());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, null);
		}
	}

	/**
	 * 게시글 ID를 받아 수정 폼에 필요한 해당 게시글 정보 반환
	 * 
	 * @param postId 게시글 번호
	 * @return MofidyPostDto 수정 폼에 필요한 해당 게시글 정보 반환
	 * 
	 * @author 이준희
	 */
	public ModifyPostDto findById(int postId) {
		ModifyPostDto currentDto = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select title, content, post_grade, writer from posts where post_id=?");
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				currentDto = new ModifyPostDto(postId, rs.getString("title"), rs.getString("content"),
						rs.getInt("post_grade"), rs.getString("writer"));
			}

		} catch (SQLException e) {
			System.out.println("PostRepository.findById() Error-> " + e.getMessage());
		} finally {
			close(conn, pstmt, rs);
		}

		return currentDto;
	}

	/**
	 * 게시글 수정 정보를 받아 posts DB에 업데이트
	 * 
	 * @param modifyDto 게시글 수정 정보
	 * 
	 * @author 이준희
	 */
	public void update(ModifyPostDto modifyDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(
					"update posts set title = ?, content = ?, post_grade = ?, mod_date = now() where post_id=?");
			pstmt.setString(1, modifyDto.getTitle());
			pstmt.setString(2, modifyDto.getContent());
			pstmt.setInt(3, modifyDto.getPostGrade());
			pstmt.setInt(4, modifyDto.getPostId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("PostRepository.update() Error-> " + e.getMessage());
		} finally {
			close(conn, pstmt, null);
		}
	}

	/**
	 * DB에 저장된 각 게시글 불러오기
	 * 
	 * @param postId
	 * @return 게시글 조회
	 * @author 명원식
	 */
	public PostDetailDto viewByPostId(int postId) {
		Connection conn = null;
		PostDetailDto view = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select * from posts where post_id = ?");
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				view = new PostDetailDto(rs.getInt("post_id"), rs.getString("title"), rs.getString("writer"),
						rs.getString("content"), rs.getInt("post_grade"), rs.getDate("mod_date").toString(),
						rs.getInt("hit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}

		return view;
	}

	/**
	 * DB에 저장된 각 게시글의 조회수를 1회씩 상승시키기
	 * 
	 * @param postId
	 * @author 명원식
	 */
	public void addHit(int postId) {
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("update posts set hit = hit + 1 where post_id = ?");
			pstmt.setInt(1, postId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, null);
		}
	}

	/**
	 * 게시글 id를 받아 게시글 설정 권한 받아오는 기능
	 * 
	 * @param postId 게시글 아이디
	 * @return postGrade 게시글 권한등급
	 * @author 이준희
	 */
	public int findGradeById(int postId) {
		int postGrade = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select post_grade from posts where post_id=?");
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				postGrade = rs.getInt("post_grade");
			}

		} catch (SQLException e) {
			System.out.println("PostRepository.findGradeById() Error-> " + e.getMessage());
		} finally {
			close(conn, pstmt, null);
		}

		return postGrade;
	}

	/**
	 * 게시글 id를 받아 게시글 삭제하는 기능
	 * 
	 * @param postId 게시글의 ID
	 * @author 명원식
	 */
	public void delete(int postId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("delete from posts where post_id=?");
			pstmt.setInt(1, postId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("PostRepository.delete() Error-> " + e.getMessage());
		} finally {
			close(conn, pstmt, null);
		}
	}

	/**
	 * 게시판 목록 조회 검색어 쿼리문
	 * 
	 * @param pageNum    페이지 번호
	 * @param listCount  페이지 게시물 노출 수
	 * @param searchType 검색 종류
	 * @param searchText 검색어
	 * 
	 * @author 김훈호
	 * @return 게시글 목록을 반환
	 */
	public List<ListDto> selectList(ListDto ListDto) {
		int pageNum = Integer.parseInt(ListDto.getPageNum());
		int listCount = ListDto.getListCount(); // 게시물 갯수
		String searchType = ListDto.getSearchType(); // 검색 종류
		String searchText = ListDto.getSearchText(); // 검색어
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();

		String whereSQL = "";
		List<ListDto> postList = new ArrayList<ListDto>();

		// 검색어 쿼리문
		if (!"".equals(searchText)) {
			if ("ALL".equals(searchType)) {
				whereSQL = "where title like concat('%', ?, '%') or content like concat('%', ?, '%') or writer like concat('%', ?, '%')";
			} else if ("TITLE".equals(searchType)) {
				whereSQL = "where title like concat('%', ?, '%')";
			} else if ("WRITER".equals(searchType)) {
				whereSQL = "where writer like concat('%', ?, '%')";
			} else if ("CONTENT".equals(searchType)) {
				whereSQL = "where content like concat('%', ?, '%')";
			}
		}
		
		String first = "select post_id, title, writer, reg_date, hit from posts ";
		String end = " order by post_id desc limit ?, ?";
		String query = first + whereSQL + end;
		
		try {
			// 목록 조회
			pstmt = conn.prepareStatement(query);

			// 검색 쿼리 있을 경우
			if (!"".equals(whereSQL)) {
				if ("ALL".equals(searchType)) {
					pstmt.setString(1, searchText);
					pstmt.setString(2, searchText);
					pstmt.setString(3, searchText);
					pstmt.setInt(4, listCount * (pageNum - 1));
					pstmt.setInt(5, listCount);
				} else {
					pstmt.setString(1, searchText);
					pstmt.setInt(2, listCount * (pageNum - 1));
					pstmt.setInt(3, listCount);
				}
			} else {
				pstmt.setInt(1, listCount * (pageNum - 1));
				pstmt.setInt(2, listCount);
			}

			// 조회
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ListDto = new ListDto();
				ListDto.setPostId(rs.getInt("post_id"));
				ListDto.setTitle(rs.getString("title"));
				ListDto.setWriter(rs.getString("writer"));
				ListDto.setHit(rs.getInt("hit"));
				ListDto.setRegDate(rs.getString("reg_date"));

				postList.add(ListDto);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			close(conn, pstmt, rs);
		}
		
		return postList;
	}

	/**
	 * 게시글 총 갯수 조회 쿼리문
	 * 
	 * @param totalCount 전체 조회
	 * 
	 * @author 김훈호
	 * @return 게시글 총 갯수를 반환
	 */
	public int selectCount(ListDto ListDto) {
		int totalCount = 0; // 전체조회, 검색어 조회시 총 데이터 저장
		String searchType = ListDto.getSearchType();
		String searchText = ListDto.getSearchText();
		String whereSQL = ""; // select 쿼리 조건 부분만 저장

		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 검색어 쿼리문
			if (!"".equals(searchText)) { // 검색어 있을 경우에만
				if ("ALL".equals(searchType)) { // 전체 검색일 경우
					whereSQL = "where title like concat('%', ?, '%') or content like concat('%', ?, '%') or writer like concat('%', ?, '%')";
				} else if ("TITLE".equals(searchType)) {
					whereSQL = "where title like concat('%', ?, '%')";
				} else if ("WRITER".equals(searchType)) {
					whereSQL = "where writer like concat('%', ?, '%')";
				} else if ("CONTENT".equals(searchType)) {
					whereSQL = "where content like concat('%', ?, '%')";
				}
			}

			// 게시물이 총 갯수를 얻는 쿼리 실행
			String query = "select count(*) as total from posts ";
			pstmt = conn.prepareStatement(query + whereSQL);

			if (!"".equals(whereSQL)) { // 검색어 있을 경우
				if ("ALL".equals(searchType)) { // 전체 검색일 경우
					pstmt.setString(1, searchText);
					pstmt.setString(2, searchText);
					pstmt.setString(3, searchText);
				} else {
					pstmt.setString(1, searchText);
				}
			}

			// 쿼리문 실행
			rs = pstmt.executeQuery();

			if (rs.next()) {
				totalCount = rs.getInt("total");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return totalCount;
	}

	public String findWriterById(int postId) {
		String writer = "";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select writer from posts where post_id=?");
			pstmt.setInt(1, postId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				writer = rs.getString("writer");
			}

		} catch (SQLException e) {
			System.out.println("PostRepository.findById() Error-> " + e.getMessage());
		} finally {
			close(conn, pstmt, rs);
		}

		return writer;

	}
}
