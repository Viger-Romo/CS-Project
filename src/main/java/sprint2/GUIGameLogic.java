package sprint2;

class GUIGameLogic {

    //Enums represents the two game mode: Simple and General and two players
    public enum GameMode { Simple, General}
    public enum Player { Blue, Red}

    //Game Logic variables
    private int boardSize;
    private GameMode mode;
    private Player player;
    private char[][] board;

    //Constructor to initialize the game logic with default values
    public GUIGameLogic(int boardSize, GameMode mode){
        this.boardSize = boardSize;
        this.mode = mode;
        this.player = Player.Blue;
        this.board = new char[boardSize][boardSize];
    }

    //Method to make move on the board
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

    //Getters and Setters
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
