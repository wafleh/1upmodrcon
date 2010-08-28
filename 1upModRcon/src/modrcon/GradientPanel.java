package modrcon;

import java.awt.*;
import javax.swing.*;

/**
 * GradientPanel is a class with a gradient background.
 * Put your non-opaque objects over it and enjoy.
 *
 * @author Pyrite[1up]
 */
public class GradientPanel extends JPanel {

    private Color startColor;
    private Color endColor;

    /* Default Constructor */
    public GradientPanel() {
        // Top Color   : #860703
        // Bottom Color: #DA542F
        this( new Color(0x860703) , new Color(0xDA542F) );
    }

    /**
     * Constructor supplying a color.
     *
     * @param startColor
     * @param endColor
     */
    public GradientPanel( Color startColor , Color endColor ) {
        super();
        this.startColor = startColor;
        this.endColor = endColor;

        ImageIcon logo = new ImageIcon();
        try {
            logo = new ImageIcon(getClass().getResource("/modrcon/resources/1upModRconLogo.png"));
        }
        catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
        JLabel logoLabel = new JLabel(logo);
        this.add(logoLabel);

    }

    @Override protected void paintComponent( Graphics g ) {
        super.paintComponent( g );
        int panelHeight = getHeight();
        int panelWidth = getWidth();
        GradientPaint gradientPaint = new GradientPaint( panelWidth / 2 , 0 , startColor , panelWidth / 2 , panelHeight , endColor );
        if( g instanceof Graphics2D ) {
            Graphics2D graphics2D = (Graphics2D)g;
            graphics2D.setPaint( gradientPaint );
            graphics2D.fillRect( 0 , 0 , panelWidth , panelHeight );
        }
    }

}