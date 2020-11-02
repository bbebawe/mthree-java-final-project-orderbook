package com.mthree.orderbook.controllers;

import com.mthree.orderbook.entities.Trade;
import com.mthree.orderbook.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/trades")
public class TradeController {

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<List<Trade>> getAllTrades(@PathVariable long stockId) {
        List<Trade> trades = tradeService.getAllTrades(stockId);
        if (trades.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(trades);
    }

    @GetMapping("/volume/{stockId}")
    public int getDailyTradeVolume(@PathVariable long stockId) {
        return tradeService.getDailyTradeVolume(stockId);
    }

    @GetMapping("/latest/{stockId}")
    public ResponseEntity<Trade> getLatestTrade(@PathVariable long stockId) {
        Trade latestTrade = tradeService.getMostRecentTrade(stockId);
        if (latestTrade == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(latestTrade);
    }
}
