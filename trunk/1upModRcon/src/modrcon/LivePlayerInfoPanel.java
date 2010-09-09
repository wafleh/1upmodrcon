/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modrcon;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

/**
 *
 * @author Pyrite
 */
public class LivePlayerInfoPanel extends JPanel {

    private MainWindow parent;

    private JScrollPane jspLivePlayerInfo;
    private JTable playerTable;
    private PlayerCountPanel pcp;
    //private MyTableModel mytmodel;

    private DefaultTableModel dtm;

    public LivePlayerInfoPanel(MainWindow owner) {
        super();
        this.parent = owner;

        //this.setLayout(new BorderLayout());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Live Player Info"));
        final PlayerNameCellRenderer playerRenderer = new PlayerNameCellRenderer();
        final CenterCellRenderer centerRenderer = new CenterCellRenderer();
        playerTable = new JTable() {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 2) {
                    return playerRenderer;
                }
                else
                    return centerRenderer;
            }
        };
        final JTableHeader header = playerTable.getTableHeader();
        final Font boldFont = header.getFont().deriveFont(Font.BOLD);
        final TableCellRenderer headerRenderer = header.getDefaultRenderer();
        header.setDefaultRenderer( new TableCellRenderer() {
            public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
                Component comp = headerRenderer.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
                comp.setFont(boldFont);

                if (column < 2)
                    ((JLabel)comp).setHorizontalAlignment(SwingConstants.CENTER);
                else
                    ((JLabel)comp).setHorizontalAlignment(SwingConstants.LEADING);

                return comp;
            }
        });

        playerTable.setShowGrid(false);
        playerTable.getColumnModel().setColumnMargin(0);
        playerTable.setSelectionMode(0);
        playerTable.setAutoCreateRowSorter(true);
        //mytmodel = new MyTableModel();
        dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int col) {
                return false; // Disallow the editing of any cell.
            }
        };
        dtm.addColumn("Score");
        dtm.addColumn("Ping");
        dtm.addColumn("Name");
        playerTable.setModel(dtm);

        jspLivePlayerInfo = new JScrollPane(playerTable);
        jspLivePlayerInfo.getViewport().setBackground(Color.WHITE);
        //jspLivePlayerInfo.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.GRAY));
        //playerTable.setFillsViewportHeight(true); // Makes table white bg fill entire table


        this.pcp = new PlayerCountPanel();
        pcp.setPreferredSize(new Dimension(300, 30));
        //pcp.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.GRAY));
        
        //this.add(jspLivePlayerInfo, BorderLayout.NORTH);
        //this.add(pcp, BorderLayout.SOUTH);
        this.add(jspLivePlayerInfo);
        this.add(pcp);

    }

    public void fireItUp() {
        String foo = "";
        try {
            Server server = (Server)this.parent.comboServerList.getSelectedItem();
            BowserQuery q = new BowserQuery(server);
            foo = q.getstatus();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.print("Error: "+e.getMessage());
        }
        this.playerTable.setModel(getPlayerDTM(foo));
        ColumnResizer.adjustColumnPreferredWidths(this.playerTable);
    }

    public DefaultTableModel getPlayerDTM(String input) {
        this.dtm.getDataVector().removeAllElements();
        String[] lines = input.split("\\n");
        for (int i=1; i<lines.length; i++) {
            //String[] lineSplit = lines[i].split(" ");
            String[] lineSplit = this.breakLines(lines[i]);

            this.dtm.addRow(lineSplit);
        }
        this.pcp.setNumPlayers(lines.length - 1);
        return this.dtm;
    }

    private String[] breakLines(String line) {
        String[] thisLine = new String[3];
        
        String scorePing = line.substring(0, line.indexOf('\"'));
        String[] tempSplit = scorePing.split(" ");

        thisLine[0] = tempSplit[0]; // Score
        thisLine[1] = tempSplit[1]; // Ping
        thisLine[2] = line.substring(line.indexOf('\"') + 1, line.lastIndexOf('\"')).trim();

        return thisLine;
    }

    class HeaderCellRenderer extends JLabel implements TableCellRenderer {
        public HeaderCellRenderer() {
            this.setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {

            String text = (String)value;
            this.setText(text);
            this.setFont(this.getFont().deriveFont(Font.BOLD));
            if (column != 2)
                this.setHorizontalAlignment(SwingConstants.CENTER);

            return this;
        }
    }

    class CenterCellRenderer extends JLabel implements TableCellRenderer {
        public CenterCellRenderer() {
            this.setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (isSelected)
                this.setBackground(table.getSelectionBackground());
            else
                this.setBackground(table.getBackground());

            String text = (String)value;
            this.setText(text);
            this.setHorizontalAlignment(SwingConstants.CENTER);
            return this;
        }
    }

    class PlayerNameCellRenderer extends JLabel implements TableCellRenderer {
        private boolean isSelected = false;

        public PlayerNameCellRenderer() { setOpaque(true); }

        public Component getTableCellRendererComponent(JTable table, 
                Object name, boolean isSelected, boolean hasFocus,
                int row, int column) {
            this.isSelected = isSelected;
            String playerName = (String)name;
            this.setText(playerName);
            if (isSelected)
                this.setBackground(table.getSelectionBackground());
            else
                this.setBackground(table.getBackground());
            return this;
        }

        private boolean isValidQuakeNumber(char testChar) {
            char[] validQuakeNumbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

            for (int i = 0; i < validQuakeNumbers.length; i++) {
                if (validQuakeNumbers[i] == testChar)
                    return true;
            }

            return false;
        }

        private Color getQuakeColor(char number) {
            if (isSelected)
                return Color.WHITE;

            switch(number) {
                case '9':
                case '1':
                    return Color.RED;
                case '2':
                    return Color.GREEN.darker();
                case '3':
                    return Color.YELLOW.darker();
                case '4':
                    return Color.BLUE;
                case '5':
                    return Color.CYAN.darker();
                case '6':
                    return Color.PINK.darker();
                case '7':
                    return Color.GRAY;
                case '0':
                case '8':
                default:
                    return Color.BLACK;
            }
        }

        private int paintPart(String portion, Color color, int location, Graphics g) {
            java.awt.geom.Rectangle2D bounds = g.getFontMetrics().getStringBounds(portion, g);
            g.setColor(color);
            int yLoc = (int)(this.getHeight() / 2) + (int)(bounds.getHeight() / 2);

            g.drawString(portion, location, yLoc - 2);

            return location + (int)(bounds.getWidth());
        }

        private void paintBackground(Graphics g) {
            g.setColor(this.getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        @Override
        public void paintComponent(Graphics g) {
            paintBackground(g);
            String playerName = this.getText();
            String temp = "";
            char color = '0';
            int xLoc = 2;
            
            for (int i = 0; i < playerName.length(); i++) {
                if (playerName.charAt(i) == '^' && this.isValidQuakeNumber(playerName.charAt(i + 1))) {
                    xLoc = paintPart(temp, getQuakeColor(color), xLoc, g);
                    color = playerName.charAt(i + 1);
                    temp = ""; // Clear temp for the next series of colored text
                    i++; // Jump past the color number
                }
                else
                    temp = temp + playerName.charAt(i);
            }
            paintPart(temp, getQuakeColor(color), xLoc, g);
        }
    }

    /*class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Score", "Ping", "Name"};
        private Object[][] data = {
            {"10", "11", "Pyrite"}
        };

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
        * Don't need to implement this method unless your table's
        * editable.
        *
        @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            return false;
            //if (col < 2) {
            //    return false;
            //} else {
            //    return true;
            //}
        }

        /*
        * Don't need to implement this method unless your table's
        * data can change.
        *
        @Override
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

        public void setData(String input) {
            // set the data
            String[] lines = input.split("\\n");
            for (int i=1; i<lines.length; i++) {
                String[] lineSplit = lines[i].split(" ");
                //this.data.
            }
            fireTableDataChanged();
        }

    }*/
}