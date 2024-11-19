package com.example.service;

import com.example.dao.UserDao;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    // Get all users
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    // Check if a user with the given login exists
    public boolean userExists(String login) {
        return userDao.findByLogin(login) != null;
    }

    // Fetch a user by login
    public User getUserByLogin(String login) {
        return userDao.findByLogin(login);
    }

    // Save a new user
    public void saveUser(User user) {
        userDao.save(user);
    }
}
