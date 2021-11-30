package dev.zopad.durak.logic;

import dev.zopad.durak.state.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameLogic {
    private static final Logger logger = LoggerFactory.getLogger(GameLogic.class);
    private final GameState gameState;

    public GameLogic(GameState gameState) {
        this.gameState = gameState;
    }

    public void play() {
        List<PlayerState> playerStates = gameState.getPlayerStates();
        while (!gameState.isGameEnded()) {
            for (PlayerState player : playerStates) {
                // Player can still hit cards and maybe go out.
                playerTakeTurn(player);
            }
            checkLastPlayer(playerStates);
        }
    }

    private void playerTakeTurn(PlayerState playerState) {
        Deck deck = gameState.getDeck();
        Hand hand = playerState.getHand();
        logger.info("Player takes turn: " + playerState);
        boolean hitAllCards = fightCards(hand);
        if (hitAllCards) {
            drawEnoughCards(playerState, deck);
            boolean playerWentOut = checkEmptyHand(playerState, hand);
            if (playerWentOut) {
                return;
            }
            int cardCountOfNextPlayer = gameState.getCardCountOfNextPlayer(playerState);
            logger.info("Next player has " + cardCountOfNextPlayer + " cards, this is the send limit.");
            gameState.sendCards(hand.sendCards(cardCountOfNextPlayer));
        }
        drawEnoughCards(playerState, deck);
        checkEmptyHand(playerState, hand);
    }

    /**
     * @return true if player is out
     */
    private boolean checkEmptyHand(PlayerState playerState, Hand hand) {
        if (hand.getCards().isEmpty()) {
            gameState.playerIsOut(playerState);
            logger.info("Player is out: " + playerState);
            return true;
        }
        return false;
    }

    private boolean fightCards(Hand hand) {
        List<Card> sentCards = gameState.getSentCards();
        if (sentCards.isEmpty()) {
            return true;
        }
        List<Card> handCards = hand.ascendingPowerOrder();
        logger.debug("Fighting against " + sentCards + " with hand cards: " + handCards);
        List<Card> hitCards = new ArrayList<>();
        for (Card enemyCard : sentCards) {
            Optional<Card> hittingCard = handCards.stream().filter(c -> c.canHit(enemyCard)).findFirst();
            if (hittingCard.isPresent()) {
                handCards.remove(hittingCard.get());
                hitCards.add(enemyCard);
                logger.info("Hitting " + enemyCard + " with " + hittingCard.get());
            }
        }
        sentCards.removeAll(hitCards);
        if (sentCards.isEmpty()) {
            return true;
        } else {
            logger.info("Picking up : " + sentCards);
            handCards.addAll(sentCards); // had to pick up cards
            sentCards.clear(); // otherwise we're duplicating...
            return false;
        }
    }

    private void drawEnoughCards(PlayerState playerState, Deck deck) {
        List<Card> cards = playerState.getHand().getCards();
        while (cards.size() < GameState.DRAW_MAX && deck.canDraw()) {
            cards.add(deck.draw());
        }
    }

    /**
     * @return true if game ended
     */
    private boolean checkLastPlayer(List<PlayerState> playerStates) {
        if (playerStates.size() == 1) {
            logger.warn("Last player, loser is : " + playerStates.get(0));
            gameState.endGame();
            return true;
        } else if (playerStates.isEmpty()) {
            logger.warn("Everyone is out, everyone won! Congrats!");
            gameState.endGame();
            return true;
        }
        return false;
    }
}
