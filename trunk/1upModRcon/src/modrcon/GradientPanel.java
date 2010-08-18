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
        this( Color.RED , Color.BLACK );
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
    }

    @Override protected void paintComponent( Graphics g ) {
        super.paintComponent( g );
        int panelHeight = getHeight();
        int panelWidth = getWidth();
        GradientPaint gradientPaint = new GradientPaint( 0 , 0 , startColor , panelWidth , panelHeight , endColor );
        if( g instanceof Graphics2D ) {
            Graphics2D graphics2D = (Graphics2D)g;
            graphics2D.setPaint( gradientPaint );
            graphics2D.fillRect( 0 , 0 , panelWidth , panelHeight );
        }
    }

}