
package com.devteria.hello_spring_boot.repository;

import com.devteria.hello_spring_boot.model.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private final Map<String, User> users =
            new ConcurrentHashMap<>();

    public UserRepository() {
        // Tài khoản mẫu phục vụ thực hành
        users.put("admin",
                new User(1, "admin", "123456", null));

        users.put("student",
                new User(2, "student", "123456", null));
    }

    public User findByUserName(String userName) {
        return users.get(userName);
    }

    public void save(User user) {
        users.put(user.getUserName(), user);
    }
}