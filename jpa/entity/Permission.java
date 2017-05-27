package cn.springlogic.user.jpa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by admin on 2017/4/14.
 */
@Data
@Entity
public class Permission {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    private String name;

    @Column(name="permission_id")
    private int permissionId;


    private String value;


}
