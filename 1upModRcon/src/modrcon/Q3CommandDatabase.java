package modrcon;

import java.util.ArrayList;
import java.io.*;



/*
ArrayList t = new ArrayList();

t.add(new Q3Command("addip", "Adds a players ip address to the banlist and bans them from the server on map change", "addip 255.255.255.255");
t.add(new Q3Command("bigtext", "Displays large text in the top center portion of players screens.", ""bigtext ""1up Mod Rcon"""
t.add(new Q3Command("capturelimit", "Sets the amount of flags needed to win a match in a CTF game.", "capturelimit 15");
t.add(new Q3Command("cmdlist", "Displays a list of commands that can be sent to the server", "cmdlist");
t.add(new Q3Command("cvarlist", "Displays a list of all available cvar's", "cvarlist");
t.add(new Q3Command("cyclemap", "Runs the next map in the servers map cycle.", "cyclemap");
t.add(new Q3Command("dumpuser", "Displays detailed information about a specific player on the server given their client number.", "dumpuser 6");
t.add(new Q3Command("exec", "Executes a script on the server", "exec clanbot.cfg");
t.add(new Q3Command("forceteam", "Forces a player to join the red or blue team or forces them into spectator mode given their client number.", "forceteam 8 spectator");
t.add(new Q3Command("fraglimit", "Sets the amount of kills needed by a player (FFA mode) or team (TDM mode) to win a match.", "fraglimit 100");
t.add(new Q3Command("guids", "Displays the Globally Unique IDentifier for each player on the server.", "guids");
t.add(new Q3Command("g_friendlyFire", "Turns friendly fire mode off or on (0 = Off 1 = On).", "g_friendlyFire 0");
t.add(new Q3Command("g_gametype", ""Sets the gametype for the server such as FFA", " CTF", " TDM. Valid values are between 0-8"", "g_gametype 3");
t.add(new Q3Command("g_gravity", "Sets the gravity for the server. The default is 800 and lower values mean less gravity.", "g_gravity 800");
t.add(new Q3Command("g_matchmode", "Matchmode is for matchplay. Features timeouts and ready-commands", "g_matchmode 0");
t.add(new Q3Command("g_maxteamkills", "Sets the maximum number of times a player can kill a teamate before they are automatically kicked from the server.", "g_maxteamkills 2");
t.add(new Q3Command("g_nextmap", "Sets the next map to load regardless of the servers mapcycle.", "g_nextmap ut4_casa");
t.add(new Q3Command("kick", "Kicks a player from the server given their client number.", "kick 3");
t.add(new Q3Command("map", "Loads a map on the server with a given name.", "map ut4_casa");
t.add(new Q3Command("mute", "Temporarily mutes a player given their client number.", "mute 12");
t.add(new Q3Command("nuke", "Rains a grenade down on a player given their client number", "nuke 6");
t.add(new Q3Command("players", ""Displays the current map", "player count and team scores"", "players");
t.add(new Q3Command("reload", "Reload does a complete restart of the current map", "reload");
t.add(new Q3Command("removeip", "Removes a players ip address from the banlist", "removeip 255.255.255.255");
t.add(new Q3Command("say", "Displays text on players screens. Does not need quotes around multiple words unlike bigtext", "say 1up ModRcon");
t.add(new Q3Command("serverinfo", "Displays detailed information about the currently connected server", "serverinfo");
t.add(new Q3Command("slap", "Slaps a player given their client number", "Slap 8");
t.add(new Q3Command("status", "Displays basic information about all players on the server.", "status");
t.add(new Q3Command("timelimit", "Sets the amount of time (in minutes) a map will run before it automatically changes", "timelimit 15");
t.add(new Q3Command("togglemute", "Mutes a player until they reconnect to the server or until they are unmuted by an administrator.", "togglemute 5");
t.add(new Q3Command("veto", "Cancels any pending votes that were called.", "veto");













*/



/**
 * A class to store the Q3Commands.
 *
 * @author Pyrite[1up]
 */
public class Q3CommandDatabase implements Serializable {

    /** An ArrayList of Q3Command objects. */
    private ArrayList commandList;

    /** The filename to write the commandList to. */
    private final String fileName = "commands.db";

    /**
     * Constructs a Q3CommandDatabase and populates it
     * if there are any Q3Commands currently stored to disk.
     */
    public Q3CommandDatabase() {
        super();
        this.commandList = new ArrayList();
        File f = new File(this.fileName);
        if (!f.exists()) {
            this.regenerateCommandList();
            this.saveDatabase();
        }
        this.loadDatabase();
    }

    public void setCommandList(ArrayList list) {
        this.commandList = list;
    }

    public ArrayList getCommandList() {
        return this.commandList;
    }

    private void regenerateCommandList() {
        ArrayList t = new ArrayList();
        t.add(new Q3Command("addip", "Adds a players ip address to the banlist and bans them from the server on map change", "addip 255.255.255.255"));
        t.add(new Q3Command("bigtext", "Displays large text in the top center portion of players screens.", "bigtext \"1up Mod Rcon\""));
        t.add(new Q3Command("capturelimit", "Sets the amount of flags needed to win a match in a CTF game.", "capturelimit 15"));
        t.add(new Q3Command("cmdlist", "Displays a list of commands that can be sent to the server", "cmdlist"));
        t.add(new Q3Command("cvarlist", "Displays a list of all available cvar's", "cvarlist"));
        t.add(new Q3Command("cyclemap", "Runs the next map in the servers map cycle.", "cyclemap"));
        t.add(new Q3Command("dumpuser", "Displays detailed information about a specific player on the server given their client number.", "dumpuser 6"));
        t.add(new Q3Command("exec", "Executes a script on the server", "exec clanbot.cfg"));
        t.add(new Q3Command("forceteam", "Forces a player to join the red or blue team or forces them into spectator mode given their client number.", "forceteam 8 spectator"));
        t.add(new Q3Command("fraglimit", "Sets the amount of kills needed by a player (FFA mode) or team (TDM mode) to win a match.", "fraglimit 100"));
        t.add(new Q3Command("guids", "Displays the Globally Unique IDentifier for each player on the server.", "guids"));
        t.add(new Q3Command("g_friendlyFire", "Turns friendly fire mode off or on (0 = Off 1 = On).", "g_friendlyFire 0"));
        t.add(new Q3Command("g_gametype", "Sets the gametype for the server such as FFA, CTF, TDM. Valid values are between 0-8.", "g_gametype 3"));
        t.add(new Q3Command("g_gravity", "Sets the gravity for the server. The default is 800 and lower values mean less gravity.", "g_gravity 800"));
        t.add(new Q3Command("g_matchmode", "Matchmode is for matchplay. Features timeouts and ready-commands", "g_matchmode 0"));
        t.add(new Q3Command("g_maxteamkills", "Sets the maximum number of times a player can kill a teamate before they are automatically kicked from the server.", "g_maxteamkills 2"));
        t.add(new Q3Command("g_nextmap", "Sets the next map to load regardless of the servers mapcycle.", "g_nextmap ut4_casa"));
        t.add(new Q3Command("kick", "Kicks a player from the server given their client number.", "kick 3"));
        t.add(new Q3Command("map", "Loads a map on the server with a given name.", "map ut4_casa"));
        t.add(new Q3Command("mute", "Temporarily mutes a player given their client number.", "mute 12"));
        t.add(new Q3Command("nuke", "Rains a grenade down on a player given their client number", "nuke 6"));
        t.add(new Q3Command("players", "Displays the current map, player count, and team scores", "players"));
        t.add(new Q3Command("reload", "Reload does a complete restart of the current map", "reload"));
        t.add(new Q3Command("removeip", "Removes a players ip address from the banlist", "removeip 255.255.255.255"));
        t.add(new Q3Command("say", "Displays text on players screens. Does not need quotes around multiple words unlike bigtext", "say 1up ModRcon"));
        t.add(new Q3Command("serverinfo", "Displays detailed information about the currently connected server", "serverinfo"));
        t.add(new Q3Command("slap", "Slaps a player given their client number", "Slap 8"));
        t.add(new Q3Command("status", "Displays basic information about all players on the server.", "status"));
        t.add(new Q3Command("timelimit", "Sets the amount of time (in minutes) a map will run before it automatically changes", "timelimit 15"));
        t.add(new Q3Command("togglemute", "Mutes a player until they reconnect to the server or until they are unmuted by an administrator.", "togglemute 5"));
        t.add(new Q3Command("veto", "Cancels any pending votes that were called.", "veto"));
        this.commandList = t;
    }

    /**
     * Add a Command to the Q3CommandDatabase.
     *
     * @param s The Command to Add.
     */
    public void addServer(Q3Command cmd) {
        this.commandList.add(cmd);
    }

    /**
     * Looks for the commandList stored on the HDD,
     * and if found, reads it into this object.
     */
    private void loadDatabase() {
        File db = new File(this.fileName);
        if (db.exists()) {
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                fis = new FileInputStream(this.fileName);
                in = new ObjectInputStream(fis);
                this.commandList = (ArrayList)in.readObject();
                in.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Saves the commandList to the Hard Disk.
     */
    public void saveDatabase() {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(this.fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(this.commandList);
            out.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

}
