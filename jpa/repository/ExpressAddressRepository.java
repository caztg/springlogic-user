package cn.springlogic.user.jpa.repository;

import cn.springlogic.user.jpa.entity.ExpressAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by admin on 2017/5/3.
 */
@Configuration
@RepositoryRestResource(path="expressaddress")
public interface ExpressAddressRepository extends JpaRepository<ExpressAddress,Integer>{

    @RestResource(path = "all",rel = "all")
    List<ExpressAddress> findByUserId(@Param("user_id")Integer userId);

}
