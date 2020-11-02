package com.mthree.orderbook.repositories;

import com.mthree.orderbook.entities.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders"
            + " WHERE quantity > 0 AND side = 'BUY' AND stock_id = ?"
            + " ORDER BY price DESC, order_time ASC", nativeQuery = true)
    List<Order> findAllBidsByPriceThenDate(long stock_id);

    @Query(value = "SELECT * FROM orders"
            + " WHERE quantity > 0 AND side = 'SELL' AND stock_id = ?"
            + " ORDER BY price ASC, order_time ASC", nativeQuery = true)
    List<Order> findAllOffersByPriceThenDate(long stock_id);

    @Query(value = "SELECT COUNT(*) FROM orders"
            + " WHERE stock_id = ? AND DATE(order_time) = CURDATE()", nativeQuery = true)
    int getTodaysOrderVolume(long stock_id);

}
