package dev.zopad.web.server.transfer;

import dev.zopad.durak.state.Card;

import java.util.Map;

public class HitCardsCriteria {

    private final String playerName;
    private final String gameId;
    private final Map<Card, Card> hitCards;

    public HitCardsCriteria(String playerName, String gameId, Map<Card, Card> hitCards) {
        this.playerName = playerName;
        this.gameId = gameId;
        this.hitCards = hitCards;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getGameId() {
        return gameId;
    }

    public Map<Card, Card> getHitCards() {
        return hitCards;
    }
}
