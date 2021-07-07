package com.tech.app.windows.handlers;

import javax.swing.*;
import java.awt.event.WindowListener;

/**
 * Classe abstraite qui permet de gérer les fenêtres de l'application. Cette classe hérite de l'interface WindowListener de Java Event
 */
public abstract class WindowHandler implements WindowListener {

    protected JFrame frame;

    public WindowHandler(JFrame frame) {
        this.frame = frame;
        this.frame.addWindowListener(this);
    }

}
