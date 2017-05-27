package cn.springlogic.user.web;

import cn.springlogic.communicate.jpa.entity.Verification;
import cn.springlogic.communicate.service.AliYunSmsService;
import cn.springlogic.communicate.service.VerificationService;
import cn.springlogic.communicate.util.CommUtil;
import cn.springlogic.user.jpa.entity.User;
import cn.springlogic.user.service.UserService;
import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/4/20.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private AliYunSmsService aliYunSmsService;

    @Autowired
    private UserService userService;

    /**
     * 注册
     *
     * @param verification
     * @param user
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<String> signUp(Verification verification, User user) {

        try {
         /*检验账户是否已经注册*/
            User tempUser = userService.findByPhone(verification.getTarget());
            if (tempUser != null) {

                LOGGER.error("该手机号已经注册!");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("该手机号已经注册!");
            }
         /*检查验证码时效性,大于两分钟 失效*/
            boolean timeVerify = verificationService.timeVerify(verification.getTarget(), verification.getType());
            if (!timeVerify) {
                LOGGER.error("验证码失效");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("验证码失效!");
            }

        /*检查验证码正确性*/
            Verification tempVerify = verificationService.findBytargetAndtypeAndCode(verification.getTarget(), verification.getType(), verification.getCode());
            if (tempVerify != null) {


                /*验证码正确,随机生成一串字符串作为初始username*/
                user.setUsername(CommUtil.getRandomString(10));
                user.setPhone(verification.getTarget());
                user.setStatus("1");

                /*保存用户信息*/
                userService.save(user);

                return ResponseEntity.ok("注册成功!");
            } else {
                LOGGER.error("验证码错误!");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("验证码错误!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.error("注册失败");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册失败!");
    }

    /**
     * 忘记密码时,发送验证码
     *
     * @param verification
     * @return
     */
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResponseEntity<String> sendCodeWithresetPwd(Verification verification) {

        try {
        /*检验账户是否已经注册*/
            User tempUser = userService.findByPhone(verification.getTarget());
            if (tempUser == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("该手机号未注册!");
            }

        /*生成验证码*/
            String code = CommUtil.genRandomNum(6);

            Map<String, String> params = new HashMap<>();

            params.put("code", code);

        /*频繁度检查*/
            boolean frequency = verificationService.frequencyVerify(verification.getTarget(), verification.getType());

            if (!frequency) {
                aliYunSmsService.send(verification.getTarget(), params);
            } else {
                LOGGER.error("获取验证码太频繁");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取验证码太频繁");
            }

            verification.setCode(code);

            if (Verification.TYPE_SMS.equals(verification.getType())) {
                verification.setType(Verification.TYPE_SMS);
            }

            /*发送成功后,同时保存在数据库*/
            verificationService.save(verification);

            return ResponseEntity.ok("发送验证码成功!");
        } catch (ClientException e) {
            LOGGER.error("发送验证码失败!",e);
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        LOGGER.error("发送验证码失败");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("发送验证码失败");
    }

    @RequestMapping(value = "/resetpwd", method = RequestMethod.PATCH)
    public ResponseEntity<String> resetPwd(Verification verification, User user) {

        try {
         /*检查验证码时效性,大于两分钟 失效*/
            boolean timeVerify = verificationService.timeVerify(verification.getTarget(), verification.getType());
            if (!timeVerify) {
                LOGGER.error("验证码失效!");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("验证码失效!");
            }

         /*检查验证码正确性*/
            Verification tempVerify = verificationService.findBytargetAndtypeAndCode(verification.getTarget(), verification.getType(), verification.getCode());
            if (tempVerify != null) {


                /*验证码正确,根据手机号找到该用户*/
                User tempUser = userService.findByPhone(verification.getTarget());
                if(tempUser==null){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("找不到该用户!");
                }

                /*重置修改用户密码*/

                userService.resetPwd(user.getPassword(), verification.getTarget());

                return ResponseEntity.ok("重置密码成功!");
            } else {
                LOGGER.error("验证码错误!");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("验证码错误!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.error("重置密码失败!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("重置密码失败!");
    }


}
