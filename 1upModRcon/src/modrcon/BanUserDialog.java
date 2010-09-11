package modrcon;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * A dialog to ban a player from the server.
 *
 * @author Pyrite
 */
public class BanUserDialog extends JDialog implements ActionListener {

    private MainWindow parent;

    private JTextField ipTextField;
    private JCheckBox rangeCheckBox;
    private JButton btnOK;
    private JButton btnCancel;

    public BanUserDialog(MainWindow owner) {
        super();
        this.parent = owner;
        this.setTitle("Add user IP to banlist");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setModal(true);
        //this.setSize(350,170);

        JPanel line1 = new JPanel();
        JLabel intro = new JLabel("Enter the IP address of the player you want to ban.");
        line1.add(intro);

        JPanel line2 = new JPanel();
        JLabel ipLabel = new JLabel("IP Address: ");
        ipTextField = new JTextField(20);
        line2.add(Box.createGlue());
        line2.add(ipLabel);
        line2.add(ipTextField);
        line2.add(Box.createGlue());

        JPanel line3 = new JPanel();
        rangeCheckBox = new JCheckBox();
        rangeCheckBox.setText("Format IP As Range");
        line3.add(rangeCheckBox);

        JPanel buttonPanel = new JPanel();
        btnOK = new JButton("OK");
        btnCancel = new JButton("Cancel");
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);
        buttonPanel.add(Box.createGlue());
        buttonPanel.add(btnOK);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(btnCancel);
        buttonPanel.add(Box.createGlue());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(line1);
        panel.add(line2);
        panel.add(line3);
        panel.add(buttonPanel);

        this.add(panel);
        this.pack();

        // Set the location of the About Window centered relative to the MainMenu
        // --CENTER--
        Point aboutBoxLocation = new Point();

        double aboutBoxX = this.parent.getLocation().getX() + ((this.parent.getWidth() / 2) - (this.getWidth() / 2));
        double aboutBoxY = this.parent.getLocation().getY() + ((this.parent.getHeight() / 2) - (this.getHeight() / 2));

        aboutBoxLocation.setLocation(aboutBoxX, aboutBoxY);
        this.setLocation(aboutBoxLocation);
        // --END CENTER--

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        AbstractButton pressedButton = (AbstractButton)e.getSource();
        if (pressedButton == btnOK) {
            this.dispose();
        }
        else if (pressedButton == btnCancel) {
            this.dispose();
        }
    }

}
