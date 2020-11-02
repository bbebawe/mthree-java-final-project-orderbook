package com.mthree.orderbook.service;

import com.mthree.orderbook.entities.Trade;

import java.util.List;

public interface TradeService {
    List<Trade> getAllTrades(long stock_id);

    Trade getMostRecentTrade(long stock_id);

    int getDailyTradeVolume(long stock_id);
}
