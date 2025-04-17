package sprint4;

//Abstract base class for Human and Computer Players
public abstract class GamePlayer {

    //The color of the player
    protected GUIGameLogic.Player playerColor;

    //Constructor
    public GamePlayer(GUIGameLogic.Player color){
        this.playerColor = color;
    }

    /**
     * Abstract method to be implemented by the subclasses for Human and Computer players
     * is mostly used to decide on how a computer uses a move from the current game state
     * @param gameLogic
     * @return a move representing the player's move
     */
    public abstract Move getMove(GUIGameLogic gameLogic);

}