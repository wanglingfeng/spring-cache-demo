package com.jospeh.demo;

import com.jospeh.demo.service.UserService;
import com.jospeh.demo.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCaching
@RestController
public class SpringCacheDemoApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 使tomcat可用
		return builder.sources(SpringCacheDemoApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringCacheDemoApplication.class, args);
	}
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/{id}")
	public String get(@PathVariable Integer id) {
		User user = new User(id, null);
		user = userService.findUser(user);
		
		return user.getName();
	}
}
