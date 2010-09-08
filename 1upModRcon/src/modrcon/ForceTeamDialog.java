package modrcon;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Displays a JDialog box with options to force a player to a specific team.
 *
 * @author Pyrite
 */
public class ForceTeamDialog extends JDialog implements ActionListener {

    private MainWindow parent;
    private JTextField playerNum;
    private JComboBox teamCombo;
    private JButton btnOK;
    private JButton btnCancel;

    public ForceTeamDialog(MainWindow owner) {
        super();
        this.parent = owner;
        this.setTitle("Forceteam User");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setModal(true);

        Container cp = this.getContentPane();
        cp.setLayout(new VerticalFlowLayout());

        JPanel ap = new JPanel();
        JLabel instructions = new JLabel("Enter the player number & team you want to force the player to.");
        JLabel lblPlayerNum = new JLabel("Player Number: ");
        playerNum = new JTextField(10);
        JLabel lblTeam = new JLabel("Team: ");
        ap.add(lblPlayerNum);
        ap.add(playerNum);
        ap.add(lblTeam);
        ap.add(getTeamCombo());

        cp.add(instructions);
        cp.add(ap);
        cp.add(getButtonPanel());

        this.pack();
        // Set the location of the About Window centered relative to the MainMenu
        // --CENTER--
        Point aboutBoxLocation = new Point();

        double aboutBoxX = owner.getLocation().getX() + ((owner.getWidth() / 2) - (this.getWidth() / 2));
        double aboutBoxY = owner.getLocation().getY() + ((owner.getHeight() / 2) - (this.getHeight() / 2));

        aboutBoxLocation.setLocation(aboutBoxX, aboutBoxY);
        this.setLocation(aboutBoxLocation);
        // --END CENTER--
        this.setVisible(true);
    }

    public JPanel getButtonPanel() {
        JPanel bp = new JPanel();
        this.btnOK = new JButton("OK");
        this.btnCancel = new JButton("Cancel");
        this.btnOK.addActionListener(this);
        this.btnCancel.addActionListener(this);
        bp.add(this.btnOK);
        bp.add(this.btnCancel);
        return bp;
    }

    public JComboBox getTeamCombo() {
        String[] teamStrings = { "Red", "Blue", "Spectator" };
        this.teamCombo = new JComboBox(teamStrings);
        return this.teamCombo;
    }

    public void actionPerformed(ActionEvent e) {
        AbstractButton pressedButton = (AbstractButton)e.getSource();
        if (pressedButton == btnOK) {
            // forceteam that user
            String player = this.playerNum.getText();
            String team = (String)this.teamCombo.getSelectedItem();
            if (player != null && player.length() > 0) {
                player = player.trim();
                try {
                    Server server = (Server)this.parent.comboServerList.getSelectedItem();
                    BowserQuery q = new BowserQuery(server);
                    q.forceTeam(player, team);
                    this.parent.consolePanel.appendToConsole(q.getResponse());
                }
                catch (Exception event) {
                    System.out.print(event.getMessage());
                }
            }
            this.dispose();
        }
        else if (pressedButton == btnCancel) {
            this.dispose();
        }
    }

}
