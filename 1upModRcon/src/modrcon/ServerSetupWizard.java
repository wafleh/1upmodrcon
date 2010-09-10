package modrcon;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.*;

/**
 * Displays a window to add a single server to the database.
 *
 * @author pyrite
 */
public class ServerSetupWizard extends JFrame implements ActionListener {

    private JLabel introText;
    private JPanel serverPanel;
    private JTextField serverName;
    private JTextField serverIP;
    private JTextField serverPort;
    private JComboBox serverMethod;
    private JPasswordField serverPassword;
    private JPanel buttonPanel;
    private JButton btnSaveNow;
    private JButton btnCancel;

    public ServerSetupWizard() {
        super();
        this.setTitle("1up ModRcon - Server Setup Wizard");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon topLeftIcon = new ImageIcon(getClass().getResource("/modrcon/resources/1up8bit_blue.png"));
        this.setIconImage(topLeftIcon.getImage());
        this.setResizable(false);
        //410x422
        this.setSize(410, 422);
        

        // Setup the Content Pane
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        GradientPanel gp = new GradientPanel(GradientPanel.WIZARD_COLOR_START, GradientPanel.WIZARD_COLOR_END);
        gp.add(this.getGPTitle());

        JPanel cPanel = new JPanel();
        cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
        cPanel.add(this.getIntroPanel());
        cPanel.add(this.getServerPanel());

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSaveNow = new JButton("Save Now");
        btnCancel = new JButton("Cancel");
        btnSaveNow.addActionListener(this);
        btnCancel.addActionListener(this);
        buttonPanel.add(btnSaveNow);
        buttonPanel.add(btnCancel);

        cp.add(gp, BorderLayout.NORTH);
        cp.add(cPanel, BorderLayout.CENTER);
        cp.add(buttonPanel, BorderLayout.SOUTH);

        this.pack();

        // Center Window on Active Monitor
        this.setLocationRelativeTo(getRootPane()); 

        this.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            // Set the OS's Native Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.out.println("Error: Was Unable to Set the OS's Native Widget Theme");
            System.out.println(e.getMessage());
        }
        new ServerSetupWizard();
    }

    private JPanel getIntroPanel() {
        JPanel panel = new JPanel();
        introText = new JLabel();
        introText.setForeground(new Color(0x228B22)); // Forrest Green
        introText.setFont(new Font("Tahoma", Font.BOLD, 11));
        introText.setOpaque(false);
        introText.setText("<html>Please take a moment to setup your first server connection.<br>If you need help just hover your mouse over one of the<br>information icons and a hint will appear for that item.<br>All fields are required!</html>");
        panel.add(introText);
        return panel;
    }

    private JPanel getServerPanel() {
        serverPanel = new JPanel();
        serverPanel.setLayout(new BoxLayout(serverPanel, BoxLayout.Y_AXIS));
        serverPanel.setBorder(BorderFactory.createTitledBorder("Server Info"));

        JLabel lblServerName = new JLabel("Server Name: ", JLabel.TRAILING);
        JLabel lblServerIP = new JLabel("Server IP: ", JLabel.TRAILING);
        JLabel lblServerPort = new JLabel("Server Port: ", JLabel.TRAILING);
        JLabel lblServerMethod = new JLabel("Login Method: ", JLabel.TRAILING);
        JLabel lblServerPassword = new JLabel("Server Password: ", JLabel.TRAILING);

        // initial jtextfileds
        serverName = new JTextField(25);
        serverName.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverIP = new JTextField(25);
        serverIP.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverPort = new JTextField(25);
        serverPort.setText("27960");
        serverPort.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverMethod = this.getMethodCombo();
        serverMethod.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverPassword = new JPasswordField(25);
        serverPassword.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);

        // Info Icons
        JLabel infoIconServer = new JLabel();
        JLabel infoIconIP = new JLabel();
        JLabel infoIconPort = new JLabel();
        JLabel infoIconMethod = new JLabel();
        JLabel infoIconPassword = new JLabel();
        infoIconServer.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/about.png")));
        infoIconIP.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/about.png")));
        infoIconPort.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/about.png")));
        infoIconMethod.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/about.png")));
        infoIconPassword.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/about.png")));
        infoIconServer.setToolTipText("Enter your server name here, it can be anything you want.");
        infoIconIP.setToolTipText("Enter the IP address for the above server here.");
        infoIconPort.setToolTipText("Enter the port the server runs on, usually it is just 27960.");
        infoIconMethod.setToolTipText("<html>The type of connection to the server (ref, mod, rcon).<br>Please note however, Moderator is a custom login type for the 1up clan.</html>");
        infoIconPassword.setToolTipText("The ref/mod/rcon password on the server.");
        infoIconServer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        infoIconIP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        infoIconPort.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        infoIconMethod.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        infoIconPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel line1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        line1.add(lblServerName);
        line1.add(serverName);
        line1.add(infoIconServer);

        JPanel line2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        line2.add(lblServerIP);
        line2.add(serverIP);
        line2.add(infoIconIP);

        JPanel line3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        line3.add(lblServerPort);
        line3.add(serverPort);
        line3.add(infoIconPort);

        JPanel line4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        line4.add(lblServerMethod);
        line4.add(serverMethod);
        line4.add(infoIconMethod);

        JPanel line5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        line5.add(lblServerPassword);
        line5.add(serverPassword);
        line5.add(infoIconPassword);

        serverPanel.add(line1);
        serverPanel.add(line2);
        serverPanel.add(line3);
        serverPanel.add(line4);
        serverPanel.add(line5);
        serverPanel.add(this.getLastInstructionsPanel());


        return serverPanel;
    }

    private boolean validateFields() {
        boolean flag = true;

        // Reset Text Field Colors
        serverName.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverIP.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverPort.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverPassword.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);

        // Ensure all fields are non-empty.
        if (serverName.getText().isEmpty()) {
            this.serverName.setBackground(GradientPanel.WARNING_COLOR_START);
            flag = false;
        }
        if (serverIP.getText().isEmpty()) {
            serverIP.setBackground(GradientPanel.WARNING_COLOR_START);
            flag = false;
        }
        if (serverPort.getText().isEmpty()) {
            serverPort.setBackground(GradientPanel.WARNING_COLOR_START);
            flag = false;
        }
        if (serverPassword.getPassword().length < 1) {
            serverPassword.setBackground(GradientPanel.WARNING_COLOR_START);
            flag = false;
        }
        // Ensure IP is really an IP or Host
        if (!ModRconUtil.isIPAddress(serverIP.getText())) {
            serverIP.setBackground(GradientPanel.WARNING_COLOR_START);
            flag = false;
        }
        // Ensure Port is really a number
        try {
            String port = serverPort.getText();
            Integer.parseInt(port);
        }
        catch (Exception e) {
            serverPort.setBackground(GradientPanel.WARNING_COLOR_START);
            flag = false;
        }

        return flag;
    }

    private JComboBox getMethodCombo() {
        String[] values = new String[] { "Referee", "Moderator", "RCON" };
        this.serverMethod = new JComboBox(values);
        this.serverMethod.setPrototypeDisplayValue("WWWWWWWWWWWWWWWWWW");
        this.serverMethod.setSelectedItem("Moderator");
        return this.serverMethod;
    }

    private JPanel getLastInstructionsPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Click Save Now once you have filled out all required fields to continue."));
        return panel;
    }


    private JLabel getGPTitle() {
        JLabel label = new JLabel("Server Setup Wizard");
        label.setFont(new Font("Tahoma", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        Rectangle2D bounds = label.getFontMetrics(label.getFont()).getStringBounds(label.getText(), null);
        label.setPreferredSize(new Dimension((int)bounds.getWidth(), 48));
        return label;
    }

    private void runMainWindow() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow mui = new MainWindow();
                mui.setVisible(true);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        AbstractButton pressedButton = (AbstractButton)e.getSource();
        if (pressedButton == btnSaveNow) {
            if (this.validateFields()) {
                // save to database
                String method = (String)serverMethod.getSelectedItem();
                if (method.equals("Referee")) {
                    method = "ref";
                }
                else if (method.equals("Moderator")) {
                    method = "mod";
                }
                else {
                    method = "rcon";
                }
                String password = new String(serverPassword.getPassword());
                Server s = new Server(serverName.getText(), serverIP.getText(), serverPort.getText(), method, password);

                ServerDatabase db = new ServerDatabase();
                db.addServer(s);
                db.saveDatabase();
                
                this.dispose();
                this.runMainWindow();
            }
        }
        else {
            this.dispose();
        }
    }



}
