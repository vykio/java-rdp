package com.tech.app.windows;

import com.tech.app.windows.handlers.MainWindowHandler;
import com.tech.app.windows.toolbars.MainToolbar;

public class MainWindow extends Window {

    public MainWindow(int width, int height) {
        super("Fenetre principale - RDP", width, height, true, true);
        setWindowHandler(new MainWindowHandler(this));

        build();
    }


    protected void build() {

        MainToolbar mt = new MainToolbar(this);

        mt.applyMenu();

        //this.setJMenuBar(this.createMainMenuBar());
    }

}
