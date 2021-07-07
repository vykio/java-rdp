package com.tech.app.windows.handlers;

import javax.swing.*;
import java.awt.event.WindowEvent;

/**
 * Classe qui permet de gérer la fenêtre de l'application. Elle hérite de la classe abstraite WindowHandler.
 */
public class MainWindowHandler extends WindowHandler {

    /**
     * Constructeur de la fenêtre de l'application.
     * @param frame : JFrame.
     */
    public MainWindowHandler(JFrame frame) {
        super(frame);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("I'm out!");
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
