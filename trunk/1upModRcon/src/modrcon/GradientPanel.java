package modrcon;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class GradientPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private GradientPanel gradientPanel = null;

    /**
    * This is the default constructor
    */
    public GradientPanel() {
        super();
        initialize();
    }

    /**
    * This method initializes this
    *
    * @return void
    */
    private void initialize() {
        this.setSize( 300 , 200 );
        this.setContentPane( getJContentPane() );
        this.setTitle( "JFrame" );
    }

    /**
    * This method initializes jContentPane
    *
    * @return javax.swing.JPanel
    */
    private JPanel getJContentPane() {
        if( jContentPane == null ) {
            jContentPane = new JPanel();
            jContentPane.setLayout( new BorderLayout() );
            jContentPane.add(getGradientPanel(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    /**
    * This method initializes gradientPanel
    *
    * @return gradient.GradientPanel
    */
    private GradientPanel getGradientPanel() {
        if( gradientPanel == null )
        {
        gradientPanel = new GradientPanel();
        }
        return gradientPanel;
    }

    public static void main(String args[]) {
        new GradientPanel();
    }

}
