package dev.zopad;

import dev.zopad.durak.logic.GameLogic;
import dev.zopad.durak.state.GameState;

public class Main {

    public static void main(String[] args) {
        GameState gameState = new GameState(4);
        gameState.diag();
        GameLogic gameLogic = new GameLogic(gameState);
        gameLogic.play();
    }
}
