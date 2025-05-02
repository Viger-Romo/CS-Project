package sprint5;

//Class to represent a single move in the SOS game
public class Move {
    public int row;
    public int col;
    public char letter;

    public Move(int row, int col, char letter){
        this.row = row;
        this.col = col;
        this.letter = letter;
    }
}
