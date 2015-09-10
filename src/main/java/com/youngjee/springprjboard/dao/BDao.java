package com.youngjee.springprjboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.youngjee.springprjboard.dto.BDto;

public class BDao {
	
	DataSource dataSource;
	
	public BDao() {
		
		
		// 실제 DB의 계정 설정은 context.xml에서 해준다 
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle9i");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<BDto> list() {
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query="select bid,bname,btitle,bcontent,bdate,bhit,bgroup,bstep,bindent from z_board order by bgroup desc, bstep asc ";
			
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int bId = rs.getInt("bid");
				String  bName = rs.getString("bname");
				String  bTitle = rs.getString("btitle");
				String  bContent = rs.getString("bcontent");
				Timestamp bDate = rs.getTimestamp("bdate");
				
				int bHit = rs.getInt("bhit");
				int bGroup = rs.getInt("bgroup");
				int bStep = rs.getInt("bstep");
				int bIndent = rs.getInt("bindent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				dtos.add(dto);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if (rs != null) rs.close();
					if (pstmt != null) pstmt.close();
					if (connection != null) connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
		}
		
		return  dtos;
	}
	
	public void write(String bName,String bTitle,String bContent) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query="insert into z_board(bid,bname,btitle,bcontent,bhit,bgroup,bstep,bindent) values( (select nvl(max(bid),0)+1 from z_board),?,?,?,0,(select nvl(max(bid),0)+1 from z_board),0,0 )  ";
			
			pstmt = connection.prepareStatement(query);
			
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			
			pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if (pstmt != null) pstmt.close();
					if (connection != null) connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
		}
	}

	public BDto contentView(String strId) {
		
		upHit(strId);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		
		BDto dto= null;
		
		try {
			connection = dataSource.getConnection();
			
			String query="select bid,bname,btitle,bcontent,bdate,bhit,bgroup,bstep,bindent from z_board where bid=? ";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, strId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int bId = rs.getInt("bid");
				String  bName = rs.getString("bname");
				String  bTitle = rs.getString("btitle");
				String  bContent = rs.getString("bcontent");
				Timestamp bDate = rs.getTimestamp("bdate");
				
				int bHit = rs.getInt("bhit");
				int bGroup = rs.getInt("bgroup");
				int bStep = rs.getInt("bstep");
				int bIndent = rs.getInt("bindent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if (rs != null) rs.close();
					if (pstmt != null) pstmt.close();
					if (connection != null) connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
		}
		return dto;
	}

	private void upHit(String strId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query="update z_board set bHit=bHit+1 where bId=?  ";
			
			pstmt = connection.prepareStatement(query);
			
			pstmt.setString(1, strId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if (pstmt != null) pstmt.close();
					if (connection != null) connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
		}
		
	}

}
