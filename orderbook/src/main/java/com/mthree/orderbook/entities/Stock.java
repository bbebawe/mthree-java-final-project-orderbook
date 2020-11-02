package com.mthree.orderbook.entities;


import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "stock")
public class Stock {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 4, nullable = false, unique = true)
    private String symbol;
    @Column(length = 50, nullable = false)
    private String name;

    public Stock(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }
}
