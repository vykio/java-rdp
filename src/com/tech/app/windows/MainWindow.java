package com.tech.app.windows;

import com.tech.app.windows.handlers.MainWindowHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.tools.Tool;

public class MainWindow extends Window {

    public MainWindow(int width, int height) {
        super("Fenetre principale - RDP", width, height, true, true);
        setWindowHandler(new MainWindowHandler(this));

        build();
    }


    protected void build() {

        Toolbar toolbar = new Toolbar();
        toolbar.createMainToolbar();
        toolbar.setVisible(true);

        //this.setJMenuBar(this.createMainMenuBar());

    }

}
