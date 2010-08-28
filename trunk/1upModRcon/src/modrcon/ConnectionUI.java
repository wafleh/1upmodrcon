/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modrcon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.event.*;

/**
 *
 * @author Pyrite[1up]
 */
public class ConnectionUI extends JFrame implements ActionListener {

    protected GradientPanel logoPanel           = new GradientPanel();
    protected JPanel connectionInfoPanel = new JPanel();
    protected JPanel buttonPanel         = new JPanel();

    protected JLabel labelServerName   = new JLabel("Servername:");
    protected JLabel labelServerIP     = new JLabel("Server IP:");
    protected JLabel labelServerPort   = new JLabel("Server Port:");
    protected JLabel labelModPassword  = new JLabel("Mod Password:");
    protected JLabel labelRconPassword = new JLabel("Rcon Password:");

    protected JComboBox comboServerName   = new JComboBox();
    protected JTextField textServerIP     = new JTextField();
    protected JTextField textServerPort   = new JTextField();
    protected JTextField textModPassword  = new JTextField();
    protected JTextField textRconPassword = new JTextField();

    protected JButton btnModConnect  = new JButton("Mod Connect");
    protected JButton btnRconConnect = new JButton("Rcon Connect");
    protected JButton btnClose       = new JButton("Close");


    public ConnectionUI () {
        // Sets Some of the JFrame Options
        this.setTitle("1up ModRcon - Connect");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700,500);
        this.setResizable(true);

        // Setup the Content Pane
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        /*
        ImageIcon logo = new ImageIcon();
        try {
            logo = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
        }
        catch (Exception e) {}
        JLabel logoLabel = new JLabel(logo);
         *
         */

        //logoPanel.setBackground(Color.red);
        //logoPanel.add(logoLabel);

        String[] labels = {"Server Name: ", "Server IP: ", "Server Port: ", "Mod Password: ", "Rcon Password: "};
        int numPairs = labels.length;




        connectionInfoPanel.setLayout(new SpringLayout());
        connectionInfoPanel.setBorder(BorderFactory.createTitledBorder("Connection Info"));

        //Create and populate the panel.
        //JPanel p = new JPanel(new SpringLayout());
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            connectionInfoPanel.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            connectionInfoPanel.add(textField);
        }

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(connectionInfoPanel,
                                        numPairs, 2, //rows, cols
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad

        buttonPanel.add(btnModConnect);
        buttonPanel.add(btnRconConnect);
        buttonPanel.add(btnClose);

        //connectionInfoPanel.add(buttonPanel);
        
        // Add various JPanels to Content Pane
        contentPane.add(logoPanel, BorderLayout.NORTH);
        contentPane.add(connectionInfoPanel, BorderLayout.CENTER);
        contentPane.add(new PlayerCountPanel(), BorderLayout.SOUTH);

        // Make the Widgets Snug
        this.pack();

        // Center the Window (Whatever Its Size) on the Screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)((screenSize.getWidth()/2)-this.getWidth()/2), (int)((screenSize.getHeight()/2)-this.getHeight()/2) );

        // Bring the Window into Focus
        this.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ConnectionUI();
    }

    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
    }


}
