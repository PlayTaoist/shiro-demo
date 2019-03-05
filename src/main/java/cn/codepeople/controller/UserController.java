/**   
 * @author lr
 * @date 2019年3月4日 上午11:54:07 
 * @version V1.0.0   
 */
package cn.codepeople.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {
    
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("name", "博客网站!");
        return "index.html";
    }
    
    @GetMapping("add")
    public String addUser() {
        return "/user/add.html";
    }
    
    @GetMapping("/update")
    public String updateUser() {
        return "/user/update.html";
    }
    
    @GetMapping("toLogin")
    public String loginBeetl() {
        return "/login.html";
    }
    
    //未授权提示页面
    @GetMapping("unAuth")
    public String unAuthBeetl() {
        return "/unAuth.html";
    }
    
    @PostMapping("login")
    public String login(String name, String password, Model model) {
        
        Subject subject = SecurityUtils.getSubject();
        
        UsernamePasswordToken  token =  new UsernamePasswordToken(name, password);
        
        try {
            subject.login(token);
            log.info("====>登录成功<====");
            return "redirect:/hello";
        } catch (UnknownAccountException e) {
            //登录失败：用户名不存在
            model.addAttribute("msg", "用户名不存在！");
            log.info("输入的用户名是：{}", name);
            return "/login.html";
        } catch (IncorrectCredentialsException e) {
            //登录失败：密码错误
            model.addAttribute("msg", "密码错误！");
            log.info("输入的密码是：{}", password);
            return "/login.html";
        }
    }
}
