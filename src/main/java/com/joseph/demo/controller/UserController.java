package com.joseph.demo.controller;

import com.joseph.demo.service.UserService;
import com.joseph.demo.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lfwang on 2017/9/19.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public Map<String, String> get(@PathVariable Integer id) {
        User user = new User(id, null);
        user = userService.findUser(user);

        Map<String, String> result = new HashMap<>();
        String name = (null == user) ? "" : user.getName();
        result.put("name", name);
        return result;
    }
    
    @PostMapping
    public void insert(@RequestParam String name) {
        User user = new User();
        user.setName(name);
        userService.insertUser(user);
    }

    @PutMapping(value = "/{id}")
    public void update(@PathVariable Integer id, @RequestParam String name) {
        User user = new User(id, name);
        userService.updateUser(user);
    }
    
    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable Integer id) {
        User user = new User();
        user.setId(id);
        userService.removeUser(user);
    }
}
