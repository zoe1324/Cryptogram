package uk.co.team3.cryptogram;

import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        Game game = new Game();
        try {
            game.gameLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
