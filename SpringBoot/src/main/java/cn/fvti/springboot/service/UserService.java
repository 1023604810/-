package cn.fvti.springboot.service;

import cn.fvti.springboot.entity.User;

public interface UserService {
    User login(String username, String password);
    User register(String username, String password);
    boolean isUsernameExists(String username);
}