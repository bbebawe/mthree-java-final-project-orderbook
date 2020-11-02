package com.mthree.orderbook.controllers;

import com.mthree.orderbook.entities.Order;
import com.mthree.orderbook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public Order addOrder(@Valid @RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping("/bids/{stockId}")
    public ResponseEntity<List<Order>> getActiveBids(@PathVariable long stockId) {
        List<Order> bids = orderService.getActiveBids(stockId);
        if (bids.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(bids);
    }

    @GetMapping("/offers/{stockId}")
    public ResponseEntity<List<Order>> getActiveOffers(@PathVariable long stockId) {
        List<Order> offers = orderService.getActiveOffers(stockId);
        if (offers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/volume/{stockId}")
    public int getTodaysOrderVolume(@PathVariable long stockId) {
        return orderService.getTodaysOrderVolume(stockId);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Order> deleteOrder(@PathVariable long orderId) {
        Order deletedOrder = orderService.deleteOrder(orderId);
        if (deletedOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(deletedOrder);
    }

}
