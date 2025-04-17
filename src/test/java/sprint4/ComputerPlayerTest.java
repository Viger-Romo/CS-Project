package sprint4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class ComputerPlayerTest {
    @Test
    void testGetMoveReturnsValidMoveSimple() {
        SimpleSOSGame game = new SimpleSOSGame(3);
        ComputerPlayer cpu = new ComputerPlayer(GUIGameLogic.Player.Blue);

        Move m = cpu.getMove(game);
        assertNotNull(m, "Move should not be null on an empty board");
        assertTrue(m.row >= 0 && m.row < game.getBoardSize());
        assertTrue(m.col >= 0 && m.col < game.getBoardSize());
        assertTrue(m.letter == 'S' || m.letter == 'O');
    }

    @Test
    void testGetMovePrioritizesWinningMoveSimple() {
        SimpleSOSGame game = new SimpleSOSGame(3);
        // Pre‑fill so that (0,2) completes S‑O‑S horizontally
        game.getBoard()[0][0] = 'S';
        game.getBoard()[0][1] = 'O';

        ComputerPlayer cpu = new ComputerPlayer(GUIGameLogic.Player.Blue);
        Move m = cpu.getMove(game);

        assertEquals(0, m.row);
        assertEquals(2, m.col);
        assertEquals('S', m.letter);
    }

    @Test
    void testGetMoveReturnsValidMoveGeneral() {
        GeneralSOSGame game = new GeneralSOSGame(3);
        ComputerPlayer cpu = new ComputerPlayer(GUIGameLogic.Player.Blue);

        Move m = cpu.getMove(game);
        assertNotNull(m, "Move should not be null on an empty board (General mode)");
        assertTrue(m.row >= 0 && m.row < game.getBoardSize());
        assertTrue(m.col >= 0 && m.col < game.getBoardSize());
        assertTrue(m.letter == 'S' || m.letter == 'O');
    }

    @Test
    void testGetMovePrioritizesWinningMoveGeneral() {
        GeneralSOSGame game = new GeneralSOSGame(3);
        // Pre‑fill so that (1,2) completes S‑O‑S horizontally
        game.getBoard()[1][0] = 'S';
        game.getBoard()[1][1] = 'O';

        ComputerPlayer cpu = new ComputerPlayer(GUIGameLogic.Player.Red);
        Move m = cpu.getMove(game);

        assertEquals(1, m.row);
        assertEquals(2, m.col);
        assertEquals('S', m.letter);
    }
}