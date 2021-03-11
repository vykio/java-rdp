package com.tech.app.windows;

import com.tech.app.models.Model;
import com.tech.app.windows.handlers.MainWindowHandler;
import com.tech.app.windows.handlers.DrawMouse;
import com.tech.app.windows.panels.DrawPanel;
import com.tech.app.windows.toolbars.DrawingToolbar;
import com.tech.app.windows.toolbars.Menu;

public class MainWindow extends Window {

    public MainWindow(int width, int height) {
        super("Fenetre principale - RDP", width, height, true, true);
        setWindowHandler(new MainWindowHandler(this));
        build();
    }


    protected void build() {

        Menu menu = new Menu(this);
        menu.applyMenu();

        Model model = new Model();

        DrawPanel dp = new DrawPanel(this,model);
        dp.applyPanel();

        DrawMouse drawMouse = new DrawMouse(this,dp);

        DrawingToolbar dToolbar = new DrawingToolbar(this,drawMouse);
        dToolbar.applyToolbar();



    }



}
