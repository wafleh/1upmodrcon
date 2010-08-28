/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AboutUI.java
 *
 * Created on Aug 15, 2010, 3:21:50 PM
 */

package modrcon;

/**
 *
 * @author Jacob
 */
public class AboutUI extends javax.swing.JDialog {

    /** Creates new form AboutUI */
    public AboutUI() {
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

        javax.swing.JTabbedPane jtpAbout = new javax.swing.JTabbedPane();
        AboutPanel = new javax.swing.JPanel();
        lblAppName = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        lblWebsite = new javax.swing.JLabel();
        lblBetaTesters = new javax.swing.JLabel();
        lblAppNameAtt = new javax.swing.JLabel();
        lblVersionAtt = new javax.swing.JLabel();
        lblWebsiteAtt = new javax.swing.JLabel();
        lblCopyrightMushroom = new javax.swing.JLabel();
        lblBetaTestersAtt = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblCopyrightTesla = new javax.swing.JLabel();
        ThirdPartyPanel = new javax.swing.JPanel();
        lbl3rdParties = new javax.swing.JLabel();
        lblProjectJedi = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTMSSoftware = new javax.swing.JLabel();
        lblProjectJediURL = new javax.swing.JLabel();
        lblTMSSoftwareURL = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();
        lblLogo = new javax.swing.JLabel();
        gradientPanel1 = new modrcon.GradientPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("1up ModRcon - About");
        setModal(true);
        setResizable(false);

        lblAppName.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        lblAppName.setLabelFor(lblAppName);
        lblAppName.setText("Application Name:");
        AboutPanel.add(lblAppName);

        lblVersion.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        lblVersion.setText("Version:");
        AboutPanel.add(lblVersion);

        lblWebsite.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        lblWebsite.setText("Website:");
        AboutPanel.add(lblWebsite);

        lblBetaTesters.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        lblBetaTesters.setText("Beta Testers:");
        AboutPanel.add(lblBetaTesters);

        lblAppNameAtt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblAppNameAtt.setLabelFor(lblVersion);
        lblAppNameAtt.setText("1up ModRcon");
        AboutPanel.add(lblAppNameAtt);

        lblVersionAtt.setFont(new java.awt.Font("Arial", 0, 11));
        lblVersionAtt.setText("1.5.1.0");
        AboutPanel.add(lblVersionAtt);

        lblWebsiteAtt.setFont(new java.awt.Font("Arial", 0, 11));
        lblWebsiteAtt.setText("www.1upclan.info");
        AboutPanel.add(lblWebsiteAtt);

        lblCopyrightMushroom.setFont(new java.awt.Font("Arial", 0, 11));
        lblCopyrightMushroom.setText("The 1up Mushroom is Copyright © 2010 Nintendo Corp of America");
        AboutPanel.add(lblCopyrightMushroom);

        lblBetaTestersAtt.setFont(new java.awt.Font("Arial", 0, 11));
        lblBetaTestersAtt.setText("RonaldLee[1up]");
        AboutPanel.add(lblBetaTestersAtt);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 11));
        AboutPanel.add(jLabel15);

        lblCopyrightTesla.setFont(new java.awt.Font("Arial", 0, 11));
        lblCopyrightTesla.setText("1up ModRcon is Copyright © 2010 Tesla[1up]. All Rights Reserved.");
        AboutPanel.add(lblCopyrightTesla);

        jtpAbout.addTab("About", AboutPanel);

        lbl3rdParties.setFont(new java.awt.Font("Arial", 0, 11));
        lbl3rdParties.setText("<html>Portions of this software use components developed by the following 3rd Parties:</html>");

        lblProjectJedi.setFont(new java.awt.Font("Arial", 0, 11));
        lblProjectJedi.setText("Project JEDI");

        jLabel3.setText("jLabel3");

        jLabel4.setText("jLabel4");

        lblTMSSoftware.setFont(new java.awt.Font("Arial", 0, 11));
        lblTMSSoftware.setText("TMS Software");

        lblProjectJediURL.setFont(new java.awt.Font("Arial", 0, 11));
        lblProjectJediURL.setForeground(new java.awt.Color(0, 51, 255));
        lblProjectJediURL.setText("http://jvcl.delphi-jedi.org");

        lblTMSSoftwareURL.setFont(new java.awt.Font("Arial", 0, 11));
        lblTMSSoftwareURL.setForeground(new java.awt.Color(0, 51, 255));
        lblTMSSoftwareURL.setText("http://tmssoftware.com");

        javax.swing.GroupLayout ThirdPartyPanelLayout = new javax.swing.GroupLayout(ThirdPartyPanel);
        ThirdPartyPanel.setLayout(ThirdPartyPanelLayout);
        ThirdPartyPanelLayout.setHorizontalGroup(
            ThirdPartyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThirdPartyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl3rdParties, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(169, 169, 169)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThirdPartyPanelLayout.createSequentialGroup()
                .addContainerGap(426, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(102, 102, 102))
            .addGroup(ThirdPartyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ThirdPartyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTMSSoftware)
                    .addComponent(lblProjectJedi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThirdPartyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProjectJediURL)
                    .addComponent(lblTMSSoftwareURL))
                .addContainerGap(223, Short.MAX_VALUE))
        );
        ThirdPartyPanelLayout.setVerticalGroup(
            ThirdPartyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThirdPartyPanelLayout.createSequentialGroup()
                .addGroup(ThirdPartyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThirdPartyPanelLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel3))
                    .addGroup(ThirdPartyPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl3rdParties, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThirdPartyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjectJedi)
                    .addComponent(lblProjectJediURL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ThirdPartyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTMSSoftware)
                    .addComponent(lblTMSSoftwareURL))
                .addGap(53, 53, 53)
                .addComponent(jLabel4)
                .addGap(39, 39, 39))
        );

        jtpAbout.addTab("Third Parties", ThirdPartyPanel);

        btnOk.setText("Ok");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        lblLogo.setBackground(new java.awt.Color(255, 102, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblLogo)
                .addContainerGap(380, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(146, Short.MAX_VALUE))
            .addComponent(gradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtpAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogo)
                    .addComponent(gradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtpAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOk)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AboutUI().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AboutPanel;
    private javax.swing.JPanel ThirdPartyPanel;
    private javax.swing.JButton btnOk;
    private modrcon.GradientPanel gradientPanel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lbl3rdParties;
    private javax.swing.JLabel lblAppName;
    private javax.swing.JLabel lblAppNameAtt;
    private javax.swing.JLabel lblBetaTesters;
    private javax.swing.JLabel lblBetaTestersAtt;
    private javax.swing.JLabel lblCopyrightMushroom;
    private javax.swing.JLabel lblCopyrightTesla;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblProjectJedi;
    private javax.swing.JLabel lblProjectJediURL;
    private javax.swing.JLabel lblTMSSoftware;
    private javax.swing.JLabel lblTMSSoftwareURL;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JLabel lblVersionAtt;
    private javax.swing.JLabel lblWebsite;
    private javax.swing.JLabel lblWebsiteAtt;
    // End of variables declaration//GEN-END:variables

}
