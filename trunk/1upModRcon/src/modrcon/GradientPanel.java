package modrcon;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
* GradientPanel is a class with a gradient background, put your non-opaque objects over it and enjoy.
* @author Mohammad Abou-Basha
*
*/
public class GradientPanel extends JPanel
{

private Color startColor;

private Color endColor;

public GradientPanel()
{
this( Color.GRAY , Color.WHITE );
}

public GradientPanel( Color startColor , Color endColor )
{
super();
this.startColor = startColor;
this.endColor = endColor;
}

@Override
protected void paintComponent( Graphics g )
{
super.paintComponent( g );
int panelHeight = getHeight();
int panelWidth = getWidth();
GradientPaint gradientPaint = new GradientPaint( 0 , 0 , startColor , panelWidth , panelHeight , endColor );
if( g instanceof Graphics2D )
{
Graphics2D graphics2D = (Graphics2D)g;
graphics2D.setPaint( gradientPaint );
graphics2D.fillRect( 0 , 0 , panelWidth , panelHeight );
}
}
}