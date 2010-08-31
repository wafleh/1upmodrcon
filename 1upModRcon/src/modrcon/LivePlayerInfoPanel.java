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

    private JScrollPane jspLivePlayerInfo;
    private JTable playerTable;
    private PlayerCountPanel pcp;
    private MyTableModel mtm;

    public LivePlayerInfoPanel() {
        super();

        //this.setLayout(new BorderLayout());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Live Player Info"));



        playerTable = new JTable();
        mtm = new MyTableModel();
        playerTable.setModel(mtm);


        jspLivePlayerInfo = new JScrollPane(playerTable);
        //playerTable.setFillsViewportHeight(true); // Makes table white bg fill entire table


        this.pcp = new PlayerCountPanel();
        
        //this.add(jspLivePlayerInfo, BorderLayout.NORTH);
        //this.add(pcp, BorderLayout.SOUTH);
        this.add(jspLivePlayerInfo);
        this.add(pcp);
    }

    public void updateTable(ArrayList data) {
        this.mtm.setData(data);
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

        public void setData(ArrayList data) {
            // set the data
            fireTableDataChanged();
        }

    }


}
