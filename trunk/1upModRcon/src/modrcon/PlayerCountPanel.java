package modrcon;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Pyrite
 */
public class PlayerCountPanel extends JPanel {

    protected JLabel players;
    protected JLabel numPlayers;
    protected JLabel slash;
    protected JLabel maxPlayers;
    protected JProgressBar progressbar;

    public PlayerCountPanel() {
        super();

        this.setLayout(new FlowLayout());
        this.setBorder(BorderFactory.createTitledBorder(""));
        Dimension dm = new Dimension();
        dm.setSize(278, 30);
        this.setPreferredSize(dm);

        this.players = new JLabel("Players: ");
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
}
