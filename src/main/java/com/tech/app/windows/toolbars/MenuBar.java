package com.tech.app.windows.toolbars;

import javax.swing.*;

/**
 * Classe abstraite utilisée pour créer la barre de menu
 */
public abstract class MenuBar extends JMenuBar {

    protected JFrame frame;
    protected JMenuBar toolbar;

    /**
     * Constructeur de la classe Abstraite MenuBar
     * @param frame Fenêtre d'appel
     */
    public MenuBar(JFrame frame) {
        this.frame = frame;
        this.toolbar = new JMenuBar();
    }

    /**
     * Récupérer l'objet JMenuBar
     * @return JMenuBar
     */
    public abstract JMenuBar getMenu();

    /**
     * Applique le menu, le rendre visible sur la frame.
     */
    public void applyMenu() {
        this.frame.setJMenuBar(this.getMenu());
        this.frame.setVisible(true);
    }

}
