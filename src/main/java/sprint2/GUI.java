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

        gameLogic = new GUIGameLogic(5, GUIGameLogic.GameMode.Simple);

        frame = new JFrame("SOS Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        //Add to frame

        boardPanel = new JPanel();
        createTopPanel();
        createBoard(5);
        createSidePanels();
        createBottomPanel();
        setMainPanel();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void setMainPanel(){

        // Main Panel
        mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void createTopPanel(){
        //Top Panel contains the Game modes and text box to change board size
        topPanel = new JPanel(new FlowLayout());
        simpleGame = new JRadioButton("Simple Game", true);
        generalGame = new JRadioButton("General Game");
        ButtonGroup gameModes = new ButtonGroup();
        gameModes.add(simpleGame);
        gameModes.add(generalGame);
        boardInputSize = new JTextField(2);

        topPanel.add(new JLabel("SOS Game Mode: "));
        topPanel.add(simpleGame);
        topPanel.add(generalGame);
        topPanel.add(new JLabel("Board Size"));
        topPanel.add(boardInputSize);
    }

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

    private void createBottomPanel(){
        //Bottom Panel for the Current Turn and maybe space for other features in the future
        bottomPanel = new JPanel(new FlowLayout());
        turnLabel = new JLabel("Current Turn: Place holder for name");
        bottomPanel.add(turnLabel);
    }

    private void createBoard(int size){
        //Game Board Panel (Grid of Buttons)
        boardPanel.removeAll();

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

    private void updateButton(int row, int col, GUIGameLogic.Player player){
        JButton button = boardButtons[row][col];
        button.setText(String.valueOf(gameLogic.getBoard()[row][col]));
        //button.setForeground(gameLogic.getCurrentPlayer() == GUIGameLogic.Player.Blue ? Color.BLUE : Color.RED);

        if (player == GUIGameLogic.Player.Blue){
            button.setForeground(Color.BLUE);
        }
        else{
            button.setForeground(Color.RED);
        }
    }

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

    private void updateBoardSize(){
        String boardSize = boardInputSize.getText().trim(); //White space is removed with .trim()
        try{
            int newBoardSize = Integer.parseInt(boardSize);

            if (newBoardSize < 3) {
                JOptionPane.showMessageDialog(frame, "Invalid input: Board size must be at least 3");
                return;
            }
            int confrimSize = JOptionPane.showConfirmDialog(frame, "Are you sure you want to change the board size to " + newBoardSize + "?",
                    "Confirm New Board Size: ", JOptionPane.YES_NO_OPTION);

        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(frame, "Invalid Input");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
        //GUI gui = new GUI();

    }
}