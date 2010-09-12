package modrcon;

import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * The Server Info Panel.
 *
 * @author Pyrite[1up]
 */
public class ServerInfoPanel extends JPanel implements MouseListener {

    private MainWindow parent;

    private JLabel server;
    private JLabel ip;
    private JLabel port;
    private JLabel gametype;
    private JLabel map;
    private JLabel joinServerLabel;

    public ServerInfoPanel(MainWindow owner) {
        super();
        this.parent = owner;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Server Info"));

        JPanel infoPanel = new JPanel(new SpringLayout());

        JLabel lblServerName = new JLabel("Server Name: ", JLabel.TRAILING);
        lblServerName.setFont(new Font("Tahoma", Font.BOLD, 11));
        JLabel lblServerIP = new JLabel("Server IP: ", JLabel.TRAILING);
        lblServerIP.setFont(new Font("Tahoma", Font.BOLD, 11));
        JLabel lblServerPort = new JLabel("Server Port: ", JLabel.TRAILING);
        lblServerPort.setFont(new Font("Tahoma", Font.BOLD, 11));
        JLabel lblGameType = new JLabel("Game Type: ", JLabel.TRAILING);
        lblGameType.setFont(new Font("Tahoma", Font.BOLD, 11));
        JLabel lblMap = new JLabel("Map: ", JLabel.TRAILING);
        lblMap.setFont(new Font("Tahoma", Font.BOLD, 11));

        // Not sure where/how to place this atm.
        JPanel joinPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        joinServerLabel = new JLabel("<html><u>Join Server</u></html>");
        joinServerLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        joinServerLabel.setForeground(GradientPanel.HEADER_COLOR_END);
        joinServerLabel.addMouseListener(this);
        joinPanel.add(joinServerLabel);

        server   = new JLabel("N/A");
        server.setFont(new Font("Tahoma", Font.PLAIN, 11));
        ip       = new JLabel("N/A");
        ip.setFont(new Font("Tahoma", Font.PLAIN, 11));
        port     = new JLabel("N/A");
        port.setFont(new Font("Tahoma", Font.PLAIN, 11));
        gametype = new JLabel("N/A");
        gametype.setFont(new Font("Tahoma", Font.PLAIN, 11));
        gametype.addMouseListener(this);
        map      = new JLabel("N/A");
        map.setFont(new Font("Tahoma", Font.PLAIN, 11));

        infoPanel.add(lblServerName);
        lblServerName.setLabelFor(server);
        infoPanel.add(server);
        infoPanel.add(lblServerIP);
        lblServerIP.setLabelFor(ip);
        infoPanel.add(ip);
        infoPanel.add(lblServerPort);
        lblServerPort.setLabelFor(port);
        infoPanel.add(port);
        infoPanel.add(lblGameType);
        lblGameType.setLabelFor(gametype);
        infoPanel.add(gametype);
        infoPanel.add(lblMap);
        lblMap.setLabelFor(map);
        infoPanel.add(map);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(infoPanel,
            5, 2, //rows, cols
            10, 6, //initX, initY
            10, 6  //xPad, yPad
        );

        this.add(infoPanel, BorderLayout.CENTER);
        this.add(joinPanel, BorderLayout.SOUTH);


    }

    public void setServerName(String name) {
        this.server.setText(name);
    }

    public void setServerIP(String ip) {
        this.ip.setText(ip);
    }

    public void setServerPort(String port) {
        this.port.setText(port);
    }

    public void setGameType(String type) {
        this.gametype.setText("<html>"+type+" (<font color=\"#DD5731\"><u>Change</u></font>)</html>");
    }

    public void setMap(String map) {
        this.map.setText(map);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.joinServerLabel) {
            PropertyManager pm = new PropertyManager();
            String pathToUrbanTerror = pm.getGamePath();
            if (pathToUrbanTerror.isEmpty()) {
                new SettingManager(this.parent);
            }
            else {
                try {
                    GameLauncher.Launch(pathToUrbanTerror, this.ip.getText());
                }
                catch (Exception event) {
                    JOptionPane.showMessageDialog(this.parent, "<html>Error: Failed to launch Urban Terror.\nReport bugs at http://1upModRcon.googlecode.com");
                }
            }
        }
        else if (e.getSource() == this.gametype) {
            String[] values = {"Free For All", "Team Deathmatch", "Team Survivor", "Follow the Leader", "Capture and Hold", "Capture the Flag", "Bomb"};
            String choice = (String)JOptionPane.showInputDialog(
                    this.parent,
                    "",
                    "Change the Server's Game Type",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    values,
                    values[0]
            );
            try {
                Server s = (Server)this.parent.comboServerList.getSelectedItem();
                BowserQuery q = new BowserQuery(s);
                int gt = ModRconUtil.getGameType(choice);
                q.sendCmd("g_gametype "+gt);
                this.parent.consolePanel.appendCommand("g_gametype "+gt);
                this.parent.consolePanel.appendToConsole(q.getResponse());
            }
            catch (Exception exc) {
                System.out.println(exc.getMessage());
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
