package modrcon;

import java.util.regex.Pattern;
import javax.swing.*;

/**
 * A utility class for 1up ModRcon.
 *
 * @author Pyrite
 */
public class ModRconUtil {
    
    private static final String key = "EncryptionIsFun";

    public static String encryptString(String str) {
        StringBuffer sb = new StringBuffer (str);
        int lenStr = str.length();
        int lenKey = key.length();
        // For each character in our string, encrypt it...
        for (int i=0, j=0; i < lenStr; i++, j++) {
            if (j >= lenKey) j = 0;  // Wrap 'round to beginning of key string.
            // XOR the chars together. Must cast back to char to avoid compile error.
            sb.setCharAt(i, (char)(str.charAt(i) ^ key.charAt(j)));
        }
        return sb.toString();
    }

    public static String decryptString(String str) {
        return encryptString(str);
    }
    
    public static String reverseString(String str) {
        StringBuffer buffer = new StringBuffer(str);
        buffer = buffer.reverse();
        return buffer.toString();
    }

    public static boolean isIPAddress(String str) {
        Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        return ipPattern.matcher(str).matches();
    }

    /**
     * Determines if a string is a Q3 IP Range.
     *
     * E.g. The range for 208.43.15.81 is 208.43.15.0:-1
     *
     * @param str The string to test.
     * @return True or False
     */
    public static boolean isIPRange(String str) {
        Pattern ipRangePattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.0:-1");
        return ipRangePattern.matcher(str).matches();
    }

    public static boolean isMac() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "mac" ) >= 0);
    }

    public static String getGameTypeString(int gt) {
        if (gt == 0) {
            return "Free for All";
        }
        else if (gt == 3) {
            return "Team Deathmatch";
        }
        else if (gt == 4) {
            return "Team Survivor";
        }
        else if (gt == 5) {
            return "Follow the Leader";
        }
        else if (gt == 6) {
            return "Capture and Hold";
        }
        else if (gt == 7) {
            return "Capture the Flag";
        }
        else if (gt == 8) {
            return "Bomb";
        }
        else {
            return "N/A";
        }
    }

    public static int getGameType(String type) {
        int gt = 0;
        if (type.equalsIgnoreCase("free for all")) {
            gt = 0;
        }
        else if (type.equalsIgnoreCase("team deathmatch")) {
            gt = 3;
        }
        else if (type.equalsIgnoreCase("team survivor")) {
            gt = 4;
        }
        else if (type.equalsIgnoreCase("follow the leader")) {
            gt = 5;
        }
        else if (type.equalsIgnoreCase("capture and hold")) {
            gt = 6;
        }
        else if (type.equalsIgnoreCase("capture the flag")) {
            gt = 7;
        }
        else if (type.equalsIgnoreCase("bomb")) {
            gt = 8;
        }
        return gt;
    }

    public static JPanel getPaddedPanel(int padding, JPanel panelToPad) {
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));
        innerPanel.add(Box.createHorizontalStrut(padding));
        innerPanel.add(panelToPad);
        innerPanel.add(Box.createHorizontalStrut(padding));

        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BoxLayout(returnPanel, BoxLayout.Y_AXIS));
        returnPanel.add(Box.createVerticalStrut(padding));
        returnPanel.add(innerPanel);
        returnPanel.add(Box.createVerticalStrut(padding));

        return returnPanel;
    }

    public static JPanel getPaddedPanel(int northSouthPadding,
            int eastWestPadding, JPanel panelToPad) {
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));
        innerPanel.add(Box.createHorizontalStrut(eastWestPadding));
        innerPanel.add(panelToPad);
        innerPanel.add(Box.createHorizontalStrut(eastWestPadding));

        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BoxLayout(returnPanel, BoxLayout.Y_AXIS));
        returnPanel.add(Box.createVerticalStrut(northSouthPadding));
        returnPanel.add(innerPanel);
        returnPanel.add(Box.createVerticalStrut(northSouthPadding));

        return returnPanel;
    }

    public static JPanel getPaddedPanel(int northPadding, int eastPadding,
            int southPadding, int westPadding, JPanel panelToPad) {
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));
        innerPanel.add(Box.createHorizontalStrut(eastPadding));
        innerPanel.add(panelToPad);
        innerPanel.add(Box.createHorizontalStrut(westPadding));

        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BoxLayout(returnPanel, BoxLayout.Y_AXIS));
        returnPanel.add(Box.createVerticalStrut(northPadding));
        returnPanel.add(innerPanel);
        returnPanel.add(Box.createVerticalStrut(southPadding));

        return returnPanel;
    }
}
