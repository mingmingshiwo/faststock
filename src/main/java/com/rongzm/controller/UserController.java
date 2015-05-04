package com.rongzm.controller;

import com.rongzm.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rongzhiming on 2015/5/4.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${project.version}")
    String version;

    @RequestMapping("/ver")
    public String version(){
        return version;
    }

    @RequestMapping(value="/",method = {RequestMethod.GET})
    public User user(){
        User user = new User();
        user.setPassword(version);
        user.setName("ming");
        return user;
    }
}
