package sprint5;

//Class the represents a Human player and extends the GamePlayer class
public class HumanPlayer extends GamePlayer {

    //Constructor
    public HumanPlayer(GUIGameLogic.Player color){
        super(color); //Uses super to call the constructor from GamePlayer
    }

    /**
     * Human players do not generate moves here, instead the GUI handles move inputs like clicking a button on the board
     * @param gameLogic
     * @return null
     */
    @Override
    public Move getMove(GUIGameLogic gameLogic){
        return null; //Human input comes from GUI
    }
}
