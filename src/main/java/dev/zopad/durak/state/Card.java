package dev.zopad.durak.state;

import dev.zopad.durak.logic.I18N;

import java.util.concurrent.ThreadLocalRandom;

public class Card {
    private final Color color;
    private final Rank rank;

    public Card(Color color, Rank rank) {
        this.color = color;
        this.rank = rank;
    }

    public Color getColor() {
        return color;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean canHit(Card otherCard) {
        if (color == otherCard.getColor()) {
            return rank.ordinal() > otherCard.getRank().ordinal();
        } else return isTrump();
    }

    public boolean canPair(Card otherCard) {
        return this != otherCard && rank == otherCard.getRank();
    }

    public int getPower() {
        return rank.ordinal() + (isTrump() ? Rank.values().length : 0) + variance();
    }

    private int variance() {
        // add a little variance (stop deadlock where non-hittable cards are passed around)
        // also simulates real life \o/
        int rand = ThreadLocalRandom.current().nextInt(100);
        int variance = 0;
        if (rand < 4) {
            variance = -2;
        } else if (rand < 9) {
            variance = -1;
        }
        if (rand > 95) {
            variance = 2;
        } else if (rand > 90) {
            variance = 1;
        }
        return variance;
    }

    public boolean isTrump() {
        return color == GameState.TRUMP;
    }

    @Override
    public String toString() {
        return "{" + I18N.getMessage(color) + " " + I18N.getMessage(rank) + "}";
    }
}
