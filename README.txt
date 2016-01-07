Spring MVC 3.1.1

기초적인 게시판 구성 기존 JDBC방식 및 JdbcTemplate방식이 비교 구성됨
서비스 대신 Command 패턴 사용  

spring jdbc oracle
mybatis
JdbcTemplate
validataion 성공 
validation test
maven project 변환
mybatis JUNIT test with h2 database

## 모델에 객체 저장
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
		* 이클립스에서 .properties 파일에서 한글이 유니코드 자동 변환되므로 보기에 불편하다. properties editor를 이클립스 마켓플레이스를 통해서 설치한다. 
		* messages.properties를 등록할 때 빈 basename에 확장자는 생략한다. 
		* jsp는 form taglib를 사용해야 한다. 
		
		
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
		
## maven project 변환
	eclipse:clean eclipse:eclipse