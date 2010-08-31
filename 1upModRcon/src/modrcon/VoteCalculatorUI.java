/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VoteCalculatorUI.java
 *
 * Created on Aug 15, 2010, 10:43:13 PM
 */

package modrcon;
import java.awt.*;
/**
 *
 * @author Jacob
 */
public class VoteCalculatorUI extends javax.swing.JFrame {

    /** Creates new form VoteCalculatorUI */
    public VoteCalculatorUI() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logoPanel = new LogoPanel(1);
        votePanel = new javax.swing.JPanel();
        scrollVote = new javax.swing.JScrollPane();
        tableVote = new javax.swing.JTable();
        lblSetAllowVote = new javax.swing.JLabel();
        lblSelectAll = new javax.swing.JLabel();
        lblUnselectAll = new javax.swing.JLabel();
        spinnerVote = new javax.swing.JSpinner();
        btnSend = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("1up ModRcon - Allow Vote Calculator");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout logoPanelLayout = new javax.swing.GroupLayout(logoPanel);
        logoPanel.setLayout(logoPanelLayout);
        logoPanelLayout.setHorizontalGroup(
            logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );
        logoPanelLayout.setVerticalGroup(
            logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );

        votePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Allow Vote Calculator"));

        tableVote.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "reload", "1"},
                {null, "restart", "2"},
                {null, "map", "4"},
                {null, "nextmap", "8"},
                {null, "kick/clientkick", "16"},
                {null, "swapTeams", "32"},
                {null, "shuffleTeams", "64"},
                {null, "g_friendlyFire", "128"},
                {null, "g_followStrict", "256"},
                {null, "g_gameType", "512"},
                {null, "g_waveRespawns", "1024"},
                {null, "timeLimit", "2048"},
                {null, "fragLimit", "4096"},
                {null, "captureLimit", "8192"},
                {null, "g_respawnDelay", "16384"},
                {null, "g_redWaveRespawnDelay", "32768"},
                {null, "g_blueWaveRespawnDelay", "65536"},
                {null, "g_bombExplodeTime", "131072"},
                {null, "g_BombDefuseTime", "262144"},
                {null, "g_survivorRoundTime", "524288"},
                {null, "g_captureScoreTime", "1048576"},
                {null, "g_warmup", "2097152"},
                {null, "g_matchMode", "4194304"},
                {null, "g_timeouts", "8388608"},
                {null, "g_timeoutLength", "16777216"},
                {null, "exec", "33554432"},
                {null, "g_swapRoles", "67108864"},
                {null, "g_maxRounds", "134217728"},
                {null, "g_gear", "268435456"},
                {null, "cyclemap", "536870912"}
            },
            new String [] {
                "", "Vote", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableVote.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        tableVote.getTableHeader().setResizingAllowed(false);
        scrollVote.setViewportView(tableVote);

        lblSetAllowVote.setText("set g_AllowVote");

        lblSelectAll.setText("All");

        lblUnselectAll.setText("None");

        spinnerVote.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1073741823, 1));

        javax.swing.GroupLayout votePanelLayout = new javax.swing.GroupLayout(votePanel);
        votePanel.setLayout(votePanelLayout);
        votePanelLayout.setHorizontalGroup(
            votePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(votePanelLayout.createSequentialGroup()
                .addGroup(votePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(votePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollVote, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, votePanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblSetAllowVote)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerVote, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSelectAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblUnselectAll)
                        .addGap(8, 8, 8)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        votePanelLayout.setVerticalGroup(
            votePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(votePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scrollVote, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(votePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSetAllowVote)
                    .addGroup(votePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSelectAll)
                        .addComponent(lblUnselectAll))
                    .addComponent(spinnerVote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        btnSend.setText("Close");
        btnSend.setMaximumSize(new java.awt.Dimension(107, 23));
        btnSend.setMinimumSize(new java.awt.Dimension(107, 23));
        btnSend.setPreferredSize(new java.awt.Dimension(107, 23));

        btnClose.setText("Send To Server");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(votePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(logoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(votePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tableVote.getTableHeader().setPreferredSize(new Dimension(tableVote.getTableHeader().getWidth(),20));
        tableVote.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableVote.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableVote.getColumnModel().getColumn(2).setPreferredWidth(100);
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VoteCalculatorUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel lblSelectAll;
    private javax.swing.JLabel lblSetAllowVote;
    private javax.swing.JLabel lblUnselectAll;
    private javax.swing.JPanel logoPanel;
    private javax.swing.JScrollPane scrollVote;
    private javax.swing.JSpinner spinnerVote;
    private javax.swing.JTable tableVote;
    private javax.swing.JPanel votePanel;
    // End of variables declaration//GEN-END:variables

}
