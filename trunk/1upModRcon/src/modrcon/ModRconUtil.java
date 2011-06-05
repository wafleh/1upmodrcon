package modrcon;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
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

    public static Cursor getHelpCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        ImageIcon i = new ImageIcon(ModRconUtil.class.getResource("/modrcon/resources/redshroom.gif"));
        Image image = i.getImage();
        //Create the hotspot for the cursor
        Point hotSpot = new Point(0,0);
        Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "ModRconHelpCursor");
        return cursor;
    }

    public static String trimAndStripColors(String name) {
        Pattern r = Pattern.compile("\\^.");
        Matcher m = r.matcher(name.trim());
        return m.replaceAll("");
    }

    /** Returns an ArrayList of 1up Server objects. */
    public static ArrayList get1upServers() {
        ArrayList servers = new ArrayList();
        try {
            String thisLine;
            URL u = new URL("http://1upclan.info/servers.xml");
            BufferedReader theHTML = new BufferedReader(new InputStreamReader(u.openStream()));
            while ((thisLine = theHTML.readLine()) != null) {
                if (thisLine.matches(".*<title>.*</title>.*")) {
                    if (!thisLine.trim().contains("Server List")) {
                        String name = thisLine.substring(thisLine.indexOf(">")+1, thisLine.indexOf("</title>"));
                        String ip = theHTML.readLine();
                        ip = ip.substring(ip.indexOf(">")+1, ip.indexOf("</description>"));
                        Server s = new Server(name, ip, "27960", "mod", "");
                        servers.add(s);
                    }
                }
            }
        }
        catch (MalformedURLException e) {}
        catch (IOException e) {}
        return servers;
    }

    public static ArrayList getDefaultUrTMaps() {
        ArrayList maps = new ArrayList();
        maps.add("ut4_casa");
        maps.add("ut4_kingdom");
        maps.add("ut4_turnpike");
        maps.add("ut4_abbey");
        maps.add("ut4_prague");
        maps.add("ut4_mandolin");
        maps.add("ut4_uptown");
        maps.add("ut4_algiers");
        maps.add("ut4_austria");
        maps.add("ut4_maya");
        maps.add("ut4_tombs");
        maps.add("ut4_elgin");
        maps.add("ut4_oildepot");
        maps.add("ut4_swim");
        maps.add("ut4_harbortown");
        maps.add("ut4_ramelle");
        maps.add("ut4_toxic");
        maps.add("ut4_sanc");
        maps.add("ut4_riyadh");
        maps.add("ut4_ambush");
        maps.add("ut4_eagle");
        maps.add("ut4_suburbs");
        maps.add("ut4_crossing");
        maps.add("ut4_subway");
        maps.add("ut4_tunis");
        maps.add("ut4_thingley");
        return maps;
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
