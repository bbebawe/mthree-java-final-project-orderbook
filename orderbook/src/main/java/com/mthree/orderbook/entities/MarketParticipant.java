package com.mthree.orderbook.entities;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "market_participant")
public class MarketParticipant {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column
    private String description;
    @Column(length = 4, nullable = false, unique = true)
    private String mpid;

    public MarketParticipant(String name, String description, String mpid) {
        this.name = name;
        this.description = description;
        this.mpid = mpid;
    }
}
