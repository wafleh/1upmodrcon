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
    private MyTableModel mytmodel;

    private DefaultTableModel dtm;

    public LivePlayerInfoPanel(MainWindow owner) {
        super();
        this.parent = owner;

        //this.setLayout(new BorderLayout());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Live Player Info"));

        playerTable = new JTable();
        playerTable.setShowGrid(false);
        playerTable.getColumnModel().setColumnMargin(0);
        mytmodel = new MyTableModel();
        dtm = new DefaultTableModel();
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
            BowserQuery q = new BowserQuery(server.getIP(), server.getPortAsInteger());
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
            String[] lineSplit = lines[i].split(" ");
            // Just here to remove the quotes from player names.
            //lineSplit[2] = lineSplit[2].substring(1, lineSplit[2].length() - 1);

            this.dtm.addRow(lineSplit);
        }
        this.pcp.setNumPlayers(lines.length - 1);
        return this.dtm;
    }

    class MyTableModel extends AbstractTableModel {
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
        */
        @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
        * Don't need to implement this method unless your table's
        * data can change.
        */
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

    }


}
