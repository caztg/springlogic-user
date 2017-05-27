package cn.springlogic.user.jpa.entity;

import cn.springlogic.address.jpa.entity.Address;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by admin on 2017/5/3.
 */
@Data
@Entity
public class ExpressAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch=FetchType.EAGER,  // 指定user属性的抓取策略 FetchType.LAZY:延迟加载   FetchType.EAGER:立即加载
            targetEntity=User.class)// 指定关联的持久化类
    /** 生成关联的外键列 */
   @JoinColumn(name="user_id", // 外键列的列名
            referencedColumnName="id") // 指定引用user表的主键列
    private User user;

    private String name;

    private String phone;

    @ManyToOne(fetch=FetchType.EAGER,  // 指定user属性的抓取策略 FetchType.LAZY:延迟加载   FetchType.EAGER:立即加载
           targetEntity=Address.class)// 指定关联的持久化类
    /** 生成关联的外键列 */
    @JoinColumn(name="address_id", // 外键列的列名
            referencedColumnName="id") // 指定引用user表的主键列
    private Address address;
}
