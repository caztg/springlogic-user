package cn.springlogic.user.service;

import cn.springlogic.user.jpa.entity.User;

/**
 * Created by admin on 2017/4/20.
 */
public interface UserService {

   void save(User user);

   User findByPhone(String phone);

   int resetPwd(String password,String phone);
}
