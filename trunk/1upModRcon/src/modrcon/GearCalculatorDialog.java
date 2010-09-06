/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modrcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Brandon
 */
public class GearCalculatorDialog extends JDialog {
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

    private ImageIcon selectAllIcon;
    private ImageIcon deselectAllIcon;

    private MainWindow parent;
    
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
        this.selectAllIcon = new ImageIcon(this.getClass().getResource("/modrcon/resources/apply.png"));
        this.deselectAllIcon = new ImageIcon(this.getClass().getResource("/modrcon/resources/remove.png"));

        this.grenadeBox = new JCheckBox("Grenades");
        this.sniperBox = new JCheckBox("Snipers");
        this.spasBox = new JCheckBox("Spas");
        this.pistolBox = new JCheckBox("Pistols");
        this.automaticBox = new JCheckBox("Automatic Guns");
        this.negevBox = new JCheckBox("Negev");

        this.sendToServerButton = new JButton("Send to Server");
        this.cancelButton = new JButton("Cancel");
    }

    private void center() {
        this.setSize(400, 380);
        int x = (int)(parent.getWidth() / 2) - (int)(this.getWidth() / 2) + this.parent.getX();
        int y = (int)(parent.getHeight() / 2) - (int)(this.getHeight() / 2) + this.parent.getY();

        this.setLocation(x, y);
    }

    private JPanel buildUI() {
        LogoPanel northPanel = new LogoPanel(LogoPanel.LOGO_CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Gear Calculator"));
        infoPanel.add(this.getCheckBoxTitlePanel());
        infoPanel.add(this.getCheckBoxPanel(grenadeBox, GRENADE_VALUE));
        infoPanel.add(this.getCheckBoxPanel(sniperBox, SNIPER_VALUE));
        infoPanel.add(this.getCheckBoxPanel(spasBox, SPAS_VALUE));
        infoPanel.add(this.getCheckBoxPanel(pistolBox, PISTOL_VALUE));
        infoPanel.add(this.getCheckBoxPanel(automaticBox, AUTOMATIC_VALUE));
        infoPanel.add(this.getCheckBoxPanel(negevBox, NEGEV_VALUE));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(this.sendToServerButton);
        buttonPanel.add(this.cancelButton);

        JPanel infoWrapperPanel = new JPanel();
        infoWrapperPanel.setLayout(new VerticalFlowLayout());
        infoWrapperPanel.add(infoPanel);
        infoWrapperPanel.add(buttonPanel);

        JPanel centerPanel = new JPanel();
        centerPanel.add(infoWrapperPanel);

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
}
