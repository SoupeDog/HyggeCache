package org.xavier.hyggecache.example.service;


import org.xavier.hyggecache.annotation.CacheInvalidate;
import org.xavier.hyggecache.annotation.CacheUpdate;
import org.xavier.hyggecache.annotation.Cacheable;
import org.xavier.hyggecache.annotation.CachedConfig;
import org.xavier.hyggecache.example.model.User;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.16
 * @since Jdk 1.8
 */
@CachedConfig(prefix = "u:")
public interface UserService {
    @Cacheable(key = "#id.toString()")
    User getUserById(int id);

    @CacheInvalidate(key = "#user.id.toString()")
    void saveUser(User user);

    @CacheUpdate(key = "#user.id.toString()")
    void updateUser(User user);
}
