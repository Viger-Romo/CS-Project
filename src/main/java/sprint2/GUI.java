package sprint2;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private JFrame frame;
    private JPanel mainPanel, topPanel, boardPanel, leftPanel, rightPanel, bottomPanel;
    private JRadioButton simpleGame, generalGame;
    private JTextField boardInputSize;
    private JRadioButton blueButtonS, blueButtonO, redButtonS, redButtonO;
    private JLabel turnLabel;
    private JButton [][] boardButtons;

    private GUIGameLogic gameLogic;

    public GUI() {

        //Initialize game logic with default size and game mode
        gameLogic = new GUIGameLogic(5, GUIGameLogic.GameMode.Simple);

        //Sets up JFrame
        frame = new JFrame("SOS Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        //Add to frame

        //Create GUI Compnents for the game
        boardPanel = new JPanel();
        createTopPanel();
        createBoard(5);
        createSidePanels();
        createBottomPanel();
        setMainPanel();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * Creates the main panel and adds components in it
     */
    private void setMainPanel(){

        // Main Panel
        mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        boardInputSize.addActionListener(e -> updateBoardSize()); //Event listener to update size on input change
    }

    /**
     * Creates the Top Panel of the JFrame
     * Contains the game modes and input to change size
     */
    private void createTopPanel(){
        //Top Panel contains the Game modes and text box to change board size
        topPanel = new JPanel(new FlowLayout());
        simpleGame = new JRadioButton("Simple Game", true);
        generalGame = new JRadioButton("General Game");
        ButtonGroup gameModes = new ButtonGroup();
        gameModes.add(simpleGame);
        gameModes.add(generalGame);

        //Add listeners to get game mode buttons
        simpleGame.addActionListener(e -> updateGameMode(GUIGameLogic.GameMode.Simple));
        generalGame.addActionListener(e -> updateGameMode(GUIGameLogic.GameMode.General));

        boardInputSize = new JTextField(2);

        topPanel.add(new JLabel("SOS Game Mode: "));
        topPanel.add(simpleGame);
        topPanel.add(generalGame);
        topPanel.add(new JLabel("Board Size"));
        topPanel.add(boardInputSize);
    }

    /**
     * Creates Sides Panels of letter options either a blue or red player can choose when it's their turn
     */
    private void createSidePanels(){
        // Left Panel holds buttons for Blue Player
        leftPanel = new JPanel(new GridLayout(3,1));
        leftPanel.add(new JLabel("Blue Player:", SwingConstants.CENTER));
        blueButtonS = new JRadioButton("S", true);
        blueButtonO = new JRadioButton("O");
        ButtonGroup blueButtonGroup = new ButtonGroup();
        blueButtonGroup.add(blueButtonS);
        blueButtonGroup.add(blueButtonO);
        leftPanel.add(blueButtonS);
        leftPanel.add(blueButtonO);

        // Right Panel holds buttons for Red Player
        rightPanel = new JPanel(new GridLayout(3,1));
        rightPanel.add(new JLabel("Red Player:", SwingConstants.CENTER));
        redButtonS = new JRadioButton("S", true);
        redButtonO = new JRadioButton("O");
        ButtonGroup redButtonGroup = new ButtonGroup();
        redButtonGroup.add(redButtonS);
        redButtonGroup.add(redButtonO);
        rightPanel.add(redButtonS);
        rightPanel.add(redButtonO);
    }

    /**
     * Creates the bottom panel to hold a label of the current player turn's
     */
    private void createBottomPanel(){
        //Bottom Panel for the Current Turn and maybe space for other buttons and labels in upcoming weeks
        bottomPanel = new JPanel(new FlowLayout());
        turnLabel = new JLabel("Current Turn: Blue Player");
        bottomPanel.add(turnLabel);
    }

    /**
     * Creates the current board with either a default value or one provided by the user
     * @param size
     */
    private void createBoard(int size){

        boardPanel.removeAll(); //Removes all the previous buttons

        boardPanel = new JPanel(new GridLayout(size, size));
        boardButtons = new JButton[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                final int row = i;
                final int col = j;
                boardButtons[i][j] = new JButton();
                boardButtons[i][j].addActionListener(e -> handleMove(row,col));
                boardPanel.add(boardButtons[i][j]);
            }
        }
    }

    /**
     * Determine selected letter for the current player
     */
    private char getSelectedLetter(){
        if (gameLogic.getCurrentPlayer() == GUIGameLogic.Player.Blue) {
            if (blueButtonS.isSelected()){
                return 'S';
            }
            else {
                return 'O';
            }
        }
        if (gameLogic.getCurrentPlayer() == GUIGameLogic.Player.Red){
            if (redButtonS.isSelected()){
                return 'S';
            }
            else {
                return 'O';
            }
        }
        return 0;
    }

    /**
     * Updates button display after a move the current user makes
     * @param row
     * @param col
     * @param player
     */
    private void updateButton(int row, int col, GUIGameLogic.Player player){
        JButton button = boardButtons[row][col];
        button.setText(String.valueOf(gameLogic.getBoard()[row][col]));

        if (player == GUIGameLogic.Player.Blue){
            button.setForeground(Color.BLUE);
        }
        else{
            button.setForeground(Color.RED);
        }
    }

    /**
     * Handles when a player makes a move on the board
     * @param row
     * @param col
     */
    private void handleMove(int row, int col) {
        char letter = getSelectedLetter();
        GUIGameLogic.Player currentPlayer = gameLogic.getCurrentPlayer();
        if (gameLogic.makeMove(row, col, letter)) {
            updateButton(row, col, currentPlayer);
            updateGameState();
        }
    }

    /**
     * updateGameState Method updates the turn of who is supposed to go
     * sets the turnLabel to the current player
     */
    private void updateGameState() {
        if (gameLogic.getCurrentPlayer() == GUIGameLogic.Player.Blue){
            turnLabel.setText("Current Turn: Blue Player");
        }
        if (gameLogic.getCurrentPlayer() == GUIGameLogic.Player.Red){
            turnLabel.setText("Current Turn: Red Player");
        }
    }


    /**
     * Changes the board size based on input the user provided in a text box
     * Method also makes sure it follows certain guidelines
     */
    private void updateBoardSize(){
        String boardSize = boardInputSize.getText().trim(); //White space is removed with .trim()
        try{
            int newBoardSize = Integer.parseInt(boardSize);

            if (newBoardSize < 3) {
                JOptionPane.showMessageDialog(frame, "Invalid input: Board size must be at least 3");
                return;
            }
            int confirmSize = JOptionPane.showConfirmDialog(frame, "Are you sure you want to change the board size to " + newBoardSize + "?",
                    "Confirm New Board Size: ", JOptionPane.YES_NO_OPTION);

            if (confirmSize == JOptionPane.YES_OPTION){
                gameLogic = new GUIGameLogic(newBoardSize, gameLogic.getMode());
                mainPanel.remove(boardPanel);
                createBoard(newBoardSize);
                mainPanel.add(boardPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }

        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(frame, "Invalid Input");
        }
    }

    /**
     * Method to update the game mode based on user confirmation
     */
    private void updateGameMode(GUIGameLogic.GameMode selectedMode) {

        String modeMessage;
        if (selectedMode == GUIGameLogic.GameMode.Simple) {
            modeMessage = "Simple";
        }
        else {
            modeMessage = "General";
        }

        int confirm = JOptionPane.showConfirmDialog(frame,
                "Do you want to switch to " + modeMessage + " mode?",
                "Confirm Game Mode Change",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            gameLogic = new GUIGameLogic(gameLogic.getBoardSize(), selectedMode); // Update game mode
            resetGame(); // Reset board and UI
        }
        else {
            // Revert selection if the user cancels
            if (selectedMode == GUIGameLogic.GameMode.Simple) {
                generalGame.setSelected(true);
            } else {
                simpleGame.setSelected(true);
            }
        }
    }

    /**
     * Method to reset the game (updates the board and UI)
     */
    private void resetGame() {
        mainPanel.remove(boardPanel); // Remove old board
        createBoard(gameLogic.getBoardSize()); // Create a new board with the current size
        mainPanel.add(boardPanel, BorderLayout.CENTER); // Re-add the new board

        frame.revalidate();
        frame.repaint();
    }


    // Add a getter for the game logic
    public GUIGameLogic getGameLogic() {
        return gameLogic;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
        //GUI gui = new GUI();

    }
}