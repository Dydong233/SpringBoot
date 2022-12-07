package com.dydong.controller;

import com.sun.org.apache.bcel.internal.classfile.Unknown;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
    @RequestMapping({"/","/index"})
    public String toIndex(Model model)
    {
        model.addAttribute("msg","hello shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username,String password,Model model){
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //登录
        try {
            subject.login(token);
            return "index";
        } catch (UnknownAccountException e) {
            //用户名不存在
            model.addAttribute("msg","用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码不存在");
            return "login";
        }
    }

    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权无法访问此页面";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "index";
    }
}
