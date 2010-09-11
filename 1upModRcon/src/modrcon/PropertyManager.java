package modrcon;

import java.util.*;
import javax.swing.*;
import java.io.*;

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

    /** Default Constructor */
    public PropertyManager(JFrame parent) {
        this.parent = parent;
        this.loadPropertyFile();
    }

    public PropertyManager() {
        this.parent = null;
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

    public String getReceiveTimeout() {
        return propFile.getProperty("receivetimeout");
    }

    public int getReceiveTimeoutNumber() {
        try {
            int timeout = Integer.parseInt(propFile.getProperty("receivetimeout"));
            return timeout;
        }
        catch (NumberFormatException e) {
            return 100;
        }
    }

    public void setReceiveTimeout(String timeout) {
        propFile.setProperty("receivetimeout", timeout);
    }

    public boolean getStatusOnConnect() {
        if (propFile.getProperty("statusonconnect").equals("true")) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setStatusOnConnect(boolean status) {
        if (status) {
            propFile.setProperty("statusonconnect", "true");
        }
        else {
            propFile.setProperty("statusonconnect", "false");
        }
    }

    public String getConsoleBGColor() {
        return propFile.getProperty("consolebgcolor");
    }

    public void setConsoleBGColor(String color) {
        propFile.setProperty("consolebgcolor", color);
    }

    public String getConsoleFGColor() {
        return propFile.getProperty("consolefgcolor");
    }

    public void setConsoleFGColor(String color) {
        propFile.setProperty("consolefgcolor", color);
    }

    /**
     * Creates a default 1upmodrcon.properties file with default values.
     *
     * @param file The file to attempt to create.
     * @return True on Success, otherwise False.
     */
    private boolean writeDefaultPropertiesFile(File file) {
        Writer writer = null;
        try {
            String text = "#Property File for 1up ModRcon\n#Fri Aug 27 23:18:57 CDT 2010\nversion=1.0\ngamepath=\nconsolebgcolor=\\#0\nconsolefgcolor=\\#FFFFFF\nreceivetimeout=100\nstatusonconnect=false\n";
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(text);
            return true;
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Loads the 1up ModRcon properties file.
     *
     * The filename is 1upmodrcon.properties
     */
    private void loadPropertyFile() {
        try {
            File pfile = new File("1upmodrcon.properties");
            if (!pfile.exists()) {
                if (!writeDefaultPropertiesFile(pfile)) {
                    JOptionPane.showMessageDialog(parent, "Error attempting to create properties file.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                } else {
                    propFile = new Properties();
                    propFile.load(new FileInputStream(pfile));
                }
            } else {
                propFile = new Properties();
                propFile.load(new FileInputStream(pfile));
            }
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
