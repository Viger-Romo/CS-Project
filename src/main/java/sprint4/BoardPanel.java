package sprint4;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

//BoardPanel Class is Custom JPanel that will be used for the board in SOSGame
//This allows for drawling lines through SOS sequences on top of the grid
public class BoardPanel extends JPanel {

    //Private Variables
    private final int size;
    private final List<Line2D> sosLines = new ArrayList<>(); //Holds complete SOS sequences
    private final Map<Line2D, Color> lineColors = new HashMap<>(); //Map each line to a color representing the player who formed it
    private GUIGameLogic.Player currentPlayer;  //Gets current player to know which color we will use

    //Constructor
    public BoardPanel(int size) {
        super(new GridLayout(size, size));
        this.size = size;
    }


    /**
     * Adds a new SOS line to the board and repaints it when a valid SOS sequence is detected
     * @param p1 is a point
     * @param p2 is a point
     * @param color
     */
    public void addSOSLine(Point p1, Point p2, Color color) {
        Line2D line = new Line2D.Double(p1, p2);
        sosLines.add(line);
        lineColors.put(line, color);
        repaint(); //Will show the new line on the UI
    }

    /**
     * Sets the current player
     * @param player
     */
    public void setCurrentPlayer(GUIGameLogic.Player player) {
        this.currentPlayer = player;
    }

    /**
     * Paint method that draws SOS lines over the board
     * this method overrides the default one
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(4)); //Thicker line
        for (Line2D line : sosLines) {
            g2.setColor(lineColors.get(line)); //Color based on player color
            g2.draw(line); //Draws the line
        }
    }

    /**
     * Clears all drawn SOS lines from the board
     */
    public void clearSOS() {
        sosLines.clear();
        lineColors.clear();
        repaint();
    }
}
