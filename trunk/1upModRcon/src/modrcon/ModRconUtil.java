package modrcon;

import java.util.regex.Pattern;

/**
 * A utility class for 1up ModRcon.
 *
 * @author Pyrite
 */
public class ModRconUtil {
    
    private static final String key = "EncryptionIsFun";

    /*
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
    */

    public static String encryptString(String str) {
        StringBuffer buffer = new StringBuffer(str);
        buffer = buffer.reverse();
        return buffer.toString();
    }

    public static String decryptString(String str) {
        return encryptString(str);
    }

    public static boolean isIPAddress(String str) {
        Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        return ipPattern.matcher(str).matches();
    }

    public static boolean isMac() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "mac" ) >= 0);
    }


}
