package com.qiujing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiujing.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

    /**
     * 用户查找
     *
     * @param userCode 账号
     * @return {@link SysUser} 用户信息
     */
    SysUser findUser(String userCode);

}