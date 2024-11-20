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

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public boolean userExists(String login) {
        return userDao.findByLogin(login) != null;
    }

    public User getUserByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public void saveUser(User user) {
        userDao.save(user);
    }
    public void deleteUser(User user) {
        userDao.delete(user);
    }
}
