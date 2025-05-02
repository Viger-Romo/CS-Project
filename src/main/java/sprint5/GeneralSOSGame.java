package sprint5;

//Class that represents the "General" game mode logic that extends GUIGameLogic class
public class GeneralSOSGame extends GUIGameLogic {

    //Constructor
    public GeneralSOSGame(int boardSize){
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
    public boolean makeMove(int row, int col, char letter){

        //Checks if board is full or game is over
        if (gameOver || board[row][col] != '\0'){
            return false;
        }

        board[row][col] = letter; //Place letter
        int sosCount = checkForSOS(row, col); //Checks for SOS sequences

        if (sosCount > 0){ //Will update score if the SOS count is greater than 0
            if (currentPlayer == Player.Blue){ //Adds to blue if current player is blue
                blueScore += sosCount;
            }
            else{ //Adds to red is not blue
                redScore += sosCount;
            }
        }
        if (isBoardFull()){ //Checks if board is full and ends game
            gameOver = true;
            determineWinner(); //Determine who is the winner
        }
        else if (sosCount == 0){ //Switch turn only if no SOS was created
            switchTurn();
        }
        return true;
    }

    /**
     * Method to determine who is the winner in a General Game
     * Checks which player has the highest sore and uses to showWinnerMessage method to declare them
     * Draw if both have same score
     */
    private void determineWinner() {
        if (blueScore > redScore) {
            showWinnerMessage("Blue wins with " + blueScore + " total SOS sequences!");
        }
        else if (redScore > blueScore) {
            showWinnerMessage("Red wins with " + redScore + " total SOS sequences!");
        }
        else {
            showWinnerMessage("Game Over! It's a draw—both players have " + blueScore + " SOS sequences.");
        }
    }
}
