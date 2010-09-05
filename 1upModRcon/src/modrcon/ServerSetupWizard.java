package modrcon;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author pyrite
 */
public class ServerSetupWizard extends JFrame {


    private JLabel introText;
    private JPanel serverPanel;
    private JLabel infoIconLabel;
    private JPanel buttonPanel;

    private JButton btnSaveNow;
    private JButton btnCancel;

    public ServerSetupWizard() {
        super();
        this.setTitle("1up ModRcon - Setup (Final Step)");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Setup the Content Pane
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        introText = new JLabel("Congratulations, you have successfully installed 1up ModRcon. Please take a moment to setup your first server connection now. If you need help, hover your mouse over one of the information icons and a hint will appear for that item.");
        //introText.setFont("Tahoma", Font.BOLD, 11);

        cp.add(new GradientPanel(GradientPanel.WIZARD_COLOR_START, GradientPanel.WIZARD_COLOR_END), BorderLayout.NORTH);

        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new ServerSetupWizard();
    }



}
