package modrcon;

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
    private String lastIP;

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
        rangeCheckBox.addActionListener(this);
        //rangeCheckBox.setEnabled(false); // Disabled until they start typing.
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

    private void banPlayer(String ip) {
        try {
            Server s = (Server)this.parent.comboServerList.getSelectedItem();
            BowserQuery q = new BowserQuery(s);
            q.sendCmd("addip "+ip);
            this.parent.getConsolePanel().appendCommand("addip "+ip);
            this.parent.getConsolePanel().appendToConsole(q.getResponse());
            this.parent.getConsolePanel().appendToConsole("Writing: banlist.txt");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        AbstractButton pressedButton = (AbstractButton)e.getSource();
        if (pressedButton == btnOK) {
            String input = this.ipTextField.getText();
            if (input != null && input.length() > 0 && (ModRconUtil.isIPAddress(input) || ModRconUtil.isIPRange(input))) {
                // Proceed with Ban
                this.banPlayer(input);
                this.dispose();
            }
            else {
                JOptionPane.showMessageDialog(this, "You must supply a valid IP address or range.");
            }
        }
        else if (pressedButton == rangeCheckBox) {
            String ip = this.ipTextField.getText();
            if (rangeCheckBox.isSelected()) {
                if (ModRconUtil.isIPAddress(ip)) {
                    this.lastIP = ip;
                    String start = ip.substring(0, ip.lastIndexOf("."));
                    this.ipTextField.setText(start+".0:-1");
                } else {
                    JOptionPane.showMessageDialog(this, "The IP you entered is not valid.");
                    this.rangeCheckBox.setSelected(false);
                }
            }
            else {
                this.ipTextField.setText(this.lastIP);
            }
        }
        else if (pressedButton == btnCancel) {
            this.dispose();
        }
    }

}
