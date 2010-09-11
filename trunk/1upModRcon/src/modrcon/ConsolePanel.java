package modrcon;

import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.datatransfer.*;
import java.io.*;

/**
 * A JPanel that holds the console and console icons.
 *
 * TODO: set console to courier new
 *
 * @author Pyrite[1up]
 */
public class ConsolePanel extends JPanel implements MouseListener {

    /** A reference to the Main Window */
    private MainWindow parent;

    /** Holds the buttons below the console. */
    private JPanel buttonPanel = new JPanel();

    //private JTextArea taConsole;
    private ConsoleTextPane taConsole;
    private JScrollPane jsp;

    private JLabel iconCopy;
    private JLabel iconClear;
    private JLabel iconFind;
    private JLabel iconSave;

    private JPopupMenu popup;

    public ConsolePanel(MainWindow owner) {
        super();
        this.parent = owner;

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Console"));
        
        //taConsole = new JTextArea();
        taConsole = new ConsoleTextPane();
        taConsole.setFont(new Font("Monospaced", Font.PLAIN, 12));
        PropertyManager pm = new PropertyManager();
        taConsole.setBackground(Color.decode(pm.getConsoleBGColor()));
        taConsole.setForeground(Color.decode(pm.getConsoleFGColor()));
        jsp = new JScrollPane(taConsole);
        
        iconCopy = new JLabel();
        iconClear = new JLabel();
        iconFind = new JLabel();
        iconSave = new JLabel();
        iconCopy.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/copy.png")));
        iconClear.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/files_remove.png")));
        iconFind.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/find.png")));
        iconSave.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/save.png")));
        iconCopy.setToolTipText("Copy selected text.");
        iconClear.setToolTipText("Clear console contents.");
        iconFind.setToolTipText("Search for one or more words in the console.");
        iconSave.setToolTipText("Save console to log.");
        iconCopy.addMouseListener(this);
        iconClear.addMouseListener(this);
        iconFind.addMouseListener(this);
        iconSave.addMouseListener(this);

        popup = getConsolePopupMenu();
        this.taConsole.addMouseListener(this);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(iconCopy);
        buttonPanel.add(iconClear);
        buttonPanel.add(iconFind);
        buttonPanel.add(iconSave);

        this.add(jsp, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPopupMenu getConsolePopupMenu() {
        JPopupMenu p = new JPopupMenu();
        JMenuItem selectAll = new JMenuItem(new MenuAction("Select All", this.parent));
        JMenuItem copySelected = new JMenuItem(new MenuAction("Copy Selected", this.parent));
        JMenuItem saveConsole = new JMenuItem(new MenuAction("Save Console As...", this.parent));
        JMenuItem clearConsole = new JMenuItem(new MenuAction("Clear Console", this.parent));    
        JMenuItem sendCP = new JMenuItem(new MenuAction("Send Connectionless Packet", this.parent));
        p.add(selectAll);
        p.add(copySelected);
        p.add(saveConsole);
        p.add(clearConsole);
        p.addSeparator();
        p.add(sendCP);
        return p;
    }

    public void selectAllText() {
        this.taConsole.requestFocusInWindow();
        this.taConsole.selectAll();
    }

    public void setConsoleBackground(Color c) {
        this.taConsole.setBackground(c);
    }

    public void setConsoleForeground(Color c) {
        this.taConsole.setForeground(c);
    }

    public String getSelectedText() {
        return this.taConsole.getSelectedText();
    }

    public String getConsoleText() {
        return this.taConsole.getText();
    }

    public void clearConsole() {
        this.taConsole.setText("");
    }

    public void appendToConsole(String text) {
        this.taConsole.append(text, "default");
    }

    public void appendCommand(String command) {
        this.taConsole.appendCommand(command);
    }

    public void appendToConsole(String text, String styleName) {
        this.taConsole.append(text, styleName);
    }

    public void appendWithColor(String playerName) {
        this.taConsole.appendWithColors(playerName);
    }

    public void saveConsole() {
        JFileChooser file = new JFileChooser();
        int choice = file.showSaveDialog(file);
        if (choice == 0) {
            String path = file.getSelectedFile().getAbsolutePath();
            String contents = this.getConsoleText();
            try {
                FileWriter outFile = new FileWriter(path);
                PrintWriter out = new PrintWriter(outFile);
                out.print(contents);
                this.appendToConsole("\nConsole log saved to: "+path+"\n");
                out.close();
                JOptionPane.showMessageDialog(this.parent, "File Saved.");
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog(this.parent, e.getMessage());
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.iconCopy) {
            StringSelection data = new StringSelection(this.getSelectedText());
            Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(data, data);
        }
        else if (e.getSource() == this.iconClear) {
            this.clearConsole();
        }
        else if (e.getSource() == this.iconFind) {
            JOptionPane.showMessageDialog(parent, "This feature will be coming in a later version!", iconFind.getToolTipText(), JOptionPane.INFORMATION_MESSAGE);
        }
        else if (e.getSource() == this.iconSave) {
            this.saveConsole();
        }
        else if (e.getSource() == this.taConsole) {
            if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) {
                this.popup.show(this.taConsole, e.getX(), e.getY());
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void mouseReleased(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

}
