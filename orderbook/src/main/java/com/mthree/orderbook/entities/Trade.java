package com.mthree.orderbook.entities;

import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "trade")
public class Trade {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private int quantity;
    @Column
    private BigDecimal price;
    @Column(insertable = false)
    @Generated(GenerationTime.INSERT)
    private LocalDateTime tradeTime;
    @ManyToMany(mappedBy = "trades", fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<>();

    public Trade(int quantity, BigDecimal price, LocalDateTime tradeTime, List<Order> orders) {
        this.quantity = quantity;
        this.price = price;
        this.tradeTime = tradeTime;
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
