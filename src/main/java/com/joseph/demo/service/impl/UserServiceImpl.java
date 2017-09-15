package com.joseph.demo.service.impl;

import com.joseph.demo.service.UserService;
import com.joseph.demo.service.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by lfwang on 2017/9/8.
 */
@Service
public class UserServiceImpl implements UserService {

    private Set<User> users;

    @PostConstruct
    public void init() {
        users = new HashSet<>();
        users.add(new User(1, "kobe bryant"));
        users.add(new User(2, "joseph wang"));
        users.add(new User(3, "cristiano ronaldo"));
    }

    @Cacheable(value = {"users"}, key = "#user.getId()", unless = "#result == null")
    @Override
    public User findUser(User user) {
        return findUserInDataBase(user.getId());
    }

    @Cacheable(value = "users", condition = "#user.getId() <= 2")
    @Override
    public User findUserInCondition(User user) {
        return findUserInDataBase(user.getId());
    }

    @CachePut(value = "users", key = "#user.getId()")
    @Override
    public User updateUser(User user) {
        updateUserInDataBase(user);
        
        return user;
    }

    @CacheEvict(value = "users")
    @Override
    public void removeUser(User user) {
        removeUserInDataBase(user.getId());
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void clear() {
        removeAllInDataBase();
    }

    /**
     * 模拟数据库查找
     * @param id
     * @return
     */
    private User findUserInDataBase(int id) {
        return users.stream().filter(u -> id == u.getId()).map(u -> {
            System.out.println("search database, id = " + id + " success");
            return u;
        }).findFirst().orElse(null);
    }

    /**
     * 模拟数据库更新
     * @param user
     */
    private void updateUserInDataBase(User user) {
        users.stream()
                .filter(u -> user.getId() == u.getId())
                .forEach(u -> {
                    System.out.println("update database " + u + " -> " + user);
                    u.setName(user.getName());
                });
    }

    /**
     * 模拟数据库删除
     * @param id
     */
    private void removeUserInDataBase(int id) {
        Optional<User> deleteUserOptional = users.stream().filter(u -> id == u.getId()).findFirst();
        deleteUserOptional.ifPresent(users::remove);
    }

    /**
     * 模拟数据库清空
     */
    private void removeAllInDataBase() {
        users.clear();
    }
}
