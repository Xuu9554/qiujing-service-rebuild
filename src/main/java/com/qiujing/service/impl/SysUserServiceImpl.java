package com.qiujing.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiujing.entity.SysUser;
import com.qiujing.exception.ServiceAssert;
import com.qiujing.exception.ServiceException;
import com.qiujing.mapper.SysUserMapper;
import com.qiujing.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 用户查找
     *
     * @param userCode 账号
     * @return {@link SysUser} 用户信息
     */
    @Override
    public SysUser findUser(String userCode) {

        ServiceAssert.isTrue(!StrUtil.isBlank(userCode), "用户不存在");
        return this.lambdaQuery().eq(SysUser::getUserCode, userCode).oneOpt().orElseThrow(() -> new ServiceException("用户不存在"));
    }

}