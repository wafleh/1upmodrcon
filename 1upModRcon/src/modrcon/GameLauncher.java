package modrcon;

/**
 * A static class to Launch a Game.
 *
 * @author Pyrite
 */
public class GameLauncher {

    /**
     * Launch a Game.
     *
     * @param pathToExe The absolute path to the game's executable.
     * @param serverIP The IP of the server to connect to.
     * @throws Exception
     */
    public static void Launch(String pathToExe, String serverIP) throws Exception {
        String[] cmd = { pathToExe, "+connect "+serverIP };
        Process p = Runtime.getRuntime().exec(cmd);
    }

}
