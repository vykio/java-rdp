package com.tech.app.windows;

import com.tech.app.functions.FUtils;
import com.tech.app.models.Model;
import com.tech.app.models.gma.ReachabilityGraph;
import com.tech.app.windows.handlers.GMAWindowHandler;
import com.tech.app.windows.panels.GMAhandler;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * Classe qui permet de créer la fenêtre du GMA qui hérite de la classe abstraite Window.
 */
public class GMAWindow extends Window {

    private final ReachabilityGraph reachabilityGraph;

    /**
     * Constructeur de la fenêtre du GMA.
     * @param width : largeur.
     * @param height : hauteur.
     * @param model : modèle actuel
     * @throws UnsupportedLookAndFeelException : dans le cas où on ne peut pas récupérer le look du système de l'utilisateur.
     */
    public GMAWindow(int width, int height, Model model) throws UnsupportedLookAndFeelException {
        super(true, "GMA - Java_RDP - " + FUtils.OS.getOs(), width, height, true, true);

        model.updateMatrices();
        this.reachabilityGraph = new ReachabilityGraph(model);
        reachabilityGraph.calculateReachabilityGraph();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            //e.printStackTrace();
            System.out.println("Thème système non trouvé");
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        }        setWindowHandler(new GMAWindowHandler(this));
        build();
    }

    /**
     * Méthode qui permet de construire le GMA dans la fenêtre.
     */
    @Override
    protected void build() {
        GMAhandler gmah = new GMAhandler(this, reachabilityGraph.getListe_node());
        gmah.init();
    }
}
