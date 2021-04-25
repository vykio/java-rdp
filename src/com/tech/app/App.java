/**
 * Application : Java 11
 * Point d'entrée de l'application : public static void main()
 */

package com.tech.app;

import com.tech.app.functions.CheckThreadViolationRepaintManager;
import com.tech.app.windows.MainWindow;

import javax.swing.*;

public class App implements Runnable {
    @Override
    public void run() {
        try {
            MainWindow mainWindow = new MainWindow(900,500);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }


    public synchronized void start() {
        SwingUtilities.invokeLater(this);
        RepaintManager.setCurrentManager(new CheckThreadViolationRepaintManager());
    }

    public static void main(String[] args) {new App().start();}
}
