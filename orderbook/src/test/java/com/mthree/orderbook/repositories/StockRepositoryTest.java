package com.mthree.orderbook.repositories;

import com.mthree.orderbook.entities.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockRepositoryTest {

    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;
    private final MarketParticipantRepository marketParticipantRepository;

    @Autowired
    public StockRepositoryTest(StockRepository stockRepository, OrderRepository orderRepository, TradeRepository tradeRepository, MarketParticipantRepository marketParticipantRepository) {
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
        this.tradeRepository = tradeRepository;
        this.marketParticipantRepository = marketParticipantRepository;
    }

    @BeforeEach
    public void setUp() {
        tradeRepository.deleteAll();
        orderRepository.deleteAll();
        marketParticipantRepository.deleteAll();
        stockRepository.deleteAll();
    }

    @Test
    public void test_addStock() {
        Stock testStock = new Stock("VOD", "Vodafone");
        Stock testStock2 = new Stock("BARC", "Barclays");
        stockRepository.save(testStock);
        stockRepository.save(testStock2);
        assertFalse(stockRepository.findAll().isEmpty());
        assertEquals(testStock, stockRepository.findById(testStock.getId()).orElse(null));
        assertEquals(2, stockRepository.findAll().size());
    }

    @Test
    public void test_getAllStocks() {
        Stock testStock = new Stock("VOD", "Vodafone");
        Stock testStock2 = new Stock("NWG", "Natwest Group Plc");
        Stock testStock3 = new Stock("BARC", "Barclays");
        stockRepository.saveAll(Arrays.asList(testStock, testStock2, testStock3));
        assertEquals(3, stockRepository.findAll().size());
        assertEquals(testStock, stockRepository.findBySymbol(testStock.getSymbol()));
        assertEquals(testStock2, stockRepository.findById(testStock2.getId()).orElse(null));
        assertEquals(testStock3, stockRepository.findById(testStock3.getId()).orElse(null));
    }

    @Test
    public void test_getStockBySymbol() {
        Stock testStock = new Stock("VOD", "Vodafone");
        Stock testStock2 = new Stock("NWG", "Natwest Group Plc");
        stockRepository.save(testStock);
        stockRepository.save(testStock2);
        assertNull(stockRepository.findBySymbol("TEST"));
        assertEquals(testStock, stockRepository.findBySymbol(testStock.getSymbol()));
        assertEquals(testStock2, stockRepository.findBySymbol(testStock2.getSymbol()));
    }

    @Test
    public void test_removeStock() {
        Stock testStock = new Stock("VOD", "Vodafone");
        Stock testStock2 = new Stock("NWG", "Natwest Group Plc");
        stockRepository.save(testStock);
        stockRepository.save(testStock2);
        stockRepository.delete(testStock);
        assertEquals(1, stockRepository.findAll().size());
        assertNull(stockRepository.findById(testStock.getId()).orElse(null));
    }

    @Test
    public void test_searchByName() {
        Stock testStock = new Stock("VOD", "Vodafone");
        Stock testStock2 = new Stock("NWG", "Natwest Group Plc");
        Stock testStock3 = new Stock("BARC", "Barclays Group Plc");
        stockRepository.saveAll(Arrays.asList(testStock, testStock2, testStock3));
        assertNotNull(stockRepository.searchByName("VO"));
        assertEquals(1, stockRepository.searchByName("VO").size());
        assertEquals(2, stockRepository.searchByName("plc").size());
        assertTrue(stockRepository.searchByName("fone").contains(testStock));
        assertTrue(stockRepository.searchByName("Ltd").isEmpty());
    }

//    @Test
//    public void test_findPaging() {
//        Stock testStock = new Stock("VOD", "Vodafone");
//        Stock testStock2 = new Stock("NWG", "Natwest Group Plc");
//        Stock testStock3 = new Stock("VOD2", "Vodafone");
//        Stock testStock4 = new Stock("NWG2", "Natwest Group Plc");
//        Stock testStock5 = new Stock("VOD3", "Vodafone");
//        Stock testStock6 = new Stock("NWG3", "Natwest Group Plc");
//        stockRepository.save(testStock);
//        stockRepository.save(testStock2);
//        stockRepository.save(testStock3);
//        stockRepository.save(testStock4);
//        stockRepository.save(testStock5);
//        stockRepository.save(testStock6);
//        assertEquals(2, stockRepository.findAllStockWithPaging(PageRequest.of(0, 2)).getSize());
//        assertEquals(2, stockRepository.findAllStockWithPaging(PageRequest.of(1, 2)).getSize());
//        assertEquals(2, stockRepository.findAllStockWithPaging(PageRequest.of(2, 2)).getSize());
//
//    }
}
