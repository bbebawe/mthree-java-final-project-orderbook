package com.mthree.orderbook.repositories;

import com.mthree.orderbook.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findBySymbol(String symbol);

    @Query(value = "SELECT s.id, s.symbol, s.name FROM stock s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
    List<Stock> searchByName(String name);

}
