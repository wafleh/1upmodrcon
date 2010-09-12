package modrcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * The Dialog that controls matching Strings within the Console.
 * @author izuriel
 */
public class SearchConsoleDialog extends JDialog implements ActionListener, 
        KeyListener, WindowListener, ItemListener {
    private ConsoleTextPane console;
    private MainWindow parent;

    private JButton findNextButton;
    private JButton cancelButton;

    private JTextField searchField;
    private String currentSearch;

    private int step;

    private JCheckBox matchCaseBox;
    private JCheckBox matchWordBox;

    private final String ENTER_QUERY = "You have to search for something!";
    private final String NOT_FOUND = "Could not find any occurrences of \"";
    private final String NO_MORE = "Could not find anymore occurrences of \"";
    
    private JLabel messageLabel;

    public SearchConsoleDialog(MainWindow parent, ConsoleTextPane console) {
        super();
        this.setTitle("Find");
        this.setIconImage(parent.getIconImage());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.addWindowListener(this);

        this.parent = parent;
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
        this.step = -1;
        this.messageLabel = new JLabel();

        this.findNextButton = new JButton("Find Next");
        this.findNextButton.addActionListener(this);
        this.cancelButton = new JButton("Cancel");
        this.cancelButton.addActionListener(this);
        this.cancelButton.setMaximumSize(new Dimension(
                (int)findNextButton.getPreferredSize().getWidth(),
                (int)cancelButton.getPreferredSize().getHeight()));

        this.matchWordBox = new JCheckBox("Match Whole Word");
        this.matchWordBox.addItemListener(this);
        this.matchCaseBox = new JCheckBox("Match Case");
        this.matchCaseBox.addItemListener(this);

        this.searchField = new JTextField(25);
        this.searchField.setText("");
        this.searchField.addKeyListener(this);
        this.currentSearch = null;
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

    private void resetSearch() {
        this.step = -1;
        this.console.removeHighlights();
    }

    private void setMessageLabel(String message, Color fontColor, Font font) {
        new MessageThread(message, fontColor, font, this.messageLabel);
    }

    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();

        if (source == this.findNextButton)
            this.performConsoleSearch();
        
        if (source == this.cancelButton) {
            this.console.removeHighlights();
            this.console.requestFocusInWindow();
            this.dispose();
        }
    }

    private void performConsoleSearch() {
        String currentText = this.searchField.getText();

        if (currentText.length() > 0) {

            if (!(currentText.equalsIgnoreCase(this.currentSearch))) {
                this.resetSearch();
                this.currentSearch = currentText;
            }

            int start;
            if (step == -1)
                start = this.console.find(currentText, 
                        this.matchCaseBox.isSelected(),
                        this.matchWordBox.isSelected());
            else
                start = this.console.find(currentText, this.step, 
                        this.matchCaseBox.isSelected(),
                        this.matchWordBox.isSelected());

            if (start == -1 && this.step == -1)
                this.setMessageLabel(this.NOT_FOUND + currentText + "\"", Color.RED,
                        this.messageLabel.getFont().deriveFont(Font.BOLD));
            else if (start == -1 && this.step > -1)
                this.setMessageLabel(this.NO_MORE + currentText + "\"", Color.BLACK,
                        this.messageLabel.getFont().deriveFont(Font.PLAIN));
            else if (start > -1)
                this.step = start + currentText.length();
        } else
            this.setMessageLabel(this.ENTER_QUERY, Color.RED,
                    this.messageLabel.getFont().deriveFont(Font.BOLD));
    }

    public void keyTyped(KeyEvent e) { }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            this.performConsoleSearch();
    }

    public void keyReleased(KeyEvent e) { }
    public void windowOpened(WindowEvent e) { }

    public void windowClosing(WindowEvent e) {
        this.console.removeHighlights();
        this.console.requestFocusInWindow();
    }

    public void windowClosed(WindowEvent e) { }
    public void windowIconified(WindowEvent e) { }
    public void windowDeiconified(WindowEvent e) { }
    public void windowActivated(WindowEvent e) { }
    public void windowDeactivated(WindowEvent e) { }

    public void itemStateChanged(ItemEvent e) {
        this.resetSearch();
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

        @Override
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
