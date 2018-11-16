package org.xavier.hyggecache.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.xavier.hyggecache.example.model.User;
import org.xavier.hyggecache.example.service.UserService;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.16
 * @since Jdk 1.8
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("user/{uId}")
    public Object getUserByUId(@PathVariable("uId") Integer uId) {
        return userService.getUserById(uId);
    }

    @GetMapping("user/update/{uId}")
    public Object updateUser(@PathVariable("uId") Integer uId) {
        User user = new User();
        user.setId(uId);
        user.setName("改Tom #" + uId);
        user.setAddress("改Tianjin #" + uId);
        userService.updateUser(user);
        return "已修改";
    }

    @GetMapping("user/remove/{uId}")
    public Object removeUser(@PathVariable("uId") Integer uId) {
        User user = new User();
        user.setId(uId);
        userService.saveUser(user);
        return "已移除";
    }
}
