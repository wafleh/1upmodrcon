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
        PropertyManager pm = new PropertyManager();
        version = new JLabel("Version: "+pm.getVersion());
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(version);
    }

}
