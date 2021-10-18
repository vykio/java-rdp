package com.tech.app;

import com.tech.app.functions.CheckThreadViolationRepaintManager;
import com.tech.app.functions.Updater;
import com.tech.app.windows.MainWindow;
import com.tech.app.windows.runnables.UpdaterRunnable;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Classe qui crée l'application et qui la fait tourner.
 */
public class App implements Runnable {
    private Updater updater;

    /**
     * Méthode dans laquelle on crée la fenêtre principale.
     */
    @Override
    public void run() {
        try {
            new MainWindow(900,500);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui initialise l'application et lance la méthode run().
     */
    public synchronized void start() throws MalformedURLException {
        updater = new Updater();
        try {
            if (updater.isUpdatable()) {
                SwingUtilities.invokeLater(new UpdaterRunnable());
            } else {
                SwingUtilities.invokeLater(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        RepaintManager.setCurrentManager(new CheckThreadViolationRepaintManager());
    }

    public static void main(String[] args) throws MalformedURLException {new App().start();}
}
