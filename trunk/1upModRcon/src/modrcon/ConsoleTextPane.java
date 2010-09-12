package modrcon;

import java.awt.Color;
import javax.swing.*;
import javax.swing.text.*;

/** Custom JTextPane for use outputting with colored styles.
 * ConsoleTextPane extends JTextPane for the purpose of outputting with
 * user specified Styles which are added at initialization and updated when the
 * ConsoleTextPane's foreground, background, or Font is changed so the styles
 * take into account these changes and the user continues to see wanted output.
 *
 * Styles predefine are -
 * Style Name   Foreground
 * -----------  ----------------
 * default      getForeground()
 * command      WHITE
 * 0            BLACK (GRAY.darker())
 * 1            RED
 * 2            GREEN
 * 3            YELLOW
 * 4            BLUE
 * 5            CYAN
 * 6            PINK
 * 7            WHITE
 * 8            BLACK (GRAY.darker())
 * @author izuriel
 */
public class ConsoleTextPane extends JTextPane {
    private StyledDocument styledDoc;

    /** Default Constructor */
    public ConsoleTextPane() {
        super();
        this.styledDoc = this.getStyledDocument();
        this.styledDoc = DocumentStyler.styleDocument(styledDoc, this.getFont(), this.getForeground());
    }

    /** Updates the styles that are in the StyledDocument (ONLY DEFAULT STYLES)
     * This method is called from setForeground, setBackground, and setFont so
     * that anytime a global Console style change is made it's reflected in any
     * NEW occurrences of the Styles of the StyledDocument, previous printed
     * Styles will reflect the colors/fonts of the Styles that they were added
     * to the console with.
     */
    private void updateStyles() {
        if (this.styledDoc != null) {
            this.styledDoc = DocumentStyler.updateStyles(this.styledDoc, this.getFont(), this.getForeground());
        }
    }

    @Override
    public void setForeground(Color foreground) {
        super.setForeground(foreground);
        this.updateStyles();
    }
    
    @Override
    public void setBackground(Color background) {
        super.setBackground(background);
        this.updateStyles();
    }

    @Override
    public void setFont(java.awt.Font newFont) {
        super.setFont(newFont);
        this.updateStyles();
    }

    /** Finds the next occurrence of the String after the given index
     * Finds the next occurrence of the passed String and selects it in the
     * console.
     * @param search The String to be found in the ConsoleTextPane
     * @return Returns <code>true</code> or <code>false</code> depending on if the string was found or not.
     */
    public boolean find(String search, int startIndex, boolean caseSensitive) {
        boolean found = false;
        this.requestFocusInWindow();
        try {
            String cText = this.styledDoc.getText(0, this.styledDoc.getLength());

            if (!caseSensitive) {
                cText = cText.toLowerCase();
                search = search.toLowerCase();
            }

            int selStart = cText.indexOf(search);
            int selEnd = selStart + search.length();

            if (selStart > 0) {
                this.setSelectionStart(selStart);
                this.setSelectionEnd(selEnd);
                found = true;
            } 
        } catch (Exception exc) { System.out.println(exc.getMessage()); }

        return found;
    }
    
    /** Finds the first occurrence of the passed String
     * Finds the first occurrence of the passed string and selects it in the
     * console.
     * @param search The String to be found in the ConsoleTextPane
     * @return Returns <code>true</code> or <code>false</code> depending on if the string was found or not.
     */
    public boolean find(String search, boolean caseSensitive) {
        boolean found = false;
        
        this.requestFocusInWindow();
        try {
            String cText = this.styledDoc.getText(0, this.styledDoc.getLength());
            
            if (!caseSensitive) {
                cText = cText.toLowerCase();
                search = search.toLowerCase();
            }
            
            int selStart = cText.indexOf(search);
            int selEnd = selStart + search.length();

            if (selStart > 0) {
                this.setSelectionStart(selStart);
                this.setSelectionEnd(selEnd);
                found = true;
            }
        } catch (Exception exc) { System.out.println(exc.getMessage()); }
        
        return found;
    }

    /** Determines if the character passed is indeed a valid console Quake 3 color number (0-8)
     * This method is a helper method for appendWithColors(), it allows the
     * console to determine if the character following a Caret (^) is supposed
     * to be a change in Style or if the user just felt like putting a random
     * Caret in their name.
     * @param testChar The character to compare to the list of acceptable color numbers.
     * @return true If the char is a Quake 3 Color number, false if not.
     */
    private boolean isValidQuakeNumber(char testChar) {
        char[] validQuakeNumbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        for (int i = 0; i < validQuakeNumbers.length; i++) {
            if (validQuakeNumbers[i] == testChar)
                return true;
        }

        return false;
    }

    /** Returns the StyledDocument this console uses.
     * This method will allow a call to DocumentStyler from outside the
     * ConsoleTextPane class use the proper StyledDocument so the Styles that
     * are added or updated will reflect their changes to the user.
     * @return The StyledDocument of this ConsoleTextPane
     */
    public StyledDocument getConsoleStyledDocument() {
        return this.styledDoc;
    }

    /** Appends the given string to the Console after processing for Quake 3 color codes.
     * This method functions different from the other append methods, the sole
     * purpose of this method is to process output for Quake 3 Color numbers
     * change the formatting as needed based on Style changes.
     * Unlike the other append methods which use a single Style when adding
     * text this method will fluctuate between Styles. This should only be
     * called when text needs to be processed for Quake colors.
     * @param stringToProcess The String that contains player names that need color processing.
     */
    public void appendWithColors(String stringToProcess) {
        String temp = "";
        String style = "default";

        for (int i = 0; i < stringToProcess.length(); i++) {
            if (stringToProcess.charAt(i) == '^' && this.isValidQuakeNumber(stringToProcess.charAt(i + 1))) {
                append(temp, style);
                style = stringToProcess.substring(i + 1, i + 2);
                temp = ""; // Clear temp for the next series of colored text
                i++; // Jump past the color number
                if ((i + 4) < stringToProcess.length()) {
                    String doubleTemp = stringToProcess.substring(i + 1, i + 4);
                    if (doubleTemp.equals("   "))
                        style = "default";
                }
            }
            else
                temp = temp + stringToProcess.charAt(i);
        }
        append(temp + "\n", style);
    }

    /** Adds the passed message to the Console with the formatting of the passed Style name
     * Adds the append text to the console following the settings of the
     * specified style. This method prints the whole append in the Style passed,
     * so break statements up if you need to fluctuate colors.
     * @param append The String to be added to the Console.
     * @param styleName The name of the Style the String needs to be added with.
     */
    public void append(String append, String styleName) {
        try { // Try to add the colored string to the output area document
            this.styledDoc.insertString(
                    this.styledDoc.getLength(), append,
                    this.styledDoc.getStyle(styleName));
            this.setCaretPosition(this.styledDoc.getLength());
        }
        catch (BadLocationException e) {
            JOptionPane.showMessageDialog(this,
                    "An error ocurred adding the input to the console. \n"
                    + "The program must exit.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    /** Appends a series of lines to the Console with the given styles per line.
     * Takes a list of ConsoleTextPane.AppendLine and adds each line to the
     * console with the set Style. This method is ideal when printing multiple
     * colors in one line to the console.
     * @param bulkAppends The AppendLine array containing each line and Style that will be appended.
     */
    public void append(AppendLine[] bulkAppends) {
        for (int i = 0; i < bulkAppends.length; i++)
            this.append(bulkAppends[i].getLine(), bulkAppends[i].getStyleName());
    }

    /** Appends the String to the Console with the default Style.
     * Appends the passed String to the console with the "default" style.
     * @param append The line to be added to the console.
     */
    public void append(String append) {
        append(append, "default");
    }

    /** Creates a String containing the command entered and the date/time entered, adding it to the console.
     * Takes the command that was entered and creates a String to alert the user
     * what command was entered and adds a date/time String to display when
     * the command was entered.
     * Format: "Command: commandVar - M/d/yyyy hh:mm:ss aa"
     * @param command The command that was entered to generate the following output.
     */
    public void appendCommand(String command) {
        java.util.Date now = new java.util.Date();
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("M/d/yyyy hh:mm:ss aa");
        String dateString = dateFormat.format(now);
        AppendLine[] commandBulk = {new AppendLine("Command: "),
                new AppendLine(command + " ", "command"), 
                new AppendLine("- " + dateString + "\n")};
        append(commandBulk);
    }
    
    class AppendLine {
        private String line;
        private String styleName;

        public AppendLine(String line, String styleName) {
            this.line = line;
            this.styleName = styleName;
        }

        public AppendLine(String line) {
            this.line = line;
            this.styleName = "default";
        }
        public String getLine() {
            return this.line;
        }
        public String getStyleName() {
            return this.styleName;
        }
        public void setLine(String line) {
            this.line = line;
        }
        public void setStyleName(String styleName) {
            this.styleName = styleName;
        }
    }
}
