package modrcon;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javazoom.jl.player.Player;

/**
 * A class for playing MP3s from the resources/sounds folder.
 *
 * MP3 files are exported from Audacity at 3200kbps.
 *
 * @author Kevin Wayne <wayne@cs.Princeton.edu>
 * @author Pyrite[1up]
 */
public enum MP3 {
    DUKE_BABE("babealicious.mp3"),
    DUKE_BALLS("ballsofsteel.mp3"),
    DUKE_SLAP("thatsgottahurt.mp3");

    private String filename;
    private Player player;

    /**
     * Prepare a MP3 file for playing.
     *
     * @param filename The full mp3 filename that resides in the resources folder.
     */
    MP3(String filename) {
        this.filename = filename;
    }

    /** Only used to stop the playback on demand. */
    public void close() { 
        if (player != null) {
            player.close();
        }
    }

    /** Play the MP3 file to the sound card. */
    public void play() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/modrcon/resources/sounds/"+filename);            
            BufferedInputStream bis = new BufferedInputStream(is);
            player = new Player(bis);
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread() {
            @Override
            public void run() {
                try { player.play(); }
                catch (Exception e) { System.out.println(e); }
            }
        }.start();
    }

}


