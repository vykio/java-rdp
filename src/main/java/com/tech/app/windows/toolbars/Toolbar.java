package com.tech.app.windows.toolbars;

import javax.swing.*;
import java.awt.*;

/**
 * Classe abstraite utilisée pour créer la DrawingToolbar
 */
public abstract class Toolbar extends JToolBar {

    protected JFrame frame;
    protected JToolBar toolbar;

    /**
     * Constructeur
     * @param frame Fenêtre d'appel
     */
    public Toolbar(JFrame frame) {
        this.frame = frame;
        this.toolbar = new JToolBar();
    }

    /**
     * Récupérer la JToolBar
     * @return JToolBar
     */
    public abstract JToolBar getToolbar();

    /**
     * Appliquer la toolbar, la rendre visible dans la frame.
     */
    public void applyToolbar() {
        frame.getContentPane();
        frame.add( this.getToolbar(), BorderLayout.NORTH );
        this.frame.setVisible(true);

    }
}
