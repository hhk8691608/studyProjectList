package com.itmayiedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableAutoConfiguration
@EnableEurekaClient
@SpringBootApplication
public class OrderApp {

	public static void main(String[] args) {
		SpringApplication.run(OrderApp.class, args);
	}

	// @Autowired
	// private Environment env;
	//
	// @Bean
	// public DataSource dataSource() {
	// DruidDataSource dataSource = new DruidDataSource();
	// dataSource.setUrl(env.getProperty("spring.datasource.url"));
	// dataSource.setUsername(env.getProperty("spring.datasource.username"));//
	// 用户名
	// dataSource.setPassword(env.getProperty("spring.datasource.password"));//
	// 密码
	// dataSource.setInitialSize(2);
	// dataSource.setMaxActive(20);
	// dataSource.setMinIdle(0);
	// dataSource.setMaxWait(60000);
	// dataSource.setValidationQuery("SELECT 1");
	// dataSource.setTestOnBorrow(false);
	// dataSource.setTestWhileIdle(true);
	// dataSource.setPoolPreparedStatements(false);
	// return dataSource;
	// }
}
