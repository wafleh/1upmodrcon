package modrcon;

import java.io.*;

/**
 * A class to store the console history.
 *
 * @author Pyrite
 */
public class HistoryDatabase {
    
    /** The console history. */
    private String history;

    /** The filename to write the console history to. */
    private final String fileName = "consolehistory.db";

    /**
     * Constructs a ServerDatabase and populates it
     * if there are any servers currently stored to disk.
     */
    public HistoryDatabase() {
        super();
        this.history = "";
        this.loadDatabase();
    }

    /**
     * Gets the stored console history.
     * 
     * @return The console history text.
     */
    public String getConsoleHistory() {
        return this.history;
    }

    /**
     * Append to the console history.
     *
     * @param s Any console output text.
     */
    public void addHistory(String s) {
        this.history += "\n"+s+"\n";
    }

    /**
     * Looks for the console history stored on the HDD,
     * and if found, reads it into this object.
     */
    private void loadDatabase() {
        File db = new File(PropertyManager.settingsPath+this.fileName);
        if (db.exists()) {
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                fis = new FileInputStream(PropertyManager.settingsPath+this.fileName);
                in = new ObjectInputStream(fis);
                this.history = (String)in.readObject();
                in.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Saves the console history to the Hard Disk.
     */
    public void saveDatabase() {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(PropertyManager.settingsPath+this.fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(this.history);
            out.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

}
