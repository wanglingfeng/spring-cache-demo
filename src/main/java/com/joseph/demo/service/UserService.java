package com.joseph.demo.service;

import com.joseph.demo.service.domain.User;

/**
 * Created by lfwang on 2017/6/15.
 */
public interface UserService {

    User findUser(User user);

    User findUserInCondition(User user);
    
    User insertUser(User user);

    User updateUser(User user);

    void removeUser(User user);

    void clear();
}
