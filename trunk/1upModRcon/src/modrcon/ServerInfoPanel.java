package modrcon;

import javax.swing.*;
import java.awt.*;

/**
 * The Server Info Panel.
 *
 * @author Pyrite[1up]
 */
public class ServerInfoPanel extends JPanel {

    private JLabel server;
    private JLabel ip;
    private JLabel port;
    private JLabel gametype;
    private JLabel map;

    public ServerInfoPanel() {
        super();
        this.setLayout(new SpringLayout());
        this.setBorder(BorderFactory.createTitledBorder("Server Info"));

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
        JLabel lblJoin = new JLabel("<html><u>Join Server</u></html>");
        lblJoin.setFont(new Font("Tahoma", Font.BOLD, 11));

        server   = new JLabel("N/A");
        ip       = new JLabel("N/A");
        port     = new JLabel("N/A");
        gametype = new JLabel("N/A");
        map      = new JLabel("N/A");

        this.add(lblServerName);
        lblServerName.setLabelFor(server);
        this.add(server);
        this.add(lblServerIP);
        lblServerIP.setLabelFor(ip);
        this.add(ip);
        this.add(lblServerPort);
        lblServerPort.setLabelFor(port);
        this.add(port);
        this.add(lblGameType);
        lblGameType.setLabelFor(gametype);
        this.add(gametype);
        this.add(lblMap);
        lblMap.setLabelFor(map);
        this.add(map);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(this,
            5, 2, //rows, cols
            10, 6, //initX, initY
            10, 6  //xPad, yPad
        );


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
        this.gametype.setText(type);
    }

    public void setMap(String map) {
        this.map.setText(map);
    }
}
