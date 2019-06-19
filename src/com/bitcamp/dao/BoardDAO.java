package com.bitcamp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bitcamp.dto.BoardDTO;

public class BoardDAO {
//table이름 myomyo임!!
	private static BoardDAO dao = new BoardDAO();

	public static BoardDAO getDAO() {
		return dao;
	}

	private BoardDAO() {
	}

	private void rsClose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}

	// 전체자료수를 얻어오는 메소드
	public int getCount(Connection conn) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(*)       ");
		sql.append("   from  myomyo        ");
		ResultSet rs = null;
		int datacount = 0;
		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			rs = pstmt.executeQuery();
			if (rs.next()) {
				datacount = rs.getInt(1);
			}
		} finally {
			rsClose(rs);
		}
		System.out.println("totalcount = " + datacount);
		return datacount;
	}// getCount

	public List<BoardDTO> getList(Connection conn, int startrow, int endrow) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" select    employee_id             ");
		sql.append(" ,         last_name               ");
		sql.append(" ,         email                   ");
		sql.append(" ,        salary                   ");
		sql.append(" from  (select rownum as rnum      ");
		sql.append("          ,      m.*               ");
		sql.append("        from myomyo m              ");
		sql.append("     where rownum<=     ?          ");
		sql.append("        )                          ");
		sql.append("    where   rnum>=      ?          ");
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		ResultSet rs = null;
		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setInt(1, endrow);
			pstmt.setInt(2, startrow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt("employee_id")+rs.getString("last_name")+rs.getString("email")+rs.getFloat("salary"));
				BoardDTO dto = new BoardDTO();
				dto.setEmployee_id(rs.getInt("employee_id"));
				dto.setLast_name(rs.getString("last_name"));
				dto.setEmail(rs.getString("email"));
				dto.setSalary(rs.getFloat("salary"));
				list.add(dto);
			}
		} finally {
			rsClose(rs);
		}
		
		return list;
	}
}
