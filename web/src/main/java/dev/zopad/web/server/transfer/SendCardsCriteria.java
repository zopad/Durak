package dev.zopad.web.server.transfer;

import dev.zopad.durak.state.Card;

import java.util.List;

public class SendCardsCriteria {

    private final String playerName;
    private final String gameId;
    private final List<Card> sentCards;

    public SendCardsCriteria(String playerName, String gameId, List<Card> sentCards) {
        this.playerName = playerName;
        this.gameId = gameId;
        this.sentCards = sentCards;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getGameId() {
        return gameId;
    }

    public List<Card> getSentCards() {
        return sentCards;
    }
}
