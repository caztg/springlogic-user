package cn.springlogic.user.jpa.entity;

import lombok.Data;

/**
 * Created by admin on 2017/6/12.
 */
@Data
public class UserModel {

    private Integer id;

    private String newPassword;

    private String oldPassword;

}
