package modrcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * The Control Panel portion of the main 1up ModRcon window.
 *
 * @author Pyrite[1up]
 */
public class ControlPanel extends JPanel implements ActionListener, KeyListener {

    /** A reference to the Main Window */
    private MainWindow parent;

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

    public ControlPanel(MainWindow owner) {
        super();
        this.parent = owner;

        this.labelType = new JLabel("foobar");
        this.labelType.setFont(new Font("Arial", Font.BOLD, 11));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Control Panel"));

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

    private void readAndDisplay() {
        try {
            java.io.InputStream is = this.getClass().getResourceAsStream("/modrcon/resources/ron.txt");
            java.io.InputStreamReader isr = new java.io.InputStreamReader(is);
            java.io.BufferedReader br = new java.io.BufferedReader(isr);

            String output = "";
            String line = br.readLine();
            while (line != null) {
                output += line + "\n";
                line = br.readLine();
            }

            br.close();
            isr.close();
            is.close();
            this.parent.getConsolePanel().appendWithColor(output);
        } catch (Exception exc) { }
    }

    public void printSize() {
        System.out.println(this.comboCommandBox.getSize().toString());
        this.comboCommandBox.setPreferredSize(new Dimension(250, 27));
        System.out.println(this.comboCommandBox.getSize().toString());
    }

    public void sendStatusCommand() {
        try {
            Server server = this.parent.getCurrentServer();
            BowserQuery q = new BowserQuery(server);
            q.sendCmd("status");
            this.parent.getConsolePanel().appendCommand("status");
            this.parent.getConsolePanel().appendWithColor(q.getResponse());
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void setLoginMethod(String s) {
        String titleCase = s.substring(0,1).toUpperCase()+s.substring(1).toLowerCase();
        this.setBorder(BorderFactory.createTitledBorder(titleCase+" Control Panel"));
        this.labelType.setText(" /"+s.toLowerCase()+" ");
    }

    public void refreshCommandCombo() {
        this.comboCommandBox.setModel(new DefaultComboBoxModel());
        Q3CommandDatabase db = new Q3CommandDatabase();
        ArrayList commands = db.getCommandList();
        for (Object o : commands) {
            Q3Command c = (Q3Command)o;
            this.comboCommandBox.addItem(c.getCommand());
        }
        this.comboCommandBox.setSelectedItem("");
    }

    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();

        if (pressedButton == btnSend) {
            if (this.comboCommandBox.getSelectedItem() != null) {
                String cmd = ((String)this.comboCommandBox.getSelectedItem()).trim();
                if (cmd.equals("pr0n")) {
                    this.parent.getConsolePanel().clearConsole();
                    this.comboCommandBox.setSelectedItem(null);
                    this.readAndDisplay();
                    SoundEffect.DUKE_BABE.play();
                }
                else {
                    try {
                        Server server = this.parent.getCurrentServer();
                        BowserQuery q = new BowserQuery(server);
                        q.sendCmd(cmd);
                        this.parent.getConsolePanel().appendCommand(cmd);
                        this.parent.getConsolePanel().appendToConsole(q.getResponse());
                        this.comboCommandBox.setSelectedItem(null);
                    }
                    catch (Exception e) {
                        System.out.print(e.getMessage());
                    }
                }
            }
        }

        else if (pressedButton == btnStatus) {
            this.sendStatusCommand();
        }
        else if (pressedButton == btnDumpUser) {
            String input = JOptionPane.showInputDialog(this.parent, "Enter the player number for the player\nyou want more info about.", "Dump User", JOptionPane.PLAIN_MESSAGE);
            
            if (input != null) {
                input = (input.length() > 0) ? input.trim() : "";
                try {
                    Server server = this.parent.getCurrentServer();
                    BowserQuery q = new BowserQuery(server);
                    q.sendCmd("dumpuser "+input);
                    this.parent.getConsolePanel().appendCommand("dumpuser "+input);
                    this.parent.getConsolePanel().appendToConsole(q.getResponse());
                }
                catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }

        else if (pressedButton == btnSlap) {
            String input = JOptionPane.showInputDialog(this.parent, "Enter the player number for the player\nyou want to slap.", "Slap User", JOptionPane.PLAIN_MESSAGE);
            if (input != null) {
                input = (input.length() > 0) ? input.trim() : "";
                try {
                    Server server = this.parent.getCurrentServer();
                    BowserQuery q = new BowserQuery(server);
                    q.sendCmd("slap "+input);
                    this.parent.getConsolePanel().appendCommand("slap "+input);
                    this.parent.getConsolePanel().appendToConsole(q.getResponse());
                }
                catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }

        else if (pressedButton == btnKick) {
            String input = JOptionPane.showInputDialog(this.parent, "Enter the player number for the player\nyou want to kick.", "Kick User", JOptionPane.PLAIN_MESSAGE);
            if (input != null) {
                input = (input.length() > 0) ? input.trim() : "";
                try {
                    Server server = this.parent.getCurrentServer();
                    BowserQuery q = new BowserQuery(server);
                    q.sendCmd("kick "+input);
                    this.parent.getConsolePanel().appendCommand("kick "+input);
                    this.parent.getConsolePanel().appendToConsole(q.getResponse());
                }
                catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }

        else if (pressedButton == btnMute) {
            String input = JOptionPane.showInputDialog(this.parent, "Enter the player number for the player\nyou want to mute.", "Mute User", JOptionPane.PLAIN_MESSAGE);
            if (input != null) {
                input = (input.length() > 0) ? input.trim() : "";
                try {
                    Server server = this.parent.getCurrentServer();
                    BowserQuery q = new BowserQuery(server);
                    q.sendCmd("mute "+input);
                    this.parent.getConsolePanel().appendCommand("mute "+input);
                    this.parent.getConsolePanel().appendToConsole(q.getResponse());
                }
                catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }

        else if (pressedButton == btnToggleMute) {
            String input = JOptionPane.showInputDialog(this.parent, "Enter the player number for the player\nyou want to togglemute.", "Togglemute User", JOptionPane.PLAIN_MESSAGE);
            if (input != null) {
                input = (input.length() > 0) ? input.trim() : "";
                try {
                    Server server = this.parent.getCurrentServer();
                    BowserQuery q = new BowserQuery(server);
                    q.sendCmd("togglemute "+input);
                    this.parent.getConsolePanel().appendCommand("togglemute "+input);
                    this.parent.getConsolePanel().appendToConsole(q.getResponse());
                }
                catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }

        else if (pressedButton == btnForceTeam) {
            new ForceTeamDialog(this.parent);
        }

        else if (pressedButton == btnBanUser) {
            new BanUserDialog(this.parent);
        }

    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            actionPerformed(new ActionEvent(btnSend, 1, ""));
        }
    }

}
