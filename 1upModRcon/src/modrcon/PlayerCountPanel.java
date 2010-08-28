package modrcon;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Pyrite
 */
public class PlayerCountPanel extends JPanel {

    private JLabel players;
    private JLabel numPlayers;
    private JLabel slash;
    private JLabel maxPlayers;
    private JProgressBar progressbar;

    public PlayerCountPanel() {
        super();

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(BorderFactory.createTitledBorder(""));

        this.players = new JLabel("Players: ");
        this.players.setFont(new Font("Tahoma", Font.BOLD, 11));
        this.slash = new JLabel(" / ");
        this.progressbar = new JProgressBar();
        this.numPlayers = new JLabel("28");
        this.maxPlayers = new JLabel("32");
        this.progressbar.setMinimum(0);
        this.progressbar.setMaximum(32);
        this.progressbar.setValue(28);
        this.progressbar.setBackground(Color.WHITE);
        this.progressbar.setForeground(Color.RED);

        this.add(players);
        this.add(numPlayers);
        this.add(slash);
        this.add(maxPlayers);
        this.add(progressbar);
    }

    public void setMaxPlayers(String max) {
        this.maxPlayers.setText(max);
    }

    public void setNumPlayers(String num) {
        this.numPlayers.setText(num);
    }
    
}
