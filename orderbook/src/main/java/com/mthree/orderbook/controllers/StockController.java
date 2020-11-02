package com.mthree.orderbook.controllers;

import com.mthree.orderbook.entities.Stock;
import com.mthree.orderbook.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/stocks")
public class StockController {

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStock();
        if (stocks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<Stock> getStockById(@PathVariable long stockId) {
        Stock stock = stockService.getStockById(stockId);
        if (stock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(stock);
    }

    @GetMapping(params = {"symbol"})
    public ResponseEntity<Stock> getStockBySymbol(@RequestParam("symbol") String symbol) {
        Stock stock = stockService.getStockBySymbol(symbol);
        if (stock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(stock);
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<List<Stock>> searchStocksByName(@RequestParam("name") String name) {
        List<Stock> stock = stockService.searchStocksByName(name);
        if (stock.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(stock);
    }

    @PostMapping("/addStock")
    public Stock addStock(@RequestBody Stock stock) {
        return stockService.addStock(stock);
    }

}
