package com.nikola3in1.repository;

import com.nikola3in1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRespository extends JpaRepository<User, Long> {
    @Query(value = "select * from `user` where `user`.`user_username` = :username", nativeQuery = true)
    User findByUsername(String username);
}
