package com.qiujing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {

    private final static long serialVersionUID = 8917755128536993241L;

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户账号
     */
    private String userCode;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginTime;

    // ------------------------------------------------------------------------------------------------------------------------

    private SysUser(Integer userId) {
        this.userId = userId;
    }

    /**
     * 构造
     *
     * @param userId 用户id
     * @return {@link SysUser} 用户信息
     */
    public static SysUser of(Integer userId) {
        return new SysUser(userId);
    }

}