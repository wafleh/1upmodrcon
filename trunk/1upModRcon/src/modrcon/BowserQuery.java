package modrcon;

import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.*;


/**
 * A Urban Terror Class for use by BowserBot for Clan 1up.
 *
 * Unauthenticated commands:
 * getstatus, getinfo, getchallenge, connect, rcon
 *
 * @author  Brandon Tanner
 * @author  Gerald Kaszuba <http://misc.slowchop.com/misc/wiki/q3query>
 * @license http://opensource.org/licenses/gpl-license.php GNU Public License
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

    private byte[] bb = null;
    private String output;

    public BowserQuery(String address, int port) throws Exception {
        this.oob = (byte)0xff;
        this.port = port;
        this.ds = new DatagramSocket();
        this.ds.setSoTimeout(2000);
        this.ia = InetAddress.getByName(address);
    }

    public void setRawOutput(boolean flag) {
        this.rawOutput = flag;
    }

    public boolean getRawOutput() {
        return this.rawOutput;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void rcon(String command) {
        this.send("rcon "+this.password+" "+command);
    }

    public void mod(String command) {
        this.send("mod "+this.password+" "+command);
    }

    public void ref(String command) {
        this.send("ref "+this.password+" "+command);
    }

    public void say(String message) {
        this.mod("say ^1"+message);
    }
    
    public void kick(int clientNumber, String message) {
        this.mod("sendclientcommand "+clientNumber+" disconnect "+String.valueOf(34)+message+String.valueOf(34));
    }

    public void crash(int clientNumber) {
        this.mod("sendclientcommand "+clientNumber+" cs 400");
    }
    
    public void renamePlayer(int clientNumber, String newName) {
        this.mod("forcecvar "+clientNumber+" name "+String.valueOf(34)+newName+String.valueOf(34));
    }

    protected void send(String data) {
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
                //String s = new String(dpacket.getData(), 0, 0, dpacket.getLength());
                //System.out.println(dpacket.getAddress() + " at port " + dpacket.getPort() + " says " + s);

                //this.bb = dpacket.getData();
                //System.out.print(stripPrintCommands(new String(dpacket.getData(), 0, 0, dpacket.getLength())));
                String packet = new String(dpacket.getData(), 0, dpacket.getLength());
                this.output += packet;
                //System.out.print(stripPrintCommands(new String(dpacket.getData(), 0, dpacket.getLength())));
            }
            catch (IOException e) {
                //System.out.print(e.getMessage());
                //System.err.println(e.getMessage());
                //System.out.println(stripPrintCommands(this.output));
                if (this.rawOutput) {
                    return this.output;
                } else {
                    String purdy = this.output;
                    purdy = stripPrintCommands(purdy);
                    purdy = stripColors(purdy);
                    return purdy;
                }
            }
        } // end while


        /*
        String response;
        while (true) {
            try {
                byte[] buff = new byte[3048]; // 65507
                //this.ds.setTrafficClass(IPTOS_RELIABILITY);
                //System.out.println(this.ds.getTrafficClass());
                //this.ds.setSoTimeout(5000); // test
                //System.out.println(ds.getReceiveBufferSize());
                //ds.setReceiveBufferSize(16000);
                this.dp = new DatagramPacket(buff, buff.length);
                //this.dp.setLength(3048);
                this.ds.receive(this.dp);
                //Thread.sleep(1000);
                response = new String(buff);
            }
            catch (SocketTimeoutException e) {
                response = new String();
                System.out.println("Error: "+e.getMessage());
            }
            catch (Exception exp) {
                response = new String();
                System.out.println("Error: "+exp.getMessage());
            }
        }
         *
         */
        //return stripPrintCommands(new String(buffer));
    }

    /**
     * Remove erroneus print commands.
     *
     * @param input The input string buffer.
     * @return      The buffer without print commands.
     */
    public String stripPrintCommands(String input) {
        Pattern r = Pattern.compile("....print\n");
        Matcher m = r.matcher(input);
        return m.replaceAll("");
    }

    public String stripColors(String input) {
        Pattern r = Pattern.compile("\\^.");
        Matcher m = r.matcher(input);
        return m.replaceAll("");
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
     * Gets the output of the getinfo command,
     * and parses it into a useful data structure.
     *
     * @return
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
