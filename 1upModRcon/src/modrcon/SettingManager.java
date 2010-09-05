package modrcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Pyrite
 */
public class SettingManager extends JDialog implements ActionListener {

    private MainWindow parent;

    private JTextField textGamePath;
    private JSpinner timeoutSpinner;
    private JCheckBox sendStatusCheck;

    private JPanel bgColorPanel;
    private JPanel fgColorPanel;



    private JButton btnSave;
    private JButton btnClose;

    public SettingManager(MainWindow owner) {
        super();
        this.parent = owner;
        this.setTitle("1up ModRcon - Settings");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setModal(true);
        ImageIcon topLeftIcon = new ImageIcon(getClass().getResource("/modrcon/resources/1up8bit_green.png"));
        this.setIconImage(topLeftIcon.getImage());

        // Setup the Content Pane
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(new LogoPanel(LogoPanel.LOGO_CENTER), BorderLayout.NORTH);
        cp.add(this.getSettingsPanel(), BorderLayout.CENTER);
        cp.add(this.getButtonPanel(), BorderLayout.SOUTH);

        this.pack();

        // Set the location of the Settings Window centered relative to the MainWindow
        // --CENTER--
        Point aboutBoxLocation = new Point();
        double aboutBoxX = owner.getLocation().getX() + ((owner.getWidth() / 2) - (this.getWidth() / 2));
        double aboutBoxY = owner.getLocation().getY() + ((owner.getHeight() / 2) - (this.getHeight() / 2));
        aboutBoxLocation.setLocation(aboutBoxX, aboutBoxY);
        this.setLocation(aboutBoxLocation);
        // --END CENTER--

        this.setVisible(true);
    }

    private JPanel getSettingsPanel() {
        JPanel sp = new JPanel();
        sp.setBorder(BorderFactory.createTitledBorder("Program Settings"));
        JLabel lblGamePath = new JLabel("Game Exe Path:");
        JLabel lblTimeOut = new JLabel("Receive Timeout:");
        JLabel lblSendStatus = new JLabel("Send status command on connect");
        JLabel lblBGColor = new JLabel("Console BG Color");
        JLabel lblFGColor = new JLabel("Console Font Color");
        
        return sp;
    }

    private JPanel getButtonPanel() {
        JPanel bp = new JPanel();
        btnSave = new JButton("Save");
        btnClose = new JButton("Close");
        btnSave.addActionListener(this);
        btnClose.addActionListener(this);
        bp.add(btnSave);
        bp.add(btnClose);
        return bp;
    }

    private void fgColorPanelMouseClicked(java.awt.event.MouseEvent evt) {
        JColorChooser mycolor = new JColorChooser();
        Color chosenColor = mycolor.showDialog(mycolor, "Select a Font Color for the Console", fgColorPanel.getBackground());
        fgColorPanel.setBackground(chosenColor);
    }


    public void actionPerformed(ActionEvent e) {
        AbstractButton pressedButton = (AbstractButton)e.getSource();
        if (pressedButton == btnSave) {
            this.dispose();
        }
        else if (pressedButton == btnClose) {
            this.dispose();
        }
    }
}
