Spring MVC 3.1.1

�������� �Խ��� ���� ���� JDBC��� �� JdbcTemplate����� �� ������
���� ��� Command ���� ���  

spring jdbc oracle
mybatis
JdbcTemplate
validataion ���� 
validation test
maven project ��ȯ
mybatis JUNIT test with h2 database

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
		
## validataion
		* ��Ŭ�������� .properties ���Ͽ��� �ѱ��� �����ڵ� �ڵ� ��ȯ�ǹǷ� ���⿡ �����ϴ�. properties editor�� ��Ŭ���� �����÷��̽��� ���ؼ� ��ġ�Ѵ�. 
		* messages.properties�� ����� �� �� basename�� Ȯ���ڴ� �����Ѵ�. 
		* jsp�� form taglib�� ����ؾ� �Ѵ�. 
		
		
		<dependency>
			<groupId>javax.validation</groupId>
			<artifactId>validation-api</artifactId>
			<version>1.1.0.Final</version>
		</dependency>
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-validator</artifactId>
			<version>5.2.2.Final</version>
		</dependency>
		
		com.youngjee.springprjboard.controller.ValidController
		com.youngjee.springprjboard.domain.User
		src\main\webapp\WEB-INF\views\validUser.jsp
		src\main\resources\messages.properties
		
		<beans:bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource"
			p:basename="messages"
	    />
		
## maven project ��ȯ
	eclipse:clean eclipse:eclipse