package com.mthree.orderbook.repositories;

import com.mthree.orderbook.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TradeRepositoryTest {

    private final TradeRepository tradeRepository;
    private final OrderRepository orderRepository;
    private final MarketParticipantRepository marketParticipantRepository;
    private final StockRepository stockRepository;

    @Autowired
    public TradeRepositoryTest(TradeRepository tradeRepository, OrderRepository orderRepository, MarketParticipantRepository marketParticipantRepository, StockRepository stockRepository) {
        this.tradeRepository = tradeRepository;
        this.orderRepository = orderRepository;
        this.marketParticipantRepository = marketParticipantRepository;
        this.stockRepository = stockRepository;
    }

    @BeforeEach
    public void setUp() {
        tradeRepository.deleteAll();
        orderRepository.deleteAll();
        marketParticipantRepository.deleteAll();
        stockRepository.deleteAll();
    }

    @Test
    public void test_AddTrade() {
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

        testTrade = tradeRepository.findById(testTrade.getId()).orElse(null);

        assertEquals(11, testTrade.getQuantity());
    }

    @Test
    public void testGetMostRecentTrade() {
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

        stockRepository.save(testStock);
        marketParticipantRepository.save(testParticipant);
        Order testOrder2 = new Order();
        testOrder2.setMarketParticipant(testParticipant);
        testOrder2.setStock(testStock);
        testOrder2.setPrice(new BigDecimal("300.50").setScale(2));
        testOrder2.setQuantity(5);
        testOrder2.setSide(Side.SELL);
        Trade testTrade2 = new Trade();
        testTrade2.setPrice(new BigDecimal("150").setScale(2));
        testTrade2.setQuantity(11);
        tradeRepository.save(testTrade2);
        testOrder2.addTrade(testTrade2);
        orderRepository.save(testOrder2);

        stockRepository.save(testStock);
        marketParticipantRepository.save(testParticipant);
        Order testOrder3 = new Order();
        testOrder3.setMarketParticipant(testParticipant);
        testOrder3.setStock(testStock);
        testOrder3.setPrice(new BigDecimal("999.99").setScale(2));
        testOrder3.setQuantity(5);
        testOrder3.setSide(Side.SELL);
        Trade testTrade3 = new Trade();
        testTrade3.setPrice(new BigDecimal("480.00").setScale(2));
        testTrade3.setQuantity(11);
        tradeRepository.save(testTrade3);
        testOrder3.addTrade(testTrade3);
        orderRepository.save(testOrder3);

        Trade mostRecent = tradeRepository.getHistoricTrades(testStock.getId()).get(0);

        assertEquals(testTrade3.getPrice(), mostRecent.getPrice());

        List<Trade> allTrades = tradeRepository.getHistoricTrades(testStock.getId());

        assertEquals(3, allTrades.size());

    }

    @Test
    public void testGetTodaysTradeVolume() {
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

        stockRepository.save(testStock);
        marketParticipantRepository.save(testParticipant);
        Order testOrder2 = new Order();
        testOrder2.setMarketParticipant(testParticipant);
        testOrder2.setStock(testStock);
        testOrder2.setPrice(new BigDecimal("300.50").setScale(2));
        testOrder2.setQuantity(5);
        testOrder2.setSide(Side.SELL);
        Trade testTrade2 = new Trade();
        testTrade2.setPrice(new BigDecimal("150").setScale(2));
        testTrade2.setQuantity(11);
        tradeRepository.save(testTrade2);
        testOrder2.addTrade(testTrade2);
        orderRepository.save(testOrder2);

        stockRepository.save(testStock);
        marketParticipantRepository.save(testParticipant);
        Order testOrder3 = new Order();
        testOrder3.setMarketParticipant(testParticipant);
        testOrder3.setStock(testStock);
        testOrder3.setPrice(new BigDecimal("999.99").setScale(2));
        testOrder3.setQuantity(5);
        testOrder3.setSide(Side.SELL);
        Trade testTrade3 = new Trade();
        testTrade3.setPrice(new BigDecimal("480.00").setScale(2));
        testTrade3.setQuantity(11);
        tradeRepository.save(testTrade3);
        testOrder3.addTrade(testTrade3);
        orderRepository.save(testOrder3);

        int todaysVolume = tradeRepository.getTodaysTradeVolume(testStock.getId());

        assertEquals(3, todaysVolume);

        Stock testStock2 = new Stock("GEO", "George");
        stockRepository.save(testStock2);

        todaysVolume = tradeRepository.getTodaysTradeVolume(testStock2.getId());

        assertEquals(0, todaysVolume);
    }
}
