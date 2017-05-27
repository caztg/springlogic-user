package cn.springlogic.user.jpa.repository;

import cn.springlogic.user.jpa.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by admin on 2017/4/11.
 */

@Configuration
@RepositoryRestResource(path="user")
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 用于 OAuth2 的认证方法.(有用,内部调用了)
     * localhost:8080/api/user/search/username?username=abc
     * @param username
     * @return
     */
    @RestResource(path="username",rel="username")
    public User findByUsername(@Param("username") String username);

    /**
     * 根据手机号查询用户信息
     * localhost:8080/api/user/search/phone?phone=111111111111
     * @param phone
     * @return
     */
    @RestResource(path="phone",rel="phone")
    public User findByphone(@Param("phone")String phone);



    //@RestResource(path = "/",rel = "/")
    @RestResource(path = "all",rel = "all")
    @Query("Select u from User u " +
            "where " +
            "(:phone IS NULL or u.phone = :phone) AND " +
            "(:nickName IS NULL or u.nickName = :nickName) AND " +
            "(:email IS NULL or u.email = :email) ")

    public Page<User> findByAll(@Param("phone")String phone,@Param(value = "nickName")String nickname,@Param("email")String email,Pageable pageable);


    /**
     *  分页条件组合模糊查询
     *
     * 1.方法名 And 后面加大写首字母
     * 2.@Param 里面的就是 地址url ? &后面的  http://localhost:8080/api/user/search/phoneAndnickname?phone=1&nickName=t
     *
     * @param phone
     * @param nickname
     * @param email
     * @param pageable
     * @return
     */
    @RestResource(path = "phoneAndnickname",rel ="phoneAndnickname" )
    public Page<User> findByphoneContainsAndNickNameContainsAndEmailContains(@Param("phone")String phone,@Param("nickName")String nickname,@Param("email")String email, Pageable pageable);
    //方法名的参数要和 属性名一致 nickName

    /**
     * 根据手机号和昵称模糊查询
     * @param phone
     * @param nickname
     * @return
     */
    public List<User> findByphoneContainsAndNickNameContains(@Param("phone")String phone,@Param("nickName")String nickname);

    /**
     *  patch 修改 在路径上给id  localhost:8080/api/user/3   然后传送json字符串
     */


    @Modifying
    @Transactional
    @Query("update User u set u.password = ?1 where u.phone = ?2")
    int update(String password , String phone);



    @RestResource(path = "admin",rel ="admin")
    @Query("SELECT u from User u,Role r where (:nickName IS NULL OR u.nickName LIKE CONCAT('%',:nickName,'%')) AND r IN ELEMENTS(u.roles) AND r.name='ROLE_ADMIN'")
    Page<User> findAdmin(@Param("nickName")String nickName,Pageable pageable);
}

