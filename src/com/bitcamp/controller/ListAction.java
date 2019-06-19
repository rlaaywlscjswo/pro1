package com.bitcamp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.dto.BoardDTO;
import com.bitcamp.service.BoardService;

public class ListAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String curr = request.getParameter("currpage");
		int currpage = 1;
		if (curr != null) {
			currpage = Integer.parseInt(curr);
		}
		int pagepercount = 10; // 1페이지당 10개씩 보여주겠다
		BoardService service = BoardService.getInstance();
		int totalcount = service.getCount();
		 int totalpage=(totalcount/pagepercount)+((totalcount%pagepercount==0)? 0:1);
		/* int totalpage = (int) Math.ceil(totalcount / pagepercount); */
		int startrow = (currpage - 1) * pagepercount + 1;
		int endrow = startrow + pagepercount - 1;
		if (endrow > totalcount) {
			endrow = totalcount;
		}
		int blocksize = 5; // 1블록당 5페이지씩만 보여준댱
		int startblock = ((currpage - 1) / blocksize) * blocksize + 1;
		int endblock = startblock + blocksize - 1;
		if (totalpage < endblock) {
			endblock = totalpage;
		}
		List<BoardDTO> list = service.getList(startrow, endrow);
		System.out.println("action복귀");
		request.setAttribute("list", list);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("currpage", currpage);
		request.setAttribute("startblock", startblock);
		request.setAttribute("endblock", endblock);
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/WEB-INF/board/list.jsp");
		System.out.println("list........");
		return forward;
	}

}
