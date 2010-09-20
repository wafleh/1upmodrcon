package modrcon;

import javax.swing.*;
import java.io.File;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * The Main Class of 1up ModRcon.
 * 
 * @author Pyrite[1up]
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Load Settings
        final PropertyManager pm = new PropertyManager();
        String lafName = pm.getLookAndFeel();
        String lafClass = "";

        if (lafName == null) {
            lafClass = UIManager.getSystemLookAndFeelClassName();
        }
        else {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (info.getName().equals(lafName)) {
                    lafClass = info.getClassName();
                }
            }
            if (lafClass.equals("")) {
                lafClass = UIManager.getSystemLookAndFeelClassName();
            }
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            // Set the OS's Native Look and Feel
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(lafClass);
        }
        catch (Exception e) {
            System.out.println("Error: Was Unable to Set the Look and Feel");
            System.out.println(e.getMessage());
        }     

        // First see if servers.xml exists, if not, create it, and run ServerSetupWizard.
        File f = new File("servers.db");
        if (f.exists()) {
            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    MainWindow mui = new MainWindow();
                    mui.setVisible(true);
                }
            });
        }
        else {
            new ServerSetupWizard();
        }
        
    }

}
