package modrcon;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.ArrayList;

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
    private JButton btnImport;
    private JButton btnCancel;

    public ServerSetupWizard() {
        super();
        this.setTitle("1up ModRcon - Server Setup Wizard");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon topLeftIcon = new ImageIcon(getClass().getResource("/modrcon/resources/1up8bit_blue.png"));
        this.setIconImage(topLeftIcon.getImage());
        this.setResizable(false);
        this.setSize(420, 450);
        

        // Setup the Content Pane
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        GradientPanel gp = new GradientPanel(GradientPanel.WIZARD_COLOR_START, GradientPanel.WIZARD_COLOR_END);
        gp.add(this.getGPTitle());

        JPanel cPanel = new JPanel(new BorderLayout());
        cPanel.add(ModRconUtil.getPaddedPanel(5, this.getIntroPanel()), BorderLayout.NORTH);
        cPanel.add(ModRconUtil.getPaddedPanel(5, this.getServerPanel()), BorderLayout.CENTER);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSaveNow = new JButton("Save Now");
        btnImport = new JButton("Import");
        btnCancel = new JButton("Cancel");
        btnSaveNow.addActionListener(this);
        btnImport.addActionListener(this);
        btnCancel.addActionListener(this);
        buttonPanel.add(btnSaveNow);
        buttonPanel.add(btnImport);
        buttonPanel.add(btnCancel);

        cp.add(gp, BorderLayout.NORTH);
        cp.add(cPanel, BorderLayout.CENTER);
        cp.add(buttonPanel, BorderLayout.SOUTH);

        this.pack();

        // Center Window on Active Monitor
        this.setLocationRelativeTo(getRootPane()); 

        this.setVisible(true);
    }

    private JPanel getIntroPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        introText = new JLabel();
        introText.setForeground(new Color(0x228B22)); // Forrest Green
        introText.setFont(new Font("Tahoma", Font.BOLD, 11));
        introText.setOpaque(false);
        introText.setText("<html>Please take a moment to setup your first server connection.<br>If you need help just hover your mouse over one of the<br>information icons and a hint will appear for that item.<br>All fields are required!</html>");
        //introText.setText("<html><p>Please take a moment to setup your first server connection. If you need help just hover your mouse over one of the information icons and a hint will appear for that item. All fields are required!</p></html>");
        panel.add(Box.createHorizontalStrut(5));
        panel.add(introText);
        panel.add(Box.createHorizontalStrut(5));
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
        //Dimension d = new Dimension(100, 20);

        serverName = new JTextField();
        serverName.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);

        Dimension d = serverName.getPreferredSize();
        Dimension d2 = new Dimension(200, (int)d.getHeight());

        serverName.setPreferredSize(d2);
        serverName.setMaximumSize(d2);
        serverIP = new JTextField();
        serverIP.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverIP.setPreferredSize(d2);
        serverIP.setMaximumSize(d2);
        serverPort = new JTextField();
        serverPort.setText("27960");
        serverPort.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverPort.setPreferredSize(d2);
        serverPort.setMaximumSize(d2);
        serverMethod = this.getMethodCombo();
        serverMethod.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverMethod.setPreferredSize(d2);
        serverMethod.setMaximumSize(d2);
        serverPassword = new JPasswordField();
        serverPassword.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverPassword.setPreferredSize(d2);
        serverPassword.setMaximumSize(d2);

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
        infoIconServer.setToolTipText(getNiceToolTip("Server Name", "Enter your server name here, it can be anything you want."));
        infoIconIP.setToolTipText(getNiceToolTip("IP Address", "Enter the IP address for the above server here."));
        infoIconPort.setToolTipText(getNiceToolTip("Port", "Enter the port the server runs on, usually it is just 27960."));
        infoIconMethod.setToolTipText(getNiceToolTip("Login Method", "The type of connection to the server (ref, mod, rcon).<br>Please note however, Moderator is a custom login type for the 1up clan."));
        infoIconPassword.setToolTipText(getNiceToolTip("Password", "The ref/mod/rcon password on the server."));
        infoIconServer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        infoIconIP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        infoIconPort.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        infoIconMethod.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        infoIconPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel line1 = new JPanel();
        line1.setLayout(new BoxLayout(line1, BoxLayout.X_AXIS));
        line1.add(Box.createHorizontalGlue());
        line1.add(lblServerName);
        line1.add(serverName);
        line1.add(Box.createHorizontalStrut(5));
        line1.add(infoIconServer);
        line1.add(Box.createHorizontalStrut(5));

        JPanel line2 = new JPanel();
        line2.setLayout(new BoxLayout(line2, BoxLayout.X_AXIS));
        line2.add(Box.createHorizontalGlue());
        line2.add(lblServerIP);
        line2.add(serverIP);
        line2.add(Box.createHorizontalStrut(5));
        line2.add(infoIconIP);
        line2.add(Box.createHorizontalStrut(5));

        JPanel line3 = new JPanel();
        line3.setLayout(new BoxLayout(line3, BoxLayout.X_AXIS));
        line3.add(Box.createHorizontalGlue());
        line3.add(lblServerPort);
        line3.add(serverPort);
        line3.add(Box.createHorizontalStrut(5));
        line3.add(infoIconPort);
        line3.add(Box.createHorizontalStrut(5));

        JPanel line4 = new JPanel();
        line4.setLayout(new BoxLayout(line4, BoxLayout.X_AXIS));
        line4.add(Box.createHorizontalGlue());
        line4.add(lblServerMethod);
        line4.add(serverMethod);
        line4.add(Box.createHorizontalStrut(5));
        line4.add(infoIconMethod);
        line4.add(Box.createHorizontalStrut(5));

        JPanel line5 = new JPanel();
        line5.setLayout(new BoxLayout(line5, BoxLayout.X_AXIS));
        line5.add(Box.createHorizontalGlue());
        line5.add(lblServerPassword);
        line5.add(serverPassword);
        line5.add(Box.createHorizontalStrut(5));
        line5.add(infoIconPassword);
        line5.add(Box.createHorizontalStrut(5));

        serverPanel.add(line1);
        serverPanel.add(Box.createVerticalStrut(5));
        serverPanel.add(line2);
        serverPanel.add(Box.createVerticalStrut(5));
        serverPanel.add(line3);
        serverPanel.add(Box.createVerticalStrut(5));
        serverPanel.add(line4);
        serverPanel.add(Box.createVerticalStrut(5));
        serverPanel.add(line5);
        serverPanel.add(Box.createVerticalStrut(15));

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
        if (serverName.getText().length() < 1) {
            this.serverName.setBackground(GradientPanel.WARNING_COLOR_START);
            flag = false;
        }
        if (serverIP.getText().length() < 1) {
            serverIP.setBackground(GradientPanel.WARNING_COLOR_START);
            flag = false;
        }
        if (serverPort.getText().length() < 1) {
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
        this.serverMethod.setSelectedItem("Moderator");
        return this.serverMethod;
    }

    public String getNiceToolTip(String title, String tip) {
        String output = "";
        output += "<html>";
        output += "<img src=\""+getClass().getResource("/modrcon/resources/about.png")+"\">";
        output += "&nbsp;&nbsp;<b>"+title+"</b><br>"+tip+"</html>";
        return output;
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
        else if (pressedButton == this.btnImport) {
            JPasswordField pass = new JPasswordField();
            Object[] msg = { "Enter your 1up mod password:", pass };
            int choice = JOptionPane.showConfirmDialog(this, msg, "Import 1up Server List", JOptionPane.OK_CANCEL_OPTION);
            if (choice == JOptionPane.OK_OPTION) {
                ArrayList servers = ModRconUtil.get1upServers();
                if (servers.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "I'm sorry, I was unable to import the 1up server list.");
                }
                else {
                    ArrayList newServerList = new ArrayList();
                    for (Object o : servers) {
                        Server s = (Server)o;
                        Server n = new Server(s.getName(), s.getIP(), s.getPortAsString(), s.getLoginType(), String.valueOf(pass.getPassword()));
                        newServerList.add(n);
                    }
                    ServerDatabase db = new ServerDatabase();
                    db.setServerList(newServerList);
                    db.saveDatabase();
                    this.dispose();
                    this.runMainWindow();
                }
            }
        }
        else if (pressedButton == this.btnCancel) {
            String str = "<html><p>Your connection settings have not been saved<br>to the database. If you exit now, you will have<br>to run this wizard again the next time you start<br>this program.<br><br>Are you sure you want to exit now?</p></html>";
            int choice = (int)JOptionPane.showConfirmDialog(this, str, "Information Not Saved" , JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                this.dispose();
            }
        }
    }



}
