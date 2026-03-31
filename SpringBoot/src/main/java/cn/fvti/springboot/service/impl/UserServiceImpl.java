package cn.fvti.springboot.service.impl;

import cn.fvti.springboot.entity.User;
import cn.fvti.springboot.repository.UserRepository;
import cn.fvti.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password) {
        // 直接返回用户对象，暂时绕过数据库验证
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    @Override
    public User register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Override
    public boolean isUsernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
