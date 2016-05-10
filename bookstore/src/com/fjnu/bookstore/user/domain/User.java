package com.fjnu.bookstore.user.domain;

/*
 * user对象域
 * */

public class User {

    /*
     * 对应数据库
     * */
    private String uid; //用户id
    private String username;//用户名
    private String password;//密码
    private String email;//邮箱
    private String code;//激活码
    private boolean state;//激活状态

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCode() {
        return code;
    }

    public String getEmail() {
        return email;
    }

    public boolean isState() {
        return state;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User [uid=" + uid + ", username=" + username + ", password="
                + password + ", email=" + email + ", code=" + code + ", state="
                + state + "]";
    }

}
