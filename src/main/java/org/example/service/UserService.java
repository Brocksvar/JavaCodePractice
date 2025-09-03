package org.example.service;

import jakarta.validation.Valid;
import org.example.dao.UserDao;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User getUser(UUID userId) {
        return userDao.findById(userId).orElseThrow();
    }

    public void createUser(@Valid User user) {
        userDao.save(user);
    }

    public void updateUser(@Valid User user) {
        userDao.save(user);
    }

    public void deleteUser(UUID userId) {
        userDao.deleteById(userId);
    }
}
