package modrcon;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Pyrite
 */
public class VersionPanel extends JPanel {

    private JLabel version;

    public VersionPanel() {
        super();
        version = new JLabel("Version: 1.5.1.0");
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(version);
    }

}
