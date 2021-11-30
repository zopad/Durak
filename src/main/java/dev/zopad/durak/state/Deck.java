package dev.zopad.durak.state;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {

    private final List<Card> deck;

    public Deck() {
        deck = shuffleDeck(createDeck());
    }

    public Card draw() {
        if (!canDraw()) {
            throw new IllegalStateException("Tried to draw from empty deck!");
        }
        return deck.remove(0);
    }

    public boolean canDraw() {
        return deck.size() > 0;
    }

    private List<Card> createDeck() {
        return Arrays.stream(Color.values()).flatMap(color -> Arrays.stream(Rank.values()).map(rank -> new Card(color, rank))).collect(Collectors.toList());
    }

    private List<Card> shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
        return deck;
    }


}
