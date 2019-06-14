package com.nikola3in1.service;

import com.nikola3in1.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User findById(Long id);
    List<User> findAll();
    User findByUsername(String username);
    void save(User user);
}
