package modrcon;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 *
 * @author pyrite
 */
public class ServerSetupWizard extends JFrame {


    private JLabel introText;
    private JPanel serverPanel;

    private JTextField serverName;
    private JTextField serverIP;
    private JTextField serverPort;
    private JTextField serverMethod;
    private JPasswordField serverPassword;


    private JLabel infoIconLabel;
    private JPanel buttonPanel;

    private JButton btnSaveNow;
    private JButton btnCancel;

    public ServerSetupWizard() {
        super();
        this.setTitle("1up ModRcon - Setup (Final Step)");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ImageIcon topLeftIcon = new ImageIcon(getClass().getResource("/modrcon/resources/1up8bit_blue.png"));
        this.setIconImage(topLeftIcon.getImage());

        // Setup the Content Pane
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        GradientPanel gp = new GradientPanel(GradientPanel.WIZARD_COLOR_START, GradientPanel.WIZARD_COLOR_END);
        
        gp.add(this.getGPTitle());

        introText = new JLabel("<html>Congratulations, you have successfully installed 1up ModRcon.<br>Please take a moment to setup your first server connection<br>now. If you need help just hover your mouse over one of the<br>information icons and a hint will appear for that item.</html>");
        introText.setForeground(new Color(0x228B22)); // Forrest Green
        introText.setFont(new Font("Tahoma", Font.BOLD, 11));

        JPanel cPanel = new JPanel(new VerticalFlowLayout());
        cPanel.add(introText);
        cPanel.add(this.getServerPanel());

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSaveNow = new JButton("Save Now");
        btnCancel = new JButton("Cancel");
        buttonPanel.add(btnSaveNow);
        buttonPanel.add(btnCancel);

        cp.add(gp, BorderLayout.NORTH);
        cp.add(cPanel, BorderLayout.CENTER);
        cp.add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
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

    private JPanel getServerPanel() {
        serverPanel = new JPanel(new SpringLayout());
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
        serverPort.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverMethod = new JTextField(25);
        serverMethod.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);
        serverPassword = new JPasswordField(25);
        serverPassword.setBackground(GradientPanel.SELECTED_GRID_CELL_BG_COLOR);

        // add components
        serverPanel.add(lblServerName);
        lblServerName.setLabelFor(serverName);
        serverPanel.add(serverName);
        serverPanel.add(lblServerIP);
        lblServerIP.setLabelFor(serverIP);
        serverPanel.add(serverIP);
        serverPanel.add(lblServerPort);
        lblServerPort.setLabelFor(serverPort);
        serverPanel.add(serverPort);
        serverPanel.add(lblServerMethod);
        lblServerMethod.setLabelFor(serverMethod);
        serverPanel.add(serverMethod);
        serverPanel.add(lblServerPassword);
        lblServerPassword.setLabelFor(serverPassword);
        serverPanel.add(serverPassword);

        
        

        SpringUtilities.makeCompactGrid(serverPanel,
             5, 2,  //rows, cols
            10, 6, //initX, initY
            10, 6  //xPad, yPad
        );
        return serverPanel;
    }


    private JLabel getGPTitle() {
        JLabel label = new JLabel("Server Setup Wizard");
        label.setFont(new Font("Tahoma", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        Rectangle2D bounds = label.getFontMetrics(label.getFont()).getStringBounds(label.getText(), null);
        label.setPreferredSize(new Dimension((int)bounds.getWidth(), 48));
        return label;
    }



}
