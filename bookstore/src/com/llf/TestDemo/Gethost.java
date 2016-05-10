package com.llf.TestDemo;

public class Gethost {

    public void fun(String email) {

        if (!email.matches("\\w+@\\w+\\.\\w+")) {
            System.out.println("Email格式错误！");
        } else {
            System.out.println("格式正确！");
        }
    }

    public static void main(String[] args) {
        Gethost gh = new Gethost();
        gh.fun("876331806@qq.com");
    }
}
