package modrcon;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

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
        this.setResizable(false);
        // Next 2 lines don't work in Java 1.5, can't setIconImage in JDialogs.
        //ImageIcon topLeftIcon = new ImageIcon(getClass().getResource("/modrcon/resources/1up8bit_green.png"));
        //this.setIconImage(topLeftIcon.getImage());

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

    protected JComponent getThirdPartiesTab() {
        JLabel text1 = new JLabel("<html>Portions of this software use code developed<br>by the following 3rd Parties.</html>", JLabel.CENTER);
        Font f = text1.getFont();
        f = f.deriveFont(Font.BOLD);
        text1.setFont(f);
        JLabel text2 = new JLabel("Q3Tool by Philip Edelbrock");
        Dimension d2 = text2.getPreferredSize();
        JLabel text3 = new JLabel("VerticalFlowLayout by Vassili Dzuba");
        Dimension d3 = text3.getPreferredSize();
        JLabel text4 = new JLabel("BrowserLauncher by Eric Albert");
        Dimension d4 = text4.getPreferredSize();
        
        JPanel t1Panel = new JPanel();
        t1Panel.setLayout(new BoxLayout(t1Panel, BoxLayout.X_AXIS));
        t1Panel.add(text1);
        
        JPanel t2Panel = new JPanel();
        t2Panel.setMaximumSize(d2);
        t2Panel.add(text2);
        
        JPanel t3Panel = new JPanel();
        t3Panel.setMaximumSize(d3);
        t3Panel.add(text3);
        
        JPanel t4Panel = new JPanel();
        t4Panel.setMaximumSize(d4);
        t4Panel.add(text4);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createGlue());
        panel.add(ModRconUtil.getPaddedPanel(0, 25, t1Panel));
        panel.add(t2Panel);
        panel.add(t3Panel);
        panel.add(t4Panel);
        panel.add(Box.createGlue());

        return panel;
    }

    protected JComponent getAboutTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel appPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        appPanel.add(Box.createHorizontalStrut(10));
        appPanel.add(new JLabel("<html><b>Application Name:</b></html>"));
        appPanel.add(Box.createHorizontalStrut(8));
        appPanel.add(new JLabel("1up ModRcon"));
        
        JPanel verPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        verPanel.add(Box.createHorizontalStrut(10));
        verPanel.add(new JLabel("<html><b>Version:</b></html>"));
        verPanel.add(Box.createHorizontalStrut(8));
        verPanel.add(new JLabel(PropertyManager.MODRCON_VERSION));

        JLabel webLabel = new JLabel("<html><u><b>http://1upclan.info/</b></u></html>");
        webLabel.setForeground(LogoPanel.HEADER_COLOR_END);
        webLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        webLabel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                try {
                    BrowserLauncher.openURL("http://1upclan.info/");
                } catch (Exception ex) { }
            }

            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });

        JPanel webPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        webPanel.add(Box.createHorizontalStrut(10));
        webPanel.add(new JLabel("<html><b>Website:</b></html>"));
        webPanel.add(Box.createHorizontalStrut(8));
        webPanel.add(webLabel);

        JPanel autPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        autPanel.add(Box.createHorizontalStrut(10));
        autPanel.add(new JLabel("<html><b>Authors:</b></html>"));
        autPanel.add(Box.createHorizontalStrut(8));
        autPanel.add(new JLabel("Tesla[1up], Pyrite[1up], izuriel"));

        JPanel betPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        betPanel.add(Box.createHorizontalStrut(10));;
        betPanel.add(new JLabel("<html><b>Beta Testers:</b><html>"));
        betPanel.add(Box.createHorizontalStrut(8));
        betPanel.add(new JLabel("RonaldLee[1up], Dougy, Tits_McGee[1up]"));

        Font f = new Font("Tahoma", Font.PLAIN, 10);
        JLabel c1Label = new JLabel("The 1up Mushroom is Copyright \u00A9 2010 Nintendo Corp of America.");
        Dimension c1Dim = c1Label.getPreferredSize();
        c1Label.setFont(f);
        JPanel copy1 = new JPanel();
        copy1.setMaximumSize(c1Dim);
        copy1.add(c1Label);

        JLabel c2Label = new JLabel("1up ModRcon is Copyright \u00A9 2010 Tesla[1up]. All Rights Reserved.");
        Dimension c2Dim = c2Label.getPreferredSize();
        c2Label.setFont(f);
        JPanel copy2 = new JPanel();
        copy2.setMaximumSize(c2Dim);
        copy2.add(c2Label);

        panel.add(Box.createHorizontalStrut(8));
        panel.add(appPanel);
        panel.add(Box.createHorizontalStrut(8));
        panel.add(verPanel);
        panel.add(Box.createHorizontalStrut(8));
        panel.add(webPanel);
        panel.add(Box.createHorizontalStrut(8));
        panel.add(autPanel);
        panel.add(Box.createHorizontalStrut(8));
        panel.add(betPanel);
        panel.add(Box.createHorizontalStrut(8));
        panel.add(copy1);
        panel.add(copy2);

        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.add(panel, BorderLayout.CENTER);

        return borderPanel;
    }

    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }


}
