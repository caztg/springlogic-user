package cn.springlogic.user.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by figo-007 on 2017/4/6.
 */
@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * administrator
       consumer
     */
    private String name;

    private String description;

    /*
    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JsonBackReference
    private Set<User> users=new HashSet<>();
*/

    /**
     * 角色所拥有的访问权限，多对多的关系
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "role_permission", joinColumns = {
            @JoinColumn(name = "role_id") }, inverseJoinColumns = {
            @JoinColumn(name = "permission_id") })
    private Set<Permission> permissions=new HashSet<>();

}
