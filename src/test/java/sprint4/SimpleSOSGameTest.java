package sprint4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class SimpleSOSGameTest {
    @Test
    void testMakeMoveEndsGameOnWinningMove() {
        SimpleSOSGame game = new SimpleSOSGame(3);
        // Pre‑fill so that (1,2) completes S‑O‑S horizontally
        game.getBoard()[1][0] = 'S';
        game.getBoard()[1][1] = 'O';

        assertFalse(game.isGameOver());
        boolean moved = game.makeMove(1, 2, 'S');
        assertTrue(moved);
        assertTrue(game.isGameOver(), "Game should end when an SOS is formed");
    }

    @Test
    void testMakeMoveReturnsFalseAfterGameOver() {
        SimpleSOSGame game = new SimpleSOSGame(3);
        // First move wins the game
        game.getBoard()[0][0] = 'S';
        game.getBoard()[0][1] = 'O';
        assertTrue(game.makeMove(0, 2, 'S'));
        assertTrue(game.isGameOver());

        // Further moves should be rejected
        assertFalse(game.makeMove(1, 1, 'O'),
                "makeMove should return false once the game is over");
    }

    @Test
    void testSwitchTurnAfterNonScoringMove() {
        SimpleSOSGame game = new SimpleSOSGame(3);
        GUIGameLogic.Player start = game.getCurrentPlayer();
        // Make a move that does NOT form SOS
        assertTrue(game.makeMove(1, 1, 'S'));
        GUIGameLogic.Player after = game.getCurrentPlayer();
        assertNotEquals(start, after,
                "Turn should switch after a non‑scoring move in Simple mode");
        assertFalse(game.isGameOver());
    }

    @Test
    void testDrawEndsGameWhenBoardFilledNoSOS() {
        SimpleSOSGame game = new SimpleSOSGame(3);
        char[][] b = game.getBoard();
        // Fill all but (2,2) with 'S' (no 'O' anywhere, so no SOS possible)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 2 && j == 2) continue;
                b[i][j] = 'S';
            }
        }
        assertFalse(game.isBoardFull());
        // Last move should fill board, not form SOS, and end game in draw
        boolean moved = game.makeMove(2, 2, 'O');
        assertTrue(moved);
        assertTrue(game.isBoardFull());
        assertTrue(game.isGameOver(), "Game should end in a draw when the board fills without any SOS");
    }
}