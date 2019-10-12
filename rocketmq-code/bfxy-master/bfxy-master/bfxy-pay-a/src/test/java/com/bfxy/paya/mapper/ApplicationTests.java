package com.bfxy.paya.mapper;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	
	@Autowired
	private CustomerAccountMapper customerAccountMapper;
	
	@Test
	public void testUpdateBalance() throws Exception {
		int count = customerAccountMapper.updateBalance("user001", new BigDecimal(898.0), 0, new Date());
		System.err.println("count : " + count);
		
	}
	
	
	
	
	
	
}
