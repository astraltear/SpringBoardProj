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

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.youngjee.springprjboard.dto.BDto;
import com.youngjee.springprjboard.util.Constant;

public class BDao {
	
	DataSource dataSource;
	
	JdbcTemplate template = null;
	
	public BDao() {
		this.template = Constant.template;
		
		// 실제 DB의 계정 설정은 context.xml에서 해준다 
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle9i");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<BDto> list() {
		
		String query="select bid,bnamse,btitle,bcontent,bdate,bhit,bgroup,bstep,bindent from z_board order by bgroup desc, bstep asc ";
		return (ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
		
/*		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query="select bid,bnamse,btitle,bcontent,bdate,bhit,bgroup,bstep,bindent from z_board order by bgroup desc, bstep asc ";
			
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
		
		return  dtos;*/
	}
	
	public void write(final String bName,final String bTitle,final String bContent) {
		
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String query="insert into z_board(bid,bname,btitle,bcontent,bhit,bgroup,bstep,bindent) values( (select nvl(max(bid),0)+1 from z_board),?,?,?,0,(select nvl(max(bid),0)+1 from z_board),0,0 )  ";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
				
				return pstmt;
			}
		});
		
		
/*		Connection connection = null;
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
		}*/
	}

	public BDto contentView(String strId) {
		
		upHit(strId);
		
		String query="select bid,bname,btitle,bcontent,bdate,bhit,bgroup,bstep,bindent from z_board where bid="+strId;
		return template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
/*		Connection connection = null;
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
		return dto;*/
	}

	private void upHit(final String strId) {
		
		String query="update z_board set bHit=bHit+1 where bId=?  ";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, strId);
			}
		});
		
		
		/*Connection connection = null;
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
		}*/
		
	}

	public void modify(final String bId,final String bName,final String bTitle,final String bContent) {
		
		String query="update z_board set bname=?,btitle=?,bcontent=? where bid =?  ";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
				pstmt.setString(4, bId);
			}
		});
		
/*		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query="update z_board set bname=?,btitle=?,bcontent=? where bid =?  ";
			
			pstmt = connection.prepareStatement(query);
			
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setString(4, bId);
			
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
		}*/
	}

	public void delete(final String bId) {
		String query="delete from z_board where bid =?  ";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bId);
				
			}
		});
		
		
/*		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query="delete from z_board where bid =?  ";
			
			pstmt = connection.prepareStatement(query);
			
			pstmt.setString(1, bId);
			
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
		}*/
		
	}

	public BDto reply_view(String strId) {
		String query="select bid,bname,btitle,bcontent,bdate,bhit,bgroup,bstep,bindent from z_board where bid="+strId;
		return template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
/*		Connection connection = null;
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
		return dto;*/
	}

	public void reply(String bId,final String bName,final String bTitle,final String bContent, final String bGroup, final String bStep,final String  bIndent) {
		
		replyShape(bGroup,bStep);
		
		String query="insert into z_board(bid,bname,btitle,bcontent,bgroup,bstep,bindent) values( (select nvl(max(bid),0)+1 from z_board), ?,?,?,?,?,? )  ";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
				pstmt.setInt(4, Integer.parseInt(bGroup));
				pstmt.setInt(5, Integer.parseInt(bStep)+1 );
				pstmt.setInt(6, Integer.parseInt(bIndent)+1);
				
			}
		});
		
		
/*		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query="insert into z_board(bid,bname,btitle,bcontent,bgroup,bstep,bindent) values( (select nvl(max(bid),0)+1 from z_board), ?,?,?,?,?,? )  ";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep)+1 );
			pstmt.setInt(6, Integer.parseInt(bIndent)+1);
			
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
		}*/
	}

	private void replyShape(final String bGroup, final String bStep) {
		
		String query="update z_board set bStep=bStep+1 where bGroup=? and bStep > ?  ";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bGroup);
				pstmt.setString(2, bStep);
				
			}
		});
		
		
/*		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query="update z_board set bStep=bStep+1 where bGroup=? and bStep > ?  ";
			
			pstmt = connection.prepareStatement(query);
			
			pstmt.setString(1, bGroup);
			pstmt.setString(2, bStep);
			
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
		}*/
	}

}
