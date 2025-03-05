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

    public GUI() {
        frame = new JFrame("SOS Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());


        //Add to frame
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
        blueButtonGroup.add(redButtonS);
        blueButtonGroup.add(redButtonO);
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
        boardPanel = new JPanel(new GridLayout(size, size));
        boardButtons = new JButton[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                boardButtons[i][j] = new JButton();
                boardPanel.add(boardButtons[i][j]);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}