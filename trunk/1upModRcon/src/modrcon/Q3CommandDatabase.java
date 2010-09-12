package modrcon;

import java.util.ArrayList;
import java.io.*;

/**
 * A class to store the Q3Commands.
 *
 * @author Pyrite[1up]
 */
public class Q3CommandDatabase implements Serializable {

    /** An ArrayList of Q3Command objects. */
    private ArrayList commandList;

    /** The filename to write the commandList to. */
    private final String fileName = "commands.db";

    /**
     * Constructs a Q3CommandDatabase and populates it
     * if there are any Q3Commands currently stored to disk.
     */
    public Q3CommandDatabase() {
        super();
        this.commandList = new ArrayList();
        this.loadDatabase();
    }

    public void setCommandList(ArrayList list) {
        this.commandList = list;
    }

    public ArrayList getCommandList() {
        return this.commandList;
    }

    /**
     * Add a Command to the Q3CommandDatabase.
     *
     * @param s The Command to Add.
     */
    public void addServer(Q3Command cmd) {
        this.commandList.add(cmd);
    }

    /**
     * Looks for the commandList stored on the HDD,
     * and if found, reads it into this object.
     */
    private void loadDatabase() {
        File db = new File(this.fileName);
        if (db.exists()) {
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                fis = new FileInputStream(this.fileName);
                in = new ObjectInputStream(fis);
                this.commandList = (ArrayList)in.readObject();
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
     * Saves the commandList to the Hard Disk.
     */
    public void saveDatabase() {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(this.fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(this.commandList);
            out.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

}
