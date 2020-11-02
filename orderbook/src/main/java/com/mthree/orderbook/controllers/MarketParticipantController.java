package com.mthree.orderbook.controllers;

import com.mthree.orderbook.entities.MarketParticipant;
import com.mthree.orderbook.service.MarketParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/participants")
public class MarketParticipantController {

    private MarketParticipantService marketParticipantService;

    @Autowired
    public MarketParticipantController(MarketParticipantService marketParticipantService) {
        this.marketParticipantService = marketParticipantService;
    }

    @GetMapping("/")
    public ResponseEntity<List<MarketParticipant>> getAllParticipants() {
        List<MarketParticipant> participants = marketParticipantService.getAllParticipants();
        if (participants.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/{participantId}")
    public ResponseEntity<MarketParticipant> getParticipantById(@PathVariable long participantId) {
        MarketParticipant participant = marketParticipantService.getParticipantById(participantId);
        if (participant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(participant);
    }

    @GetMapping(params = {"mpid"})
    public ResponseEntity<MarketParticipant> getParticipantByMpid(@RequestParam("mpid") String mpid) {
        MarketParticipant participant = marketParticipantService.getParticipantByMpid(mpid);
        if (participant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(participant);
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<List<MarketParticipant>> searchParticipantsByName(@RequestParam("name") String name) {
        List<MarketParticipant> participants = marketParticipantService.searchParticipantsByName(name);
        if (participants.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(participants);
    }

    @GetMapping
    @PostMapping("/add")
    public MarketParticipant addParticipant(@RequestBody MarketParticipant marketParticipant) {
        return marketParticipantService.addParticipant(marketParticipant);
    }

}
