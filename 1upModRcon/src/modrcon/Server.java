package modrcon;

/**
 * A server class is a representation of Q3 based game server
 * consisting of a name, ip, port, login method, and password.
 * 
 * @author Pyrite[1up]
 */
public class Server {

    public String name;
    public String ip;
    public String port;
    public String loginType;
    public String password;

    public Server() {
        this.name = "";
        this.ip = "";
        this.port = "27960";
        this.loginType = "";
        this.password = "";
    }

    public Server(String name, String ip, String port, String loginType, String password) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.loginType = loginType;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getIP() {
        return this.ip;
    }

    public String getPortAsString() {
        return this.port;
    }

    public int getPortAsInteger() {
        return Integer.parseInt(this.port);
    }

    public String getLoginType() {
        return this.loginType;
    }

    public String getPassword() {
        return this.password;
    }
    
    public String[] toArray() {
        String[] server = {this.name, this.ip, this.port, this.loginType, this.password };
        return server;
    }
    
    @Override public String toString() {
        return this.name;
    }

}
