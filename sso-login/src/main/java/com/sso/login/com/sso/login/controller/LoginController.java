package com.sso.login.com.sso.login.controller;

import com.sso.login.com.sso.login.entity.User;
import com.sso.login.com.sso.login.utils.LoginCacheUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    //    模拟数据库用户数据
    public static Set<User> dbUser;

    static {
        dbUser = new HashSet<>();
        dbUser.add(new User(0, "zhangsan", "1"));
        dbUser.add(new User(1, "lisi", "2"));
        dbUser.add(new User(2, "wangwu", "3"));
    }

    //处理“/login”下的post请求
    @PostMapping
    public String doLogin(User user, HttpSession session, HttpServletResponse response) {
        System.out.println(dbUser);
        String target = (String) session.getAttribute("target");
        System.out.println(target);
        /**
         * ->lambda操作符
         * (lambda表达式的参数列表)“->”（lambda表达式所需要执行的功能[lambda 体]）
         * public boolean test(T t)
         * 左侧是接口的参数列表"t"，右边是接口的实现"test"
         * stream流.中间操作：filter操作和findFirst操作（返回集合的第一个对象）
         */

        Optional<User> first = dbUser.stream().filter(dbUser -> dbUser.getUsername().equals(user.getUsername()) &&
                dbUser.getPassword().equals(user.getPassword()))
                .findFirst();

        //判断用户是否登录
        if (first.isPresent()) {
            //保存用户登录信息
            String token = UUID.randomUUID().toString();//随机生成一个token，UUID（全局唯一标识符）
            Cookie cookie = new Cookie("TOKEN", token);
            //cookie要在子系统之间互相访问，要在同一个域下
            cookie.setDomain("xiaoming.com");
            response.addCookie(cookie);
            //模拟缓存，把User和随机生成的cookie存（put）到“loginUser”方法中的map
            LoginCacheUtil.loginUser.put(token, first.get());
        } else {
            //登陆失败
            session.setAttribute("msg", "用户名或密码错误");
            return "login";
        }

   /*     for (User dbuser:dbUser) {
           if(dbuser.getUsername().equals(user.getUsername())&&dbuser.getPassword().equals(user.getPassword())){
            //保存用户登录信息
            String token = UUID.randomUUID().toString();//随机生成一个token，UUID（全局唯一标识符）
            LoginCacheUtil.loginUser.put(token,dbuser);
           }else {
              // 登陆失败
            session.setAttribute("msg","用户名或密码错误");
            return "login";
           }
        }*/

//        重定向到target地址
        return "redirect:" + target;
    }

    /**
     * 给其他子系统开放一个接口，通过token获取登录用户的信息
     *
     * @param token
     * @return
     */
    @GetMapping("/info")
    @ResponseBody
    //ResponseEntity <T>  ，泛型T 表示要设置的返回的 响应体
    public ResponseEntity<User> getUserInfo(String token) {
        if (!StringUtils.isEmpty(token)) {
            User user = LoginCacheUtil.loginUser.get(token);
            return ResponseEntity.ok(user);
        } else {
            //错误请求（我要token，你却没给我）
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
