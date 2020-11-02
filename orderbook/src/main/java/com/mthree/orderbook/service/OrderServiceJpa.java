package com.mthree.orderbook.service;

import com.mthree.orderbook.entities.*;
import com.mthree.orderbook.repositories.MarketParticipantRepository;
import com.mthree.orderbook.repositories.OrderRepository;
import com.mthree.orderbook.repositories.StockRepository;
import com.mthree.orderbook.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceJpa implements OrderService {

    private final OrderRepository orderRepository;
    private final MarketParticipantRepository marketParticipantRepository;
    private final StockRepository stockRepository;
    private final TradeRepository tradeRepository;

    @Autowired
    public OrderServiceJpa(OrderRepository orderRepository, MarketParticipantRepository marketParticipantRepository, StockRepository stockRepository, TradeRepository tradeRepository) {
        this.orderRepository = orderRepository;
        this.marketParticipantRepository = marketParticipantRepository;
        this.stockRepository = stockRepository;
        this.tradeRepository = tradeRepository;
    }

    @Override
    public Order addOrder(Order order) {
        orderRepository.save(order);
        if (order.getSide() == Side.BUY) {
            return matchBidOrder(order);
        } else {
            return matchOfferOrder(order);
        }
    }

    @Override
    public Order matchBidOrder(Order bid) {
        BigDecimal bidPrice = bid.getPrice();
        BigDecimal offerPrice;
        List<Order> offerList = orderRepository.findAllOffersByPriceThenDate(bid.getStock().getId());
        for (Order offer : offerList) {
            offerPrice = offer.getPrice();
            if (bidPrice.compareTo(offerPrice) != -1) {
                int bidQuantity = bid.getQuantity();
                int offerQuantity = offer.getQuantity();
                Trade trade = new Trade();
                if (bidQuantity >= offerQuantity) {
                    offer.setQuantity(0);
                    bid.setQuantity(bidQuantity - offerQuantity);
                    trade.setQuantity(offerQuantity);
                    trade.setPrice(offerPrice);
                } else {
                    bid.setQuantity(0);
                    offer.setQuantity(offerQuantity - bidQuantity);
                    trade.setQuantity(bidQuantity);
                    trade.setPrice(bidPrice);
                }
                tradeRepository.save(trade);
                offer.addTrade(trade);
                bid.addTrade(trade);
                orderRepository.save(offer);
            }
        }
        orderRepository.save(bid);
        return bid;
    }

    @Override
    public Order matchOfferOrder(Order offer) {
        BigDecimal offerPrice = offer.getPrice();
        BigDecimal bidPrice;
        List<Order> bidList = orderRepository.findAllBidsByPriceThenDate(offer.getStock().getId());
        for (Order bid : bidList) {
            bidPrice = bid.getPrice();
            if (bidPrice.compareTo(offerPrice) != -1) {
                int bidQuantity = bid.getQuantity();
                int offerQuantity = offer.getQuantity();
                Trade trade = new Trade();
                if (bidQuantity >= offerQuantity) {
                    offer.setQuantity(0);
                    bid.setQuantity(bidQuantity - offerQuantity);
                    trade.setQuantity(offerQuantity);
                    trade.setPrice(offerPrice);
                } else {
                    bid.setQuantity(0);
                    offer.setQuantity(offerQuantity - bidQuantity);
                    trade.setQuantity(bidQuantity);
                    trade.setPrice(bidPrice);
                }
                tradeRepository.save(trade);
                bid.addTrade(trade);
                offer.addTrade(trade);
                orderRepository.save(bid);

            }
        }
        orderRepository.save(offer);
        return offer;
    }

    @Override
    public List<Order> getActiveOffers(long stockId) {
        List<Order> activeOffers = orderRepository.findAllOffersByPriceThenDate(stockId);
        activeOffers.forEach(offer -> offer.setTrades(new ArrayList<>()));
        return activeOffers;
    }

    @Override
    public List<Order> getActiveBids(long stockId) {
        List<Order> activeBids = orderRepository.findAllBidsByPriceThenDate(stockId);
        activeBids.forEach(bid -> bid.setTrades(new ArrayList<>()));
        return activeBids;
    }

    @Override
    public int getTodaysOrderVolume(long stockId) {
        return orderRepository.getTodaysOrderVolume(stockId);
    }

    @Override
    public Order deleteOrder(long orderId) {
        Order orderToBeDeleted = orderRepository.getOne(orderId);
        if (orderToBeDeleted != null) {
            orderRepository.delete(orderToBeDeleted);
        }
        return orderToBeDeleted;
    }

}
