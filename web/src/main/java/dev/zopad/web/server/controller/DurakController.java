package dev.zopad.web.server.controller;

import com.fasterxml.jackson.annotation.JsonValue;
import dev.zopad.durak.state.GameState;
import dev.zopad.web.server.service.DurakService;
import dev.zopad.web.server.transfer.HitCardsCriteria;
import dev.zopad.web.server.transfer.SendCardsCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DurakController {

    @Autowired
    private DurakService service;

    @GetMapping
    public String hello() {
        return "Hell0 w0rld";
    }

    @GetMapping(value = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameState newGame(@RequestParam("username") String username) {
        return GameState.startNewGameOnePlayer(username);
    }

    // TODO these 2 posts should return the new state to the client (including drawn cards)
    // so we don't need polling or pushing it later.
    @PostMapping(value = "/sendCards", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendCards(SendCardsCriteria sendCardsCriteria) {

    }

    @PostMapping(value = "/hitCards", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void hitcards(HitCardsCriteria hitCardsCriteria) {

    }




}
