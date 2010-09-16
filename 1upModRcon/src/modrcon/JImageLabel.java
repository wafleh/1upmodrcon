package modrcon;

import javax.swing.*;

/**
 * A JLabel with a single image for the Tooltip.
 *
 * @author Pyrite
 */
public class JImageLabel extends JLabel {

    private String resourcePath;

    /**
     * Constructs a JImageLabel.
     *
     * @param text The text for the JLabel.
     * @param resourcePath The path to the image for the tooltip.
     */
    public JImageLabel(String text, String resourcePath) {
        super(text);
        this.resourcePath = resourcePath;
        this.setToolTipText("");
    }

    public void setResourcePath(String path) {
        this.resourcePath = path;
    }

    @Override
    public JToolTip createToolTip() {
        JToolTipWithIcon tip = new JToolTipWithIcon(new ImageIcon(getClass().getResource(resourcePath)));
        tip.setComponent(this);
        return tip;
    }

}
