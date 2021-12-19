package dev.zopad.web.server;

import dev.zopad.durak.state.Card;

import java.util.List;

public class SendCardsMessage {
        private String userName;
        private List<Card> sentCards;

    public SendCardsMessage(String userName, List<Card> sentCards) {
        this.userName = userName;
        this.sentCards = sentCards;
    }

    public String getUserName() {
        return userName;
    }

    public List<Card> getSentCards() {
        return sentCards;
    }
}
