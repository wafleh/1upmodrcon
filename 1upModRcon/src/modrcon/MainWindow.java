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
 * The main application window.
 *
 * @author Pyrite[1up]
 */
public class MainWindow extends JFrame implements ItemListener {

    private String frameTitle;
    
    public LogoPanel logoPanel;
    private JPanel contentPanel;
    private VersionPanel versionPanel;

    public ServerInfoPanel serverInfoPanel;
    public ConsolePanel consolePanel;
    public ControlPanel controlPanel;
    public LivePlayerInfoPanel livePlayerInfoPanel;
    
    public JComboBox comboServerList;
    private JButton btnConnect;
    
    private JLabel sinfoServerName;
    private JLabel sinfoServerIP;
    private JLabel sinfoServerPort;
    private JLabel sinfoGameType;
    private JLabel sinfoMap;

    /** Creates new form NewJFrame */
    public MainWindow() {
        // Sets Some of the JFrame Options
        this.frameTitle = "1up ModRcon - Main (Moderator Mode)";
        this.setTitle(this.frameTitle);
        ImageIcon topLeftIcon = new ImageIcon(getClass().getResource("/modrcon/resources/1up8bit_green.png"));
        this.setIconImage(topLeftIcon.getImage());
        this.setJMenuBar(this.getModRconMenuBar());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setSize(700,500);
        this.setResizable(true);

        // Setup the Content Pane
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        this.logoPanel = new LogoPanel(LogoPanel.LOGO_LEFT);
        this.contentPanel = new JPanel();
        this.versionPanel = new VersionPanel();
        this.consolePanel = new ConsolePanel(this);
        this.controlPanel = new ControlPanel(this, 2);
        this.serverInfoPanel = new ServerInfoPanel();
        this.livePlayerInfoPanel = new LivePlayerInfoPanel(this);

        this.comboServerList = new JComboBox();
        this.comboServerList.addItemListener(this);
        this.btnConnect = new JButton("Connect");
        this.logoPanel.add(this.comboServerList);
        
        // so the question is what layout to make the contentPanel.
        //this.contentPanel.setLayout(new BoxLayout(this.contentPanel, BoxLayout.LINE_AXIS));
        this.contentPanel.setLayout(new BorderLayout());

        JPanel left = new JPanel();
        left.setLayout(new BorderLayout());
        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());

        left.add(this.serverInfoPanel, BorderLayout.NORTH);
        left.add(this.livePlayerInfoPanel, BorderLayout.SOUTH);
        right.add(this.consolePanel, BorderLayout.CENTER);
        right.add(this.controlPanel, BorderLayout.SOUTH);


        this.contentPanel.add(left, BorderLayout.WEST);
        this.contentPanel.add(right, BorderLayout.CENTER);

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
        refreshServerCombo();
        refreshServerInfo();
        this.livePlayerInfoPanel.fireItUp();

        // Populate Live Server Info

        // Make the Widgets Snug
        this.pack();

        // Center the Window (Whatever Its Size) on the Screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)((screenSize.getWidth()/2)-this.getWidth()/2), (int)((screenSize.getHeight()/2)-this.getHeight()/2) );

        // Bring the Window into Focus
        this.setVisible(true);
    }

    private JMenuBar getModRconMenuBar() {
        JMenuBar jmb = new JMenuBar();

        JMenu fileMenu  = new JMenu("File");
        fileMenu.setMnemonic('F');
        JMenu editMenu  = new JMenu("Edit");
        editMenu.setMnemonic('E');
        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.setMnemonic('T');
        JMenu helpMenu  = new JMenu("Help");
        helpMenu.setMnemonic('H');

        // Define Menu Items
        
        JMenuItem jmiSave = new JMenuItem(new MenuAction("Save Console As...", this));
        jmiSave.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/save.png")));
        jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiExit = new JMenuItem(new MenuAction("Exit", this));
        jmiExit.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/del.png")));

        JMenuItem jmiCopy = new JMenuItem(new MenuAction("Copy", this));
        jmiCopy.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/copy.png")));
        jmiCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiClear = new JMenuItem(new MenuAction("Clear", this));
        jmiClear.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/files_remove.png")));
        jmiClear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

        JMenuItem jmiFind = new JMenuItem(new MenuAction("Find", this));
        jmiFind.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/find.png")));
        jmiFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiSelectAll = new JMenuItem(new MenuAction("Select All", this));
        jmiSelectAll.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/files_add.png")));
        jmiSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiManageServers = new JMenuItem(new MenuAction("Manage Servers", this));
        jmiManageServers.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/objects.png")));
        jmiManageServers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiManageCommands = new JMenuItem(new MenuAction("Manage Commands", this));
        jmiManageCommands.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/objects.png")));
        jmiManageCommands.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiVoteCalc = new JMenuItem(new MenuAction("Vote Calculator", this));
        jmiVoteCalc.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/calculator.png")));
        jmiVoteCalc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiGearCalc = new JMenuItem(new MenuAction("Gear Calculator", this));
        jmiGearCalc.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/calculator.png")));
        jmiGearCalc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiSettings = new JMenuItem(new MenuAction("Settings", this));
        jmiSettings.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/app_options.png")));
        jmiSettings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiHelp = new JMenuItem(new MenuAction("1up ModRcon Help", this));
        jmiHelp.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/help.png")));
        jmiHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

        JMenuItem jmiUpdate = new JMenuItem(new MenuAction("Check for update", this));
        jmiUpdate.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/web.png")));
        jmiUpdate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiAbout = new JMenuItem(new MenuAction("About", this));
        jmiAbout.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/about.png")));

        // Add Menu Items to Menus

        fileMenu.add(jmiSave);
        fileMenu.addSeparator();
        fileMenu.add(jmiExit);
        editMenu.add(jmiCopy);
        editMenu.add(jmiClear);
        editMenu.add(jmiFind);
        editMenu.addSeparator();
        editMenu.add(jmiSelectAll);
        toolsMenu.add(jmiManageServers);
        toolsMenu.add(jmiManageCommands);
        toolsMenu.addSeparator();
        toolsMenu.add(jmiVoteCalc);
        toolsMenu.add(jmiGearCalc);
        toolsMenu.addSeparator();
        toolsMenu.add(jmiSettings);
        helpMenu.add(jmiHelp);
        helpMenu.add(jmiUpdate);
        helpMenu.addSeparator();
        helpMenu.add(jmiAbout);

        // Add Menus to MenuBar

        jmb.add(fileMenu);
        jmb.add(editMenu);
        jmb.add(toolsMenu);
        jmb.add(helpMenu);
        
        return jmb;
    }

    public void setConsoleBackground(Color color) {
        this.consolePanel.setConsoleBackground(color);
    }

    public void setConsoleForeground(Color color) {
        this.consolePanel.setConsoleForeground(color);
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
            q.setPassword(server.getPassword()); // may not be needed.
            Map map = q.getServerInfo();
            this.serverInfoPanel.setServerName((String)map.get("hostname"));
            this.serverInfoPanel.setServerIP(server.getIP());
            this.serverInfoPanel.setServerPort(server.getPortAsString());
            this.serverInfoPanel.setGameType(ServerInfo.getGameTypeString(Integer.parseInt((String)map.get("gametype"))));
            this.serverInfoPanel.setMap((String)map.get("mapname"));
        }
        catch (Exception e) {
            System.out.println("Exception Caught: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
        if (source == this.comboServerList) {
            this.livePlayerInfoPanel.fireItUp();
            this.refreshServerInfo();
        }
    }



}
