package dev.zopad.durak.state;

import dev.zopad.durak.logic.GameLogic;
import dev.zopad.durak.logic.I18N;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameState {
    public static final int DRAW_MAX = 5;
    public static final Color TRUMP = chooseTrump();
    private static final Logger logger = LoggerFactory.getLogger(GameState.class);

    private final List<PlayerState> playerStates;
    private final Deck deck;
    private final int playerCount;
    private boolean gameEnded = false;
    private final List<Card> sentCards = new ArrayList<>();
    private final String[] playerNames = {"Andor", "Béla", "Cecil", "Dénes", "Edmond", "Feri"};

    public GameState(int playerCount) {
        this(playerCount, null);
    }

    public GameState(int playerCount, String username) {
        if (StringUtils.isNotEmpty(username)) {
            playerNames[0] = username;
        }
        this.playerCount = playerCount;
        playerStates = new CopyOnWriteArrayList<>();
        deck = new Deck();
        init();
    }

    public static GameState startNewGame() {
        GameState gameState = new GameState(4);
        gameState.diag();
        GameLogic gameLogic = new GameLogic(gameState);
        gameLogic.play();
        return gameState;
    }

    public static GameState startNewGameOnePlayer(String username) {
        GameState gameState = new GameState(4, username);
        gameState.diag();
        GameLogic gameLogic = new GameLogic(gameState);
        gameLogic.play();
        return gameState;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public List<PlayerState> getPlayerStates() {
        return playerStates;
    }

    public Deck getDeck() {
        return deck;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void endGame() {
        gameEnded = true;
    }

    public void playerIsOut(PlayerState playerState) {
        if (deck.canDraw()) {
            throw new IllegalStateException("Player cannot be out while there are still cards to draw.");
        }
        playerStates.remove(playerState);
    }

    public void sendCards(List<Card> cards) {
        if (!sentCards.isEmpty()) {
            throw new IllegalStateException("Tried to send cards but there were still some on board.");
        }
        sentCards.addAll(cards);
    }

    public List<Card> getSentCards() {
        return sentCards;
    }

    private void init() {
        for (int i = 0; i < playerCount; i++) {
            Hand hand = drawHand();
            PlayerState state = new PlayerState(hand, playerNames[i]);
            playerStates.add(state);
        }
    }

    private Hand drawHand() {
        List<Card> cards = new ArrayList<>(DRAW_MAX * 2);
        for (int i = 0; i < DRAW_MAX; i++) {
            Card card = deck.draw();
            cards.add(card);
        }
        return new Hand(cards);
    }

    private static Color chooseTrump() {
        int rand = ThreadLocalRandom.current().nextInt(4);
        LoggerFactory.getLogger(GameState.class).debug("Trump is: {}\n", I18N.getMessage(Color.values()[rand]));
        return Color.values()[rand];
    }

    public void diag() {
        logger.info("Started game with {} players%n", playerCount);
    }

    public int getCardCountOfNextPlayer(PlayerState playerState) {
        int naturalIndexOfCurrent = playerStates.indexOf(playerState) + 1;
        int nextIndex = naturalIndexOfCurrent == playerStates.size() ? 0 : naturalIndexOfCurrent;
        return playerStates.get(nextIndex).getHand().getCards().size();
    }
}
