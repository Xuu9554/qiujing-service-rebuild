package com.qiujing.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.qiujing.common.ResponseEntity;
import com.qiujing.entity.SysUser;
import com.qiujing.exception.ServiceAssert;
import com.qiujing.exception.ServiceException;
import com.qiujing.holder.RedissonHolder;
import com.qiujing.request.PortalLoginRequest;
import com.qiujing.service.SysUserService;
import com.qiujing.vo.LoginCaptcha;
import com.qiujing.vo.PortalLoginResult;
import org.redisson.api.RBucket;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;

import static com.qiujing.common.Constant.TOKEN;

@RestController
@RequestMapping("portal")
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class LoginController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private RedissonHolder redissonHolder;

    private final static RandomGenerator NUMBER_GENERATOR = new RandomGenerator("0123456789", 4);

    private final static Duration EXPIRED = Duration.ofMinutes(10);

    @GetMapping("generateCaptcha")
    public ResponseEntity<LoginCaptcha> generateCaptcha() {

        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 50, NUMBER_GENERATOR, 0);

        // 浅灰色背景
        lineCaptcha.setBackground(new Color(245, 245, 245));

        // 字体可按效果继续调整
        lineCaptcha.setFont(new Font("Arial", Font.PLAIN, 30));
        lineCaptcha.createCode();

        String verifyKey = IdUtil.fastSimpleUUID();
        redissonHolder.getBucket("PORTAL:CAPTCHA:" + verifyKey, StringCodec.INSTANCE).set(lineCaptcha.getCode(), EXPIRED);

        return ResponseEntity.success(LoginCaptcha.of(verifyKey, lineCaptcha.getImageBase64Data()));
    }

    @PostMapping("login")
    public ResponseEntity<PortalLoginResult> login(@RequestBody @Valid PortalLoginRequest request) {

        RBucket<String> verifyKey = redissonHolder.getBucket("PORTAL:CAPTCHA:" + request.getVerifyKey(), StringCodec.INSTANCE);
        String verifyCode = Opt.ofBlankAble(verifyKey.get()).orElseThrow(ServiceException::new, "验证码已过期，请重新获取");

        ServiceAssert.isTrue(verifyCode.equalsIgnoreCase(request.getVerifyCode()), "验证码错误");

        String userCode = request.getUserCode();
        SysUser user = userService.findUser(userCode);
        ServiceAssert.isTrue(SecureUtil.md5(request.getPassword()).equalsIgnoreCase(user.getPassword()), "密码错误");

        String token = IdUtil.fastSimpleUUID();
        redissonHolder.getBucket("PORTAL:TOKEN:" + token, StringCodec.INSTANCE).set(userCode, Duration.ofDays(3));

        verifyKey.delete();
        userService.updateById(SysUser.of(user.getUserId()).setLastLoginTime(LocalDateTime.now()));
        return ResponseEntity.success(PortalLoginResult.of(token, user.getUserName()));
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        Opt.ofBlankAble(request.getHeader(TOKEN)).ifPresent(token -> { //
            redissonHolder.getBucket("PORTAL:TOKEN:" + token, StringCodec.INSTANCE).delete();
        });

        return ResponseEntity.success("退出成功");
    }

}
