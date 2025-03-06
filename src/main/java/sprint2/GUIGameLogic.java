package sprint2;

public class GUIGameLogic {

    public enum GameMode { Simple, General}
    public enum Player { Blue, Red}

    private int boardSize;
    private GameMode mode;
    private Player player;
    private char[][] board;


    public GUIGameLogic(int boardSize, GameMode mode){
        this.boardSize = boardSize;
        this.mode = mode;
        this.player = Player.Blue;
        this.board = new char[boardSize][boardSize];
    }

    public boolean makeMove(int row, int col, char letter) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize){
            return false;
        }
        board[row][col] = letter;

        if (player == Player.Red){
            player = Player.Blue;
        }
        else{
            player = Player.Red;
        }

        //Need to add something here to check if sos was scored;

        return true;

    }


    public int getBoardSize(){
        return boardSize;
    }

    public GameMode getMode(){
        return mode;
    }

    public Player getCurrentPlayer(){
        return player;
    }

    public char[][] getBoard(){
        return board.clone();
    }

    public void setGameMode(GameMode  mode){
        this.mode = mode;
    }
}
