package com.astraltear.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.youngjee.springprjboard.domain.User;

public class MyBatisTest {
	private static final Logger log = LoggerFactory.getLogger(MyBatisTest.class);
	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void setup() throws IOException {
		
		log.debug("SETUP METHOD! SETUP METHODSETUP METHODSETUP METHODSETUP METHODSETUP METHODSETUP METHOD");
		
		String resource ="mybatis-config-test.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("user-test.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
		
	}

	private DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:~/astral");
		dataSource.setUsername("sa");
		return dataSource;
	}

	@Test
	public void gettingStarted() throws IOException {
//		JDK 1.7 이상 
//		아래와 같이 구현하면 close 메소드가 필요 없음. Sqlsession 객체가 Closable Interface를 구현함. 위의 close는 필요 없음   
		
		try( SqlSession session = sqlSessionFactory.openSession() ){
			User user = session.selectOne("UserMapper.findById", "astraltear");
			log.debug("User :::::{}",user);
		}
		
		
/*		SqlSession session = sqlSessionFactory.openSession();
		try {
			User user = session.selectOne("UserMapper.findById", "testUser");
			log.debug("User :::::{}",user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}*/
	}
	
	@Test
	public void insert() {
		try(SqlSession session = sqlSessionFactory.openSession()){
			
			User user = new User("astraltear", "astraltear", "astraltear", "astral@astral.com");
			session.insert("UserMapper.create", user);
			User dbUser = session.selectOne("UserMapper.findById", "astraltear");
			log.debug("User :::::{}",dbUser);
		}
	}
}
