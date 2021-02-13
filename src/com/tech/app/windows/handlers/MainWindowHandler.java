package com.tech.app.windows.handlers;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class MainWindowHandler extends WindowHandler {

    public MainWindowHandler(JFrame frame) {
        super(frame);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("I'm out!");
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
