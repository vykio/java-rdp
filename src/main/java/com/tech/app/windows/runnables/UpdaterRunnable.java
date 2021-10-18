package com.tech.app.windows.runnables;

import com.tech.app.functions.Updater;
import javax.swing.*;
import java.io.IOException;

public class UpdaterRunnable implements Runnable {
    @Override
    public void run() {
        JFrame frame = new JFrame();
        int status = JOptionPane.showConfirmDialog(frame, "Une mise à jour est disponible, souhaitez-vous l'installer ?");

        if (status == JOptionPane.YES_OPTION) {
            System.out.println("OK");

            try {
                Updater updater = new Updater();
                if (updater.downloadUpdate()) {
                    System.out.println("Download is complete!");
                    Runtime.getRuntime().exec("cmd /c start update.bat java-rdp-" + updater.getInternetVersion());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        System.exit(0);
    }
}
