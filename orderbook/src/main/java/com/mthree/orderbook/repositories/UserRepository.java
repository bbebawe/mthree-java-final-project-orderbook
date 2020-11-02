package com.mthree.orderbook.repositories;

import com.mthree.orderbook.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.id, u.username, u.password FROM users u WHERE u.username=?1 AND u.password=?2", nativeQuery = true)
    User findUser(String username, String password);
}
