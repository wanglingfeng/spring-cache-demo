package com.joseph.demo;

import com.joseph.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringCacheDemoApplicationTests {

	@Autowired
	private UserService userService;
	
	@Test
	public void contextLoads() {
	}
	
	/*@Test
	public void testFindUser() throws InterruptedException {
		// 设置查询条件
		User user1 = new User(1, null);
		User user2 = new User(2, null);
		User user3 = new User(3, null);

		System.out.println("第一次查询");
		System.out.println(userService.findUser(user1));
		System.out.println(userService.findUser(user2));
		System.out.println(userService.findUser(user3));

		System.out.println("\n第二次查询");
		System.out.println(userService.findUser(user1));
		System.out.println(userService.findUser(user2));
		System.out.println(userService.findUser(user3));

		// 在classpath:ehcache/ehcache.xml中，设置了userCache的缓存时间为5s, 这里设置等待
		Thread.sleep(7000);

		System.out.println("\n缓存过期，再次查询");
		System.out.println(userService.findUser(user1));
		System.out.println(userService.findUser(user2));
		System.out.println(userService.findUser(user3));
	}*/
}
