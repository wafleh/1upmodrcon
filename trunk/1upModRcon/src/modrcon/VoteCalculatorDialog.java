package modrcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * Creates a Dialog that allows you to change the vote settings on the server.
 * @author izuriel
 */
public class VoteCalculatorDialog extends JDialog implements MouseListener,
            ItemListener, ChangeListener, ActionListener, WindowListener {
    private final int MIN_VALUE = 0;
    private final int MAX_VALUE = 1073741823;

    private MainWindow parent;

    private JCheckBox dontShowBox;

    private JPanel warningPanel;
    private JPanel votePanel;

    private ImageIcon selectAllIcon;
    private ImageIcon selectAllGrayIcon;
    private ImageIcon deselectAllIcon;
    private ImageIcon deselectAllGrayIcon;

    private JLabel selectAllLabel;
    private JLabel deselectAllLabel;
    private JLabel showCalcLabel;
    private JCheckBox showAgainBox;

    private PropertyManager pManager;

    private Color mouseOverColor;
    private Color defaultColor;

    //private JSpinner voteSpinner;
    private JLabel voteLabel;

    private JButton sendButton;
    private JButton cancelButton;

    private java.util.ArrayList<VoteCheckBox> vbcList;

    public VoteCalculatorDialog(MainWindow parent) {
        super();
        this.setTitle("1up ModRcon - Allow Vote Calculator");
        this.setIconImage(parent.getIconImage());
        this.parent = parent;
        this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.addWindowListener(this);

        this.initialize();
        this.add(buildUI());
        this.center();
        this.setVisible(true);
    }

    private void initialize() {
        this.selectAllIcon = new ImageIcon(this.getClass().getResource("/modrcon/resources/apply.png"));
        this.selectAllGrayIcon = new ImageIcon(ImageUtil.desaturate(ImageUtil.toBufferedImage(this.selectAllIcon.getImage())));
        this.deselectAllIcon = new ImageIcon(this.getClass().getResource("/modrcon/resources/remove.png"));
        this.deselectAllGrayIcon = new ImageIcon(ImageUtil.desaturate(ImageUtil.toBufferedImage(this.deselectAllIcon.getImage())));

        this.dontShowBox = new JCheckBox("Don't show this message again");
        this.dontShowBox.setForeground(Color.WHITE);
        this.dontShowBox.setOpaque(false);

        this.selectAllLabel = new JLabel();
        this.selectAllLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.selectAllLabel.setIcon(this.selectAllGrayIcon);
        this.deselectAllLabel = new JLabel();
        this.deselectAllLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.deselectAllLabel.setIcon(this.deselectAllGrayIcon);

        this.pManager = new PropertyManager();

        this.defaultColor = this.getBackground();
        this.mouseOverColor = new Color(235, 235, 235);

        this.sendButton = new JButton("Send to Server");
        this.sendButton.addActionListener(this);
        this.cancelButton = new JButton("Cancel");
        this.cancelButton.addActionListener(this);

        //this.voteSpinner = new JSpinner();
        //this.voteSpinner.setValue(MIN_VALUE);
        //this.voteSpinner.addChangeListener(this);
        //((JSpinner.NumberEditor)this.voteSpinner.getEditor()).getTextField().setEditable(false);
        //Dimension sDim = new Dimension(130, (int)this.voteSpinner.getPreferredSize().getHeight());
        //this.voteSpinner.setPreferredSize(sDim);
        //this.voteSpinner.setMaximumSize(sDim);
        //this.voteSpinner.setMinimumSize(sDim);
        this.voteLabel = new JLabel(MIN_VALUE + "");

        this.showCalcLabel = new JLabel();

        this.vbcList = new java.util.ArrayList<VoteCheckBox>();
    }

    private void center() {
        //this.setSize(500, 500);
        this.pack();

        int x = parent.getX() + ((parent.getWidth() / 2) - (this.getWidth() / 2));
        int y = parent.getY() + ((parent.getHeight() / 2) - (this.getHeight() / 2));

        this.setLocation(x, y);
    }

    private JPanel buildUI() {
        JPanel uiPanel = new JPanel();
        uiPanel.setLayout(new BorderLayout());
        uiPanel.add(this.getTopPanel(), BorderLayout.NORTH);
        uiPanel.add(this.getCenterPanel(), BorderLayout.CENTER);
        uiPanel.add(this.getBottomPanel(), BorderLayout.SOUTH);

        return uiPanel;
    }

    private JPanel getTopPanel() {
        LogoPanel topPanel = new LogoPanel(LogoPanel.LOGO_CENTER);

        return topPanel;
    }

    private JPanel getBottomPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createGlue());
        buttonPanel.add(this.sendButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(this.cancelButton);
        buttonPanel.add(Box.createGlue());

        return ModRconUtil.getPaddedPanel(0, 0, 10, 0, buttonPanel);
    }

    private JPanel getCenterPanel() {
        this.warningPanel = new JPanel();
        this.warningPanel.setLayout(new BorderLayout());
        this.warningPanel.add(this.getVoteWarningPanel(), BorderLayout.CENTER);
        this.warningPanel.setVisible(true);
        this.votePanel = new JPanel();
        this.votePanel.setLayout(new BorderLayout());
        this.votePanel.add(this.getVoteTypePanel(), BorderLayout.CENTER);
        this.votePanel.setVisible(false);
        
        if (!(this.pManager.getShowVoteWarning()))
            this.switchPanels();
            

        JPanel altPanel = new JPanel();
        altPanel.setLayout(new BoxLayout(altPanel, BoxLayout.X_AXIS));
        altPanel.add(this.warningPanel);
        altPanel.add(this.votePanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(Box.createHorizontalStrut(15));
        bottomPanel.add(new JLabel("Set g_allowvote "));
        //bottomPanel.add(this.voteSpinner);
        bottomPanel.add(this.voteLabel);
        bottomPanel.add(Box.createGlue());
        bottomPanel.add(this.selectAllLabel);
        bottomPanel.add(Box.createHorizontalStrut(3));
        bottomPanel.add(this.deselectAllLabel);
        bottomPanel.add(Box.createHorizontalStrut(15));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Allow Vote Calculator"));
        centerPanel.add(altPanel, BorderLayout.CENTER);
        centerPanel.add(ModRconUtil.getPaddedPanel(0, 0, 5, 0, bottomPanel), BorderLayout.SOUTH);

        return ModRconUtil.getPaddedPanel(8, centerPanel);
    }

    private JPanel getVoteWarningPanel() {
        ImageIcon yeildIcon = new ImageIcon(this.getClass().getResource("/modrcon/resources/info.png"));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createHorizontalStrut(5));
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(yeildIcon);
        topPanel.add(iconLabel);
        topPanel.add(Box.createHorizontalStrut(3));
        JLabel tempLabel = new JLabel("WARNING");
        tempLabel.setFont(tempLabel.getFont().deriveFont(Font.BOLD));
        tempLabel.setForeground(Color.WHITE);
        topPanel.add(tempLabel);
        topPanel.add(Box.createGlue());
        topPanel.setOpaque(false);

        JLabel warningLabel = new JLabel();
        warningLabel.setText("<html><p>Please be advised that a bug exists in Urban "
                + "Terror that may cause your server to crash when the <font "
                + "color='yellow'>Shuffle Teams</font> vote is called. You "
                + "should only allow this if you are 100% certain that it will "
                + "not adversly effect the server in any way.</p></html>");
        warningLabel.setForeground(Color.WHITE);
        Dimension wDim = new Dimension(280, 100);
        warningLabel.setPreferredSize(wDim);
        warningLabel.setMaximumSize(wDim);
        warningLabel.setMinimumSize(wDim);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(Box.createGlue());
        centerPanel.add(Box.createHorizontalStrut(10));
        centerPanel.add(warningLabel);
        centerPanel.add(Box.createHorizontalStrut(10));
        centerPanel.add(Box.createGlue());

        this.showCalcLabel = new JLabel("<html><u>I Understand show me the calculator</u></html>");
        this.showCalcLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.showCalcLabel.addMouseListener(this);
        this.showCalcLabel.setForeground(Color.WHITE);
        this.showCalcLabel.setFont(this.showCalcLabel.getFont().deriveFont(Font.BOLD));
        this.showCalcLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel understandPanel = new JPanel();
        understandPanel.setLayout(new BoxLayout(understandPanel, BoxLayout.X_AXIS));
        understandPanel.setOpaque(false);
        understandPanel.add(Box.createGlue());
        understandPanel.add(this.showCalcLabel);
        understandPanel.add(Box.createGlue());

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.X_AXIS));
        checkBoxPanel.setOpaque(false);
        checkBoxPanel.add(Box.createGlue());
        checkBoxPanel.add(this.dontShowBox);
        checkBoxPanel.add(Box.createHorizontalStrut(5));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(understandPanel);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(Box.createGlue());
        bottomPanel.add(checkBoxPanel);
        bottomPanel.add(Box.createVerticalStrut(5));

        GradientPanel gWarningPanel = new GradientPanel(GradientPanel.WARNING_COLOR_START, GradientPanel.WARNING_COLOR_END);
        gWarningPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        gWarningPanel.setLayout(new BorderLayout());
        gWarningPanel.add(topPanel, BorderLayout.NORTH);
        gWarningPanel.add(centerPanel, BorderLayout.CENTER);
        gWarningPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        return ModRconUtil.getPaddedPanel(15, gWarningPanel);
    }

    private JPanel getVoteCheckBoxPanel(String text, long value) {
        VoteCheckBox vBox = new VoteCheckBox(text, value);
        vBox.addMouseListener(this);
        vBox.addItemListener(this);
        JLabel valueLabel = new JLabel(value + "");

        this.vbcList.add(vBox);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.addMouseListener(this);
        panel.add(vBox);
        panel.add(Box.createGlue());
        panel.add(valueLabel);
        panel.add(Box.createHorizontalStrut(5));

        vBox.setPanel(panel);

        return panel;
    }

    private JPanel getVoteTypePanel() {
        JPanel voteListPanel = new JPanel();
        voteListPanel.setLayout(new BoxLayout(voteListPanel, BoxLayout.Y_AXIS));
        voteListPanel.add(this.getVoteCheckBoxPanel("reload", 1));
        voteListPanel.add(this.getVoteCheckBoxPanel("restart", 2));
        voteListPanel.add(this.getVoteCheckBoxPanel("map", 4));
        voteListPanel.add(this.getVoteCheckBoxPanel("nextmap", 8));
        voteListPanel.add(this.getVoteCheckBoxPanel("kick/clientkick" ,16));
        voteListPanel.add(this.getVoteCheckBoxPanel("swapTeams", 32));
        voteListPanel.add(this.getVoteCheckBoxPanel("shuffleTeams", 64));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_friendlyFire", 128));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_followStrict", 256));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_gameType", 512));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_waveRespawns", 1024));
        voteListPanel.add(this.getVoteCheckBoxPanel("timelimit", 2048));
        voteListPanel.add(this.getVoteCheckBoxPanel("fragLimit", 4096));
        voteListPanel.add(this.getVoteCheckBoxPanel("captureLimit", 8192));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_respawnDelay", 16384));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_redWaveRespawnDelay", 32768));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_blueWaveRespawnDelay", 65536));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_bombExplodeTime", 131072));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_bombDefuseTime", 262144));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_survivorRoundTime", 524288));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_caputureScoreTime", 1048576));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_warmup", 2097152));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_matchMode", 4194304));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_timeouts", 8388608));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_timeoutLength", 16777216));
        voteListPanel.add(this.getVoteCheckBoxPanel("exec", 33554432));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_swapRoles", 67108864));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_maxRounds", 134217728));
        voteListPanel.add(this.getVoteCheckBoxPanel("g_gear", 268435456));
        voteListPanel.add(this.getVoteCheckBoxPanel("cyclemap", 536870912));

        JScrollPane sPane = new JScrollPane(voteListPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sPane.getVerticalScrollBar().setUnitIncrement(20);

        JLabel voteTitleLabel = new JLabel("     Vote");
        voteTitleLabel.setFont(voteTitleLabel.getFont().deriveFont(Font.BOLD, 14));
        JLabel valueLabel = new JLabel("Value     ");
        valueLabel.setFont(voteTitleLabel.getFont());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.add(voteTitleLabel);
        titlePanel.add(Box.createGlue());
        titlePanel.add(valueLabel);

        JPanel returnPanel = new JPanel(new BorderLayout());
        returnPanel.add(titlePanel, BorderLayout.NORTH);
        returnPanel.add(sPane, BorderLayout.CENTER);

        return ModRconUtil.getPaddedPanel(15, returnPanel);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            JPanel panel = (JPanel)e.getSource();
            VoteCheckBox vBox = (VoteCheckBox)(panel.getComponent(0));
            if (vBox.isSelected())
                vBox.setSelected(false);
            else
                vBox.setSelected(true);
        }
        if (e.getSource() instanceof JLabel) {
            JLabel source = (JLabel)e.getSource();
            if (source == this.selectAllLabel) {
                for (VoteCheckBox vBox: vbcList)
                    vBox.setSelected(true);
            }

            if (source == this.deselectAllLabel) {
                for (VoteCheckBox vBox: vbcList)
                    vBox.setSelected(false);
            }

            if (source == this.showCalcLabel)
                this.switchPanels();
        }
    }

    @Override
    public void dispose() {
        pManager.savePropertyFile();
        super.dispose();
    }

    private void switchPanels() {
        this.selectAllLabel.setIcon(this.selectAllIcon);
        this.selectAllLabel.addMouseListener(this);
        this.deselectAllLabel.setIcon(this.deselectAllIcon);
        this.deselectAllLabel.addMouseListener(this);
        this.warningPanel.setVisible(false);
        this.votePanel.setVisible(true);
        this.defaultColor = votePanel.getBackground();

        if (pManager.getShowVoteWarning())
            pManager.setShowVoteWarning(!(this.dontShowBox.isSelected()));
    }

    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) {
        Container source;

        if (!(e.getSource() instanceof JLabel)) {
            if (e.getSource() instanceof JPanel)
                source = (JPanel)e.getSource();
            else
                source = (VoteCheckBox)e.getSource();

            source.setBackground(this.mouseOverColor);
        }
    }

    public void mouseExited(MouseEvent e) {
        Container source;

        if (!(e.getSource() instanceof JLabel)) {
            if (e.getSource() instanceof JPanel)
                source = (JPanel)e.getSource();
            else
                source = (VoteCheckBox)e.getSource();

            source.setBackground(this.defaultColor);
        }
    }

    public void itemStateChanged(ItemEvent e) {
        VoteCheckBox source = (VoteCheckBox)e.getSource();
        //int value = (Integer)(this.voteSpinner.getValue());
        int value = Integer.parseInt(this.voteLabel.getText());

        if (source.isSelected())
            value += source.getValue();
        else
            value -= source.getValue();

        //this.voteSpinner.setValue(value);
        this.voteLabel.setText("" + value);
    }

    public void stateChanged(ChangeEvent e) {
        JSpinner source = (JSpinner)e.getSource();
        int value = (Integer)source.getValue();

        if (value > MAX_VALUE)
            value = MAX_VALUE;
        if (value < MIN_VALUE)
            value = MIN_VALUE;

        source.setValue(value);
    }

    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();

        if (source == this.sendButton) {
            // Todo
            System.out.println("g_allowvote " + this.voteLabel.getText());
        }

        if (source == this.cancelButton)
            this.dispose();
    }

    public void windowOpened(WindowEvent e) { }
    public void windowClosing(WindowEvent e) { }

    public void windowClosed(WindowEvent e) {
        this.dispose();
    }

    public void windowIconified(WindowEvent e) { }
    public void windowDeiconified(WindowEvent e) { }
    public void windowActivated(WindowEvent e) { }
    public void windowDeactivated(WindowEvent e) { }

    private class VoteCheckBox extends JCheckBox {
        private long value;
        private JPanel panel;

        public VoteCheckBox(String text, long value) {
            super(text);
            this.setOpaque(false);
            this.value = value;
        }

        public void setPanel(JPanel panel) {
            this.panel = panel;
        }

        public long getValue() {
            return this.value;
        }

        public void setBackground(Color color) {
            if (panel != null)
                this.panel.setBackground(color);
        }
    }
}
