package com.mthree.orderbook.repositories;

import com.mthree.orderbook.entities.Trade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query(value = "SELECT COUNT(*)/2 FROM trade LEFT JOIN trade_orders"
            + " ON trade.id = trade_orders.trade_id"
            + " JOIN orders ON trade_orders.order_id = orders.id"
            + " WHERE orders.stock_id = ?"
            + " AND DATE(order_time) = CURDATE()", nativeQuery = true)
    int getTodaysTradeVolume(long stockId);

    @Query(value = "SELECT DISTINCT trade.* FROM trade LEFT JOIN trade_orders"
            + " ON trade.id = trade_orders.trade_id"
            + " JOIN orders ON trade_orders.order_id = orders.id"
            + " WHERE orders.stock_id = ?"
            + " ORDER BY trade.trade_time DESC", nativeQuery = true)
    List<Trade> getHistoricTrades(long stockId);

}
