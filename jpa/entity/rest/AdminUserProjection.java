package cn.springlogic.user.jpa.entity.rest;

import cn.springlogic.user.jpa.entity.Role;
import cn.springlogic.user.jpa.entity.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fitcooker.app.AppDataPreFixSerializer;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2017/5/26.
 */
@Projection(name = "adminuser", types = {User.class})
public interface AdminUserProjection {

    int getId();

    String getNickName();

    String getUsername();
    @JsonSerialize(using = AppDataPreFixSerializer.class)
    String getAvatar();

    Set<Role> getRoles();

    String getPhone();

    String getStatus();

    Date getCreateTime();

    String getEmail();

}
