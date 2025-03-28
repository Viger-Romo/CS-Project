package sprint3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import sprint3.GUIGameLogic.GameMode;
import sprint3.GUIGameLogic.Player;

class GUIGameLogicTest {

    private GUIGameLogic gameLogic;
    private GUIGameLogic simpleGame;
    private GUIGameLogic generalGame;

    @BeforeEach
    void setUp() {
        // Initialize the game logic with a default board size and game mode before each test
        gameLogic = new sprint3.GUIGameLogic(5, sprint3.GUIGameLogic.GameMode.Simple);
        simpleGame = new GUIGameLogic(5, GameMode.Simple);
        generalGame = new GUIGameLogic(5, GameMode.General);
    }

    // User Story 1: Choose a board size
    @Test
    void testBoardSizeInitialization() {
        assertEquals(5, gameLogic.getBoardSize(), "Board size should be initialized to 5");
    }

    // User Story 2: Choose the game mode of a chosen board
    @Test
    void testGameModeInitialization() {
        assertEquals(sprint3.GUIGameLogic.GameMode.Simple, gameLogic.getMode(), "Game mode should be initialized to Simple");
    }

    // User Story 3: Start a new game of the chosen board size and game mode
    @Test
    void testNewGameInitialization() {
        gameLogic = new sprint3.GUIGameLogic(7, sprint3.GUIGameLogic.GameMode.General);
        assertEquals(7, gameLogic.getBoardSize(), "Board size should be initialized to 7");
        assertEquals(sprint3.GUIGameLogic.GameMode.General, gameLogic.getMode(), "Game mode should be initialized to General");
    }

    // User Story 4: Make a move in a simple game
    @Test
    void testMakeMoveInSimpleGame() {
        assertTrue(gameLogic.makeMove(0, 0, 'S'), "Move should be valid");
        assertEquals('S', gameLogic.getBoard()[0][0], "Board should have 'S' at position (0, 0)");
        assertEquals(sprint3.GUIGameLogic.Player.Red, gameLogic.getCurrentPlayer(), "Current player should switch to Red after Blue's move");
    }

    // User Story 5: Make a move in a general game
    @Test
    void testMakeMoveInGeneralGame() {
        gameLogic = new sprint3.GUIGameLogic(5, sprint3.GUIGameLogic.GameMode.General);
        assertTrue(gameLogic.makeMove(1, 1, 'O'), "Move should be valid");
        assertEquals('O', gameLogic.getBoard()[1][1], "Board should have 'O' at position (1, 1)");
        assertEquals(sprint3.GUIGameLogic.Player.Red, gameLogic.getCurrentPlayer(), "Current player should switch to Red after Blue's move");
    }

    // SOS Detection Tests
    @Test
    public void testHorizontalSOSDetection() {
        generalGame.makeMove(0, 0, 'S');
        generalGame.makeMove(0, 1, 'O');
        generalGame.makeMove(0, 2, 'S');
        assertEquals(1, generalGame.getBlueScore());
    }

    @Test
    public void testVerticalSOSDetection() {
        generalGame.makeMove(0, 0, 'S');
        generalGame.makeMove(1, 0, 'O');
        generalGame.makeMove(2, 0, 'S');
        assertEquals(1, generalGame.getBlueScore());
    }

    @Test
    public void testDiagonalSOSDetection() {
        generalGame.makeMove(0, 0, 'S');
        generalGame.makeMove(1, 1, 'O');
        generalGame.makeMove(2, 2, 'S');
        assertEquals(1, generalGame.getBlueScore());
    }

    @Test
    public void testSimpleGameEndsOnSOS(){
        // Blue creates horizontal SOS
        simpleGame.makeMove(0, 0, 'S'); // Blue's move ➔ S _ _
        simpleGame.makeMove(1, 0, 'O'); // Red's move ➔ switches back to Blue
        simpleGame.makeMove(0, 1, 'O'); // Blue's move ➔ S O _

        // Red completes the SOS
        boolean finalMoveResult = simpleGame.makeMove(0, 2, 'S'); // Red's move ➔ S O S

        // Verify game state
        assertFalse(simpleGame.makeMove(1, 1, 'S'), "Subsequent moves should fail");
        assertEquals(Player.Red, simpleGame.getCurrentPlayer(), "Red should be declared winner");
    }

    @Test
    public void testBlueWinsSimple(){
        // Vertical SOS test
        simpleGame.makeMove(0, 0, 'S'); // Blue
        simpleGame.makeMove(0, 1, 'O'); // Red
        simpleGame.makeMove(1, 0, 'O'); // Blue
        simpleGame.makeMove(1, 1, 'S'); // Red
        simpleGame.makeMove(2, 0, 'S'); // Blue creates vertical S-O-S

        assertFalse(simpleGame.makeMove(2, 2, 'O'), "Game should prevent new moves");
        assertEquals(Player.Blue, simpleGame.getCurrentPlayer(), "Blue should remain as winner");
    }

    @Test
    public void testBlueWinsGeneral(){
        // Blue creates an SOS (horizontal)
        generalGame.makeMove(0, 0, 'S'); // Blue
        generalGame.makeMove(1, 0, 'O'); // Red
        generalGame.makeMove(0, 1, 'O'); // Blue
        generalGame.makeMove(1, 1, 'S'); // Red
        generalGame.makeMove(0, 2, 'S'); // Blue

        // Blue creates another SOS (vertical)
        generalGame.makeMove(2, 0, 'S'); // Blue
        generalGame.makeMove(2, 1, 'O'); // Red
        generalGame.makeMove(3, 0, 'O'); // Blue
        generalGame.makeMove(3, 1, 'S'); // Red
        generalGame.makeMove(4, 0, 'S'); // Blue

        // Fill the board to end the game
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (generalGame.getBoard()[row][col] == '\0') {
                    generalGame.makeMove(row, col, 'O');
                }
            }
        }

        assertTrue(generalGame.getBlueScore() > generalGame.getRedScore(), "Blue should have higher score");
        assertEquals(4, generalGame.getBlueScore(), "Blue should have 4 points");
        assertEquals(0, generalGame.getRedScore(), "Red should have 0 points");
    }

    @Test
    public void testRedWinsGeneral(){
        // Blue's turn (no SOS)
        generalGame.makeMove(0, 0, 'O'); // Blue
        // Red creates an SOS (horizontal)
        generalGame.makeMove(0, 1, 'S'); // Red
        generalGame.makeMove(1, 0, 'O'); // Blue
        generalGame.makeMove(0, 2, 'O'); // Red
        generalGame.makeMove(1, 1, 'O'); // Blue
        generalGame.makeMove(0, 3, 'S'); // Red completes S-O-S (score +1)

        // Red creates another SOS (diagonal)
        generalGame.makeMove(1, 2, 'S'); // Red (gets another turn)
        generalGame.makeMove(2, 0, 'O'); // Blue
        generalGame.makeMove(2, 1, 'S'); // Red
        generalGame.makeMove(3, 0, 'S'); // Blue
        generalGame.makeMove(3, 1, 'S'); // Red
        generalGame.makeMove(4, 0, 'O'); // Blue
        generalGame.makeMove(4, 1, 'S'); // Red completes diagonal S-O-S (score +1)

        // Fill the board to end the game
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (generalGame.getBoard()[row][col] == '\0') {
                    generalGame.makeMove(row, col, 'O');
                }
            }
        }

        assertTrue(generalGame.getRedScore() > generalGame.getBlueScore(), "Red should have higher score");
        assertEquals(2, generalGame.getRedScore(), "Red should have 2 points");
        assertEquals(0, generalGame.getBlueScore(), "Blue should have 0 points");
    }
}