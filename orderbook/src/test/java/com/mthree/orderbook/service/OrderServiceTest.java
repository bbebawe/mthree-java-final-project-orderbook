package com.mthree.orderbook.service;

import com.mthree.orderbook.entities.MarketParticipant;
import com.mthree.orderbook.entities.Order;
import com.mthree.orderbook.entities.Side;
import com.mthree.orderbook.entities.Stock;
import com.mthree.orderbook.repositories.TradeRepository;
import com.mthree.orderbook.repositories.MarketParticipantRepository;
import com.mthree.orderbook.repositories.OrderRepository;
import com.mthree.orderbook.repositories.StockRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final MarketParticipantRepository marketParticipantRepository;
    private final TradeRepository tradeRepository;

    @Autowired
    public OrderServiceTest(OrderService orderService, OrderRepository orderRepository, StockRepository stockRepository, MarketParticipantRepository marketParticipantRepository, TradeRepository tradeRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
        this.marketParticipantRepository = marketParticipantRepository;
        this.tradeRepository = tradeRepository;
    }

    @BeforeEach
    void setUp() {
        tradeRepository.deleteAll();
        orderRepository.deleteAll();
        stockRepository.deleteAll();
        marketParticipantRepository.deleteAll();
    }

    @Test
    public void test_addOrder() {
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
        Order actualOrder = orderService.addOrder(testOrder);

        assertEquals(testOrder, actualOrder);
    }

    @Test
    public void testMatchBidAlgorithm() {
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

        Order testBid = new Order();
        MarketParticipant testParticipant5 = new MarketParticipant("Ben", "Investment Bank", "BEN");
        marketParticipantRepository.save(testParticipant5);
        testBid.setMarketParticipant(testParticipant5);
        testBid.setStock(testStock);
        testBid.setPrice(new BigDecimal("150").setScale(2));
        testBid.setQuantity(20);
        testBid.setSide(Side.BUY);
        orderRepository.save(testBid);

        orderService.matchBidOrder(testBid);

        assertEquals(3, testBid.getTrades().size());
        assertEquals(0, orderRepository.findById(testOrder4.getId()).orElse(null).getQuantity());
        assertEquals(0, orderRepository.findById(testOrder2.getId()).orElse(null).getQuantity());
        assertEquals(0, orderRepository.findById(testOrder3.getId()).orElse(null).getQuantity());
        assertEquals(5, testBid.getQuantity());

        Order testBid2 = new Order();
        MarketParticipant testParticipant6 = new MarketParticipant("Gregory", "Investment Bank", "GREG");
        marketParticipantRepository.save(testParticipant6);
        testBid2.setMarketParticipant(testParticipant6);
        testBid2.setStock(testStock);
        testBid2.setPrice(new BigDecimal("100").setScale(2));
        testBid2.setQuantity(20);
        testBid2.setSide(Side.BUY);
        orderRepository.save(testBid2);

        orderService.matchBidOrder(testBid2);

        assertEquals(0, testBid2.getTrades().size());
        Order testBid3 = new Order();
        Stock testStock2 = new Stock("NXT", "Next");
        stockRepository.save(testStock2);
        MarketParticipant testParticipant7 = new MarketParticipant("Phillip", "Investment Bank", "PHIL");
        marketParticipantRepository.save(testParticipant7);
        testBid3.setMarketParticipant(testParticipant7);
        testBid3.setStock(testStock2);
        testBid3.setPrice(new BigDecimal("1000.00").setScale(2));
        testBid3.setQuantity(20);
        testBid3.setSide(Side.BUY);
        orderRepository.save(testBid3);

        orderService.matchBidOrder(testBid3);

        assertEquals(0, testBid3.getTrades().size());

        Order testBid4 = new Order();
        testBid4.setMarketParticipant(testParticipant7);
        testBid4.setStock(testStock);
        testBid4.setPrice(new BigDecimal("300.00").setScale(2));
        testBid4.setQuantity(1);
        testBid4.setSide(Side.BUY);
        orderRepository.save(testBid4);

        orderService.matchBidOrder(testBid4);

        assertEquals(4, orderRepository.findById(testOrder.getId()).orElse(null).getQuantity());
    }

    @Test
    public void testMatchOfferAlgorithm() {
        Stock testStock = new Stock("VOD", "Vodafone");
        MarketParticipant testParticipant = new MarketParticipant("JP Morgan Chase Bank", "Investment Bank", "JPMS");
        Order testOrder = new Order();
        marketParticipantRepository.save(testParticipant);
        stockRepository.save(testStock);
        testOrder.setMarketParticipant(testParticipant);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal("250.00").setScale(2));
        testOrder.setQuantity(5);
        testOrder.setSide(Side.BUY);
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
        testOrder4.setPrice(new BigDecimal("40.00").setScale(2));
        testOrder4.setQuantity(5);
        testOrder4.setSide(Side.BUY);
        orderRepository.save(testOrder4);

        Order testOffer = new Order();
        MarketParticipant testParticipant5 = new MarketParticipant("Ben", "Investment Bank", "BEN");
        marketParticipantRepository.save(testParticipant5);
        testOffer.setMarketParticipant(testParticipant5);
        testOffer.setStock(testStock);
        testOffer.setPrice(new BigDecimal("150").setScale(2));
        testOffer.setQuantity(20);
        testOffer.setSide(Side.SELL);
        orderRepository.save(testOffer);

        orderService.matchOfferOrder(testOffer);

        assertEquals(1, testOffer.getTrades().size());
        assertEquals(0, orderRepository.findById(testOrder.getId()).orElse(null).getQuantity());
        assertEquals(15, testOffer.getQuantity());
    }

    @Test
    public void testDeleteOrder() {
        Stock testStock = new Stock("VOD", "Vodafone");
        MarketParticipant testParticipant = new MarketParticipant("JP Morgan Chase Bank", "Investment Bank", "JPMS");
        Order testOrder = new Order();
        marketParticipantRepository.save(testParticipant);
        stockRepository.save(testStock);
        testOrder.setMarketParticipant(testParticipant);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal("250.00").setScale(2));
        testOrder.setQuantity(5);
        testOrder.setSide(Side.BUY);
        orderRepository.save(testOrder);

        List<Order> allBids = orderService.getActiveBids(testStock.getId());

        assertEquals(1, allBids.size());

        Order deletedOrder = orderService.deleteOrder(testOrder.getId());
        allBids = orderService.getActiveBids(testStock.getId());

        assertTrue(allBids.isEmpty());
//        assertEquals(testOrder, deletedOrder);
    }
}
