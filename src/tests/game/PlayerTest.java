package tests.game;

import main.Constants;
import main.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by samir on 9/21/16.
 */
public class PlayerTest {

    @Test
    public void validPlayerConstructor() throws Exception {
        String color = Constants.WHITE;
        String name = "Player 0";

        Player player = new Player(color, name);

        assertFalse(player.check);
        assertFalse(player.checkMate);

        assertEquals(name, player.name);
        assertEquals(color, player.color);
    }

    @Test
    public void resetPlayerState() throws Exception {
        Player player = new Player(Constants.WHITE, "P0");
        player.check = true;
        player.checkMate = true;

        player.resetState();

        assertFalse(player.check);
        assertFalse(player.checkMate);
    }

}
