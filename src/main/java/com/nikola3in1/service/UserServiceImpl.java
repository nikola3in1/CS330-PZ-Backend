package com.nikola3in1.service;

import com.nikola3in1.model.User;
import com.nikola3in1.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRespository userRepo;

    @Override
    public User findById(Long id) {
        Optional<User> res = userRepo.findById(id);
        return res.orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public void save(User user) {
    userRepo.save(user);
    }
}
