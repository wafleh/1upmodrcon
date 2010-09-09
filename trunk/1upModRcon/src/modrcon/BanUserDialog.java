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
        this.setSize(350,170);

        Container cp = this.getContentPane();
        cp.setLayout(new VerticalFlowLayout());

        JPanel line1 = new JPanel(false);
        JLabel intro = new JLabel("Enter the IP address of the player you want to ban.");
        line1.add(intro);

        JPanel line2 = new JPanel(false);
        JLabel ipLabel = new JLabel("IP Address: ");
        ipTextField = new JTextField(20);
        line2.add(ipLabel);
        line2.add(ipTextField);

        JPanel line3 = new JPanel(false);
        rangeCheckBox = new JCheckBox();
        rangeCheckBox.setText("Format IP As Range");
        line3.add(rangeCheckBox);

        JPanel buttonPanel = new JPanel(false);
        btnOK = new JButton("OK");
        btnCancel = new JButton("Cancel");
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);
        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        cp.add(line1);
        cp.add(line2);
        cp.add(line3);
        cp.add(buttonPanel);

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
