package cn.springlogic.user.jpa.repository;

import cn.springlogic.user.jpa.entity.Role;
import cn.springlogic.user.jpa.entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by admin on 2017/4/14.
 */
@Configuration
@RepositoryRestResource(path="role")
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(@Param("name")String name);

}
