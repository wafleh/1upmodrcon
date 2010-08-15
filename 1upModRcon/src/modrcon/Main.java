package modrcon;

import javax.swing.*;

/**
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
        // TODO code application logic here
        //new UI();
        new ServerAdmin().setVisible(true);
    }

}
