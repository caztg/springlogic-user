package cn.springlogic.user.service.impl;

import cn.springlogic.user.jpa.entity.User;
import cn.springlogic.user.jpa.repository.UserRepository;
import cn.springlogic.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2017/4/20.
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findByphone(phone);
    }

    @Override
    public int resetPwd(String password, String phone) {
        return userRepository.update(password,phone);
    }
}
