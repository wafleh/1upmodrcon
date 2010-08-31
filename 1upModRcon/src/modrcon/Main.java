package modrcon;

import java.awt.Color;
import javax.swing.*;

/**
 * The Main Class of 1up ModRcon.
 * 
 * @author Zhalix[1up]
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
        PropertyManager pm = new PropertyManager();

        // Start Main UI
        MainWindow mui = new MainWindow();
        //mui.setConsoleBackground(Color.decode(pm.getConsoleBGColor()));
        //mui.setConsoleForeground(Color.decode(pm.getConsoleFGColor()));
        mui.setVisible(true);

        mui.controlPanel.printSize();

    }

}
