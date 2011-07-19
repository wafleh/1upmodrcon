package modrcon;

import javax.swing.*;
import java.io.*;
import java.util.zip.*;

/**
 * A class to import/export 1up ModRcon Settings,
 * so they can be shared amongst clan mates.
 *
 * @author Pyrite
 */
public class ImportExportManager {

    /** A reference to the Main Window */
    private MainWindow parent;

    public ImportExportManager(MainWindow owner) {
        this.parent = owner;
    }

    /** Gets an array of the settings filenames that ModRcon uses. */
    private String[] getSettingsFiles() {
        File dir = new File(PropertyManager.settingsPath);
        return dir.list();
    }

    public void importSettings() {
        // not implemented yet.
    }

    public void exportSettings() {
        // These are the files to include in the ZIP file
        String[] filenames = this.getSettingsFiles();

        // Create a buffer for reading the files
        byte[] buf = new byte[1024];

        try {
            // Create the ZIP file
            String outFilename = "ModRcon_v"+PropertyManager.MODRCON_VERSION+"_SettingsBackup.pu1";
            JFileChooser file = new JFileChooser();
            file.setSelectedFile(new File(outFilename));
            int choice = file.showSaveDialog(parent);
            if (choice == 0) {
                String path = file.getSelectedFile().getAbsolutePath();
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path));

                // Compress the files
                for (int i=0; i<filenames.length; i++) {
                    FileInputStream in = new FileInputStream(PropertyManager.settingsPath+filenames[i]);

                    // Add ZIP entry to output stream.
                    out.putNextEntry(new ZipEntry(filenames[i]));

                    // Transfer bytes from the file to the ZIP file
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    // Complete the entry
                    out.closeEntry();
                    in.close();
                }

                // Complete the ZIP file
                out.close();

                JOptionPane.showMessageDialog(parent, "1up ModRcon Settings Exported Successfully!", "File Saved", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            System.out.println("Error Creating Export File:" + e.getMessage());
        }
    }

}
