package modrcon;
import java.awt.*;
import javax.swing.text.*;

/** Adds and updates the static styles as well as handling new style creation.
 * Adds the static Quake 3 Color styles as well as the "default" text color
 * style and a "command" color style for the purpose of displaying commands
 * entered in a different color.
 * This class also contains the methods to allow the programmer to Dynamically
 * create a new style and/or update a specific style at runtime.
 * @author izuriel
 * @version 1.0
 */
public class DocumentStyler {
    /** *** This Method should only be called from the constructor ***
     * This Method should only be called from the constructor as it creates
     * each of the built in styles and adds them to the document. There is an
     * updateStyles that will update the built in styles which should be used
     * when the Foreground Color, Background Color, or Font change.
     * @param documentToStyle The ConsoleTextPane's StyledDocument the styles will be stored in.
     * @param consoleFont The ConsoleTextPane's Font to print the styles in.
     * @param defaultForeground The ConsoleTextPane's Foreground color for the purpose of setting the default style.
     * @param defaultBackground The ConsoleTextPane's Background color so that each style has the same background as the console.
     * @return The modified StyledDocument, to which all printing is done.
     */
    public static StyledDocument styleDocument(StyledDocument documentToStyle,
            Font consoleFont, Color defaultForeground) {
        Style parentStyle = StyleContext.getDefaultStyleContext()
                .getStyle(StyleContext.DEFAULT_STYLE);

        // Default Style
        DocumentStyler.addNewStyle(documentToStyle, "default", consoleFont,
                defaultForeground);
        
        // Command Style
        DocumentStyler.addNewStyle(documentToStyle, "command", consoleFont,
                Color.WHITE);

        // =====================================================================
        // QUAKE 3 Console Styles
        // =====================================================================
        // ^0 - BLACK
        DocumentStyler.addNewStyle(documentToStyle, "0", consoleFont,
                Color.GRAY.darker());

        // ^1 - RED
        DocumentStyler.addNewStyle(documentToStyle, "1", consoleFont,
                Color.RED);

        // ^2 - GREEN
        DocumentStyler.addNewStyle(documentToStyle, "2", consoleFont,
                Color.GREEN);

        // ^3 - YELLOW
        DocumentStyler.addNewStyle(documentToStyle, "3", consoleFont,
                Color.YELLOW);

        // ^4 - Blue
        DocumentStyler.addNewStyle(documentToStyle, "4", consoleFont,
                Color.BLUE);

        // ^5 - Cyan
        DocumentStyler.addNewStyle(documentToStyle, "5", consoleFont,
                Color.CYAN);

        // ^6 - Pink
        DocumentStyler.addNewStyle(documentToStyle, "6", consoleFont,
                Color.PINK);

        // ^7 - White
        DocumentStyler.addNewStyle(documentToStyle, "7", consoleFont,
                Color.WHITE);

        // ^8 - BLACK
        DocumentStyler.addNewStyle(documentToStyle, "8", consoleFont,
                Color.GRAY.darker());

        // ^9 - RED
        DocumentStyler.addNewStyle(documentToStyle, "9", consoleFont,
                Color.RED);
        
        return documentToStyle;
    }
    /** Used at Runtime to add a new style to the StyledDocument for use.
     * This method is used by styleDocument() and by at runtime to add styles
     * to the StyledDocument. <strong>Be warned that any styles added at
     * runtime (i.e. those that are not built in to this class) MUST be updated
     * manually if any changes occur to font/background color on the Console.
     * </strong>
     * @param documentToUpdate The StyledDocument the new Style will be added to.
     * @param styleName The name you want to give the style, used to access it for updating or printing.
     * @param styleFont The Font you want to set for this style, to go with default pass the getFont() from the console.
     * @param styleBackground This can be set to the default background color (getBackground()) or you can change it to highlight the text.
     */
    public static void addNewStyle(StyledDocument documentToUpdate, 
            String styleName, Font styleFont, Color styleForeground) {
        Style parentStyle = StyleContext.getDefaultStyleContext()
                .getStyle(StyleContext.DEFAULT_STYLE);

        Style temp = documentToUpdate.addStyle(styleName, parentStyle);
        setStyleFont(temp, styleFont);
        StyleConstants.setForeground(temp, styleForeground);
    }
    /** Used at Runtime to add a new style to the StyledDocument for use.
     * This method is used by styleDocument() and by at runtime to add styles
     * to the StyledDocument. Unlike other styles, this style is added with a 
     * background color which will "highlight" the text. <strong>Be warned that
     * any styles added at runtime (i.e. those that are not built in to this 
     * class) MUST be updated manually if any changes occur to font/background
     * color on the Console.</strong>
     * @param documentToUpdate The StyledDocument the new Style will be added to.
     * @param styleName The name you want to give the style, used to access it for updating or printing.
     * @param styleFont The Font you want to set for this style, to go with default pass the getFont() from the console.
     * @param styleForeground The text color for the style.
     * @param styleBackground This can be set to the default background color (getBackground()) or you can change it to highlight the text.
     */
    public static void addNewStyleWithHighlight(StyledDocument documentToUpdate,
            String styleName, Font styleFont, Color styleForeground,
            Color styleBackground) {
        Style parentStyle = StyleContext.getDefaultStyleContext()
                .getStyle(StyleContext.DEFAULT_STYLE);

        Style temp = documentToUpdate.addStyle(styleName, parentStyle);
        setStyleFont(temp, styleFont);
        StyleConstants.setForeground(temp, styleForeground);
        StyleConstants.setBackground(temp, styleBackground);
    }
    /** Makes changes to a Style that is already in the StyledDocument.
     * This method takes the give StyleName and changes the settings for that
     * style based on the data passed. This method is used by updateStyles for
     * all the default styles, and this method MUST be used at runtime for any
     * special and/or added styles that need to be updated.
     * @param documentToUpdate The StyledDocument the Style changes should apply to.
     * @param styleName The name of the Style that you wish to update.
     * @param newFont The Font you want the style to use, default would be getFont()
     * @param newForeground The Foreground color to update/change/set for this style.
     * @return Returns the StyledDocument after the changes have been made to the specified Style.
     */
    public static StyledDocument updateSpecificStyle(
            StyledDocument documentToUpdate, String styleName, Font newFont,
            Color newForeground) {
        Style temp = documentToUpdate.getStyle(styleName);
        setStyleFont(temp, newFont);
        StyleConstants.setForeground(temp, newForeground);

        return documentToUpdate;
    }
    /** Makes changes to a Style that is already in the StyledDocument.
     * This method takes the give StyleName and changes the settings for that
     * style based on the data passed. This method is used by updateStyles for
     * all the default styles, and this method MUST be used at runtime for any
     * special and/or added styles that need to be updated. *** This is used to
     * update a style that has a background color ***
     * @param documentToUpdate The StyledDocument the Style changes should apply to.
     * @param styleName The name of the Style that you wish to update.
     * @param newFont The Font you want the style to use, default would be getFont()
     * @param newForeground The Foreground color to update/change/set for this style.
     * @param newBackground The Background color to update/change/set for this style.
     * @return Returns the StyledDocument after the changes have been made to the specified Style.
     */
    public static StyledDocument updateSpecificStyleWithHighlight(
            StyledDocument documentToUpdate, String styleName, Font newFont,
            Color newForeground, Color newBackground) {
        Style temp = documentToUpdate.getStyle(styleName);
        setStyleFont(temp, newFont);
        StyleConstants.setForeground(temp, newForeground);
        StyleConstants.setBackground(temp, newBackground);

        return documentToUpdate;
    }
    /** Updates the Foreground and Background colors of a specific Style.
     * This method is here for those that need to update the foreground and/or
     * background color of a Style and leave the Font as whatever is already
     * defined.
     * @param documentToUpdate The StyledDocument containing the Style you wish to update.
     * @param styleName The name of the Style that needs to be updated.
     * @param newForeground The Foreground color that will be set for this Style.
     * @param newBackground The Background color that will be set for this STyle.
     * @return Returns the updated StyledDocument after the changes to the given Style are made.
     */
    public static StyledDocument updateSpecificStyleColor(
            StyledDocument documentToUpdate, String styleName,
            Color newForeground) {
        Style temp = documentToUpdate.getStyle(styleName);
        StyleConstants.setForeground(temp, newForeground);

        return documentToUpdate;
    }
    /** Updates the Font of a specific Style.
     * This method is here for those that just need to make changes to a Styles
     * Font. This method only changes the Styles Font and has no effect on
     * Foreground color or Background color.
     * @param documentToUpdate The StyledDocument containing the Style that needs to be updated.
     * @param styleName The name of the Style you wish the Font changes to apply to.
     * @param newFont The Font that will be applied to this Style.
     * @return Returns the updated StyledDocument after the changes to the given Style are made.
     */
    public static StyledDocument updateSpecificStyleFont(
            StyledDocument documentToUpdate, String styleName, Font newFont) {
        Style temp = documentToUpdate.getStyle(styleName);
        setStyleFont(temp, newFont);

        return documentToUpdate;
    }
    /** Sets the Font options for a style so that it prints in newFont
     * This method is a helper, it sets the styles Font Family, Font Size, and
     * whether or not the Font is Bold and/or Italic.
     * @param style The Style object in which the Font information will be stored.
     * @param newFont The newFont that needs to be set to the Style object.
     */
    private static void setStyleFont(Style style, Font newFont) {
        StyleConstants.setFontFamily(style, newFont.getFamily());
        StyleConstants.setFontSize(style, newFont.getSize());
        StyleConstants.setBold(style, newFont.isBold());
        StyleConstants.setItalic(style, newFont.isItalic());
    }
    /** *** This Method should only be called outside the constructor ***
     * This Method should only be called outside the constructor and only after
     * styleDocument() has been called because it updates the styles that have
     * already been added to the list.
     * <strong>*** This does not update styles manually added from outside, those must
     * be updated seperately using updateSpecificStyle() ***</strong>
     * @param documentToUpdate The ConsoleTextPane's StyledDocument the styles are stored in.
     * @param consoleFont The ConsoleTextPane's Font to print the styles in.
     * @param defaultForeground The ConsoleTextPane's Foreground color for the purpose of setting the default style.
     * @param defaultBackground The ConsoleTextPane's Background color so that each style has the same background as the console.
     * @return The modified StyledDocument, to which all printing is done.
     */
    public static StyledDocument updateStyles(StyledDocument documentToUpdate,
            Font consoleFont, Color defaultForeground) {
        // Default Style
        DocumentStyler.updateSpecificStyle(documentToUpdate, "default",
                consoleFont, defaultForeground);

        // Command Style
        DocumentStyler.updateSpecificStyle(documentToUpdate, "command",
                consoleFont, Color.WHITE);

        // =====================================================================
        // QUAKE 3 Console Styles
        // =====================================================================
        // ^0 - BLACK
        DocumentStyler.updateSpecificStyle(documentToUpdate, "0", consoleFont,
                Color.GRAY.darker());

        // ^1 - RED
        DocumentStyler.updateSpecificStyle(documentToUpdate, "1", consoleFont,
                Color.RED);

        // ^2 - GREEN
        DocumentStyler.updateSpecificStyle(documentToUpdate, "2", consoleFont,
                Color.GREEN);

        // ^3 - YELLOW
        DocumentStyler.updateSpecificStyle(documentToUpdate, "3", consoleFont,
                Color.YELLOW);

        // ^4 - Blue
        DocumentStyler.updateSpecificStyle(documentToUpdate, "4", consoleFont,
                Color.BLUE);

        // ^5 - Cyan
        DocumentStyler.updateSpecificStyle(documentToUpdate, "5", consoleFont,
                Color.CYAN);

        // ^6 - Pink
        DocumentStyler.updateSpecificStyle(documentToUpdate, "6", consoleFont,
                Color.PINK);

        // ^7 - White
        DocumentStyler.updateSpecificStyle(documentToUpdate, "7", consoleFont,
                Color.WHITE);

        // ^8 - BLACK
        DocumentStyler.updateSpecificStyle(documentToUpdate, "8", consoleFont,
                Color.GRAY.darker());

        // ^9 - RED
        DocumentStyler.updateSpecificStyle(documentToUpdate, "9", consoleFont,
                Color.RED);

        return documentToUpdate;
    }
}
