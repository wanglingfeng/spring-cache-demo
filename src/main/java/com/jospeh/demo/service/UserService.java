package com.jospeh.demo.service;

import com.jospeh.demo.service.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by lfwang on 2017/6/15.
 */
public interface UserService {

    User findUser(User user);

    User findUserInCondition(User user);

    User updateUser(User user);

    void removeUser(User user);

    void clear();
}
