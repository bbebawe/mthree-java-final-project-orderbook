package com.mthree.orderbook.service;

import com.mthree.orderbook.entities.MarketParticipant;

import java.util.List;

public interface MarketParticipantService {
    List<MarketParticipant> getAllParticipants();

    MarketParticipant getParticipantById(long participantId);

    MarketParticipant getParticipantByMpid(String mpid);

    List<MarketParticipant> searchParticipantsByName(String name);

    MarketParticipant addParticipant(MarketParticipant participant);
}
