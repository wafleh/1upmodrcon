package modrcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author Pyrite
 */
public class SettingManager extends JDialog implements ActionListener, MouseListener {

    private MainWindow parent;

    private FileChooserPanel gamePathPanel;
    private JSpinner timeoutSpinner;
    private JCheckBox sendStatusCheck;

    private JLabel bgColorLabel;
    private JLabel fgColorLabel;

    private JComboBox themeCombo;
    private String currentLAF;



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

        this.getSettings();
        this.setVisible(true);
    }

    private JPanel getSettingsPanel() {
        JPanel sp = new JPanel();
        sp.setLayout(new BoxLayout(sp, BoxLayout.Y_AXIS));
        sp.setBorder(BorderFactory.createTitledBorder("Program Settings"));
        JLabel lblGamePath = new JLabel("Game Exe Path:", JLabel.TRAILING);
        JLabel lblTimeOut = new JLabel("Receive Timeout:", JLabel.TRAILING);
        JLabel lblLAF = new JLabel("Look and Feel:", JLabel.TRAILING);
        JLabel lblSendStatus = new JLabel("Send status command on connect");
        JLabel lblBGColor = new JLabel("Console BG Color");
        JLabel lblFGColor = new JLabel("Console Font Color");
        
        this.gamePathPanel = new FileChooserPanel(this.parent, 25);
        this.timeoutSpinner = new JSpinner();
        this.timeoutSpinner.setValue(1500);

        JPanel springPanel = new JPanel(new SpringLayout());
        springPanel.add(lblGamePath);
        lblGamePath.setLabelFor(gamePathPanel);
        springPanel.add(gamePathPanel);
        springPanel.add(lblTimeOut);
        lblTimeOut.setLabelFor(timeoutSpinner);
        springPanel.add(timeoutSpinner);
        JComboBox theme = this.getThemeCombo();
        springPanel.add(lblLAF);
        lblLAF.setLabelFor(theme);
        springPanel.add(theme);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(springPanel,
             3, 2, //rows, cols
            10, 6, //initX, initY
            10, 6  //xPad, yPad
        );

        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.sendStatusCheck = new JCheckBox();
        checkPanel.add(sendStatusCheck);
        checkPanel.add(lblSendStatus);

        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.bgColorLabel = new JLabel();
        this.bgColorLabel.setPreferredSize(new Dimension(16,16));
        this.bgColorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.bgColorLabel.setOpaque(true);
        this.bgColorLabel.setBackground(Color.BLACK);
        this.bgColorLabel.addMouseListener(this);
        this.fgColorLabel = new JLabel();
        this.fgColorLabel.setPreferredSize(new Dimension(16,16));
        this.fgColorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.fgColorLabel.setBackground(Color.WHITE);
        this.fgColorLabel.setOpaque(true);
        this.fgColorLabel.addMouseListener(this);
        colorPanel.add(this.bgColorLabel);
        colorPanel.add(lblBGColor);
        colorPanel.add(this.fgColorLabel);
        colorPanel.add(lblFGColor);

        sp.add(springPanel);
        sp.add(checkPanel);
        sp.add(new JSeparator());
        sp.add(colorPanel);
        
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

    private void getSettings() {
        PropertyManager pm = new PropertyManager();
        this.gamePathPanel.setGamePath(pm.getGamePath());
        this.timeoutSpinner.setValue(pm.getReceiveTimeoutNumber());
        this.sendStatusCheck.setSelected(pm.getStatusOnConnect());
        this.bgColorLabel.setBackground(Color.decode(pm.getConsoleBGColor()));
        this.fgColorLabel.setBackground(Color.decode(pm.getConsoleFGColor()));
    }

    public void actionPerformed(ActionEvent e) {
        AbstractButton pressedButton = (AbstractButton)e.getSource();
        if (pressedButton == btnSave) {
            PropertyManager pm = new PropertyManager();
            pm.setGamePath(this.gamePathPanel.getGamePath());
            pm.setReceiveTimeout(this.timeoutSpinner.getValue().toString());
            pm.setStatusOnConnect(this.sendStatusCheck.isSelected());
            // Console Colors
            String bg = "#" + Integer.toHexString(bgColorLabel.getBackground().getRGB() & 0x00ffffff).toUpperCase();
            String fg = "#" + Integer.toHexString(fgColorLabel.getBackground().getRGB() & 0x00ffffff).toUpperCase();
            pm.setConsoleBGColor(bg);
            pm.setConsoleFGColor(fg);
            this.parent.getConsolePanel().setConsoleBackground(bgColorLabel.getBackground());
            this.parent.getConsolePanel().setConsoleForeground(fgColorLabel.getBackground());
            pm.setLookAndFeel(this.themeCombo.getSelectedItem().toString());
            if (!this.currentLAF.equals(this.themeCombo.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this.parent, "Note: Changing the Look & Feel doesn't\ntake effect until 1up ModRcon is restarted.");
            }
            pm.savePropertyFile();
            this.dispose();
        }
        else if (pressedButton == btnClose) {
            this.dispose();
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == bgColorLabel) {
            JColorChooser mycolor = new JColorChooser();
            Color chosenColor = mycolor.showDialog(mycolor, "Select a Background Color for the Console", bgColorLabel.getBackground());
            if (chosenColor != null)
                bgColorLabel.setBackground(chosenColor);
        }
        else if (e.getSource() == fgColorLabel) {
            JColorChooser mycolor = new JColorChooser();
            Color chosenColor = mycolor.showDialog(mycolor, "Select a Font Color for the Console", fgColorLabel.getBackground());
            if (chosenColor != null)
                fgColorLabel.setBackground(chosenColor);
        }
    }

    public void mousePressed(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void mouseReleased(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private JComboBox getThemeCombo() {
        this.themeCombo = new JComboBox();
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            themeCombo.addItem(info.getName());
        }
        themeCombo.setSelectedItem(UIManager.getLookAndFeel().getName());
        this.currentLAF = UIManager.getLookAndFeel().getName();
        return this.themeCombo;
    }
}
