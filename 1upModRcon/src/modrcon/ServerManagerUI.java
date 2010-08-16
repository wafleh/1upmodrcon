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
public class ServerManagerUI extends JFrame implements ActionListener {

    protected JPanel logoPanel           = new JPanel();
    protected JPanel serverListPanel     = new JPanel();
    protected JPanel buttonPanel         = new JPanel();

    protected JLabel labelServerName   = new JLabel("Servername:");
    protected JLabel labelServerIP     = new JLabel("Server IP:");
    protected JLabel labelServerPort   = new JLabel("Server Port:");
    protected JLabel labelModPassword  = new JLabel("Mod Password:");
    protected JLabel labelRconPassword = new JLabel("Rcon Password:");

    protected JLabel labelNote = new JLabel("Note: Changes are only saved to the database after clicking the Save button.");

    protected JComboBox comboServerName   = new JComboBox();
    protected JTextField textServerIP     = new JTextField();
    protected JTextField textServerPort   = new JTextField();
    protected JTextField textModPassword  = new JTextField();
    protected JTextField textRconPassword = new JTextField();

    protected JButton btnSave  = new JButton("Save");
    protected JButton btnClose = new JButton("Close");

    public ServerManagerUI() {
        // Sets Some of the JFrame Options
        this.setTitle("1up ModRcon - Manage Servers");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(724,490);
        this.setResizable(false);

        // Setup the Content Pane
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());


        ImageIcon logo = new ImageIcon();
        try {
            logo = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
        }
        catch (Exception e) {}
        JLabel logoLabel = new JLabel(logo);

        //logoPanel.setBackground(Color.red);
        logoPanel.add(logoLabel);

        serverListPanel.setLayout(new FlowLayout());
        serverListPanel.setBorder(BorderFactory.createTitledBorder("Server List"));


        buttonPanel.add(btnSave);
        buttonPanel.add(btnClose);

        //connectionInfoPanel.add(buttonPanel);

        // Add various JPanels to Content Pane
        contentPane.add(logoPanel, BorderLayout.NORTH);
        contentPane.add(serverListPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Make the Widgets Snug
        this.pack();

        // Center the Window (Whatever Its Size) on the Screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)((screenSize.getWidth()/2)-this.getWidth()/2), (int)((screenSize.getHeight()/2)-this.getHeight()/2) );

        // Bring the Window into Focus
        this.setVisible(true);

    }

    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
    }

}
