/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modrcon;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * The Control Panel portion of the main 1up ModRcon window.
 *
 * @author Pyrite[1up]
 */
public class ControlPanel extends JPanel {
    
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

    public ControlPanel(int type) {
        super();

        this.type = type;
        this.labelType = new JLabel("/"+this.getType().toLowerCase());
        this.labelType.setFont(new Font("Arial", Font.BOLD, 11));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder(this.getType()+" Control Panel"));

        this.comboCommandBox = new JComboBox();
        this.comboCommandBox.setEditable(true);

        this.top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));      
        

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

}
