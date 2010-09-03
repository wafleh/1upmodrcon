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
        version = new JLabel("Version: September 3, 2010");
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(version);
    }

}
