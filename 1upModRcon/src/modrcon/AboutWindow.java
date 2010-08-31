package modrcon;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Pyrite
 */
public class AboutWindow extends JDialog {

    private MainWindow parent;

    public AboutWindow(MainWindow owner) {
        super();
        this.parent = owner;
        this.setTitle("1up ModRcon - About");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Setup the Content Pane
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(new LogoPanel(1), BorderLayout.NORTH);

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


}
