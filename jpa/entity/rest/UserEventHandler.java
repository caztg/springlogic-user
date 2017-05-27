package cn.springlogic.user.jpa.entity.rest;

import cn.springlogic.communicate.util.CommUtil;
import cn.springlogic.user.jpa.entity.Role;
import cn.springlogic.user.jpa.entity.User;
import cn.springlogic.user.jpa.repository.RoleRepository;
import cn.springlogic.user.jpa.repository.UserRepository;
import cn.springlogic.vip.jpa.entity.Experience;
import cn.springlogic.vip.jpa.repository.ExperienceRepository;
import com.fitcooker.app.BussinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2017/4/28.
 */

@Component
@RepositoryEventHandler(User.class)

public class UserEventHandler {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private RoleRepository roleRepository;


    @HandleBeforeCreate
    public void beforeCreate(User user) throws BussinessException {


            //验证手机号是否已经注册
            User temp = userRepository.findByphone(user.getPhone());
            if(temp!=null){

                   // throw new Exception("手机号已被注册");
                    throw new BussinessException("手机号已经被注册");

            }


        //验证码正确,随机生成一串字符串作为初始username
        user.setUsername(CommUtil.getRandomString(10));
        user.setStatus("1");

        Role role = roleRepository.findByName("ROLE_USER");
        Set<Role> userRoles=new HashSet<>();
        userRoles.add(role);
        user.setRoles(userRoles);


    }

    //create 对应的是post ,save对应的是PUT请求
    @HandleAfterCreate
    public void afterCreate(User  user){

        Experience experience=new Experience();
        experience.setAmmount(0);
        experience.setUser(user);

        experienceRepository.save(experience);

    }




}
