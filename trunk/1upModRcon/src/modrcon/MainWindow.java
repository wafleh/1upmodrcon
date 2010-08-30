/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modrcon;

import javax.swing.*;
import java.awt.Color;
import java.util.*;
import java.awt.datatransfer.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.event.*;

/**
 *
 * @author Pyrite
 */
public class MainWindow extends JFrame {
    
    private GradientPanel logoPanel;
    private JPanel contentPanel;
    private VersionPanel versionPanel;

    public ConsolePanel consolePanel;
    public ControlPanel controlPanel;
    
    private JTextArea consoleTextArea;
    private JComboBox comboServerList;
    
    private JLabel sinfoServerName;
    private JLabel sinfoServerIP;
    private JLabel sinfoServerPort;
    private JLabel sinfoGameType;
    private JLabel sinfoMap;

    /** Creates new form NewJFrame */
    public MainWindow() {
        // Sets Some of the JFrame Options
        this.setTitle("1up ModRcon - Main (Moderator Mode)");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700,500);
        this.setResizable(true);

        // Setup the Content Pane
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        JMenuBar menubar = new JMenuBar();
        

        this.logoPanel = new GradientPanel();
        this.contentPanel = new JPanel();
        this.versionPanel = new VersionPanel();
        this.consolePanel = new ConsolePanel();
        this.controlPanel = new ControlPanel(2);

        this.contentPanel.setLayout(new BorderLayout());

        JPanel left = new JPanel();
        left.setLayout(new BorderLayout());
        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());

        left.add(new ServerInfoPanel(), BorderLayout.NORTH);
        left.add(new LivePlayerInfoPanel(), BorderLayout.SOUTH);
        right.add(this.consolePanel, BorderLayout.CENTER);
        right.add(this.controlPanel, BorderLayout.SOUTH);


        this.contentPanel.add(left, BorderLayout.WEST);
        this.contentPanel.add(right, BorderLayout.EAST);

        cp.add(logoPanel, BorderLayout.NORTH);
        cp.add(contentPanel, BorderLayout.CENTER);
        cp.add(versionPanel, BorderLayout.SOUTH);


        /*
         * Add MenuBar
         * Add LogoPanel NORTH
         * Add ContentPanel CENTER
         * Add VersionPanel South
         */

        //initComponents();
        //refreshServerCombo();

        // Populate Live Server Info

        // Make the Widgets Snug
        this.pack();

        // Center the Window (Whatever Its Size) on the Screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)((screenSize.getWidth()/2)-this.getWidth()/2), (int)((screenSize.getHeight()/2)-this.getHeight()/2) );

        // Bring the Window into Focus
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    public void setConsoleBackground(Color color) {
        consoleTextArea.setBackground(color);
    }

    public void setConsoleForeground(Color color) {
        consoleTextArea.setForeground(color);
    }

    public void refreshServerCombo() {
        // Load Server List
        ServerParser sp = new ServerParser();
        java.util.List servers = sp.getServers();
        for (int i=0; i < servers.size(); i++) {
            this.comboServerList.addItem(servers.get(i));
        }
    }

    public void refreshServerInfo() {
        Server server = (Server)this.comboServerList.getSelectedItem();
        try {
            BowserQuery q = new BowserQuery(server.getIP(), server.getPortAsInteger());
            q.setPassword(server.getModPass());
            Map map = q.getServerInfo();
            this.sinfoServerName.setText((String)map.get("hostname"));
            this.sinfoServerIP.setText(server.getIP());
            this.sinfoServerPort.setText(server.getPortAsString());
            this.sinfoGameType.setText(ServerInfo.getGameTypeString(Integer.parseInt((String)map.get("gametype"))));
            this.sinfoMap.setText((String)map.get("mapname"));
            /*
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
               String key = iterator.next().toString();
               String value = map.get(key).toString();
               System.out.println(key + " " + value);
            }
            */
        }
        catch (Exception e) {
            System.out.println("Exception Caught: "+e.getMessage());
            e.printStackTrace();
        }
    }



}
