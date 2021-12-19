package dev.zopad.durak.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Hand {
    private static final Logger logger = LoggerFactory.getLogger(Hand.class);
    private final List<Card> cards;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public Card getWorstCard(List<Card> cardList) {
        return cardList.stream().min(Comparator.comparingInt(Card::getPower))
                .orElseThrow(() -> new IllegalStateException("Could not select worst card. Cards were: " + cards));
    }

    public List<Card> ascendingPowerOrder() {
        debugCheckCardsAreDistinct();
        cards.sort(Comparator.comparingInt(Card::getPower));
        return cards;
    }

    public List<Card> sendCards(int limit) {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Tried to send cards with empty hand!");
        }
        if (limit > 5) {
            limit = 5; // max limit to send is 5.
        }
        if (limit == 2 || limit == 4) {
            limit = limit - 1; // limit must be an odd number.
        }
        Set<Card> pairs = new HashSet<>();
        // for now, try to make the most, worst pairs
        List<Card> cards = ascendingPowerOrder();
        cards.forEach((c1) -> cards.forEach((c2 -> {
            // max 2 pairs (4 cards) allowed.
            if (pairs.size() < 4 && !pairs.contains(c1) && !pairs.contains(c2) && c1.canPair(c2)) {
                pairs.add(c1);
                pairs.add(c2);
            }
        })));
        List<Card> cardsToSend = new ArrayList<>(pairs);

        if (this.cards.size() == cardsToSend.size() && !cardsToSend.isEmpty()) {
            // e.g. 4 cards, can not put down 2 pairs because we need a sender
            logger.debug("Have to remove last 2 cards from sending: " + cardsToSend);
            cardsToSend.remove(cardsToSend.size() - 1);
            cardsToSend.remove(cardsToSend.size() - 1);
        }
        if (cardsToSend.size() > limit) {
            logger.debug("Have to remove cards from sending due to limit: " + limit);
            while (cardsToSend.size() > limit) {
                cardsToSend.remove(cardsToSend.size() - 1);
            }
        }

        if (cardsToSend.size() % 2 == 0) {
            // need to add one more card to send
            List<Card> remainingCards = cards.stream().filter(Predicate.not(cardsToSend::contains)).collect(Collectors.toList());
            Card worstCard = getWorstCard(remainingCards); // do not duplicate card, choose from remaining cards
            cardsToSend.add(worstCard);
        }

        if (cardsToSend.size() % 2 == 0) {
            throw new IllegalStateException(String.format("Tried to send %d cards", cardsToSend.size()));
        }

        logger.info("Sending cards: " + cardsToSend);
        this.cards.removeAll(cardsToSend);
        return cardsToSend;
    }


    /**
     * Can be modified in place which is convenient for removal
     * (putting down cards).
     *
     * @return list of cards currently in hand for a player
     */
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return cards.toString();
    }

    private void debugCheckCardsAreDistinct() {
        List<Card> distinctCards = cards.stream().distinct().collect(Collectors.toList());
        List<Card> currentCardsCopy = new ArrayList<>(cards);
        currentCardsCopy.removeAll(distinctCards);
        if (!currentCardsCopy.isEmpty()) {
            throw new IllegalStateException("Found duplicate cards: " + currentCardsCopy);
        }
    }
}
