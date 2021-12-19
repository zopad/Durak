import dev.zopad.durak.logic.GameLogic;
import dev.zopad.durak.state.GameState;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

public class StabilityTest {

    private static final int TEST_RUNS = 100;

    /**
     * Asserts that there are no exceptions during normal gameplay.
     */
    @Test
    public void testRuns() {
        for (int i = 0; i < TEST_RUNS; i++) {
            int playerCount = ThreadLocalRandom.current().nextInt(5) + 2; // [2-6] players
            GameState gameState = new GameState(playerCount);
            GameLogic gameLogic = new GameLogic(gameState);
            gameLogic.play();
        }
    }
}
