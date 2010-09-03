package modrcon;

import java.awt.Color;
import javax.swing.*;

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

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow mui = new MainWindow();
                mui.setConsoleBackground(Color.decode(pm.getConsoleBGColor()));
                mui.setConsoleForeground(Color.decode(pm.getConsoleFGColor()));
                mui.setVisible(true);
            }
        });
        
    }

}
