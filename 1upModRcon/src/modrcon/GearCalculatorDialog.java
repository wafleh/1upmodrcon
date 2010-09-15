/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modrcon;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Brandon
 */
public class GearCalculatorDialog extends JDialog implements MouseListener, 
        ActionListener, ItemListener, ChangeListener {
    private JCheckBox grenadeBox;
    private JCheckBox sniperBox;
    private JCheckBox spasBox;
    private JCheckBox pistolBox;
    private JCheckBox automaticBox;
    private JCheckBox negevBox;

    private JButton sendToServerButton;
    private JButton cancelButton;

    private static final int GRENADE_VALUE = 1;
    private static final int SNIPER_VALUE = 2;
    private static final int SPAS_VALUE = 4;
    private static final int PISTOL_VALUE = 8;
    private static final int AUTOMATIC_VALUE = 16;
    private static final int NEGEV_VALUE = 32;

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 63;

    private JLabel selectAllLabel;
    private JLabel deselectAllLabel;

    private ImageIcon selectAllIcon;
    private ImageIcon deselectAllIcon;

    private JSpinner gearSpinner;

    private MainWindow parent;

    private boolean fireEvents;
    
    public GearCalculatorDialog(MainWindow parent) {
        super();
        this.setTitle("1up ModRcon - Gear Calculator");
        this.setModal(true);
        this.setIconImage(parent.getIconImage());
        this.setResizable(false);
        this.parent = parent;

        this.initialize();
        this.add(this.buildUI());
        this.center();

        this.setVisible(true);
    }

    private void initialize() {
        this.fireEvents = true;

        this.selectAllIcon = new ImageIcon(this.getClass().getResource("/modrcon/resources/apply.png"));
        this.deselectAllIcon = new ImageIcon(this.getClass().getResource("/modrcon/resources/remove.png"));

        this.gearSpinner = new JSpinner();
        this.gearSpinner.addChangeListener(this);
        this.gearSpinner.setValue(MIN_VALUE);
        ((JSpinner.NumberEditor)this.gearSpinner.getEditor()).getTextField().setEditable(false);

        this.grenadeBox = new JCheckBox("Grenades");
        this.grenadeBox.setSelected(true);
        this.grenadeBox.addItemListener(this);
        this.sniperBox = new JCheckBox("Snipers");
        this.sniperBox.setSelected(true);
        this.sniperBox.addItemListener(this);
        this.spasBox = new JCheckBox("Spas");
        this.spasBox.setSelected(true);
        this.spasBox.addItemListener(this);
        this.pistolBox = new JCheckBox("Pistols");
        this.pistolBox.setSelected(true);
        this.pistolBox.addItemListener(this);
        this.automaticBox = new JCheckBox("Automatic Guns");
        this.automaticBox.setSelected(true);
        this.automaticBox.addItemListener(this);
        this.negevBox = new JCheckBox("Negev");
        this.negevBox.setSelected(true);
        this.negevBox.addItemListener(this);

        this.sendToServerButton = new JButton("Send to Server");
        this.sendToServerButton.addActionListener(this);
        this.cancelButton = new JButton("Cancel");
        this.cancelButton.addActionListener(this);

        this.setServerGearSettings();

        Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        this.selectAllLabel = new JLabel(this.selectAllIcon);
        this.selectAllLabel.addMouseListener(this);
        this.selectAllLabel.setCursor(handCursor);
        this.deselectAllLabel = new JLabel(this.deselectAllIcon);
        this.deselectAllLabel.addMouseListener(this);
        this.deselectAllLabel.setCursor(handCursor);
    }

    private void setServerGearSettings() {
        int gGearNum = 0;

        try {
            BowserQuery bQuery = new BowserQuery(this.parent.getCurrentServer());
            gGearNum = bQuery.getGearSetting();
        } catch(Exception exc) { System.out.println(exc.getMessage()); }

        this.gearSpinner.setValue(gGearNum);
        this.fireEvents = false;
        this.updateGearBoxes();
        this.fireEvents = true;
    }

    private void center() {
        this.pack();
        int x = (int)(parent.getWidth() / 2) - (int)(this.getWidth() / 2) + this.parent.getX();
        int y = (int)(parent.getHeight() / 2) - (int)(this.getHeight() / 2) + this.parent.getY();

        this.setLocation(x, y);
    }

    private JPanel buildUI() {
        LogoPanel northPanel = new LogoPanel(LogoPanel.LOGO_CENTER);

        JPanel infoTopPanel = new JPanel();
        infoTopPanel.setLayout(new BoxLayout(infoTopPanel, BoxLayout.Y_AXIS));
        infoTopPanel.add(this.getCheckBoxTitlePanel());
        infoTopPanel.add(this.getCheckBoxPanel(grenadeBox, GRENADE_VALUE));
        infoTopPanel.add(this.getCheckBoxPanel(sniperBox, SNIPER_VALUE));
        infoTopPanel.add(this.getCheckBoxPanel(spasBox, SPAS_VALUE));
        infoTopPanel.add(this.getCheckBoxPanel(pistolBox, PISTOL_VALUE));
        infoTopPanel.add(this.getCheckBoxPanel(automaticBox, AUTOMATIC_VALUE));
        infoTopPanel.add(this.getCheckBoxPanel(negevBox, NEGEV_VALUE));
        infoTopPanel.add(Box.createVerticalStrut(10));

        JLabel gearLabel = new JLabel("Set g_gear");
        JPanel gearSpinnerPanel = new JPanel();
        gearSpinnerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        gearSpinnerPanel.add(gearLabel);
        gearSpinnerPanel.add(this.gearSpinner);

        JPanel controlButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlButtonPanel.add(this.selectAllLabel);
        controlButtonPanel.add(this.deselectAllLabel);

        JPanel infoBottomPanel = new JPanel();
        infoBottomPanel.setLayout(new BorderLayout());
        infoBottomPanel.setPreferredSize(new Dimension(260, 30));
        infoBottomPanel.add(gearSpinnerPanel, BorderLayout.WEST);
        infoBottomPanel.add(controlButtonPanel, BorderLayout.EAST);
        this.gearSpinner.setPreferredSize(new Dimension(45, this.gearSpinner.getEditor().getFont().getSize() + 10));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Gear Calculator"));
        infoPanel.add(infoTopPanel);
        infoPanel.add(infoBottomPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(this.sendToServerButton);
        buttonPanel.add(this.cancelButton);

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new VerticalFlowLayout());
        wrapperPanel.add(infoPanel);
        wrapperPanel.add(buttonPanel);

        JPanel centerPanel = new JPanel();
        centerPanel.add(wrapperPanel);

        JPanel borderPanel = new JPanel();
        borderPanel.setLayout(new BorderLayout());
        borderPanel.add(northPanel, BorderLayout.NORTH);
        borderPanel.add(centerPanel, BorderLayout.CENTER);

        return borderPanel;
    }

    private JPanel getCheckBoxTitlePanel() {
        JLabel gearLabel = new JLabel("     Gear");
        gearLabel.setFont(gearLabel.getFont().deriveFont(Font.BOLD, 14));
        JLabel valueLabel = new JLabel("Value");
        valueLabel.setFont(valueLabel.getFont().deriveFont(Font.BOLD, 14));

        JPanel gearLabelPanel = new JPanel();
        gearLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        gearLabelPanel.add(gearLabel);

        JPanel valueLabelPanel = new JPanel();
        valueLabelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        valueLabelPanel.add(valueLabel);

        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BorderLayout());
        returnPanel.add(gearLabelPanel, BorderLayout.WEST);
        returnPanel.add(valueLabelPanel, BorderLayout.EAST);

        returnPanel.setPreferredSize(new Dimension(260, 25));

        return returnPanel;
    }

    private JPanel getCheckBoxPanel(JCheckBox checkBox, int value) {
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        checkBoxPanel.add(checkBox);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        labelPanel.add(new JLabel(value + ""));

        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BorderLayout());
        returnPanel.add(checkBoxPanel, BorderLayout.CENTER);
        returnPanel.add(labelPanel, BorderLayout.EAST);

        returnPanel.setPreferredSize(new Dimension(260, 25));

        return returnPanel;
    }

    public void mouseClicked(MouseEvent e) {
        JLabel sourceLabel = (JLabel)e.getSource();
        if (sourceLabel == this.selectAllLabel) {
            this.grenadeBox.setSelected(true);
            this.sniperBox.setSelected(true);
            this.spasBox.setSelected(true);
            this.pistolBox.setSelected(true);
            this.automaticBox.setSelected(true);
            this.negevBox.setSelected(true);
        }

        if (sourceLabel == this.deselectAllLabel) {
            this.grenadeBox.setSelected(false);
            this.sniperBox.setSelected(false);
            this.spasBox.setSelected(false);
            this.pistolBox.setSelected(false);
            this.automaticBox.setSelected(false);
            this.negevBox.setSelected(false);
        }
    }
    public void mousePressed(MouseEvent e) {setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
    public void mouseReleased(MouseEvent e) {setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
    public void mouseEntered(MouseEvent e) {setCursor(new Cursor(Cursor.HAND_CURSOR));}
    public void mouseExited(MouseEvent e) {setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}

    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();
        if (source == this.sendToServerButton) {
            String cmd = "g_gear " + this.gearSpinner.getValue();
            try {
                Server server = this.parent.getCurrentServer();
                BowserQuery q = new BowserQuery(server);
                q.sendCmd(cmd);
                this.parent.getConsolePanel().appendCommand(cmd);
                this.parent.getConsolePanel().appendToConsole(q.getResponse());
            }
            catch (Exception event) {
                System.out.print(event.getMessage());
            }
            this.dispose();
        }
        if (source == this.cancelButton) {
            this.dispose();
        }
    }

    public void itemStateChanged(ItemEvent e) {
        JCheckBox source = (JCheckBox)e.getSource();
        int oldValue = (Integer)this.gearSpinner.getValue();

        if (this.fireEvents) {
            if (source == this.grenadeBox) {
                if (this.grenadeBox.isSelected())
                    oldValue -= GRENADE_VALUE;
                else
                    oldValue += GRENADE_VALUE;
            }

            if (source == this.sniperBox) {
                if (this.sniperBox.isSelected())
                    oldValue -= SNIPER_VALUE;
                else
                    oldValue += SNIPER_VALUE;
            }

            if (source == this.spasBox) {
                if (this.spasBox.isSelected())
                    oldValue -= SPAS_VALUE;
                else
                    oldValue += SPAS_VALUE;
            }

            if (source == this.pistolBox) {
                if (this.pistolBox.isSelected())
                    oldValue -= PISTOL_VALUE;
                else
                    oldValue += PISTOL_VALUE;
            }

            if (source == this.automaticBox) {
                if (this.automaticBox.isSelected())
                    oldValue -= AUTOMATIC_VALUE;
                else
                    oldValue += AUTOMATIC_VALUE;
            }

            if (source == this.negevBox) {
                if (this.negevBox.isSelected())
                    oldValue -= NEGEV_VALUE;
                else
                    oldValue += NEGEV_VALUE;
            }
        }

        this.gearSpinner.setValue(oldValue);
    }

    public void stateChanged(ChangeEvent e) {
        JSpinner source = (JSpinner)e.getSource();
        int value = (Integer)source.getValue();
        
        if (value > MAX_VALUE)
            value = MAX_VALUE;
        if (value < MIN_VALUE)
            value = MIN_VALUE;

        source.setValue(value);
        this.fireEvents = false;
        this.updateGearBoxes();
        this.fireEvents = true;
    }

    private void updateGearBoxes() {
        int gGearNum = (Integer)this.gearSpinner.getValue();

        if (gGearNum >= this.NEGEV_VALUE) {
            this.negevBox.setSelected(true);
            gGearNum -= this.NEGEV_VALUE;
        } else
            this.negevBox.setSelected(false);

        if (gGearNum >= this.AUTOMATIC_VALUE) {
            this.automaticBox.setSelected(true);
            gGearNum -= this.AUTOMATIC_VALUE;
        } else
            this.automaticBox.setSelected(false);

        if (gGearNum >= this.PISTOL_VALUE) {
            this.pistolBox.setSelected(true);
            gGearNum -= this.PISTOL_VALUE;
        } else
            this.pistolBox.setSelected(false);

        if (gGearNum >= this.SPAS_VALUE) {
            this.spasBox.setSelected(true);
            gGearNum -= this.SPAS_VALUE;
        } else
            this.spasBox.setSelected(false);

        if (gGearNum >= this.SNIPER_VALUE) {
            this.sniperBox.setSelected(true);
            gGearNum -= this.SNIPER_VALUE;
        } else
            this.sniperBox.setSelected(false);

        if (gGearNum >= this.GRENADE_VALUE)
            this.grenadeBox.setSelected(true);
        else
            this.grenadeBox.setSelected(false);
    }
}
