package com.mthree.orderbook.entities;

import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @NotNull
    @Min(0)
    private BigDecimal price;
    @Column(nullable = false)
    @NotNull
    @Min(0)
    private int quantity;
    @Column(insertable = false)
    @Generated(GenerationTime.INSERT)
    private LocalDateTime orderTime;
    @Column
    @Enumerated(EnumType.STRING)
    private Side side;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "participant_id")
    private MarketParticipant marketParticipant;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_id")
    private Stock stock;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trade_orders",
            joinColumns = {
                @JoinColumn(name = "order_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "trade_id")})
    private List<Trade> trades = new ArrayList<>();

    public Order(BigDecimal price, int quantity, LocalDateTime orderTime, Side side, MarketParticipant marketParticipant, Stock stock, List<Trade> trades) {
        this.price = price;
        this.quantity = quantity;
        this.orderTime = orderTime;
        this.side = side;
        this.marketParticipant = marketParticipant;
        this.stock = stock;
        this.trades = trades;
    }

    public void addTrade(Trade trade) {
        this.trades.add(trade);
    }
}
