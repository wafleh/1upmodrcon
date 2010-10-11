package modrcon;

import java.io.*;
import java.util.*;

/**
 * A class to store the servers.
 *
 * @author Pyrite
 */
public class ServerDatabase implements Serializable {

    /** An ArrayList of Server objects. */
    private ArrayList serverList;

    /** The filename to write the serverList to. */
    private final String fileName = "servers.db";

    /**
     * Constructs a ServerDatabase and populates it
     * if there are any servers currently stored to disk.
     */
    public ServerDatabase() {
        super();
        this.serverList = new ArrayList();
        this.loadDatabase();
    }

    /**
     * Gets the server list.
     *
     * @return The server list.
     */
    public ArrayList getServerList() {
        return this.serverList;
    }

    /**
     * Sets the server list.
     *
     * @param list The server list.
     */
    public void setServerList(ArrayList list) {
        this.serverList = list;
    }

    /**
     * Add a Server to the ServerDatabase.
     *
     * @param s The Server to Add.
     */
    public void addServer(Server s) {
        // do some code
        this.serverList.add(s);
    }

    /**
     * Looks for the serverList stored on the HDD,
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
                this.serverList = (ArrayList)in.readObject();
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
     * Saves the serverList to the Hard Disk.
     */
    public void saveDatabase() {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(PropertyManager.settingsPath+this.fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(this.serverList);
            out.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

}
