package com.tech.app.windows;

import com.tech.app.windows.handlers.WindowHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Classe abstraite qui permet d'avoir la base d'une fenêtre.
 */
public abstract class Window extends JFrame {

    protected String title;
    protected int width, height;
    protected WindowHandler windowHandler;

    protected boolean resizable;
    protected boolean visible;

    /**
     * Constructeur qui permet de créer une fenêtre.
     * @param subWindow : booléen pour savoir si la fenêtre est une sous-fenêtre.
     * @param title : titre de la fenêtre.
     * @param width : largeur.
     * @param height : hauteur.
     * @param windowHandler : gestionnaire de fenêtre.
     * @param resizable : booléen pour savoir si la fenêtre peut être redimensionner par l'utilisateur.
     * @param visible : booléen pour savoir si la fenêtre est visible.
     */
    public Window(boolean subWindow, String title, int width, int height, WindowHandler windowHandler, boolean resizable, boolean visible) {
        this.title = title;
        this.width = width;
        this.height = height;

        this.windowHandler = windowHandler;

        this.resizable = resizable;
        this.visible = visible;

        this.setTitle(this.title);
        this.setSize(this.width, this.height);
        this.setMinimumSize(new Dimension(this.width, this.height));
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.setResizable(this.resizable);
        this.setVisible(this.visible);

        this.setLocationRelativeTo(null);
        if (subWindow) {
            this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        } else {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        this.pack();
    }

    public Window(boolean subWindow, String title, int width, int height, boolean resizable, boolean visible) { this(subWindow, title, width, height, null, resizable, visible); }

    public Window(boolean subWindow, String title, int width, int height) { this(subWindow, title, width, height, null, true, true); }

    public Window(boolean subWindow, String title) { this(subWindow, title, 500, 500, null, true, true); }

    public Window() { this(true, "DEBUG - TEST WINDOW", 500, 500, null, true, true); }

    /**
     * Méthode qui permet d'initialiser la fenêtre.
     */
    protected abstract void build();

    /**
     * Méthode qui permet d'attribuer un gestionnaire de fenêtre à une fenêtre.
     * @param windowHandler : gestionnaire de fenêtre.
     */
    protected void setWindowHandler(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }


}
