package com.example.dao;

import com.example.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByLogin(String login);
    User findByLoginAndIdNot(String login, Integer id);
}
