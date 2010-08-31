package modrcon;

import java.awt.*;
import javax.swing.*;

/**
 * LogoPanel is a GradientPanel with predefined
 * colors that displays the 1up Logo.
 *
 * @author Pyrite[1up]
 */
public class LogoPanel extends GradientPanel {

    /** Left alignment of the 1up Logo. */
    public static final int LOGO_LEFT = 0;
    /** Center alignment of the 1up Logo. */
    public static final int LOGO_CENTER = 1;
    /** Right alignment of the 1up Logo. */
    public static final int LOGO_RIGHT = 2;

    /**
     * Constructs a new <code>LogoPanel</code> with the specified
     * alignment of the 1up Logo and a default 5-unit horizontal and vertical gap.
     * The value of the alignment argument must be one of
     * <code>LogoPanel.LOGO_LEFT</code>, <code>LogoPanel.LOGO_RIGHT</code>,
     * or <code>LogoPanel.LOGO_CENTER</code>.
     * @param direction The direction value.
     */
    public LogoPanel(int direction) {
        super(GradientPanel.HEADER_COLOR_START, GradientPanel.HEADER_COLOR_END);

        this.setLayout(new FlowLayout(direction));

        ImageIcon logo = new ImageIcon();
        try {
            logo = new ImageIcon(getClass().getResource("/modrcon/resources/1upModRconLogo.png"));
        }
        catch (Exception e) {
            System.out.println("Error Setting Logo in LogoPanel: "+e.getMessage());
        }
        JLabel logoLabel = new JLabel(logo);

        this.add(logoLabel);
    }

    public void doit(JComboBox jcb) {
        this.removeAll();
        this.setLayout(new BorderLayout());
        ImageIcon logo = new ImageIcon();
        try {
            logo = new ImageIcon(getClass().getResource("/modrcon/resources/1upModRconLogo.png"));
        }
        catch (Exception e) {
            System.out.println("Error Setting Logo in LogoPanel: "+e.getMessage());
        }
        JLabel logoLabel = new JLabel(logo);

        this.add(logoLabel, BorderLayout.WEST);
        this.add(jcb, BorderLayout.EAST);
        this.updateUI();

    }

}
