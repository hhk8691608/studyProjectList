package com.bfxy.store.service.provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bfxy.store.mapper.StoreMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	
	@Autowired
	private StoreMapper storeMapper;
	
	@Test
	public void testSelectVersion() throws Exception {
		int version = storeMapper.selectVersion("1", "001");
		System.err.println("version: " + version);
	}
	
	@Test
	public void testSelectStoreCount() throws Exception {
		int storeCount = storeMapper.selectStoreCount("1", "001");
		System.err.println("storeCount: " + storeCount);
	}
	
	
	
	
	
	
}
