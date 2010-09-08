package modrcon;

import javax.swing.*;
import java.io.File;

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
        
        try {
            // Set the OS's Native Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.out.println("Error: Was Unable to Set the OS's Native Widget Theme");
            System.out.println(e.getMessage());
        }
        
        // Load Settings
        final PropertyManager pm = new PropertyManager();

        // First see if servers.xml exists, if not, create it, and run ServerSetupWizard.
        File f = new File("servers.xml");
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
