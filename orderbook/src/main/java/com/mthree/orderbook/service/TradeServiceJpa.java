package com.mthree.orderbook.service;

import com.mthree.orderbook.entities.Order;
import com.mthree.orderbook.entities.Trade;
import com.mthree.orderbook.repositories.TradeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceJpa implements TradeService {

    private final TradeRepository tradeRepository;

    @Autowired
    public TradeServiceJpa(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> getAllTrades(long stockId) {
        List<Trade> allTrades = tradeRepository.getHistoricTrades(stockId);
        allTrades.forEach(trade -> trade.getOrders().forEach(order -> order.setTrades(new ArrayList<>())));
        return allTrades;
    }

    @Override
    public Trade getMostRecentTrade(long stockId) {
        List<Trade> allTrades = tradeRepository.getHistoricTrades(stockId);
        if (allTrades.isEmpty()) {
            return null;
        }
        Trade latestTrade = allTrades.get(0);
        latestTrade.setOrders(new ArrayList<>());
        return latestTrade;
    }

    @Override
    public int getDailyTradeVolume(long stockId) {
        return tradeRepository.getTodaysTradeVolume(stockId);
    }

}
