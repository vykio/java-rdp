/**
 * Classe générique, utilisée pour gérer les evenements de fenetre,
 * voir MainWindowHandler.java pour voir comment l'utiliser
 */

package com.tech.app.windows.handlers;

import javax.swing.*;
import java.awt.event.WindowListener;

public abstract class WindowHandler implements WindowListener {

    protected JFrame frame;

    public WindowHandler(JFrame frame) {
        this.frame = frame;
        this.frame.addWindowListener(this);
    }

}
