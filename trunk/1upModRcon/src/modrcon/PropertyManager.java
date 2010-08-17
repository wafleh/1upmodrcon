package modrcon;

import java.util.*;
import javax.swing.*;

/**
 * A class to read/write properties for 1up ModRcon.
 *
 * @author Pyrite[1up]
 */
public class PropertyManager {

    /** The Properties File */
    private Properties propFile;

    /** The parent JFrame */
    private JFrame parent;

    public PropertyManager(JFrame parent) {
        this.parent = parent;
        this.loadPropertyFile();
    }

    public String getVersion() {
        return propFile.getProperty("version");
    }

    public void setVersion(String version) {
        propFile.setProperty("version", version);
    }

    public String getGamePath() {
        return propFile.getProperty("gamepath");
    }

    public void setGamePath(String path) {
        propFile.setProperty("gamepath", path);
    }

    /**
     * Loads the 1up ModRcon properties file.
     *
     * The filename is 1upmodrcon.properties
     */
    private void loadPropertyFile() {
        try {
            propFile = new Properties();
            propFile.load(new java.io.FileInputStream(new java.io.File("1upmodrcon.properties")));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Error loading property file!\nShutting down program.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * Saves the properties file.
     *
     * The filename is 1upmodrcon.properties
     */
    public void savePropertyFile() {
        try {
            propFile.store(new java.io.FileOutputStream(new java.io.File("1upmodrcon.properties")), "Property File for 1up ModRcon");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Error saving property file, if you changed\n"
                    + "settings then they have not been saved.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
