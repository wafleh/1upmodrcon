package modrcon;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Displays an About window for 1up ModRcon.
 *
 * @author Pyrite
 */
public class AboutWindow extends JDialog implements ActionListener {

    private MainWindow parent;

    private JTabbedPane tabbedPane;

    public AboutWindow(MainWindow owner) {
        super();
        this.parent = owner;
        this.setTitle("1up ModRcon - About");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setSize(385, 310);
        this.setResizable(false);
        ImageIcon topLeftIcon = new ImageIcon(getClass().getResource("/modrcon/resources/1up8bit_green.png"));
        this.setIconImage(topLeftIcon.getImage());

        // Setup the Content Pane
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());  

        tabbedPane = new JTabbedPane();
        JComponent panel1 = getAboutTab();
        tabbedPane.addTab("About", panel1);
        JComponent panel2 = getThirdPartiesTab();
        tabbedPane.addTab("Third Parties", panel2);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(this);
        buttonPanel.add(btnClose);

        cp.add(new LogoPanel(1), BorderLayout.NORTH);
        cp.add(tabbedPane, BorderLayout.CENTER);
        cp.add(buttonPanel, BorderLayout.SOUTH);

        //this.pack();

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

    protected JComponent getThirdPartiesTab() {
        JPanel panel = new JPanel(false);
        panel.setLayout(new VerticalFlowLayout());
        JLabel text1 = new JLabel("<html>Portions of this software use components<br>developed by the following 3rd Parties.</html>");
        JLabel text2 = new JLabel("Q3Tool by Philip Edelbrock");
        JLabel text3 = new JLabel("VerticalFlowLayout by Vassili Dzuba");
        JLabel text4 = new JLabel("BrowserLauncher by Eric Albert");
        panel.add(text1);
        panel.add(text2);
        panel.add(text3);
        panel.add(text4);
        return panel;
    }

    protected JComponent getAboutTab() {
        JPanel panel = new JPanel(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel appLabel = new JLabel("<html><b>Application Name:</b>  1up ModRcon</html>");
        JLabel verLabel = new JLabel("<html><b>Version:</b>  Beta 0.7</html>");
        JLabel webLabel = new JLabel("<html><b>Website:</b>  http://1upclan.info/</html>");
        JLabel autLabel = new JLabel("<html><b>Authors:</b>  Tesla[1up], Pyrite[1up], Izuriel[1up]</html>");
        JLabel betLabel = new JLabel("<html><b>Beta Testers:</b>  RonaldLee[1up], Dougy, Tits_McGee[1up]</html>");

        JPanel copy1 = new JPanel();
        copy1.setLayout(new BoxLayout(copy1, BoxLayout.X_AXIS));
        copy1.add(new JLabel("The 1up Mushroom is Copyright \u00A9 2010 Nintendo Corp of America."));
        JPanel copy2 = new JPanel();
        copy2.setLayout(new BoxLayout(copy2, BoxLayout.X_AXIS));
        copy2.add(new JLabel("1up ModRcon is Copyright \u00A9 2010 Tesla[1up]. All Rights Reserved."));

        panel.add(Box.createHorizontalStrut(8));
        panel.add(appLabel);
        panel.add(Box.createHorizontalStrut(8));
        panel.add(verLabel);
        panel.add(Box.createHorizontalStrut(8));
        panel.add(webLabel);
        panel.add(Box.createHorizontalStrut(8));
        panel.add(autLabel);
        panel.add(Box.createHorizontalStrut(8));
        panel.add(betLabel);
        
        //panel.add(copy1);
        //panel.add(copy2);
        

        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }


}
