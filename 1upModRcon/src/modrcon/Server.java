package modrcon;

/**
 * A server class is a representation of Q3 based game server
 * consisting of a name, ip, port, login method, and password.
 * 
 * @author Pyrite[1up]
 */
public class Server {

    /** The server name. */
    private String name;
    
    /** The server IPv4 address. */
    private String ip;

    /** The server port (usually 27960). */
    private String port;

    /** The server login method (ref, mod, rcon). */
    private String loginType;

    /** The server password un-encrypted. */
    private String decryptedPassword;

    /** The server password encrypted. */
    private String encryptedPassword;

    public Server() {
        this.name = "";
        this.ip = "";
        this.port = "27960";
        this.loginType = "";
        this.decryptedPassword = "";
    }

    /**
     * Constructs a server object given the following parameters:
     *
     * @param name The server name.
     * @param ip The server IPv4 address.
     * @param port The server port, usually just 27960.
     * @param loginType The server login type which is either ref, mod, or rcon.
     * @param password The server password un-encrypted.
     */
    public Server(String name, String ip, String port, String loginType, String password) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.loginType = loginType;
        this.decryptedPassword = password;
        this.encryptedPassword = ModRconUtil.encryptString(password);
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

    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public String getDecryptedPassword() {
        return this.decryptedPassword;
    }
    
    public String[] toArray() {
        String[] server = {this.name, this.ip, this.port, this.loginType, this.decryptedPassword };
        return server;
    }
    
    @Override public String toString() {
        return this.name;
    }

}
