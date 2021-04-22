package com.tech.app.windows;

import com.tech.app.functions.FUtils;
import com.tech.app.models.Model;
import com.tech.app.models.gma.ReachabilityGraph;
import com.tech.app.windows.handlers.DrawMouse;
import com.tech.app.windows.handlers.MainWindowHandler;
import com.tech.app.windows.handlers.SaveManager;
import com.tech.app.windows.panels.DrawPanel;
import com.tech.app.windows.toolbars.DrawingToolbar;
import com.tech.app.windows.toolbars.Menu;

import javax.swing.*;
import javax.swing.plaf.metal.*;

public class MainWindow extends Window {

    public MainWindow(int width, int height) throws UnsupportedLookAndFeelException {
        super(false, "Java_RDP - " + FUtils.OS.getOs(), width, height, true, true);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            //e.printStackTrace();
            System.out.println("Thème système non trouvé");
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        }
        setWindowHandler(new MainWindowHandler(this));
        build();
    }


    protected void build() {

        SaveManager sm = new SaveManager();
        Model model = new Model();

        Menu menu = new Menu(this);
        menu.applyMenu();
        menu.applyModel(model);
        menu.applySaveManager(sm);

        DrawPanel dp = new DrawPanel(this,model);
        dp.applyPanel();
        menu.applyDrawPanel(dp);

        DrawMouse drawMouse = new DrawMouse(this,dp);

        DrawingToolbar dToolbar = new DrawingToolbar(this,drawMouse);
        dToolbar.applyToolbar();
        dToolbar.applyModel(model);
        dToolbar.applySaveManager(sm);
        dToolbar.applyDrawPanel(dp);
        dToolbar.applyMenuBridge(menu);
    }

}
