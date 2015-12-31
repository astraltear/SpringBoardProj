Spring MVC 3.1.1

spring jdbc oracle
mybatis
JdbcTemplate

�������� �Խ��� ���� ���� JDBC��� �� JdbcTemplate����� �� ������
���� ��� Command ���� ���  

## �𵨿� ��ü ����
	model.addAttribute("request", request);
	Map<String, Object> map = model.asMap();
	HttpServletRequest request = (HttpServletRequest) map.get("request");

## JdbcTemplate query
	template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));
	
## JdbcTemplate update
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
				
		String query="update z_board set bHit=bHit+1 where bId=?  ";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, strId);
			}
		});