package cn.springlogic.user.jpa.repository;

import cn.springlogic.user.jpa.entity.Permission;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by admin on 2017/4/14.
 */
@Configuration
@RepositoryRestResource(path="permission")
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
}
