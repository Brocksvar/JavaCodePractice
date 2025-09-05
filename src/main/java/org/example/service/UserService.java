package org.example.service;

import jakarta.validation.Valid;
import org.example.dao.UserDao;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<UserDto> getAllUsers() {
        return userDao.findAll().stream().map(User::fromEntity).toList();
    }

    public UserDto getUser(UUID userId) {
        return userDao.findById(userId).orElseThrow().fromEntity();
    }

    public void createUser(@Valid UserDto user) {
        userDao.save(user.toEntity());
    }

    public void updateUser(@Valid UserDto user) {
        userDao.save(user.toEntity());
    }

    public void deleteUser(UUID userId) {
        userDao.deleteById(userId);
    }
}
