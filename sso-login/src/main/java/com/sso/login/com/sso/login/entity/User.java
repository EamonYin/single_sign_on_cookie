package com.sso.login.com.sso.login.entity;

import lombok.*;
import lombok.experimental.Accessors;


//@Data//自动为User的属性添加getter和setter方法
//@AllArgsConstructor//全参构造器
//@NoArgsConstructor//添加无参构造器
//@Accessors(chain = true)//链式调用(例子：new User().setId().setUsername())
//@Getter
//@Setter
//@ToString
public class User {
    private Integer id;
    private String username;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
