package sprint4;

import javax.swing.*;
import java.util.*;

//Abstract base class for SOS game logic for the Simple and General Game modes
public abstract class GUIGameLogic {

    //Enum represents the Player
    public enum Player { Blue, Red }

    //Game Logic variables that are protected
    protected char[][] board;
    protected int boardSize, blueScore = 0, redScore = 0;
    protected Player currentPlayer;
    protected boolean gameOver;


    //Constructor to initialize the game logic with default values
    public GUIGameLogic(int boardSize){
        this.boardSize = boardSize;
        this.board = new char[boardSize][boardSize];
        this.currentPlayer = Player.Blue;
        this.gameOver = false;
    }


    /**
     * Abstract method to be implemented by subclasses to define how a move is made either Simple or General mode
     * @param row
     * @param col
     * @param letter
     * @return True if move was valid, false if not
     */
    public abstract boolean makeMove(int row, int col, char letter);

    /**
     * Method that switches turns between players
     */
    protected void switchTurn() { currentPlayer = (currentPlayer == Player.Blue) ? Player.Red : Player.Blue; }

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
    protected int checkForSOS(int row, int col){
        return checkHorizontal(row, col) + checkVertical(row, col) + checkDiagonal(row, col);
    }

    /**
     * Method is used to display the winner or if it is a draw
     * @param message
     */
    protected void showWinnerMessage(String message){
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Method that checks for SOS sequences in order to be used to draw lines through SOS sequences later
     * @param row
     * @param col
     * @return List of SOS sequences there are in the board
     */
    public List<int[]> getSOSSequences(int row, int col) {
        List<int[]> seqs = new ArrayList<>();
        //Checks Horizontal
        // Check for S-O-S with current as first S
        if (col <= boardSize - 3 && board[row][col]=='S' && board[row][col+1]=='O' && board[row][col+2]=='S') {
            seqs.add(new int[]{row, col, row, col+1, row, col+2});
        }
        // Check for S-O-S with current as middle O
        if (col > 0 && col < boardSize - 1 && board[row][col]=='O' && board[row][col-1]=='S' && board[row][col+1]=='S') {
            seqs.add(new int[]{row, col-1, row, col, row, col+1});
        }
        // Check for S-O-S with current as last S
        if (col >= 2 && board[row][col]=='S' && board[row][col-1]=='O' && board[row][col-2]=='S') {
            seqs.add(new int[]{row, col-2, row, col-1, row, col});
        }


        //Checks Vertical
        // Check for S-O-S with current as first S
        if (row <= boardSize - 3 && board[row][col]=='S' && board[row+1][col]=='O' && board[row+2][col]=='S') {
            seqs.add(new int[]{row, col, row+1, col, row+2, col});
        }
        // Check for S-O-S with current as middle O
        if (row > 0 && row < boardSize - 1 && board[row][col]=='O' && board[row-1][col]=='S' && board[row+1][col]=='S') {
            seqs.add(new int[]{row-1, col, row, col, row+1, col});
        }
        // Check for S-O-S with current as last S
        if (row >= 2 && board[row][col]=='S' && board[row-1][col]=='O' && board[row-2][col]=='S') {
            seqs.add(new int[]{row-2, col, row-1, col, row, col});
        }

        //Checks Diagonal
        // Check top-left to bottom-right (first S)
        if (row <= boardSize - 3 && col <= boardSize - 3 && board[row][col]=='S' && board[row+1][col+1]=='O' && board[row+2][col+2]=='S') {
            seqs.add(new int[]{row, col, row+1, col+1, row+2, col+2});
        }
        // Check top-left to bottom-right (first S)
        if (row > 0 && row < boardSize - 1 && col > 0 && col < boardSize - 1 && board[row][col]=='O' && board[row-1][col-1]=='S' && board[row+1][col+1]=='S') {
            seqs.add(new int[]{row-1, col-1, row, col, row+1, col+1});
        }
        // Check top-left to bottom-right (last S)
        if (row >= 2 && col >= 2 && board[row][col]=='S' && board[row-1][col-1]=='O' && board[row-2][col-2]=='S') {
            seqs.add(new int[]{row-2, col-2, row-1, col-1, row, col});
        }
        // Check bottom-left to top-right (first S)
        if (row >= 2 && col <= boardSize - 3 && board[row][col]=='S' && board[row-1][col+1]=='O' && board[row-2][col+2]=='S') {
            seqs.add(new int[]{row, col, row-1, col+1, row-2, col+2});
        }
        // Check bottom-left to top-right (middle O)
        if (row > 0 && row < boardSize - 1 && col > 0 && col < boardSize - 1 && board[row][col]=='O' && board[row+1][col-1]=='S' && board[row-1][col+1]=='S') {
            seqs.add(new int[]{row+1, col-1, row, col, row-1, col+1});
        }
        // Check bottom-left to top-right (last S)
        if (row <= boardSize - 3 && col >= 2 && board[row][col]=='S' && board[row+1][col-1]=='O' && board[row+2][col-2]=='S') {
            seqs.add(new int[]{row, col, row+1, col-1, row+2, col-2});
        }
        return seqs;
    }

    //Getters and Setters
    public char[][] getBoard() { return board; }
    public int getBoardSize() { return boardSize; }
    public Player getCurrentPlayer() { return currentPlayer; }
    public int getBlueScore() { return blueScore; }
    public int getRedScore() { return redScore; }
    public boolean isGameOver() { return gameOver; }

}
