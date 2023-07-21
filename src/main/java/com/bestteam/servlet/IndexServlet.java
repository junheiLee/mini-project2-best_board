package com.bestteam.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bestteam.dto.ListDto;
import com.bestteam.service.PostService;

/**
 * listDto 메서드에 보낼 객체생성
 * 게시판 목록/게시물 총 수를 
 * 얻을 쿼리문 실행하는 서블릿
 * 
 * @param pageNum 페이지 번호
 * @param searchType 검색 종류
 * @param searchText 검색어
 * 
 * @author 김훈호
 */
@WebServlet("/board")
public class IndexServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private PostService postService = PostService.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Call IndexServlet doGet()");
		String pageNum = request.getParameter("pageNum");
		String searchType = request.getParameter("searchCondition");
		String searchText = request.getParameter("searchContent");
		
		if (pageNum == null) {
			pageNum = "1";
		}
		
		if (searchText == null) {
			searchType = "";
			searchText = "";
		}

		ListDto listDto = new ListDto();
		listDto.setPageNum(pageNum);
		listDto.setSearchType(searchType);
		listDto.setSearchText(searchText);

		List<ListDto> postList = postService.selectList(listDto);
		int totalCount = postService.getTotalPage(listDto);
		
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("postList", postList);
		request.setAttribute("searchCondition", searchType);
		request.setAttribute("searchContent", searchText);
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}
