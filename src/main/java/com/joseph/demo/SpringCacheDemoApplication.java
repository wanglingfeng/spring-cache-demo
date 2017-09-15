package com.joseph.demo;

import com.joseph.demo.service.UserService;
import com.joseph.demo.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
	
	@GetMapping(value = "/{id}")
	public Map<String, String> get(@PathVariable Integer id) {
		User user = new User(id, null);
		user = userService.findUser(user);
		
		Map<String, String> result = new HashMap<>();
		result.put("name", user.getName());
		return result;
	}
	
	@PutMapping(value = "/{id}")
	public void update(@PathVariable Integer id, @RequestParam String name) {
		User user = new User(id, name);
		userService.updateUser(user);
	}
}
