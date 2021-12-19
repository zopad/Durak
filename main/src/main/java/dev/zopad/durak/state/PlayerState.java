package dev.zopad.durak.state;

public class PlayerState {
    private Hand hand;
    private String playerName;


    public PlayerState(Hand hand, String playerName) {
        this.hand = hand;
        this.playerName = playerName;
    }

    public Hand getHand() {
        return hand;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String toString() {
        return "PlayerState{" +
                "hand=" + hand +
                ", playerName='" + playerName + '\'' +
                '}';
    }
}
