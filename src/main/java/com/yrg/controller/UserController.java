package com.yrg.controller;

import com.yrg.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    /**
     * 测试方法
     */
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        System.out.println("UserController.hello()");
        return "ok";
    }

    /**
     * 测试thymeleaf
     */
    @RequestMapping("/testThymeleaf")
    public String testThymeleaf(Model m){
        m.addAttribute("name","你好");
        return "test";
    }

    @RequestMapping("/add")
    public String add(){
        return "/user/add";
    }

    @RequestMapping("/update")
    public String update(){
        return "/user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "/login";
    }

    /**
     * 登陆逻辑处理
     */
    @RequestMapping("/login")
    public String login(String name,String password,Model model){
        /**
         * 使用shiro编写认证操作
         */
        //1.获取Subjec
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户信息
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        //3.执行登陆方法
        try {
            subject.login(token);
            //登陆成功
            return "redirect:/testThymeleaf";
        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登陆失败，用户名不存在
            model.addAttribute("msg","用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e){
            //登陆失败，密码错误
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }


    @RequestMapping("/unAuth")
    public String unAuth(){
        return "unAuth";
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/loginOut")
    public String loginOut(){
        SecurityUtils.getSubject().logout();
        return "login";
    }
}
