package modrcon;

import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.*;


/**
 * A class to query Q3 based games, like Urban Terror.
 *
 * Unauthenticated commands:
 * getstatus, getinfo, getchallenge, connect, disconnect, rcon, ipAuthorize.
 *
 * @author  Brandon Tanner
 * @author  Philip Edelbrock (phil@studiogeologie.com) <http://secure.netroedge.com/~phil/q3tool-1.0.0/q3tool.java>
 * @author  http://opensource.org/licenses/gpl-license.php GNU Public License
 */
public class BowserQuery {

    /** The password for use with rcon/mod/ref. */
    private String password;

    /** The out-of-bound data to send prior to commands. */
    private byte oob;

    /** The Server Port. */
    private int port;

    /** The Client Socket Pointer. */
    private DatagramSocket ds;

    /** The UDP Datagram Packet. */
    private DatagramPacket dp;

    /** The Internet Address Object. */
    private InetAddress ia;

    /** Enable/Disable Raw Server Output */
    private boolean rawOutput;

    /** The login method to the server (ref, mod, rcon). */
    private String method;

    /** The last command sent. */
    private String lastCommand;

    /** Buffer used to store output from the server. */
    private String output;

    public BowserQuery(Server s) throws Exception {
        this.oob = (byte)0xff;
        this.port = s.getPortAsInteger();
        this.method = s.getLoginType();
        this.password = s.getDecryptedPassword();
        this.ds = new DatagramSocket();
        //this.ds.setSoTimeout(2000); // not sure if this has any effect here.
        this.ia = InetAddress.getByName(s.getIP());
    }

    public void setRawOutput(boolean flag) {
        this.rawOutput = flag;
    }

    public boolean getRawOutput() {
        return this.rawOutput;
    }

    /**
     * Sends an authenticated command to the server.
     *
     * @param cmd The command to send.
     */
    public void sendCmd(String cmd) {
        this.lastCommand = cmd;
        if (this.method.equals("ref")) {
            this.ref(cmd);
        }
        else if (this.method.equals("mod")) {
            this.mod(cmd);
        }
        else {
            this.rcon(cmd);
        }
    }

    public void forceTeam(String player, String team) {
        this.sendCmd("forceteam "+player+" "+team);
    }

    private void rcon(String command) {
        this.send("rcon "+this.password+" "+command);
    }

    private void mod(String command) {
        this.send("mod "+this.password+" "+command);
    }

    private void ref(String command) {
        this.send("ref "+this.password+" "+command);
    }

    public void say(String message) {
        this.sendCmd("say ^1"+message);
    }
    
    public void kick(int clientNumber, String message) {
        String command = "sendclientcommand "+clientNumber+" disconnect "+String.valueOf(34)+message+String.valueOf(34);
        this.sendCmd(command);
    }

    public void crash(int clientNumber) {
        String command = "sendclientcommand "+clientNumber+" cs 400";
        this.sendCmd(command);
    }
    
    public void renamePlayer(int clientNumber, String newName) {
        String command = "forcecvar "+clientNumber+" name "+String.valueOf(34)+newName+String.valueOf(34);
        this.sendCmd(command);
    }

    private void send(String data) {
        try {
            String out = "xxxx"+data;
            byte [] buff = out.getBytes();
            buff[0] = this.oob;
            buff[1] = this.oob;
            buff[2] = this.oob;
            buff[3] = this.oob;
            this.dp = new DatagramPacket(buff, buff.length, this.ia, this.port);
            this.ds.send(this.dp);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getResponse() {
        DatagramPacket dpacket;
        byte[] buffer = new byte[2048];
        this.output = "";
        while (true) {
            try {
                dpacket = new DatagramPacket(buffer, buffer.length);
                // Decrease value speeds things up, increase slows things down.
                this.ds.setSoTimeout(100);
                this.ds.receive(dpacket);
                String packet = new String(dpacket.getData(), 0, dpacket.getLength());
                this.output += packet;
            }
            catch (IOException e) {
                if (this.rawOutput) {
                    return this.output;
                } else {
                    //todo: replace blank player name with "UnknownPlayer"
                    String purdy = this.output;
                    purdy = stripPrintCommands(purdy);
                    //purdy = stripColors(purdy);
                    return purdy;
                }
            }
        } // end while
    }

    /**
     * Remove print commands.
     *
     * @param input The input string buffer.
     * @return      The buffer without print commands.
     */
    private String stripPrintCommands(String input) {
        Pattern r = Pattern.compile("....print\n");
        Matcher m = r.matcher(input);
        return m.replaceAll("");
    }

    private String stripColors(String input) {
        Pattern r = Pattern.compile("\\^.");
        Matcher m = r.matcher(input);
        return m.replaceAll("");
    }

    /**
     * Gets the current gear setting.
     *
     * This method might not always be accurate given
     * its current implementation method. It should be
     * redone in the future to search for g_gear in the
     * output and return the value associated with it.
     *
     * @return The gear setting integer.
     */
    public int getGearSetting() {
        String input = this.getstatus();
        String[] lines = input.split("\\n");
        String[] values = lines[0].split("\\\\");
        int gear = Integer.parseInt(values[70]);
        return gear;
    }

    public String getstatus() {
        this.send("getstatus");
        String resp = this.getResponse();
        // Remove OOB command
        Pattern r = Pattern.compile("....statusResponse\n");
        Matcher m = r.matcher(resp);
        resp = m.replaceAll("");
        return resp;
    }

    /**
     * Send Connection-less Packet Commands to the Server.
     *
     * @param cmd The connection-less packet command to send.
     */
    public void sendConnectionlessPacket(String cmd) {
        this.send(cmd);
    }

    /**
     * Gets the output of the getinfo command,
     * and parses it into a useful data structure.
     *
     * @return The output in a HashMap
     */
    public Map getServerInfo() {
        this.send("getinfo");
        String resp = this.getResponse();
        
        // Remove OOB command
        Pattern r = Pattern.compile("....infoResponse\n");
        Matcher m = r.matcher(resp);
        resp = m.replaceAll("");

        //System.out.println(resp);

        // Remove the first character
        resp = resp.substring(1);
        // Split String by Single Backslash
        String[] foo = resp.split("\\\\");       

        // Now Separate Keys and Values
        ArrayList<String> keys = new ArrayList();
        ArrayList<String> vals = new ArrayList();
        boolean direction = true;
        for (int i=0; i < foo.length; i++) {
            if (direction) {
                keys.add(foo[i]);
            } else {
                vals.add(foo[i]);
            }
            direction = (direction) ? false : true;
        }

        // Create Associative Array of Key/Value Pairs.
        Map<String, String> map = new HashMap();
        for (int i=0; i < keys.size(); i++) {
            String key = keys.get(i);
            String val = vals.get(i);
            map.put(key, val);
        }
        return map;
    }
    
}
