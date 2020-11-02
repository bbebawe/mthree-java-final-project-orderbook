package com.mthree.orderbook.service;

import com.mthree.orderbook.entities.Stock;

import java.util.List;

public interface StockService {
    List<Stock> getAllStock();

    Stock getStockBySymbol(String symbol);

    Stock getStockById(long stockId);

    List<Stock> searchStocksByName(String name);

    Stock addStock(Stock stock);
}
