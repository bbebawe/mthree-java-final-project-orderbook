package com.mthree.orderbook.service;

import com.mthree.orderbook.entities.User;

public interface UserService {
    User findUser(String username, String password);
}
