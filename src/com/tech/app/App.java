/**
 * Application : Java 11
 * Point d'entrée de l'application : public static void main()
 */

package com.tech.app;

import com.tech.app.windows.MainWindow;

import java.awt.*;

public class App implements Runnable {
    @Override
    public void run() {

        MainWindow mainWindow = new MainWindow(500,500);

        EventQueue.invokeLater(() -> {

            mainWindow.createMenuBar();
            mainWindow.setVisible(true);
        });

    }

    public synchronized void start() {
        new Thread(this).start();
    }

    public static void main(String[] args) {   new App().start();    }
}
