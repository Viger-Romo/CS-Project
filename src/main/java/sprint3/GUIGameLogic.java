package sprint3;

import javax.swing.*;

class GUIGameLogic {

    //Enums represents the two game mode: Simple and General and two players
    public enum GameMode { Simple, General}
    public enum Player { Blue, Red}

    //Game Logic variables
    private int boardSize, blueScore = 0, redScore = 0;;
    private GameMode mode;
    private Player player;
    private char[][] board;
    private boolean gameOver;

    //Constructor to initialize the game logic with default values
    public GUIGameLogic(int boardSize, GameMode mode){
        this.boardSize = boardSize;
        this.mode = mode;
        this.player = Player.Blue;
        this.board = new char[boardSize][boardSize];
    }

    /**
     * Method to make a move on the board using either and 'S' or 'O'
     * method also makes sure to check for an SOS for both game modes
     * @param row
     * @param col
     * @param letter
     * @return True if move was valid, false if not
     */
    public boolean makeMove(int row, int col, char letter) {
        if (gameOver || board[row][col] != '\0'){ //Checks if board is full or game is over
            return false;
        }
        board[row][col] = letter; //Place letter

        if (mode == GameMode.Simple) { //Checks if game is simple
            int sosCount = checkForSOS(row,col); //Checks for an SOS sequence
            if (sosCount > 0) { //If there is an SOS
                gameOver = true;
                JOptionPane.showMessageDialog(null, "Game Over, " + player + " Wins."); //Show winner
                return true;
            }
            else if (isBoardFull()) { //No SOS but board is full
                gameOver = true;
                JOptionPane.showMessageDialog(null, "Game Over, Game Ends in Draw."); //Show game ends in draw
            }
            else {
                switchTurn(); //Switches turns
            }
        }
        else { //For General game mode
            int sosCount = checkForSOS(row, col); //Checks for SOS sequences

            if (sosCount > 0) { //Will update score is SOS count greater than 0
                if (player == Player.Blue) { //Adds to blue if current player is blue
                    blueScore += sosCount;
                }
                else { //Adds to red
                    redScore += sosCount;
                }
            }
            if (isBoardFull()) { //Checks if board full and ends game
                gameOver = true;
                determineWinner(); //Determines who wins
            }
            else {
                // Switch turn only if no SOS was created
                if (sosCount == 0) {
                    switchTurn();
                }
            }
        }
        return true;
    }

    /**
     * Method that switches turns between players
     */
    private void switchTurn() {
        player = (player == Player.Blue) ? Player.Red : Player.Blue;
    }

    /**
     * Method that checks if the board is full
     * @return True if board is full, False if not
     */
    public boolean isBoardFull() {
        for (char[] row : board) {
            for (char cell: row){
                if (cell == '\0'){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Methods Checks for an SOS sequence in the horizontal direction involving the current cell
     * @param row
     * @param col
     * @return Count of how many SOS sequences there are in the horizontal direction
     */
    private int checkHorizontal(int row, int col) {
        int count = 0;
        // Check for S-O-S with current as first S
        if (col <= board.length - 3 && board[row][col] == 'S' && board[row][col+1] == 'O' && board[row][col+2] == 'S') {
            count++;
        }
        // Check for S-O-S with current as middle O
        if (col > 0 && col < board.length - 1 && board[row][col] == 'O' && board[row][col-1] == 'S' && board[row][col+1] == 'S') {
            count++;
        }
        // Check for S-O-S with current as last S
        if (col >= 2 && board[row][col] == 'S' && board[row][col-2] == 'S' && board[row][col-1] == 'O') {
            count++;
        }
        return count;
    }

    /**
     * Method Checks for SOS sequence in the Vertical direction involving the current cell
     * @param row
     * @param col
     * @return count of how many SOS sequences there are in the vertical direction
     */
    private int checkVertical(int row, int col) {
        int count = 0;
        // Check for S-O-S with current as first S
        if (row <= board.length - 3 && board[row][col] == 'S' && board[row+1][col] == 'O' && board[row+2][col] == 'S') {
            count++;
        }
        // Check for S-O-S with current as middle O
        if (row > 0 && row < board.length - 1 && board[row][col] == 'O' && board[row-1][col] == 'S' && board[row+1][col] == 'S') {
            count++;
        }
        // Check for S-O-S with current as last S
        if (row >= 2 && board[row][col] == 'S' && board[row-2][col] == 'S' && board[row-1][col] == 'O') {
            count++;
        }
        return count;
    }

    /**
     * Method Checks for SOS sequence in the Diagonal direction involving the current cell
     * @param row
     * @param col
     * @return count of how SOS sequences there are in the diagonal direction
     */
    private int checkDiagonal(int row, int col) {
        int count = 0;
        // Check top-left to bottom-right (first S)
        if (row <= board.length - 3 && col <= board.length - 3 && board[row][col] == 'S' && board[row+1][col+1] == 'O' && board[row+2][col+2] == 'S') {
            count++;
        }
        // Check top-left to bottom-right (middle O)
        if (row > 0 && row < board.length - 1 && col > 0 && col < board.length - 1 && board[row][col] == 'O' && board[row-1][col-1] == 'S' && board[row+1][col+1] == 'S') {
            count++;
        }
        // Check top-left to bottom-right (last S)
        if (row >= 2 && col >= 2 && board[row][col] == 'S' && board[row-2][col-2] == 'S' && board[row-1][col-1] == 'O') {
            count++;
        }
        // Check bottom-left to top-right (first S)
        if (row >= 2 && col <= board.length - 3 && board[row][col] == 'S' && board[row-1][col+1] == 'O' && board[row-2][col+2] == 'S') {
            count++;
        }
        // Check bottom-left to top-right (middle O)
        if (row > 0 && row < board.length - 1 && col > 0 && col < board.length - 1 && board[row][col] == 'O' && board[row+1][col-1] == 'S' && board[row-1][col+1] == 'S') {
            count++;
        }
        // Check bottom-left to top-right (last S)
        if (row <= board.length - 3 && col >= 2 && board[row][col] == 'S' && board[row+2][col-2] == 'S' && board[row+1][col-1] == 'O') {
            count++;
        }
        return count;
    }
    /**
     * Method checks for SOS in all direction by call check method
     * @param row
     * @param col
     * @return Total number of SOS sequences found
     */
    private int checkForSOS(int row, int col) {
        return checkHorizontal(row, col) + checkVertical(row, col) + checkDiagonal(row, col);
    }

    /**
     * Method is used to display the winner based on score (Only words for general mode)
     */
    private void determineWinner() {
        if (blueScore > redScore) {
            JOptionPane.showMessageDialog(null, "Blue wins with " + blueScore + " SOSs!");
        }
        else if (redScore > blueScore) {
            JOptionPane.showMessageDialog(null, "Red wins with " + redScore + " SOSs!");
        }
        else {
            JOptionPane.showMessageDialog(null, "Game is a draw!");
        }
    }

    //Getters and Setters
    public int getBoardSize(){ return boardSize; }
    public GameMode getMode(){ return mode; }
    public Player getCurrentPlayer(){ return player; }
    public char[][] getBoard(){ return board.clone(); }
    public int getBlueScore() { return blueScore; }
    public int getRedScore() { return redScore; }
}