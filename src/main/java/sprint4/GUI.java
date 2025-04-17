package sprint4;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.SwingUtilities;

public class GUI extends JFrame {

    private static final int DEFAULT_BOARD_SIZE = 5;

    private JFrame frame;
    private JPanel mainPanel, topPanel, leftPanel, rightPanel, bottomPanel;
    private JRadioButton simpleGame, generalGame;
    private JTextField boardInputSize;
    private JRadioButton blueButtonS, blueButtonO, redButtonS, redButtonO, btnBlueHuman, btnBlueComputer, btnRedHuman, btnRedComputer;
    private JButton btnNewGame;
    private JLabel turnLabel, blueScoreLabel, redScoreLabel;
    private JButton[][] boardButtons;


    private GUIGameLogic gameLogic;
    private GamePlayer bluePlayer;
    private GamePlayer redPlayer;

    // Enum to track game mode for the GUI
    public enum GameMode { Simple, General }

    private BoardPanel boardPanel; //Special Panel that allows SOS lines to be drawn

    public GUI() {
        // Initialize game logic with default board size and game mode which is Simple
        gameLogic = new SimpleSOSGame(DEFAULT_BOARD_SIZE);

        // Set up JFrame
        frame = new JFrame("SOS Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        createTopPanel();
        createSidePanels();
        createBottomPanel();
        setMainPanel();
        createBoard(DEFAULT_BOARD_SIZE);


        frame.add(mainPanel); //Adds to frame
        frame.setLocationRelativeTo(null); //Centers the JFrame to middle of screen
        frame.setVisible(true);

        initializePlayers(); //Initialize the players
        checkAndPerformComputerMove(); //If first move is by computer
    }

    /**
     * Creates the main panel and adds sub panels with their components in it.
     */
    private void setMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        boardInputSize.addActionListener(e -> updateBoardSize()); //Event listener to update size on input change
    }

    /**
     * Creates the top panel of the JFrame.
     * Contains the game modes and input to change size
     */
    private void createTopPanel() {
        //Top Panel contains the Game modes and text box to change board size
        topPanel = new JPanel(new FlowLayout());
        simpleGame = new JRadioButton("Simple Game", true);
        generalGame = new JRadioButton("General Game");
        ButtonGroup gameModes = new ButtonGroup();
        gameModes.add(simpleGame);
        gameModes.add(generalGame);

        // Adds listeners to get game mode buttons
        simpleGame.addActionListener(e -> updateGameMode(GameMode.Simple));
        generalGame.addActionListener(e -> updateGameMode(GameMode.General));

        boardInputSize = new JTextField(2);

        //Adds to Panel
        topPanel.add(new JLabel("SOS Game Mode: "));
        topPanel.add(simpleGame);
        topPanel.add(generalGame);
        topPanel.add(new JLabel("Board Size:"));
        topPanel.add(boardInputSize);
    }

    /**
     * Creates Sides Panels of letter options either a blue or red player can choose when it's their turn
     * And they can also choose if they are a human player or computer
     */
    private void createSidePanels() {
        // Left Panel holds buttons for Blue Player
        leftPanel = new JPanel(new GridLayout(7, 1,0,0));
        leftPanel.add(new JLabel("Blue Player:", SwingConstants.CENTER));
        blueButtonS = new JRadioButton("S", true);
        blueButtonO = new JRadioButton("O");
        ButtonGroup blueButtonGroup = new ButtonGroup();
        blueButtonGroup.add(blueButtonS);
        blueButtonGroup.add(blueButtonO);
        leftPanel.add(blueButtonS);
        leftPanel.add(blueButtonO);

        //Adds buttons to make Blue Player a Computer or Human
        leftPanel.add(new JLabel("Player Type: ", SwingConstants.CENTER));
        btnBlueHuman = new JRadioButton("Human", true);
        btnBlueComputer = new JRadioButton("Computer");
        ButtonGroup bluePlayerType = new ButtonGroup();
        bluePlayerType.add(btnBlueHuman);
        bluePlayerType.add(btnBlueComputer);
        leftPanel.add(btnBlueHuman);
        leftPanel.add(btnBlueComputer);

        //Label to keep track of score for Blue in General Game mode
        blueScoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        leftPanel.add(blueScoreLabel);

        // Right Panel holds buttons for Red Player
        rightPanel = new JPanel(new GridLayout(7, 1, 0,0));
        rightPanel.add(new JLabel("Red Player:", SwingConstants.CENTER));
        redButtonS = new JRadioButton("S", true);
        redButtonO = new JRadioButton("O");
        ButtonGroup redButtonGroup = new ButtonGroup();
        redButtonGroup.add(redButtonS);
        redButtonGroup.add(redButtonO);
        rightPanel.add(redButtonS);
        rightPanel.add(redButtonO);

        //Adds buttons to make Red Player a Computer or Human
        rightPanel.add(new JLabel("Player Type: ", SwingConstants.CENTER));
        btnRedHuman = new JRadioButton("Human",true);
        btnRedComputer = new JRadioButton("Computer");
        ButtonGroup redPlayerType = new ButtonGroup();
        redPlayerType.add(btnRedHuman);
        redPlayerType.add(btnRedComputer);
        rightPanel.add(btnRedHuman);
        rightPanel.add(btnRedComputer);

        //Label to keep track of score for Red in General Game mode
        redScoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        rightPanel.add(redScoreLabel);
    }

    /**
     * Creates the bottom panel to hold a label of the current player turn's and button to start a new game
     */
    private void createBottomPanel() {
        //Bottom Panel for the Current Turn label and Button for new game
        bottomPanel = new JPanel(new FlowLayout());
        turnLabel = new JLabel("Current Turn: Blue Player");

        btnNewGame = new JButton("New Game"); //Button for new game
        btnNewGame.addActionListener(e -> resetGame()); //Event listener to start new game

        bottomPanel.add(turnLabel);
        bottomPanel.add(btnNewGame);
    }

    /**
     * Creates the current board with either a default value or one provided by the user
     * @param size
     */
    private void createBoard(int size) {

        if (boardPanel != null) {
            mainPanel.remove(boardPanel);
        }
        boardPanel = new BoardPanel(size);;
        boardButtons = new JButton[size][size];
        for (int i = 0; i < size; i++){            //Adds buttons to the board
            for (int j = 0; j < size; j++){
                final int row = i;
                final int col = j;
                boardButtons[i][j] = new JButton();
                boardButtons[i][j].addActionListener(e -> handleMove(row, col)); //Action Listener is added to listen to actions
                boardPanel.add(boardButtons[i][j]);
            }
        }
        //Refreshes GUI
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Determine the letter selected by the current player.
     */
    private char getSelectedLetter() {

        // '?' is used instead of the previous if statements in past sprints
        if (gameLogic.getCurrentPlayer() == GUIGameLogic.Player.Blue) {
            return blueButtonS.isSelected() ? 'S' : 'O';
        } else {
            return redButtonS.isSelected() ? 'S' : 'O';
        }
    }

    /**
     * Updates button display to a JLabel of the user's selected letter and color once clicked
     * @param row
     * @param col
     * @param player
     */
    private void updateButton(int row, int col, GUIGameLogic.Player player) {
        JLabel label = new JLabel(String.valueOf(gameLogic.getBoard()[row][col]), SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24)); //Changes the font and font size of label

        // '?' Operator used instead of if else statements compared to past sprints
        label.setForeground(player == GUIGameLogic.Player.Blue ? Color.BLUE : Color.RED);

        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); //Creates black border for JLabel

        //Replaces JButton with the new JLabel created in order to prevent user from clicking on that button again
        boardPanel.remove(boardButtons[row][col]);
        boardPanel.add(label, row * gameLogic.getBoardSize() + col); //Adds label

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    /**
     * Handles when a player makes a move on the board
     * @param row
     * @param col
     */
    private void handleMove(int row, int col){
        GUIGameLogic.Player currentColor = gameLogic.getCurrentPlayer();

        //Determine which player object is currently playing
        GamePlayer currentPlayer = (currentColor == GUIGameLogic.Player.Blue) ? bluePlayer : redPlayer;

        if (currentPlayer instanceof ComputerPlayer) {
            return; // ignore manual input during computer turn.
        }

        char letter = getSelectedLetter();
        if (gameLogic.makeMove(row, col, letter)) { //Checks for SOS with makeMove() method from the Simple or General SOSGame class
            updateButton(row, col, currentColor);
            updateGameState();
            updateScoreLabel();

            // Delay the line‑drawing until AFTER Swing has laid out the new JLabel
            List<int[]> seqs = gameLogic.getSOSSequences(row, col);
            SwingUtilities.invokeLater(() -> {
                for (int[] seq : seqs) {
                    drawSOSLine(seq[0], seq[1], seq[4], seq[5]); //Draw a line through the formed SOS
                }
            });

            checkAndPerformComputerMove(); //If the next player is a computer it will make an automatic move
        }
    }

    /**
     * Method that will Draw a line through an SOS sequence on the board
     * It computes the center and end points in the boardPanel coordinates
     * will draw line between those coordinates
     * @param r1 row index of starting cell
     * @param c1 colum index of starting cell
     * @param r3 row index of ending cell
     * @param c3 colum index of ending cell
     */
    private void drawSOSLine(int r1, int c1, int r3, int c3) {
        //Find the start and end components
        Component start = boardPanel.getComponent(r1 * gameLogic.getBoardSize() + c1);
        Component end   = boardPanel.getComponent(r3 * gameLogic.getBoardSize() + c3);

        //Compute the center points of the start and end components
        Point p1 = SwingUtilities.convertPoint(start, start.getWidth()/2, start.getHeight()/2, boardPanel);
        Point p2 = SwingUtilities.convertPoint(end, end.getWidth()/2,   end.getHeight()/2,   boardPanel);

        //Checks which color the line should be
        Color lineColor = (gameLogic.getCurrentPlayer() == GUIGameLogic.Player.Blue) ? Color.BLUE : Color.RED;
        boardPanel.addSOSLine(p1, p2, lineColor); //Adds the new line to the boardPanel
    }

    /**
     * updateGameState Method updates the turn of who is supposed to go
     * sets the turnLabel to the current player
     */
    private void updateGameState() {
        // '?' Operator is used to reduce if else statements compared to previous sprints
        turnLabel.setText("Current Turn: " + (gameLogic.getCurrentPlayer() == GUIGameLogic.Player.Blue ? "Blue Player" : "Red Player"));
    }

    /**
     * updateScoreLabel Method updates the total score of the Blue Player or Red Player
     */
    private void updateScoreLabel() {
        blueScoreLabel.setText("Score: " + gameLogic.getBlueScore());
        redScoreLabel.setText("Score: " + gameLogic.getRedScore());
    }

    /**
     * Changes the board size based on input the user provided in a text box
     * Method also makes sure it follows certain guidelines
     */
    private void updateBoardSize() {
        String input = boardInputSize.getText().trim(); //White space is removed with .trim()
        try {
            int newSize = Integer.parseInt(input);

            if (newSize < 3) {  //Checks if board size is invalid
                JOptionPane.showMessageDialog(frame, "Invalid input: Board size must be at least 3");
                return;
            }

            //Confirms if user wants to change board size
            int confirmSize = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to change the board size to " + newSize + "?",
                    "Confirm New Board Size", JOptionPane.YES_NO_OPTION);

            if (confirmSize == JOptionPane.YES_OPTION) { // Reinitialize gameLogic with the same mode and the new board size

                //Checks the current game mode and then reinitialize the game with the same mode with new board size
                if (gameLogic instanceof SimpleSOSGame) {
                    gameLogic = new SimpleSOSGame(newSize);
                }
                else {
                    gameLogic = new GeneralSOSGame(newSize);
                }

                //Refreshes the GUI
                mainPanel.remove(boardPanel);
                createBoard(newSize);
                mainPanel.add(boardPanel, BorderLayout.CENTER);
                turnLabel.setText("Current Turn: Blue Player");
                frame.revalidate();
                frame.repaint();
            }
        }
        catch (NumberFormatException e) { //Catches invalid input
            JOptionPane.showMessageDialog(frame, "Invalid Input");
        }
    }

    /**
     * Method to update the game mode based on user confirmation
     * Reinitialize the gameLogic with the selected mode
     */
    private void updateGameMode(GameMode mode) {
        int boardSize = gameLogic.getBoardSize();

        if (mode == GameMode.Simple) { //Checks if Game Mode is simple
            //Ask user to confirm to switch to simple mode
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Do you want to switch to Simple mode?",
                    "Confirm Mode Change", JOptionPane.YES_NO_OPTION);


            if (confirm == JOptionPane.YES_OPTION) { //If user confirms, reinitialize the game logic with Simple mode
                gameLogic = new SimpleSOSGame(boardSize);
                resetGameUI(); //Resets the GUI
            }
            else {
                // revert selection if canceled
                if (gameLogic instanceof GeneralSOSGame) {
                    generalGame.setSelected(true);
                }
                else {
                    simpleGame.setSelected(true);
                }
            }
        }
        else if (mode == GameMode.General) { //Checks if Game Mode is General
            //Ask user to confirm to switch to simple mode
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Do you want to switch to General mode?",
                    "Confirm Mode Change", JOptionPane.YES_NO_OPTION);


            if (confirm == JOptionPane.YES_OPTION) { //If user confirms, reinitialize the game logic with General mode
                gameLogic = new GeneralSOSGame(boardSize);
                resetGameUI(); //Resets the GUI
            }
            else {
                //Revert the selection if canceled
                if (gameLogic instanceof SimpleSOSGame) {
                    simpleGame.setSelected(true);
                }
                else {
                    generalGame.setSelected(true);
                }
            }
        }
    }

    /**
     * Resets the game while keeping the current game mode.
     */
    private void resetGame() {
        int boardSize = gameLogic.getBoardSize();
        boardPanel.clearSOS();

        //Reinitialize the game logic withe same game mode and board size
        if (gameLogic instanceof SimpleSOSGame) {
            gameLogic = new SimpleSOSGame(boardSize);
        }
        else {
            gameLogic = new GeneralSOSGame(boardSize);
        }
        // Reinitialize players using the current radio button selections:
        initializePlayers();

        updateScoreLabel(); //Updates score
        mainPanel.remove(boardPanel); //Removes board
        createBoard(boardSize); //Creates board
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        //Reset the selections the player made
        blueButtonS.setSelected(true);
        redButtonS.setSelected(true);

        //Set turn label to blue player
        turnLabel.setText("Current Turn: Blue Player");

        frame.revalidate();
        frame.repaint();

        checkAndPerformComputerMove(); //If blue is a computer player, it will make a move immediately
    }

    /**
     * Resets the UI after a game mode change.
     */
    private void resetGameUI() {
        mainPanel.remove(boardPanel);
        boardPanel.clearSOS();
        createBoard(gameLogic.getBoardSize());
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        turnLabel.setText("Current Turn: Blue Player");
        updateScoreLabel();
        frame.revalidate();
        frame.repaint();
        checkAndPerformComputerMove();
    }

    /**
     * Initializes the blue and red players based on the selected player types
     */
    private void initializePlayers() {

        //Check if blue player should be a human or computer based on radio button
        bluePlayer = btnBlueComputer.isSelected()
                ? new ComputerPlayer(GUIGameLogic.Player.Blue) : new HumanPlayer(GUIGameLogic.Player.Blue);
        //Check if red player should be a human or computer based on radio button
        redPlayer = btnRedComputer.isSelected()
                ? new ComputerPlayer(GUIGameLogic.Player.Red) : new HumanPlayer(GUIGameLogic.Player.Red);
    }

    /**
     * Checks whether the current player is a computer and, if so, performs
     * the computer move after a brief delay.
     */
    private void checkAndPerformComputerMove() {

        if (gameLogic.isGameOver()) return; //If the game is over it will not perform moves

        GUIGameLogic.Player currentColor = gameLogic.getCurrentPlayer();

        //Gets the current player
        GamePlayer currentPlayer = (currentColor == GUIGameLogic.Player.Blue) ? bluePlayer : redPlayer;
        System.out.println("Current turn: " + currentColor + " (" + currentPlayer.getClass().getSimpleName() + ")"); //debug tool

        if (!(currentPlayer instanceof ComputerPlayer)) return; //Move on if the current player is a computer, else return

        //Timer to cause a 500ms delay before computer makes move
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Move move = currentPlayer.getMove(gameLogic); //Gets the move from the computer based on current game state
                System.out.println("Computer move: " + move); //debug tool

                //If move works, apply it to the game logic and update the UI
                if (move != null && gameLogic.makeMove(move.row, move.col, move.letter)) {
                    updateButton(move.row, move.col, currentColor); //Update the board button
                    updateGameState();
                    updateScoreLabel();

                    //Will draw lines for computer player moves if SOS is formed
                    List<int[]> seqs = gameLogic.getSOSSequences(move.row, move.col);
                    SwingUtilities.invokeLater(() -> {
                        for (int[] seq : seqs) {
                            drawSOSLine(seq[0], seq[1], seq[4], seq[5]); //Draws a line through SOS sequence
                            }
                        });

                }
                checkAndPerformComputerMove(); //Recursively check if the next turn is also a computer and perform a move
            }
        });
        //Makes timer only run once so no actions repeat
        timer.setRepeats(false);
        timer.start(); //Start timer
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
