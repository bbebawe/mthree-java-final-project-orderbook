package com.mthree.orderbook.service;

import com.mthree.orderbook.entities.MarketParticipant;
import com.mthree.orderbook.entities.Order;
import com.mthree.orderbook.entities.Side;
import com.mthree.orderbook.entities.Stock;
import com.mthree.orderbook.entities.Trade;
import com.mthree.orderbook.repositories.MarketParticipantRepository;
import com.mthree.orderbook.repositories.OrderRepository;
import com.mthree.orderbook.repositories.StockRepository;
import com.mthree.orderbook.repositories.TradeRepository;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TradeServiceTest {

    private final TradeService tradeService;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final MarketParticipantRepository marketParticipantRepository;
    private final TradeRepository tradeRepository;

    @Autowired
    public TradeServiceTest(TradeService tradeService, OrderRepository orderRepository, StockRepository stockRepository, MarketParticipantRepository marketParticipantRepository, TradeRepository tradeRepository) {
        this.tradeService = tradeService;
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
        this.marketParticipantRepository = marketParticipantRepository;
        this.tradeRepository = tradeRepository;
    }

    @BeforeEach
    public void setUp() {
        tradeRepository.deleteAll();
        orderRepository.deleteAll();
        stockRepository.deleteAll();
        marketParticipantRepository.deleteAll();
    }

    @Test
    public void testGetLatestTrade() {
        Stock testStock = new Stock("VOD", "Vodafone");
        MarketParticipant testParticipant = new MarketParticipant("JP Morgan Chase Bank", "Investment Bank", "JPMS");
        stockRepository.save(testStock);
        marketParticipantRepository.save(testParticipant);
        Order testOrder = new Order();
        testOrder.setMarketParticipant(testParticipant);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal("100").setScale(2));
        testOrder.setQuantity(5);
        testOrder.setSide(Side.SELL);
        Trade testTrade = new Trade();
        testTrade.setPrice(new BigDecimal("800").setScale(2));
        testTrade.setQuantity(11);
        tradeRepository.save(testTrade);
        testOrder.addTrade(testTrade);
        orderRepository.save(testOrder);

        Trade actualTrade = tradeService.getMostRecentTrade(testStock.getId());

        assertEquals(testTrade.getPrice(), actualTrade.getPrice());
        assertNull(tradeService.getMostRecentTrade(0));

    }

}
