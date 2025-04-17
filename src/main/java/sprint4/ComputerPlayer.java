package sprint4;

import java.util.ArrayList;
import java.util.List;

//Class that represents a computer player and extends the GamePlayer class
public class ComputerPlayer extends GamePlayer {

    //Constructor
    public ComputerPlayer(GUIGameLogic.Player color){
        super(color); //Uses super to call constructor from GamePlayer
    }

    /**
     * gets a Move that the computer is going to make
     * Tries to find a move that results in the most sos sequences
     * chooses random sos if not possible
     * @param gameLogic
     * @return Move which is the best move for the computer to make
     */
    @Override
    public Move getMove(GUIGameLogic gameLogic) {
        int boardSzie = gameLogic.getBoardSize();
        int SOSCount = 0, bestRow = -1, bestCol = -1;
        char bestChar = ' ';

        //Try every empty cell with both 'S' and 'O'
        for (int i = 0; i < boardSzie; i++) {
            for (int j = 0; j < boardSzie; j++) {
                if (gameLogic.getBoard()[i][j] == '\0') {
                    int sCount = simulateMove(gameLogic, i, j, 'S'); //Simulates placing an 'S' and evaluates SOS count
                    if (sCount > SOSCount) {
                        SOSCount = sCount;
                        bestRow = i;
                        bestCol = j;
                        bestChar = 'S';
                    }
                    int oCount = simulateMove(gameLogic, i, j, 'O'); //Simulates placing an 'O' and evaluates SOS count
                    if (oCount > SOSCount) {
                        SOSCount = oCount;
                        bestRow = i;
                        bestCol = j;
                        bestChar = 'O';
                    }
                }
            }
        }

        //If a good SOS move was found and returns it
        if (bestRow != -1 && bestCol != -1 && SOSCount > 0) {
            return new Move(bestRow, bestCol, bestChar); //Returns move
        }
        else { //Picks a random spot if no SOS sequences where found
            List<int[]> emptyCells = new ArrayList<>();
            for (int i = 0; i < boardSzie; i++) {
                for (int j = 0; j < boardSzie; j++) {
                    if (gameLogic.getBoard()[i][j] == '\0') {
                        emptyCells.add(new int[]{i, j});
                    }
                }
            }
            if (emptyCells.isEmpty()) {
                return null; //No more valid moves
            }
            //Choose a random empty cell
            int[] cell = emptyCells.get((int) (Math.random() * emptyCells.size()));
            char letter = (Math.random() < 0.5) ? 'S' : 'O'; //Uses '?' operator instead if else statements
            return new Move(cell[0], cell[1], letter); //Returns move
        }
    }

    /**
     * Simulates placing a letter on the board
     * @param gameLogic
     * @param row
     * @param col
     * @param letter
     * @return the number of SOS sequences that would result from that move
     */
    private int simulateMove(GUIGameLogic gameLogic, int row, int col, char letter){
        if (gameLogic.getBoard()[row][col] != '\0'){
            return 0; //Cell is occupied
        }
        gameLogic.getBoard()[row][col] = letter; //Temporarily places the letter
        int count = gameLogic.checkForSOS(row,col); //Count SOS sequences formed using checkForSOS method from GUIGameLogic class
        gameLogic.getBoard()[row][col] = '\0'; //Undo the move just made
        return count;
    }
}
