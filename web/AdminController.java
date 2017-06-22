package cn.springlogic.user.web;

import cn.springlogic.user.jpa.entity.Role;
import cn.springlogic.user.jpa.entity.User;
import cn.springlogic.user.jpa.repository.RoleRepository;
import cn.springlogic.user.jpa.repository.UserRepository;
import cn.springlogic.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/9.
 */
@Controller
@RequestMapping("/api/user/admin")
public class AdminController {



    @Autowired
    private UserRepository userRepository;




    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<Object> signupAdmin(@RequestBody User user){

        try {
            User save = userRepository.save(user);

            return ResponseEntity.ok(save);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
