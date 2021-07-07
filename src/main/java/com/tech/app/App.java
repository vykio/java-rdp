package com.tech.app;

import com.tech.app.functions.CheckThreadViolationRepaintManager;
import com.tech.app.windows.MainWindow;

import javax.swing.*;

/**
 * Classe qui crée l'application et qui la fait tourner.
 */
public class App implements Runnable {
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
    public synchronized void start() {
        SwingUtilities.invokeLater(this);
        RepaintManager.setCurrentManager(new CheckThreadViolationRepaintManager());
    }

    public static void main(String[] args) {new App().start();}
}
