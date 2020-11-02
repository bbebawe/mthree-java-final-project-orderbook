package com.mthree.orderbook.repositories;

import com.mthree.orderbook.entities.MarketParticipant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MarketParticipantRepositoryTest {

    private final MarketParticipantRepository marketParticipantRepository;
    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;
    private final StockRepository stockRepository;

    @Autowired
    public MarketParticipantRepositoryTest(MarketParticipantRepository marketParticipantRepository, OrderRepository orderRepository, TradeRepository tradeRepository, StockRepository stockRepository) {
        this.marketParticipantRepository = marketParticipantRepository;
        this.orderRepository = orderRepository;
        this.tradeRepository = tradeRepository;
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
    public void test_addParticipant() {
        MarketParticipant expectedMp1 = new MarketParticipant("JP Morgan Chase Bank", "Investment Bank", "JPMS");
        marketParticipantRepository.save(expectedMp1);
        assertEquals(1, marketParticipantRepository.findAll().size());
        assertEquals(expectedMp1, marketParticipantRepository.findById(expectedMp1.getId()).orElse(null));
    }

    @Test
    public void test_getAllParticipants() {
        MarketParticipant expectedMp1 = new MarketParticipant("JP Morgan Chase Bank", "Investment Bank", "JPMS");
        MarketParticipant expectedMp2 = new MarketParticipant("Citibank, N.A", "Investment Bank", "CGQT");
        MarketParticipant expectedMp3 = new MarketParticipant("Ameritrade Inc", "Financial Services Company", "FOMA");
        marketParticipantRepository.saveAll(Arrays.asList(expectedMp1, expectedMp2, expectedMp3));
        assertEquals(3, marketParticipantRepository.findAll().size());
        List<MarketParticipant> savedParticipants = marketParticipantRepository.findAll();
        assertTrue(savedParticipants.contains(expectedMp1));
        assertTrue(savedParticipants.contains(expectedMp2));
        assertTrue(savedParticipants.contains(expectedMp3));
    }

    @Test
    public void test_deleteParticipant() {
        MarketParticipant expectedMp1 = new MarketParticipant("JP Morgan Chase Bank", "Investment Bank", "JPMS");
        MarketParticipant expectedMp2 = new MarketParticipant("Citibank, N.A", "Investment Bank", "CGQT");
        MarketParticipant expectedMp3 = new MarketParticipant("Ameritrade Inc", "Financial Services Company", "FOMA");
        marketParticipantRepository.saveAll(Arrays.asList(expectedMp1, expectedMp2, expectedMp3));
        marketParticipantRepository.deleteById(expectedMp1.getId());
        marketParticipantRepository.deleteById(expectedMp2.getId());
        List<MarketParticipant> savedParticipants = marketParticipantRepository.findAll();
        assertTrue(!savedParticipants.contains(expectedMp2.getId()));
        assertNull(marketParticipantRepository.findById(expectedMp2.getId()).orElse(null));
    }
}
