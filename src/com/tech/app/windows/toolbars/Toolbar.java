package com.tech.app.windows.toolbars;

import javax.swing.*;
import java.awt.*;

public abstract class Toolbar extends JToolBar {

    protected JFrame frame;
    protected JToolBar toolbar;

    public Toolbar(JFrame frame) {
        this.frame = frame;
        this.toolbar = new JToolBar();
    }

    public abstract JToolBar getToolbar();
    public void applyToolbar() {
        frame.getContentPane();
        frame.add( this.getToolbar(), BorderLayout.NORTH );
        this.frame.setVisible(true);

    }
}
