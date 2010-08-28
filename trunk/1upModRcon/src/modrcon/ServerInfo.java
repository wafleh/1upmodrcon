package modrcon;

import java.util.*;

/**
 * The ServerInfo class represents the data returned 
 * from the server with the getinfo command.
 *
 * @author Pyrite[1up]
 */
public class ServerInfo {
    private String gametype;

    public ServerInfo(Map input) {
        this.gametype = (String)input.get("gametype");
    }

    public static String getGameTypeString(int gt) {
        if (gt == 0) {
            return "FFA";
        }
        else if (gt == 3) {
            return "TDM";
        }
        else if (gt == 4) {
            return "TS";
        }
        else if (gt == 5) {
            return "FTL";
        }
        else if (gt == 6) {
            return "C&H";
        }
        else if (gt == 7) {
            return "CTF";
        }
        else if (gt == 8) {
            return "Bomb";
        }
        else {
            return "N/A";
        }
    }
}
