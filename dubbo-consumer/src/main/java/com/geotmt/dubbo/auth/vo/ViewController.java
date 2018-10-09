package com.geotmt.dubbo.auth.vo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/index.html")
    public String index(){
        return"index";
    }

    @RequestMapping("/welcome.html")
    public String welcome(){
        return"welcome";
    }

    @RequestMapping("/success")
    public String success(){
        return"success";
    }

    @RequestMapping("/login.html")
    public String login(){
        return"login";
    }

    @RequestMapping("/admin-list.html")
    public String adminList(){
        return"admin-list";
    }

    @RequestMapping("/admin-role.html")
    public String adminRole(){
        return"admin-role";
    }

    @RequestMapping("/admin-cate.html")
    public String adminCate(){
        return"admin-cate";
    }

    @RequestMapping("/admin-rule.html")
    public String adminRule(){
        return"admin-rule";
    }

    @RequestMapping("/role-add.html")
    public String addRole(){
        return"role-add";
    }

    @RequestMapping("/admin-add.html")
    public String addAdmin(){
        return"admin-add";
    }

    @RequestMapping("/403.html")
    public String miss(){
        return"403";
    }
}
