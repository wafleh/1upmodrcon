package modrcon;

import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.datatransfer.*;

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

    private JTextArea taConsole;
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
        
        taConsole = new JTextArea();
        taConsole.setFont(new Font("Monospaced", 0, 12));
        taConsole.setBackground(Color.BLACK);
        taConsole.setForeground(Color.YELLOW);
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

        popup = new JPopupMenu("1up ModRcon Console");
        popup.add(new JMenuItem("Select All"));
        popup.add(new JMenuItem("Copy Selected"));
        popup.add(new JMenuItem("Save Console As..."));
        this.taConsole.addMouseListener(this);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(iconCopy);
        buttonPanel.add(iconClear);
        buttonPanel.add(iconFind);
        buttonPanel.add(iconSave);

        this.add(jsp, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
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
        this.taConsole.append(text);
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
            JOptionPane.showMessageDialog(parent, "This feature will be coming in a later version!", iconSave.getToolTipText(), JOptionPane.INFORMATION_MESSAGE);
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
