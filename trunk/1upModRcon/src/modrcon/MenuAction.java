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
            this.parent.getConsolePanel().saveConsole();
        }

        else if (selection.equals("Save Selected to File")) {
            this.parent.getConsolePanel().saveSelected();
        }

        else if (selection.equals("Copy") || selection.equals("Copy Selected")) {
            String selectedText = this.parent.getConsolePanel().getSelectedText(); //this.consoleTextArea.getSelectedText();
            StringSelection data = new StringSelection(selectedText);
            Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(data, data);
        }

        else if (selection.equals("Clear") || selection.equals("Clear Console")) {
            this.parent.getConsolePanel().clearConsole();
        }

        else if (selection.equals("Load History")) {
            this.parent.getConsolePanel().loadConsoleHistory();
        }

        else if (selection.equals("Find")) {
            this.parent.getConsolePanel().findText();
        }

        else if (selection.equals("Select All")) {
            this.parent.getConsolePanel().selectAllText();
        }

        else if (selection.equals("Manage Servers")) {
            new ServerManager(this.parent);
        }

        else if (selection.equals("Manage Commands")) {
            new Q3CommandManager(this.parent);
        }

        else if (selection.equals("Settings")) {
            new SettingManager(this.parent);
        }

        else if (selection.equals("Server Info")) {
            try {
                Server server = this.parent.getCurrentServer();
                BowserQuery q = new BowserQuery(server);
                q.sendCmd("serverinfo");
                this.parent.getConsolePanel().appendCommand("serverinfo");
                this.parent.getConsolePanel().appendToConsole(q.getResponse());
            }
            catch (Exception exc) {
                System.out.println(exc.getMessage());
            }
        }

        else if (selection.equals("Exit")) {
            //parent.savePropertyFile();
            System.exit(0);
        }

        /*
        else if (selection.equals("1up ModRcon Help")) {
            try {
                BrowserLauncher.openURL("http://1upclan.info/1uprcon/help/");
            }
            catch (Exception exception) {}
        }
        */
        
        else if (selection.equals("About")) {
            new AboutWindow(this.parent);
        }

        else if (selection.equals("Gear Calculator")) {
            new GearCalculatorDialog(this.parent);
        }

        else if (selection.equals("Vote Calculator")) {
            new VoteCalculatorDialog(this.parent);
        }

        else if (selection.equals("Send Connectionless Packet")) {
            String cmd = (String)JOptionPane.showInputDialog(this.parent, "<html>Enter a Connectionless Packet Command.<br>E.g. getstatus, getinfo, getchallenge, etc.</html>", "getinfo");
            if (cmd != null) {
                try {
                    Server server = this.parent.getCurrentServer();
                    BowserQuery q = new BowserQuery(server);
                    q.setRawOutput(true);
                    q.sendConnectionlessPacket(cmd);
                    this.parent.getConsolePanel().appendCommand(cmd);
                    this.parent.getConsolePanel().appendToConsole(q.getResponse());
                }
                catch (Exception exc) {
                    System.out.println(exc.getMessage());
                }
            }
        }

        else if (selection.equals("Dump User")) {
            JTable source = this.parent.getLivePlayerInfoPanel().getLivePlayerInfoTable();
            String name = (String)source.getModel().getValueAt(source.getSelectedRow(), 2);
            try {
                BowserQuery q = new BowserQuery(this.parent.getCurrentServer());
                String cmd = "dumpuser \""+name+"\"";
                q.sendCmd(cmd);
                this.parent.getConsolePanel().appendCommand(cmd);
                this.parent.getConsolePanel().appendToConsole(q.getResponse());
            }
            catch (Exception e) {}
        }

        else if (selection.equals("Force Name")) {
            JTable source = this.parent.getLivePlayerInfoPanel().getLivePlayerInfoTable();
            String name = (String)source.getModel().getValueAt(source.getSelectedRow(), 2);
            name = ModRconUtil.trimAndStripColors(name);
            
        }

        else if (selection.equals("Slap User")) {
            JTable source = this.parent.getLivePlayerInfoPanel().getLivePlayerInfoTable();
            String name = (String)source.getModel().getValueAt(source.getSelectedRow(), 2);
            name = ModRconUtil.trimAndStripColors(name);
            try {
                BowserQuery q = new BowserQuery(this.parent.getCurrentServer());
                String cmd = "slap \""+name+"\"";
                q.sendCmd(cmd);
                this.parent.getConsolePanel().appendCommand(cmd);
                this.parent.getConsolePanel().appendToConsole(q.getResponse());
            }
            catch (Exception e) {}
        }

        else if (selection.equals("Kick User")) {
            JTable source = this.parent.getLivePlayerInfoPanel().getLivePlayerInfoTable();
            String name = (String)source.getModel().getValueAt(source.getSelectedRow(), 2);
            try {
                BowserQuery q = new BowserQuery(this.parent.getCurrentServer());
                String cmd = "kick \""+name+"\"";
                q.sendCmd(cmd);
                this.parent.getConsolePanel().appendCommand(cmd);
                this.parent.getConsolePanel().appendToConsole(q.getResponse());
            }
            catch (Exception e) {}
        }

        else if (selection.equals("Mute User")) {
            JTable source = this.parent.getLivePlayerInfoPanel().getLivePlayerInfoTable();
            String name = (String)source.getModel().getValueAt(source.getSelectedRow(), 2);
            name = ModRconUtil.trimAndStripColors(name);
            try {
                BowserQuery q = new BowserQuery(this.parent.getCurrentServer());
                String cmd = "mute \""+name+"\"";
                q.sendCmd(cmd);
                this.parent.getConsolePanel().appendCommand(cmd);
                this.parent.getConsolePanel().appendToConsole(q.getResponse());
            }
            catch (Exception e) {}
        }

        else if (selection.equals("ToggleMute User")) {
            JTable source = this.parent.getLivePlayerInfoPanel().getLivePlayerInfoTable();
            String name = (String)source.getModel().getValueAt(source.getSelectedRow(), 2);
            try {
                BowserQuery q = new BowserQuery(this.parent.getCurrentServer());
                String cmd = "togglemute \""+name+"\"";
                q.sendCmd(cmd);
                this.parent.getConsolePanel().appendCommand(cmd);
                this.parent.getConsolePanel().appendToConsole(q.getResponse());
            }
            catch (Exception e) {}
        }

        else if (selection.equals("Move To Red Team")) {
            JTable source = this.parent.getLivePlayerInfoPanel().getLivePlayerInfoTable();
            String name = (String)source.getModel().getValueAt(source.getSelectedRow(), 2);
            name = ModRconUtil.trimAndStripColors(name);
            try {
                BowserQuery q = new BowserQuery(this.parent.getCurrentServer());
                String cmd = "forceteam \""+name+"\" red";
                q.sendCmd(cmd);
                this.parent.getConsolePanel().appendCommand(cmd);
                this.parent.getConsolePanel().appendToConsole(q.getResponse());
            }
            catch (Exception e) {}
        }

        else if (selection.equals("Move To Blue Team")) {
            JTable source = this.parent.getLivePlayerInfoPanel().getLivePlayerInfoTable();
            String name = (String)source.getModel().getValueAt(source.getSelectedRow(), 2);
            name = ModRconUtil.trimAndStripColors(name);
            try {
                BowserQuery q = new BowserQuery(this.parent.getCurrentServer());
                String cmd = "forceteam \""+name+"\" blue";
                q.sendCmd(cmd);
                this.parent.getConsolePanel().appendCommand(cmd);
                this.parent.getConsolePanel().appendToConsole(q.getResponse());
            }
            catch (Exception e) {}
        }

        else if (selection.equals("Move To Spectators")) {
            JTable source = this.parent.getLivePlayerInfoPanel().getLivePlayerInfoTable();
            String name = (String)source.getModel().getValueAt(source.getSelectedRow(), 2);
            name = ModRconUtil.trimAndStripColors(name);
            try {
                BowserQuery q = new BowserQuery(this.parent.getCurrentServer());
                String cmd = "forceteam \""+name+"\" spectator";
                q.sendCmd(cmd);
                this.parent.getConsolePanel().appendCommand(cmd);
                this.parent.getConsolePanel().appendToConsole(q.getResponse());
            }
            catch (Exception e) {}
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
