package sprint2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GUIGameLogicTest {

    private GUIGameLogic gameLogic;

    @BeforeEach
    void setUp() {
        // Initialize the game logic with a default board size and game mode before each test
        gameLogic = new GUIGameLogic(5, GUIGameLogic.GameMode.Simple);
    }

    // User Story 1: Choose a board size
    @Test
    void testBoardSizeInitialization() {
        assertEquals(5, gameLogic.getBoardSize(), "Board size should be initialized to 5");
    }

    // User Story 2: Choose the game mode of a chosen board
    @Test
    void testGameModeInitialization() {
        assertEquals(GUIGameLogic.GameMode.Simple, gameLogic.getMode(), "Game mode should be initialized to Simple");
    }

    // User Story 3: Start a new game of the chosen board size and game mode
    @Test
    void testNewGameInitialization() {
        gameLogic = new GUIGameLogic(7, GUIGameLogic.GameMode.General);
        assertEquals(7, gameLogic.getBoardSize(), "Board size should be initialized to 7");
        assertEquals(GUIGameLogic.GameMode.General, gameLogic.getMode(), "Game mode should be initialized to General");
    }

    // User Story 4: Make a move in a simple game
    @Test
    void testMakeMoveInSimpleGame() {
        assertTrue(gameLogic.makeMove(0, 0, 'S'), "Move should be valid");
        assertEquals('S', gameLogic.getBoard()[0][0], "Board should have 'S' at position (0, 0)");
        assertEquals(GUIGameLogic.Player.Red, gameLogic.getCurrentPlayer(), "Current player should switch to Red after Blue's move");
    }

    // User Story 5: Make a move in a general game
    @Test
    void testMakeMoveInGeneralGame() {
        gameLogic = new GUIGameLogic(5, GUIGameLogic.GameMode.General);
        assertTrue(gameLogic.makeMove(1, 1, 'O'), "Move should be valid");
        assertEquals('O', gameLogic.getBoard()[1][1], "Board should have 'O' at position (1, 1)");
        assertEquals(GUIGameLogic.Player.Red, gameLogic.getCurrentPlayer(), "Current player should switch to Red after Blue's move");
    }

    // Additional test for invalid move
    @Test
    void testInvalidMove() {
        assertFalse(gameLogic.makeMove(-1, 0, 'S'), "Move should be invalid due to out-of-bounds row");
        assertFalse(gameLogic.makeMove(0, -1, 'S'), "Move should be invalid due to out-of-bounds column");
        assertFalse(gameLogic.makeMove(5, 0, 'S'), "Move should be invalid due to out-of-bounds row");
        assertFalse(gameLogic.makeMove(0, 5, 'S'), "Move should be invalid due to out-of-bounds column");
    }
}