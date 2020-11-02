package com.mthree.orderbook.repositories;

import com.mthree.orderbook.entities.MarketParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarketParticipantRepository extends JpaRepository<MarketParticipant, Long> {
    
    MarketParticipant findByMpid(String mpid);

    @Query(value = "SELECT p.id, p.name, p.description, p.mpid FROM market_participant p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
    List<MarketParticipant> searchByName(String name);


}
