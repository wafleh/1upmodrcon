package modrcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * The Dialog that controls matching Strings within the Console.
 * @author izuriel
 */
public class SearchConsoleDialog extends JDialog implements ActionListener{
    private ConsoleTextPane console;

    private JButton findNextButton;
    private JButton cancelButton;

    private JTextField searchField;

    private java.util.ArrayList<Integer> previous;

    private JCheckBox matchCaseBox;
    private JCheckBox matchWordBox;

    private JRadioButton searchUpRadio;
    private JRadioButton searchDownRadio;

    private final String ENTER_QUERY = "You have to search for something!";
    private final String NOT_FOUND_1 = "Could not find anymore occurrences of \"";
    private final String NOT_FOUND_2 = "\"";
    
    private JLabel messageLabel;

    public SearchConsoleDialog(MainWindow parent, ConsoleTextPane console) {
        super();
        this.setTitle("Find");
        this.setIconImage(parent.getIconImage());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setResizable(false);

        this.console = console;
        initialize();

        this.add(buildUI());
        this.center(parent);
        this.setVisible(true);
    }

    private void center(MainWindow parent) {
        this.pack();

        int x = parent.getX() + ((parent.getWidth() / 2) - (this.getWidth() / 2));
        int y = parent.getY() + ((parent.getHeight() / 2) - (this.getHeight() / 2));

        this.setLocation(x, y);
    }

    private void initialize() {
        this.previous = new java.util.ArrayList<Integer>();
        this.messageLabel = new JLabel();

        this.findNextButton = new JButton("Find Next");
        this.findNextButton.addActionListener(this);
        this.cancelButton = new JButton("Cancel");
        this.cancelButton.addActionListener(this);
        //this.cancelButton.setPreferredSize(new Dimension((int)findNextButton.getPreferredSize().getWidth(), (int)cancelButton.getPreferredSize().getHeight()));

        this.matchWordBox = new JCheckBox("Match Whole Word");
        this.matchCaseBox = new JCheckBox("Match Case");

        this.searchUpRadio = new JRadioButton("Up");
        this.searchDownRadio = new JRadioButton("Down");
        this.searchDownRadio.setSelected(true);

        ButtonGroup directionGroup = new ButtonGroup();
        directionGroup.add(this.searchUpRadio);
        directionGroup.add(this.searchDownRadio);

        this.searchField = new JTextField(25);
    }

    private JPanel buildUI() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(this.getEastPanel(), BorderLayout.EAST);
        searchPanel.add(this.getCenterPanel(), BorderLayout.CENTER);
        searchPanel.add(this.getBottomPanel(), BorderLayout.SOUTH);

        return searchPanel;
    }

    private JPanel getBottomPanel() {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
        labelPanel.add(Box.createGlue());
        labelPanel.add(this.messageLabel);
        labelPanel.add(Box.createGlue());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setPreferredSize(new Dimension(20, 20));
        bottomPanel.add(Box.createGlue());
        bottomPanel.add(labelPanel);
        bottomPanel.add(Box.createVerticalStrut(8));

        return bottomPanel;
    }

    private JPanel getCenterPanel() {
        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.X_AXIS));
        queryPanel.add(Box.createGlue());
        queryPanel.add(Box.createHorizontalStrut(10));
        queryPanel.add(new JLabel("Find what:"));
        queryPanel.add(Box.createHorizontalStrut(3));
        queryPanel.add(this.searchField);
        queryPanel.add(Box.createHorizontalStrut(5));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(queryPanel);
        topPanel.add(Box.createVerticalStrut(10));

        JPanel directionPanel = new JPanel();
        directionPanel.setBorder(BorderFactory.createTitledBorder("Direction"));
        directionPanel.setLayout(new BoxLayout(directionPanel, BoxLayout.X_AXIS));
        directionPanel.add(this.searchUpRadio);
        directionPanel.add(this.searchDownRadio);

        JPanel eastCenterPanel = new JPanel();
        eastCenterPanel.setLayout(new BoxLayout(eastCenterPanel, BoxLayout.X_AXIS));
        eastCenterPanel.add(directionPanel);
        eastCenterPanel.add(Box.createHorizontalStrut(5));

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.add(eastCenterPanel);
        eastPanel.add(Box.createVerticalStrut(5));

        JPanel searchControlPanel = new JPanel();
        searchControlPanel.setLayout(new BoxLayout(searchControlPanel, BoxLayout.Y_AXIS));
        searchControlPanel.add(this.matchWordBox);
        searchControlPanel.add(this.matchCaseBox);

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.X_AXIS));
        westPanel.add(Box.createHorizontalStrut(5));
        westPanel.add(searchControlPanel);

        JPanel returnPanel = new JPanel(new BorderLayout());
        returnPanel.add(topPanel, BorderLayout.NORTH);
        returnPanel.add(eastPanel, BorderLayout.EAST);
        returnPanel.add(westPanel, BorderLayout.WEST);

        return returnPanel;
    }

    private JPanel getEastPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(this.findNextButton);
        buttonPanel.add(Box.createVerticalStrut(3));
        buttonPanel.add(this.cancelButton);
        buttonPanel.add(Box.createGlue());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(buttonPanel);
        topPanel.add(Box.createHorizontalStrut(5));

        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BoxLayout(returnPanel, BoxLayout.Y_AXIS));
        returnPanel.add(Box.createVerticalStrut(5));
        returnPanel.add(topPanel);
        returnPanel.add(Box.createGlue());

        return returnPanel;
    }

    private void setMessageLabel(String message, Color fontColor, Font font) {
        new MessageThread(message, fontColor, font, this.messageLabel);
    }

    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();

        if (source == this.findNextButton)
            this.setMessageLabel("Not implmented yet.", Color.RED, this.messageLabel.getFont().deriveFont(Font.BOLD));
        
        if (source == this.cancelButton)
            this.dispose();
    }

    private class MessageThread extends Thread {
        private String message;
        private Color fontColor;
        private JLabel label;
        private Font font;

        public MessageThread(String message, Color fontColor, Font font, JLabel label) {
            super();
            this.message = message;
            this.fontColor = fontColor;
            this.font = font;
            this.label = label;
            this.startUp();
        }

        private void startUp() {
            start();
        }

        public void run() {
            try {
                label.setForeground(fontColor);
                label.setFont(font);
                label.setText(message);
                sleep(2000);
                label.setText("");
            } catch (Exception e) {
                label.setText("");
            }
        }
    }
}
