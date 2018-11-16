package org.xavier.hyggecache.example.service;

import org.springframework.stereotype.Component;
import org.xavier.hyggecache.example.model.User;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.16
 * @since Jdk 1.8
 */
@Component
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(int id) {
        User user = new User();
        user.setId(id);
        user.setName("Tom #" + id);
        user.setAddress("Tianjin #" + id);
        System.out.println("真实调用 UserServiceImpl");
        return user;
    }

    @Override
    public void saveUser(User user) {
    }

    @Override
    public void updateUser(User user) {
    }
}
