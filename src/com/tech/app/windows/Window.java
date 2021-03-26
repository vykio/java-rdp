/**
 * Classe Window générique, voir MainWindow.java pour voir comment l'utiliser
 */

package com.tech.app.windows;

import com.tech.app.windows.handlers.WindowHandler;

import javax.swing.*;
import java.awt.*;

public abstract class Window extends JFrame {

    protected String title;
    protected int width, height;
    protected WindowHandler windowHandler;

    protected boolean resizable;
    protected boolean visible;

    public Window(String title, int width, int height, WindowHandler windowHandler, boolean resizable, boolean visible) {
        this.title = title;
        this.width = width;
        this.height = height;

        this.windowHandler = windowHandler;

        this.resizable = resizable;
        this.visible = visible;

        this.setTitle(this.title);
        this.setSize(this.width, this.height);
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.setResizable(this.resizable);
        this.setVisible(this.visible);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Window(String title, int width, int height, boolean resizable, boolean visible) { this(title, width, height, null, resizable, visible); }

    public Window(String title, int width, int height) { this(title, width, height, null, true, true); }

    public Window(String title) { this(title, 500, 500, null, true, true); }

    public Window() { this("DEBUG - TEST WINDOW", 500, 500, null, true, true); }



    protected abstract void build();

    protected void setWindowHandler(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }


}
