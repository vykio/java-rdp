package com.tech.app.windows.toolbars;

import javax.swing.*;

public abstract class Toolbar extends JMenuBar {

    protected JFrame frame;
    protected JMenuBar toolbar;

    public Toolbar(JFrame frame) {
        this.frame = frame;
        this.toolbar = new JMenuBar();
    }

    public abstract JMenuBar getMenu();
    public void applyMenu() {
        this.frame.setJMenuBar(this.getMenu());
        this.frame.setVisible(true);
    }

}
