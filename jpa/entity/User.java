package cn.springlogic.user.jpa.entity;

import cn.springlogic.vip.jpa.entity.Experience;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;


import javax.persistence.*;
import javax.persistence.Entity;

import java.util.*;

/**
 * Created by figo-007 on 2017/4/6.
 */
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String username;

    private String password;

    @Column(name="create_time")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name="update_time")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column
    private String email;

    @Column(name = "nick_name")
    private String nickName;
    @Column
    private String avatar;

    @Column(unique = true)
    private String phone;
    @Column
    private String status;



    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},//JoinColumns定义本方在中间表的主键映射
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})//inverseJoinColumns定义另一在中间表的主键映射

   // @JsonManagedReference  // 配置后,反序列化失败.
   // @Fetch(FetchMode.SUBSELECT)
    private Set<Role> roles=new HashSet<>();


}
