package com.tech.app.windows;

import com.tech.app.windows.handlers.MainWindowHandler;
import com.tech.app.windows.panels.DrawPanel;
import com.tech.app.windows.toolbars.DrawingToolbar;
import com.tech.app.windows.toolbars.Menu;
import com.tech.app.windows.toolbars.MenuBar;

import javax.swing.*;

public class MainWindow extends Window {

    public MainWindow(int width, int height) {
        super("Fenetre principale - RDP", width, height, true, true);
        setWindowHandler(new MainWindowHandler(this));
        build();
    }


    protected void build() {

        Menu menu = new Menu(this);
        menu.applyMenu();

        DrawingToolbar dToolbar = new DrawingToolbar(this);
        dToolbar.applyToolbar();

        DrawPanel dp = new DrawPanel(this);
        dp.applyPanel();

    }



}
