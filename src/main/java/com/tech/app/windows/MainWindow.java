package com.tech.app.windows;

import com.tech.app.functions.FUtils;
import com.tech.app.models.Model;
import com.tech.app.windows.handlers.DrawMouse;
import com.tech.app.windows.handlers.MainWindowHandler;
import com.tech.app.windows.handlers.SaveManager;
import com.tech.app.windows.panels.DrawPanel;
import com.tech.app.windows.toolbars.DrawingToolbar;
import com.tech.app.windows.toolbars.Menu;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * Classe qui permet de créer la fenêtre de l'application qui hérite de la classe abstraite Window.
 */
public class MainWindow extends Window {

    /**
     * Constructeur qui permet de créer la fenêtre de l'application.
     * @param width : largeur.
     * @param height : hauteur.
     * @throws UnsupportedLookAndFeelException : dans le cas où on ne peut pas récupérer le look du système de l'utilisateur.
     */
    public MainWindow(int width, int height) throws UnsupportedLookAndFeelException {
        super(false, "Java_RDP - " + FUtils.OS.getOs() + " - v" + FUtils.Program.getVersion(), width, height, true, true);
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

    /**
     * Méthode qui permet de mettre en place les différentes fonctionnalités de la fenêtre principale :
     * - Sauvegarde,
     * - Menu / Barre d'outils,
     * - Zone de dessin,
     * - Gestionnaire de la souris.
     */
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

        DrawMouse drawMouse = new DrawMouse(dp);

        DrawingToolbar dToolbar = new DrawingToolbar(this,drawMouse);
        dToolbar.applyToolbar();
        dToolbar.applyMenuBridge(menu);
    }

}
