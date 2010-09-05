package modrcon;

import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import javax.swing.*;
import java.io.*;

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
        super();
        this.parent = owner;

        this.setTitle("1up ModRcon - Manage Servers");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ImageIcon topLeftIcon = new ImageIcon(getClass().getResource("/modrcon/resources/1up8bit_green.png"));
        this.setIconImage(topLeftIcon.getImage());
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

    private void writeFile(String contents) {
        try {
            // Will put the file where the 1upmodrcon.properties file exists.
            // Will also create the file if it does not exist!
            FileOutputStream fos = new FileOutputStream("servers.xml");
            OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
            out.write(contents);
            out.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void readFile() {
        DefaultTableModel dm = (DefaultTableModel)this.serverTable.getModel();

        // Clear any Rows that may be there by default.
        dm.getDataVector().removeAllElements();

        // Bring in Servers from servers.xml
        ServerParser sp = new ServerParser();
        java.util.List servers = sp.getServers();
        for (int i=0; i < servers.size(); i++) {
            dm.addRow(((Server)servers.get(i)).toArray());
        }
        ColumnResizer.adjustColumnPreferredWidths(this.serverTable);
    }

    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
        if (pressedButton == btnSave) {
            // TODO add your handling code here:
            TableModel tm = this.serverTable.getModel();
            int numRows = tm.getRowCount();
            String contents = "<servers>\r\n";
            for (int i = 0; i < numRows; i++) {
                contents += "\t<server>\r\n";

                String name = (String)tm.getValueAt(i, 0);
                if (name == null) name = "";
                contents += "\t\t<name>"+name+"</name>\r\n";

                String ip = (String)tm.getValueAt(i, 1);
                if (ip == null) ip = "";
                contents += "\t\t<ip>"+ip+"</ip>\r\n";

                String port = (String)tm.getValueAt(i, 2);
                if (port == null) port = "";
                contents += "\t\t<port>"+port+"</port>\r\n";

                String loginType = (String)tm.getValueAt(i, 3);
                if (loginType == null) loginType = "";
                contents += "\t\t<logintype>"+loginType+"</logintype>\r\n";

                String password = (String)tm.getValueAt(i, 4);
                if (password == null) password = "";
                contents += "\t\t<password>"+password+"</password>\r\n";

                contents += "\t</server>\r\n";
            }
            contents += "</servers>\r\n";

            //System.out.print(contents);
            this.writeFile(contents);
            this.parent.refreshServerCombo();
            this.dispose();
        }
        else if (pressedButton == btnClose) {
            this.dispose();
        }
        else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == iconAddRow) {
            DefaultTableModel tm = (DefaultTableModel)this.serverTable.getModel();
            tm.addRow(new Server("foo", "192.168", "27960", "mod", "foo").toArray());
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
