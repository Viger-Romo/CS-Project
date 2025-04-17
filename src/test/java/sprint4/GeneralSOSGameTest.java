package sprint4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class GeneralSOSGameTest {
    @Test
    void testMakeMoveKeepsTurnAfterSOS() {
        GeneralSOSGame game = new GeneralSOSGame(3);
        // Pre‑fill so that (2,2) completes S‑O‑S vertically at bottom left → bottom right
        game.getBoard()[2][0] = 'S';
        game.getBoard()[2][1] = 'O';

        GUIGameLogic.Player before = game.getCurrentPlayer();
        boolean moved = game.makeMove(2, 2, 'S');

        assertTrue(moved);
        assertFalse(game.isGameOver());
        assertEquals(before, game.getCurrentPlayer(),
                "Turn should not switch after a scoring move in General mode");
    }

    @Test
    void testMakeMoveEndsGameOnFullBoard() {
        GeneralSOSGame game = new GeneralSOSGame(3);
        // Fill all but (2,2) with any non-'\0' char (here all 'S'), so board is nearly full
        char[][] b = game.getBoard();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (!(i == 2 && j == 2))
                    b[i][j] = 'S';
        assertFalse(game.isBoardFull());
        // Last move into (2,2) ends the game
        boolean moved = game.makeMove(2, 2, 'S');
        assertTrue(moved);
        assertTrue(game.isGameOver(), "Game should end when the board becomes full in General mode");
    }

    @Test
    void testScoreIncrementsByMultipleSOS() {
        GeneralSOSGame game = new GeneralSOSGame(3);
        // Pre‑fill to create two SOS sequences when placing 'O' at (1,1)
        game.getBoard()[1][0] = 'S';
        game.getBoard()[1][2] = 'S';
        game.getBoard()[0][1] = 'S';
        game.getBoard()[2][1] = 'S';

        int beforeScore = game.getBlueScore();
        assertTrue(game.makeMove(1, 1, 'O'),
                "Move forming two SOS sequences should succeed");
        assertEquals(beforeScore + 2, game.getBlueScore(),
                "Score should increase by the number of SOS formed");
        assertFalse(game.isGameOver(),
                "Game should not end just because SOS were formed in General mode");
        assertEquals(GUIGameLogic.Player.Blue, game.getCurrentPlayer(),
                "Same player retains turn after scoring");
    }

    @Test
    void testSwitchTurnAfterNonScoringMove() {
        GeneralSOSGame game = new GeneralSOSGame(3);
        GUIGameLogic.Player start = game.getCurrentPlayer();

        assertTrue(game.makeMove(1, 1, 'S'),
                "Non‑scoring move should succeed");
        assertFalse(game.isGameOver(),
                "Game should not end on a non‑scoring move");
        assertNotEquals(start, game.getCurrentPlayer(),
                "Turn should switch after a non‑scoring move in General mode");
    }
}