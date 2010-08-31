package modrcon;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.FlowLayout;

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
        // Top Color   : RGB (131, 4, 1),   Hex: 830401
        // Bottom Color: RGB (221, 87, 49), Hex: DD5731
        super( new Color(0x830401) , new Color(0xDD5731) );

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

}
