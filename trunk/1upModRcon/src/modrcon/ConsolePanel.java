/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modrcon;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 *
 * @author pyrite
 */
public class ConsolePanel extends JPanel {

    /** Holds the console text area. */
    private JPanel top = new JPanel();
    /** Holds the buttons below the console. */
    private JPanel bottom = new JPanel();

    private JTextArea taConsole;
    private JScrollPane jsp;



    private JLabel icon1;
    private JLabel icon2;
    private JLabel icon3;
    private JLabel icon4;

    public ConsolePanel() {
        super();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Console"));
        
        taConsole = new JTextArea();
        taConsole.setFont(new Font("Monospaced", 0, 12));
        jsp = new JScrollPane(taConsole);

        icon1 = new JLabel();
        icon2 = new JLabel();
        icon3 = new JLabel();
        icon4 = new JLabel();
        icon1.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/files_remove.png")));
        icon2.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/files_remove.png")));
        icon3.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/files_remove.png")));
        icon4.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/files_remove.png")));

        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        bottom.setLayout(new FlowLayout());
        bottom.setAlignmentX(FlowLayout.LEFT);

        top.add(jsp);
        bottom.add(icon1);
        bottom.add(icon2);
        bottom.add(icon3);
        bottom.add(icon4);

        this.add(top);
        this.add(bottom);
    }

}
