package modrcon;

import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 *
 * @author Pyrite
 */
public class ServerManager extends JDialog implements ActionListener, MouseListener {
    
    private MainWindow parent;

    private JScrollPane jsp;
    private JTable serverTable;
    private JLabel iconKeyAssignments;
    private JLabel iconAddRow;
    private JLabel iconEditCell;
    private JLabel iconDeleteRow;
    private JButton btnSave;
    private JButton btnClose;

    public ServerManager(MainWindow owner) {
        super(owner);
        this.parent = owner;
        this.setTitle("1up ModRcon - Manage Servers");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setModal(true);

        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        JPanel row2 = new JPanel();
        row2.setLayout(new BorderLayout());
        row2.setBorder(BorderFactory.createTitledBorder("Server List"));

        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BorderLayout());
        iconKeyAssignments = new JLabel("Key Assignments");
        iconKeyAssignments.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/about.png")));
        JPanel tableIconPanel = new JPanel();

        iconAddRow = new JLabel();
        iconAddRow.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/add.png")));
        iconAddRow.addMouseListener(this);
        iconEditCell = new JLabel();
        iconEditCell.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/files_edit.png")));
        iconEditCell.addMouseListener(this);
        iconDeleteRow = new JLabel();
        iconDeleteRow.setIcon(new ImageIcon(getClass().getResource("/modrcon/resources/del.png")));
        iconDeleteRow.addMouseListener(this);

        tableIconPanel.add(iconAddRow);
        tableIconPanel.add(iconEditCell);
        tableIconPanel.add(iconDeleteRow);

        iconPanel.add(iconKeyAssignments, BorderLayout.WEST);
        iconPanel.add(tableIconPanel, BorderLayout.EAST);

        DefaultTableModel dtm = new DefaultTableModel();
        serverTable = new JTable(dtm);

        dtm.addColumn("Server Name");
        dtm.addColumn("IP");
        dtm.addColumn("Port");
        dtm.addColumn("Login Type");
        dtm.addColumn("Password");

        String[] values = new String[] { "ref", "mod", "rcon" };
        TableColumn col = serverTable.getColumnModel().getColumn(3);
        col.setCellEditor(new MyComboBoxEditor(values));
        col.setCellRenderer(new MyComboBoxRenderer(values));
        TableColumn passCol = serverTable.getColumnModel().getColumn(4);
        passCol.setCellRenderer(new PasswordCellRenderer());


        serverTable.setPreferredScrollableViewportSize(new Dimension(350, 200));
        serverTable.setGridColor(Color.LIGHT_GRAY);

        

        jsp = new JScrollPane(serverTable);
        row2.add(jsp, BorderLayout.CENTER);
        row2.add(iconPanel, BorderLayout.SOUTH);

        JPanel row3 = new JPanel();
        row3.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        btnSave = new JButton("Save");
        btnSave.addActionListener(this);
        btnClose = new JButton("Close");
        btnClose.addActionListener(this);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnClose);

        row3.add(new JLabel(" Note: Changes are only saved to the database after clicking the Save button."), BorderLayout.WEST);
        row3.add(buttonPanel, BorderLayout.EAST);

        cp.add(new LogoPanel(LogoPanel.LOGO_CENTER), BorderLayout.NORTH);
        cp.add(row2, BorderLayout.CENTER);
        cp.add(row3, BorderLayout.SOUTH);

        this.pack();
        this.readFile();

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

    private void readFile() {
        DefaultTableModel dm = (DefaultTableModel)this.serverTable.getModel();

        // Clear any Rows that may be there by default.
        dm.getDataVector().removeAllElements();

        // Bring in Servers from servers.db
        ServerDatabase db = new ServerDatabase();
        ArrayList servers = db.getServerList();
        for (Object o : servers) {
            Server s = (Server)o;
            dm.addRow(s.toArray());
        }
        ColumnResizer.adjustColumnPreferredWidths(this.serverTable);
    }

    private void saveTable() {
        ArrayList servers = new ArrayList();
        TableModel tm = this.serverTable.getModel();
        int numRows = tm.getRowCount();
        for (int i=0; i< numRows; i++) {
            String name = (String)tm.getValueAt(i, 0);
            String ip = (String)tm.getValueAt(i, 1);
            String port = (String)tm.getValueAt(i, 2);
            String loginType = (String)tm.getValueAt(i, 3);
            String password = (String)tm.getValueAt(i, 4);
            Server s = new Server(name, ip, port, loginType, password);
            servers.add(s);
        }
        ServerDatabase db = new ServerDatabase();
        db.setServerList(servers);
        db.saveDatabase();
    }

    /**
     * Checks all table cells to verify that they are non-empty.
     *
     * @return True if all cells are populated, otherwise returns false.
     */
    private boolean validateFields() {
        boolean flag = true;
        TableModel tm = this.serverTable.getModel();
        int numRows = tm.getRowCount();
        int numCols = tm.getColumnCount();
        for (int i=0; i < numRows; i++) {
            for (int j=0; j < numCols; j++) {
                String cell = (String)tm.getValueAt(i, j);
                if (cell.equals("")) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
        if (pressedButton == btnSave) {
            if (this.validateFields()) {
                this.saveTable();
                this.parent.refreshServerCombo();
                this.dispose();
            }
            else {
                JOptionPane.showMessageDialog(this, "<html>All fields are required, and cannot be empty.<br>If you are still in editing mode on a particular cell,<br>hit tab or click outside of the cell, then try to save again.</html>");
            }
        }
        else if (pressedButton == btnClose) {
            this.dispose();
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == iconAddRow) {
            DefaultTableModel tm = (DefaultTableModel)this.serverTable.getModel();
            tm.addRow(new Server("", "", "27960", "mod", "").toArray());
        }
        else if (e.getSource() == iconDeleteRow) {
            DefaultTableModel tm = (DefaultTableModel)this.serverTable.getModel();
            int row = this.serverTable.getSelectedRow();
            if (row != -1) {
                tm.removeRow(row);
            }
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

}

class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {
    public MyComboBoxRenderer(String[] items) {
        super(items);
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        setSelectedItem(value);
        return this;
    }
}

class MyComboBoxEditor extends DefaultCellEditor {
    public MyComboBoxEditor(String[] items) {
        super(new JComboBox(items));
    }
}

class PasswordCellRenderer extends JPasswordField implements TableCellRenderer {
    public PasswordCellRenderer() {
      super();
      // This displays astericks in fields since it is a password.
      // It does not affect the actual value of the cell.
      this.setText("filler123");
    }
    public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
        return this;
    }
}
