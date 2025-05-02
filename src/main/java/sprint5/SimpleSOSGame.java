package sprint5;

//Class that represents the "Simple" game mode logic that extends GUIGameLogic class
public class SimpleSOSGame extends GUIGameLogic {

    //Constructor
    public SimpleSOSGame(int boardSize){
        super(boardSize); //Uses super to call the constructor of the abstract class
    }

    /**
     * Method to make a move on the board using either and 'S' or 'O' in Simple Game mode
     * @param row
     * @param col
     * @param letter
     * @return True if move was valid, false if not
     */
    @Override
    public boolean makeMove(int row, int col, char letter) {
        if (gameOver || board[row][col] != '\0'){ //Checks if board is full or game is over
            return false;
        }

        board[row][col] = letter; //Place letter

        int sosCount = checkForSOS(row, col); //Checks for an SOS sequence
        if (sosCount > 0){ //If there is an SOS sequence
            gameOver = true;
            showWinnerMessage("Game Over! " + currentPlayer + " wins"); //Show winner using showWinnerMessage
            return true;
        }
        if (isBoardFull()){ //No SOS but board is full
            gameOver = true;
            showWinnerMessage("Game Over! Game ends in a Draw"); //Show draw using showWinnerMessage
        }
        else {
            switchTurn(); //Switches turns
        }
        return true;
    }
}
