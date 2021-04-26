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



    protected abstract void build();

    protected void setWindowHandler(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }


}
