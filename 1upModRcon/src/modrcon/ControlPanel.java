package modrcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The Control Panel portion of the main 1up ModRcon window.
 *
 * @author Pyrite[1up]
 */
public class ControlPanel extends JPanel implements ActionListener, KeyListener {

    /** A reference to the Main Window */
    private MainWindow parent;

    public static int TYPE_RCON = 3;
    public static int TYPE_MOD = 2;
    public static int TYPE_REF = 1;

    private JLabel labelType;
    private JComboBox comboCommandBox;
    
    private JButton btnSend = new JButton("Send");
    private JButton btnStatus = new JButton("Status");
    private JButton btnDumpUser = new JButton("Dumpuser");
    private JButton btnSlap = new JButton("Slap");
    private JButton btnKick = new JButton("Kick");
    private JButton btnMute = new JButton("Mute");
    private JButton btnToggleMute = new JButton("ToggleMute");
    private JButton btnForceTeam = new JButton("ForceTeam");
    private JButton btnBanUser = new JButton("Ban User");
    
    
    /** The type of currently logged in user (Rcon, Mod, Ref). */
    private int type;

    public ControlPanel(MainWindow owner, int type) {
        super();
        this.parent = owner;

        this.type = type;
        this.labelType = new JLabel(" /"+this.getType().toLowerCase()+" ");
        this.labelType.setFont(new Font("Arial", Font.BOLD, 11));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder(this.getType()+" Control Panel"));

        this.comboCommandBox = new JComboBox();
        this.comboCommandBox.setEditable(true);
        this.comboCommandBox.getEditor().getEditorComponent().addKeyListener(this);

        btnSend.addActionListener(this);
        btnStatus.addActionListener(this);
        btnDumpUser.addActionListener(this);
        btnSlap.addActionListener(this);
        btnKick.addActionListener(this);
        btnMute.addActionListener(this);
        btnToggleMute.addActionListener(this);
        btnForceTeam.addActionListener(this);
        btnBanUser.addActionListener(this);

        this.setLayout(new BorderLayout());
        this.add(getWestPanel(), BorderLayout.WEST);
        this.add(getCenterPanel(), BorderLayout.CENTER);
        //this.add(getTopPanel());
        //this.add(getBottomPanel());

    }

    private JPanel getWestPanel() {
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.add(Box.createVerticalStrut(4));
        westPanel.add(this.labelType);
        westPanel.add(Box.createGlue());

        return westPanel;
    }

    private JPanel getCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(this.comboCommandBox);
        topPanel.add(Box.createHorizontalStrut(5));
        topPanel.add(this.btnSend);
        topPanel.add(Box.createHorizontalStrut(5));

        JPanel bottomPanel = getBottomPanel();

        centerPanel.add(topPanel);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(bottomPanel);
        centerPanel.add(Box.createGlue());

        return centerPanel;
    }

    private JPanel getBottomPanel() {
        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BoxLayout(returnPanel, BoxLayout.X_AXIS));

        returnPanel.add(this.btnStatus);
        returnPanel.add(Box.createGlue());
        returnPanel.add(this.btnDumpUser);
        returnPanel.add(Box.createGlue());
        returnPanel.add(this.btnSlap);
        returnPanel.add(Box.createGlue());
        returnPanel.add(this.btnKick);
        returnPanel.add(Box.createGlue());
        returnPanel.add(this.btnMute);
        returnPanel.add(Box.createGlue());
        returnPanel.add(this.btnToggleMute);
        returnPanel.add(Box.createGlue());
        returnPanel.add(this.btnForceTeam);
        returnPanel.add(Box.createGlue());
        returnPanel.add(this.btnBanUser);
        return returnPanel;
    }

    private JPanel getTopPanel() {
        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BoxLayout(returnPanel, BoxLayout.X_AXIS));

        returnPanel.add(Box.createHorizontalStrut(8));
        returnPanel.add(this.labelType);
        returnPanel.add(Box.createHorizontalStrut(5));
        returnPanel.add(this.comboCommandBox);
        returnPanel.add(Box.createHorizontalStrut(5));
        returnPanel.add(this.btnSend);
        returnPanel.add(Box.createHorizontalStrut(8));

        return returnPanel;
    }

    public void printSize() {
        System.out.println(this.comboCommandBox.getSize().toString());
        this.comboCommandBox.setPreferredSize(new Dimension(250, 27));
        System.out.println(this.comboCommandBox.getSize().toString());
    }

    public void sendStatusCommand() {
        try {
            Server server = (Server)this.parent.comboServerList.getSelectedItem();
            BowserQuery q = new BowserQuery(server);
            q.sendCmd("status");
            this.parent.consolePanel.appendCommand("status");
            this.parent.consolePanel.appendWithColor(q.getResponse());
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    private String getType() {
        if (this.type == 1) {
            return "Ref";
        }
        else if (this.type == 2) {
            return "Mod";
        }
        else if (this.type == 3) {
            return "Rcon";
        }
        else {
            return "ERROR";
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();

        if (pressedButton == btnSend) {
            if (this.comboCommandBox.getSelectedItem() != null) {
                String cmd = ((String)this.comboCommandBox.getSelectedItem()).trim();
                try {
                    Server server = (Server)this.parent.comboServerList.getSelectedItem();
                    BowserQuery q = new BowserQuery(server);
                    q.sendCmd(cmd);
                    this.parent.consolePanel.appendCommand(cmd);
                    this.parent.consolePanel.appendToConsole(q.getResponse());
                }
                catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }

        else if (pressedButton == btnStatus) {
            this.sendStatusCommand();
        }
        else if (pressedButton == btnDumpUser) {
            String input = JOptionPane.showInputDialog(this.parent, "Enter the player number for the player\nyou want more info about.", "Dump User", JOptionPane.PLAIN_MESSAGE);
            input = (input != null && input.length() > 0) ? input.trim() : "";
            try {
                Server server = (Server)this.parent.comboServerList.getSelectedItem();
                BowserQuery q = new BowserQuery(server);
                q.sendCmd("dumpuser "+input);
                this.parent.consolePanel.appendCommand("dumpuser "+input);
                this.parent.consolePanel.appendToConsole(q.getResponse());
            }
            catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }

        else if (pressedButton == btnSlap) {
            String input = JOptionPane.showInputDialog(this.parent, "Enter the player number for the player\nyou want to slap.", "Slap User", JOptionPane.PLAIN_MESSAGE);
            input = (input != null && input.length() > 0) ? input.trim() : "";
            try {
                Server server = (Server)this.parent.comboServerList.getSelectedItem();
                BowserQuery q = new BowserQuery(server);
                q.sendCmd("slap "+input);
                this.parent.consolePanel.appendCommand("slap "+input);
                this.parent.consolePanel.appendToConsole(q.getResponse());
            }
            catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }

        else if (pressedButton == btnKick) {
            String input = JOptionPane.showInputDialog(this.parent, "Enter the player number for the player\nyou want to kick.", "Kick User", JOptionPane.PLAIN_MESSAGE);
            input = (input != null && input.length() > 0) ? input.trim() : "";
            try {
                Server server = (Server)this.parent.comboServerList.getSelectedItem();
                BowserQuery q = new BowserQuery(server);
                q.sendCmd("kick "+input);
                this.parent.consolePanel.appendCommand("kick "+input);
                this.parent.consolePanel.appendToConsole(q.getResponse());
            }
            catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }

        else if (pressedButton == btnMute) {
            String input = JOptionPane.showInputDialog(this.parent, "Enter the player number for the player\nyou want to mute.", "Mute User", JOptionPane.PLAIN_MESSAGE);
            input = (input != null && input.length() > 0) ? input.trim() : "";
            try {
                Server server = (Server)this.parent.comboServerList.getSelectedItem();
                BowserQuery q = new BowserQuery(server);
                q.sendCmd("mute "+input);
                this.parent.consolePanel.appendCommand("mute "+input);
                this.parent.consolePanel.appendToConsole(q.getResponse());
            }
            catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }

        else if (pressedButton == btnToggleMute) {
            String input = JOptionPane.showInputDialog(this.parent, "Enter the player number for the player\nyou want to togglemute.", "Togglemute User", JOptionPane.PLAIN_MESSAGE);
            input = (input != null && input.length() > 0) ? input.trim() : "";
            try {
                Server server = (Server)this.parent.comboServerList.getSelectedItem();
                BowserQuery q = new BowserQuery(server);
                q.sendCmd("togglemute "+input);
                this.parent.consolePanel.appendCommand("togglemute "+input);
                this.parent.consolePanel.appendToConsole(q.getResponse());
            }
            catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }

        else if (pressedButton == btnForceTeam) {
            new ForceTeamDialog(this.parent);
        }

        else if (pressedButton == btnBanUser) {
            new BanUserDialog(this.parent);
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            actionPerformed(new ActionEvent(btnSend, 1, ""));
        }
    }

}