package cn.springlogic.user.web;

import cn.springlogic.address.jpa.entity.Address;
import cn.springlogic.blog.web.BlogRestController;
import cn.springlogic.social.jpa.entity.Follow;
import cn.springlogic.social.jpa.entity.Publication;
import cn.springlogic.social.jpa.entity.PublicationFavor;
import cn.springlogic.social.jpa.repository.FollowRepository;
import cn.springlogic.social.jpa.repository.PublicationFavorRepository;
import cn.springlogic.user.jpa.entity.ExpressAddress;
import cn.springlogic.user.jpa.entity.User;
import cn.springlogic.user.jpa.repository.ExpressAddressRepository;
import cn.springlogic.user.jpa.repository.UserRepository;
import cn.springlogic.vip.jpa.entity.Experience;
import cn.springlogic.vip.jpa.entity.ExperienceLevel;
import cn.springlogic.vip.jpa.repository.ExperienceLevelRepository;
import cn.springlogic.vip.jpa.repository.ExperienceRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/5/11.
 */
@RepositoryRestController
@RequestMapping(value = "user")
public class UserRestController {

    @Autowired
    private PagedResourcesAssembler pagedResourcesAssembler;
    @Autowired
    private UserRepository userRepository;

    /**
     * 会员列表
     * 需要加载账户角色
     *
     * @param pageable
     * @param resourceAssembler
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/search/vip")
    public ResponseEntity<PagedResources<PersistentEntityResource>> publicationSearchByTopic(@RequestParam(name = "nick_name", required = false) String nickName,
                                                                                             @RequestParam(name = "phone", required = false) String phone,
                                                                                             @RequestParam(name = "email", required = false) String email,
                                                                                             Pageable pageable,
                                                                                             PersistentEntityResourceAssembler resourceAssembler) {
        //查询出所有的user
        Page<User> users = userRepository.findByAll(phone, nickName, email, pageable);


        //通过工具类转化器组装
        Page<User> VipPage = users.map(new UsersConverter());


        return ResponseEntity.ok(pagedResourcesAssembler.toResource(VipPage, resourceAssembler));
    }

    /**
     * 转换器类
     */
    private static final class UsersConverter implements Converter<User, User> {



        private UsersConverter() {


        }

        @Override
        public User convert(User source) {

            source.setPassword(null);
            source.setUsername(null);
            source.setAvatar(null);

            return source;
        }
    }


}
