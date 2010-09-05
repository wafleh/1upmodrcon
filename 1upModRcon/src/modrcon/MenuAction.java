package modrcon;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.datatransfer.*;

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
            JFileChooser file = new JFileChooser();
            int choice = file.showSaveDialog(file);
            if (choice == 0) {
                String path = file.getSelectedFile().getAbsolutePath();
                String contents = parent.consolePanel.getConsoleText();
                JOptionPane.showMessageDialog(parent, contents);
            }
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
        
        else {
            JOptionPane.showMessageDialog(parent, "This feature will be coming in a later version!", selection.toString(), JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
