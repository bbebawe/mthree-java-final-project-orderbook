package com.mthree.orderbook.service;

import com.mthree.orderbook.entities.User;
import com.mthree.orderbook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceJpa implements  UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceJpa(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUser(String username, String password) {
       return userRepository.findUser(username, password);
    }
}
