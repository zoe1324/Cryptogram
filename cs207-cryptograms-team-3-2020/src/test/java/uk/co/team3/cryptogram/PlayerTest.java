package uk.co.team3.cryptogram;

import org.junit.jupiter.api.Test;
import uk.co.team3.cryptogram.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void newPlayerStatsBeginZero() {
        Player player = new Player("test");
        assertEquals(0, player.getCryptogramsCompleted());
        assertEquals(0, player.getCryptogramsPlayed());
        assertEquals(0, player.getTotalGuesses());
        assertEquals(0, player.getCorrectGuesses());
    }

    @Test
    void incrementCorrectGuesses() {
        Player player = new Player("test");
        player.incrementCorrectGuesses();
        assertEquals(1, player.getCorrectGuesses());
    }

    @Test
    void incrementTotalGuesses() {
        Player player = new Player("test");
        player.incrementTotalGuesses();
        assertEquals(1, player.getTotalGuesses());
    }

    @Test
    void decrementTotalGuesses() {
        Player player = new Player("test");
        player.incrementTotalGuesses();
        assertEquals(1, player.getTotalGuesses());
        player.decrementTotalGuesses();
        assertEquals(0, player.getTotalGuesses());
    }

    @Test
    void decrementTotalGuessesFromZero() {
        Player player = new Player("test");
        player.decrementTotalGuesses();
        assertEquals(0, player.getTotalGuesses());
    }

    @Test
    void incrementCryptogramsPlayed() {
        Player player = new Player("test");
        player.incrementCryptogramsPlayed();
        assertEquals(1, player.getCryptogramsPlayed());
    }

    @Test
    void incrementCryptogramsCompleted() {
        Player player = new Player("test");
        player.incrementCryptogramsCompleted();
        assertEquals(1, player.getCryptogramsCompleted());
    }

    @Test
    void getAccuracy() {
        Player player = new Player("test");
        for (int i = 0; i < 3; i++) {
            player.incrementCorrectGuesses();
        }
        for (int i = 0; i < 4; i++) {
            player.incrementTotalGuesses();
        }
        assertEquals(75, player.getAccuracy());
    }
}