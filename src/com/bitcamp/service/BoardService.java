package com.bitcamp.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.bitcamp.comm.DBConn;
import com.bitcamp.dao.BoardDAO;
import com.bitcamp.dto.BoardDTO;

public class BoardService {
	private static BoardService instance = new BoardService();

	public static BoardService getInstance() {
		return instance;
	}

	private BoardService() {
	}

	public int getCount() {
		DBConn db = DBConn.getdb();
		Connection conn = null;
		int datacount = 0;
		try {
			conn = db.getConnection();
			BoardDAO dao = BoardDAO.getDAO();
			System.out.println("¼­ºñ½º1");
			datacount = dao.getCount(conn);
		} catch (SQLException | NamingException e) {
			System.out.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		}
		return datacount;
	}// getCount

	public List<BoardDTO> getList(int startrow, int endrow) {
		DBConn db = DBConn.getdb();
		Connection conn = null;
		List<BoardDTO> list = null;
		try {
			conn = db.getConnection();
			BoardDAO dao = BoardDAO.getDAO();
			list = dao.getList(conn, startrow, endrow);
		} catch (SQLException | NamingException e) {
			System.out.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		}
		return list;
	}

}
