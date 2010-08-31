package modrcon;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

/**
 * The Control Panel portion of the main 1up ModRcon window.
 *
 * @author Pyrite[1up]
 */
public class ControlPanel extends JPanel implements ActionListener {

    /** A reference to the Main Window */
    private MainWindow parent;

    public static int TYPE_RCON = 3;
    public static int TYPE_MOD = 2;
    public static int TYPE_REF = 1;

    private JPanel top = new JPanel();
    private JPanel bottom = new JPanel();

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

        this.top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));      

        btnSend.addActionListener(this);
        btnStatus.addActionListener(this);
        btnDumpUser.addActionListener(this);

        this.top.add(labelType);
        this.top.add(comboCommandBox);
        this.top.add(btnSend);
        this.bottom.add(btnStatus);
        this.bottom.add(btnDumpUser);
        this.bottom.add(btnSlap);
        this.bottom.add(btnKick);
        this.bottom.add(btnMute);
        this.bottom.add(btnToggleMute);
        this.bottom.add(btnForceTeam);

        this.add(top);
        this.add(bottom);

    }

    public void printSize() {
        System.out.println(this.comboCommandBox.getSize().toString());
        this.comboCommandBox.setPreferredSize(new Dimension(250, 27));
        System.out.println(this.comboCommandBox.getSize().toString());
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

        if (pressedButton == btnStatus) {
            try {
                Server server = (Server)this.parent.comboServerList.getSelectedItem();
                BowserQuery q = new BowserQuery(server.getIP(), server.getPortAsInteger());
                q.setPassword(server.getPassword());
                q.mod("status");
                this.parent.consolePanel.appendToConsole(q.getResponse());
            }
            catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
        else if (pressedButton == btnDumpUser) {
            String input = JOptionPane.showInputDialog(this.parent, "Enter the player number for the player\nyou want more info about.", "Dump User", JOptionPane.PLAIN_MESSAGE);
            input = (input != null && input.length() > 0) ? input.trim() : "";
            try {
                Server server = (Server)this.parent.comboServerList.getSelectedItem();
                BowserQuery q = new BowserQuery(server.getIP(), server.getPortAsInteger());
                q.setPassword(server.getPassword());
                q.mod("dumpuser "+input);
                this.parent.consolePanel.appendToConsole(q.getResponse());
            }
            catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
        else if (pressedButton == btnSend) {
            String cmd = ((String)this.comboCommandBox.getSelectedItem()).trim();
            try {
                Server server = (Server)this.parent.comboServerList.getSelectedItem();
                BowserQuery q = new BowserQuery(server.getIP(), 27960);
                q.setPassword(server.getPassword());
                q.mod(cmd);
                this.parent.consolePanel.appendToConsole(q.getResponse());
            }
            catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
        else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

}
