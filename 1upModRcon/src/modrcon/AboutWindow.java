/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modrcon;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Pyrite
 */
public class AboutWindow extends JFrame {

    public AboutWindow() {
        super();
        this.setTitle("1up ModRcon - About");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Setup the Content Pane
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(new LogoPanel(1), BorderLayout.NORTH);

        this.pack();
        this.setVisible(true);
    }


}
