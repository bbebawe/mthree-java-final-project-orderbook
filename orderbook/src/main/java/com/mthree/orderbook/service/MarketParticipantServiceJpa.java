package com.mthree.orderbook.service;

import com.mthree.orderbook.entities.MarketParticipant;
import com.mthree.orderbook.repositories.MarketParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketParticipantServiceJpa implements MarketParticipantService {

    private MarketParticipantRepository marketParticipantRepository;

    @Autowired
    public MarketParticipantServiceJpa(MarketParticipantRepository marketParticipantRepository) {
        this.marketParticipantRepository = marketParticipantRepository;
    }

    @Override
    public List<MarketParticipant> getAllParticipants() {
        return marketParticipantRepository.findAll();
    }

    @Override
    public MarketParticipant getParticipantById(long participantId) {
        return marketParticipantRepository.findById(participantId).orElse(null);
    }

    @Override
    public MarketParticipant getParticipantByMpid(String mpid) {
        return marketParticipantRepository.findByMpid(mpid);
    }

    @Override
    public List<MarketParticipant> searchParticipantsByName(String name) {
        return marketParticipantRepository.searchByName(name);
    }

    @Override
    public MarketParticipant addParticipant(MarketParticipant participant) {
        marketParticipantRepository.save(participant);
        return participant;
    }
}
