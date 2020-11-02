package com.mthree.orderbook.service;

import com.mthree.orderbook.entities.Order;

import java.util.List;

public interface OrderService {

    Order addOrder(Order order);

    Order matchBidOrder(Order bid);

    Order matchOfferOrder(Order offer);

    List<Order> getActiveOffers(long stock_id);

    List<Order> getActiveBids(long stock_id);

    int getTodaysOrderVolume(long stock_id);

    Order deleteOrder(long order_id);
}
