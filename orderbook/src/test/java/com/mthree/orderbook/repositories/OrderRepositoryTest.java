package com.mthree.orderbook.repositories;

import com.mthree.orderbook.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderRepositoryTest {

    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;
    private final MarketParticipantRepository marketParticipantRepository;
    private final StockRepository stockRepository;

    @Autowired
    public OrderRepositoryTest(OrderRepository orderRepository, TradeRepository tradeRepository, MarketParticipantRepository marketParticipantRepository, StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.tradeRepository = tradeRepository;
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
    public void test_addOrder() {
        Stock testStock = new Stock("VOD", "Vodafone");
        MarketParticipant testParticipant = new MarketParticipant("JP Morgan Chase Bank", "Investment Bank", "JPMS");
        marketParticipantRepository.save(testParticipant);
        stockRepository.save(testStock);
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
        Order savedOrder = orderRepository.findById(testOrder.getId()).orElse(null);
//        assertEquals(testOrder, savedOrder);
//        System.out.println(testTrade.toString());
//        System.out.println(testOrder.toString());
    }

    @Test
    public void testGetOrderedBids() {
        List<Order> expectedOrderedBids = new ArrayList<>();
        Stock testStock = new Stock("VOD", "Vodafone");
        MarketParticipant testParticipant = new MarketParticipant("JP Morgan Chase Bank", "Investment Bank", "JPMS");
        Order testOrder = new Order();
        marketParticipantRepository.save(testParticipant);
        stockRepository.save(testStock);
        testOrder.setMarketParticipant(testParticipant);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal("50").setScale(2));
        testOrder.setQuantity(5);
        testOrder.setSide(Side.BUY);
        orderRepository.save(testOrder);
        testOrder.setPrice(new BigDecimal("60").setScale(2));
        orderRepository.save(testOrder);

        Order testOrder2 = new Order();
        MarketParticipant testParticipant2 = new MarketParticipant("George", "Investment Bank", "GEO");
        marketParticipantRepository.save(testParticipant2);
        testOrder2.setMarketParticipant(testParticipant2);
        testOrder2.setStock(testStock);
        testOrder2.setPrice(new BigDecimal("100").setScale(2));
        testOrder2.setQuantity(5);
        testOrder2.setSide(Side.BUY);
        orderRepository.save(testOrder2);

        Order testOrder3 = new Order();
        MarketParticipant testParticipant3 = new MarketParticipant("Beshoy", "Investment Bank", "BESH");
        marketParticipantRepository.save(testParticipant3);
        testOrder3.setMarketParticipant(testParticipant3);
        testOrder3.setStock(testStock);
        testOrder3.setPrice(new BigDecimal("100").setScale(2));
        testOrder3.setQuantity(5);
        testOrder3.setSide(Side.BUY);
        orderRepository.save(testOrder3);

        Order testOrder4 = new Order();
        MarketParticipant testParticipant4 = new MarketParticipant("Hannah", "Investment Bank", "HAN");
        marketParticipantRepository.save(testParticipant4);
        testOrder4.setMarketParticipant(testParticipant4);
        testOrder4.setStock(testStock);
        testOrder4.setPrice(new BigDecimal("150").setScale(2));
        testOrder4.setQuantity(5);
        testOrder4.setSide(Side.BUY);
        orderRepository.save(testOrder4);

        expectedOrderedBids.add(testOrder4);
        expectedOrderedBids.add(testOrder2);
        expectedOrderedBids.add(testOrder3);
        expectedOrderedBids.add(testOrder);

        List<Order> actualOrderedBids = orderRepository.findAllBidsByPriceThenDate(testStock.getId());

        assertEquals(4, actualOrderedBids.size());

//        assertEquals(expectedOrderedBids.toString(), actualOrderedBids.toString());
    }

    @Test
    public void testGetOrderedOffers() {
        List<Order> expectedOrderedBids = new ArrayList<>();
        Stock testStock = new Stock("VOD", "Vodafone");
        MarketParticipant testParticipant = new MarketParticipant("JP Morgan Chase Bank", "Investment Bank", "JPMS");
        Order testOrder = new Order();
        marketParticipantRepository.save(testParticipant);
        stockRepository.save(testStock);
        testOrder.setMarketParticipant(testParticipant);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal("250.00").setScale(2));
        testOrder.setQuantity(5);
        testOrder.setSide(Side.SELL);
        orderRepository.save(testOrder);

        Order testOrder2 = new Order();
        MarketParticipant testParticipant2 = new MarketParticipant("George", "Investment Bank", "GEO");
        marketParticipantRepository.save(testParticipant2);
        testOrder2.setMarketParticipant(testParticipant2);
        testOrder2.setStock(testStock);
        testOrder2.setPrice(new BigDecimal("100").setScale(2));
        testOrder2.setQuantity(5);
        testOrder2.setSide(Side.SELL);
        orderRepository.save(testOrder2);

        Order testOrder3 = new Order();
        MarketParticipant testParticipant3 = new MarketParticipant("Beshoy", "Investment Bank", "BESH");
        marketParticipantRepository.save(testParticipant3);
        testOrder3.setMarketParticipant(testParticipant3);
        testOrder3.setStock(testStock);
        testOrder3.setPrice(new BigDecimal("100").setScale(2));
        testOrder3.setQuantity(5);
        testOrder3.setSide(Side.SELL);
        orderRepository.save(testOrder3);

        Order testOrder4 = new Order();
        MarketParticipant testParticipant4 = new MarketParticipant("Hannah", "Investment Bank", "HAN");
        marketParticipantRepository.save(testParticipant4);
        testOrder4.setMarketParticipant(testParticipant4);
        testOrder4.setStock(testStock);
        testOrder4.setPrice(new BigDecimal("40.00").setScale(2));
        testOrder4.setQuantity(5);
        testOrder4.setSide(Side.SELL);
        orderRepository.save(testOrder4);

        expectedOrderedBids.add(testOrder4);
        expectedOrderedBids.add(testOrder2);
        expectedOrderedBids.add(testOrder3);
        expectedOrderedBids.add(testOrder);

        List<Order> actualOrderedBids = orderRepository.findAllOffersByPriceThenDate(testStock.getId());

        assertEquals(4, actualOrderedBids.size());
//        assertEquals(expectedOrderedBids.toString(), actualOrderedBids.toString());
    }

    @Test
    public void testGetOrderVolume() {
        List<Order> expectedOrderedBids = new ArrayList<>();
        Stock testStock = new Stock("VOD", "Vodafone");
        MarketParticipant testParticipant = new MarketParticipant("JP Morgan Chase Bank", "Investment Bank", "JPMS");
        Order testOrder = new Order();
        marketParticipantRepository.save(testParticipant);
        stockRepository.save(testStock);
        testOrder.setMarketParticipant(testParticipant);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal("250.00").setScale(2));
        testOrder.setQuantity(5);
        testOrder.setSide(Side.SELL);
        orderRepository.save(testOrder);

        Order testOrder2 = new Order();
        MarketParticipant testParticipant2 = new MarketParticipant("George", "Investment Bank", "GEO");
        marketParticipantRepository.save(testParticipant2);
        testOrder2.setMarketParticipant(testParticipant2);
        testOrder2.setStock(testStock);
        testOrder2.setPrice(new BigDecimal("100").setScale(2));
        testOrder2.setQuantity(5);
        testOrder2.setSide(Side.SELL);
        orderRepository.save(testOrder2);

        Order testOrder3 = new Order();
        MarketParticipant testParticipant3 = new MarketParticipant("Beshoy", "Investment Bank", "BESH");
        marketParticipantRepository.save(testParticipant3);
        testOrder3.setMarketParticipant(testParticipant3);
        testOrder3.setStock(testStock);
        testOrder3.setPrice(new BigDecimal("100").setScale(2));
        testOrder3.setQuantity(5);
        testOrder3.setSide(Side.BUY);
        orderRepository.save(testOrder3);

        Order testOrder4 = new Order();
        MarketParticipant testParticipant4 = new MarketParticipant("Hannah", "Investment Bank", "HAN");
        marketParticipantRepository.save(testParticipant4);
        testOrder4.setMarketParticipant(testParticipant4);
        testOrder4.setStock(testStock);
        testOrder4.setPrice(new BigDecimal("40.00").setScale(2));
        testOrder4.setQuantity(5);
        testOrder4.setSide(Side.BUY);
        orderRepository.save(testOrder4);

        int todaysVolume = orderRepository.getTodaysOrderVolume(testStock.getId());

        assertEquals(4, todaysVolume);

        Stock testStock2 = new Stock("GEO", "George");
        stockRepository.save(testStock2);

        todaysVolume = orderRepository.getTodaysOrderVolume(testStock2.getId());

        assertEquals(0, todaysVolume);

    }
}
