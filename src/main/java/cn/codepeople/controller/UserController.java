/**   
 * @author lr
 * @date 2019年3月4日 上午11:54:07 
 * @version V1.0.0   
 */
package cn.codepeople.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.fsg.uid.UidGenerator;

import cn.codepeople.entity.User;
import cn.codepeople.service.UserService;
import cn.codepeople.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {
    
    @Autowired
    private UidGenerator uidGenerator;
    @Autowired
    private UserService userService;
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("name", "博客网站!");
        long uid0 = uidGenerator.getUID();
        long uid1 = uidGenerator.getUID();
        long uid2 = uidGenerator.getUID();
        long uid3 = uidGenerator.getUID();
        log.info("生成的UID编码是1===>{}&{}", uid0, uidGenerator.parseUID(uid0));
        log.info("生成的UID编码是2===>{}&{}", uid1, uidGenerator.parseUID(uid1));
        log.info("生成的UID编码是3===>{}&{}", uid2, uidGenerator.parseUID(uid2));
        log.info("生成的UID编码是4===>{}&{}", uid3, uidGenerator.parseUID(uid3));
        return "index.html";
    }
    
    @GetMapping("add")
    public String addUser() {
        return "/user/add.html";
    }
    
    @PostMapping("add")
    @ResponseBody
    public int addUserPost(String name, String password) {
        User user = new User();
        user.setUserId(Long.toString(uidGenerator.getUID()));
        user.setName(name);
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(ShiroUtil.sha256(password, salt));
        user.setPerms("sys:user:add");
        return userService.saveUser(user);
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
