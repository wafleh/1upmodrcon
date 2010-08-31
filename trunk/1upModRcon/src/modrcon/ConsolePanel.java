package modrcon;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that holds the console and console icons.
 *
 * @author Pyrite[1up]
 */
public class ConsolePanel extends JPanel {

    /** A reference to the Main Window */
    private MainWindow parent;

    /** Holds the buttons below the console. */
    private JPanel buttonPanel = new JPanel();

    private JTextArea taConsole;
    private JScrollPane jsp;

    private JLabel icon1;
    private JLabel icon2;
    private JLabel icon3;
    private JLabel icon4;

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

        icon1 = new JLabel();
        icon2 = new JLabel();
        icon3 = new JLabel();
        icon4 = new JLabel();
        icon1.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/copy.png")));
        icon2.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/files_remove.png")));
        icon3.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/find.png")));
        icon4.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/save.png")));

        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(icon1);
        buttonPanel.add(icon2);
        buttonPanel.add(icon3);
        buttonPanel.add(icon4);

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
        System.out.println("Appending to Console: "+text);
        this.taConsole.append(text);
    }

}
