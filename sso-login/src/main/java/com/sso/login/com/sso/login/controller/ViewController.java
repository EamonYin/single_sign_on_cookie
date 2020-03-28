package com.sso.login.com.sso.login.controller;

import com.sso.login.com.sso.login.entity.User;
import com.sso.login.com.sso.login.utils.LoginCacheUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * 页面跳转逻辑
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    /**
     * 跳转到登录页面
     *
     * @return
     * @RequestParam中 value表示参数名字  required表示是否为必需，defaultValue表示默认值
     * @CookieValue中 有cookie就获取“TOKEN”没有就不获取，cookie不是必须的
     */
    @GetMapping("/login")
    public String tologin(@RequestParam(required = false, defaultValue = "") String target,
                          HttpSession session,
                          @CookieValue(required = false, value = "TOKEN") Cookie cookie) {
        /**
         * 判断target是否为空
         * 为空：主页面
         * 不为空：存到session
         */
        if (StringUtils.isEmpty(target)) {
            target = "http://www.xiaoming.com:9010";
        }
        if (cookie != null) {
            /**
             * 如果是登录的用户再次访问登陆系统时，就重定向主页面（target）
             */
            String value = cookie.getValue();
            //获取模拟缓存类LoginCacheUtil中拿出对应token的用户信息
            User user = LoginCacheUtil.loginUser.get(value);
            if (user != null) {
                return "redirect:" + target;
            }
        }
//        else {
        //重定向地址
        session.setAttribute("target", target);
        return "login";
//        }
    }
}
