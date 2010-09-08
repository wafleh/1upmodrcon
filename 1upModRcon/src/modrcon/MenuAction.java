package modrcon;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.datatransfer.*;
import java.io.*;

/**
 * Handles the menu bar actions for the MainWindow singleton.
 *
 * @author  Pyrite[1up]
 * @version 1.0
 */
public class MenuAction extends AbstractAction {

    /** A reference to the Main Window */
    private MainWindow parent;

    /**
     * Constructor to create a MenuAction object.
     *
     * @param n      The menu item name.
     * @param parent The parent MainWindow object.
     */
    public MenuAction(String n, MainWindow parent) {
        super(n);
        this.parent = parent;
    }

    /* Inherits Javadoc */
    public void actionPerformed(ActionEvent evt) {

        String selection = (String)getValue(Action.NAME);

        if (selection.equals("Save Console As...")) {
            this.parent.consolePanel.saveConsole();
        }

        else if (selection.equals("Copy")) {
            String selectedText = this.parent.consolePanel.getSelectedText(); //this.consoleTextArea.getSelectedText();
            StringSelection data = new StringSelection(selectedText);
            Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(data, data);
        }
        else if (selection.equals("Clear")) {
            this.parent.consolePanel.clearConsole();
        }

        else if (selection.equals("Manage Servers")) {
            new ServerManager(this.parent);
        }

        else if (selection.equals("Settings")) {
            new SettingManager(this.parent);
        }

        else if (selection.equals("Exit")) {
            //parent.savePropertyFile();
            System.exit(0);
        }

        else if (selection.equals("About")) {
            new AboutWindow(this.parent);
        }

        else if (selection.equals("Gear Calculator")) {
        new GearCalculatorDialog(this.parent);
        }

        else if (selection.equals("Send \"getstatus\"")) {
            try {
                Server server = (Server)this.parent.comboServerList.getSelectedItem();
                BowserQuery bQuery = new BowserQuery(server);
                this.parent.consolePanel.appendCommand("getstatus");
                this.parent.consolePanel.appendToConsole(bQuery.getstatus());
            } catch (Exception exc) { System.out.println(exc.getMessage()); }
        }

        else if (selection.equals("Send \"getinfo\"")) {
            try {
                Server server = (Server)this.parent.comboServerList.getSelectedItem();
                BowserQuery bQuery = new BowserQuery(server);
                this.parent.consolePanel.appendCommand("getinfo");
                this.parent.consolePanel.appendToConsole(this.getStringFromMap(bQuery.getServerInfo()));
            } catch (Exception exc) { System.out.println(exc.getMessage()); }
        }
       
        else {
            JOptionPane.showMessageDialog(parent, "This feature will be coming in a later version!", selection.toString(), JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private String getStringFromMap(Map map) {
        String returnString = "";

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            returnString += pairs.getKey() + "=" + pairs.getValue() + "\n";
        }

        return returnString;
    }
}
