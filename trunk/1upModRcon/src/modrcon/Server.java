package modrcon;

/**
 * A server class is a representation of Q3 based game server
 * consisting of a name, ip, port, mod password and/or rcon password.
 * 
 * @author Pyrite[1up]
 */
public class Server {

    public String name;
    public String ip;
    public String port;
    public String modpass;
    public String rconpass;

    public Server() {
        this.name = "";
        this.ip = "";
        this.port = "27960";
        this.modpass = "";
        this.rconpass = "";
    }

    public Server(String name, String ip, String port, String modpass, String rconpass) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.modpass = modpass;
        this.rconpass = rconpass;
    }
    
    public String[] toArray() {
        String[] server = {this.name, this.ip, this.port, this.modpass, this.rconpass };
        return server;
    }
    
    @Override public String toString() {
        return "Name: "+name+", IP: "+ip+", Port: "+port+", ModPass: "+modpass+", RconPass: "+rconpass;
    }

}