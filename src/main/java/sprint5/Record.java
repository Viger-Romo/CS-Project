package sprint5;

import java.io.*;

public class Record {

    private static final String RECORDED_GAME = "RecordedGame.txt";
    private BufferedWriter writer;
    private int moveCount;

    /**
     * Method that opens or creates the recording file, and creates csv header
     * @return true or false if file was successfully opened
     */
    public boolean startRecording() {
        try {
            writer = new BufferedWriter(new FileWriter(RECORDED_GAME)); //Overwrite or create new file
            writer.write("move,player,row,col,letter");
            writer.newLine();
            moveCount = 0;
            return true;
        }
        catch (IOException e){
            System.out.println("Error when trying to record");
            return false;
        }
    }

    /**
     * Record one move and append to record file
     * @param moveNum
     * @param player
     * @param row
     * @param col
     */
    public void recordMove(int moveNum, String player, int row, int col, char letter){
        if (writer == null){
            return;
        }
        try{
            writer.write(moveNum + "," + player + "," + row + "," + col + "," + letter);
            writer.newLine();
            writer.flush();
        }
        catch (IOException ignored){} //Ignore write failures
    }

    /**
     * Record the final result and append it to file
     * @param result
     */
    public void recordResult(String result){
        if (writer == null){
            return;
        }
        try{
            writer.write(result);
            writer.newLine();
            writer.flush();
        }
        catch (IOException ignored){}
    }

    /**
     * Close the file
     */
    public void stopRecording() {
        if (writer != null) {
            try { writer.close(); } catch (IOException ignored) {}
            writer = null;
        }
    }

    /**
     * Checks if a recording exits on the disk
     * @return true if non-directory file named "RecordedGame.txt" exits.
     * */
    public boolean hasRecorded() {
        File f = new File(RECORDED_GAME);
        return f.exists() && !f.isDirectory();
    }
}
