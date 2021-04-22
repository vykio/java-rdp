package com.tech.app.windows;

import com.tech.app.functions.FUtils;
import com.tech.app.models.Model;
import com.tech.app.models.gma.ReachabilityGraph;
import com.tech.app.windows.handlers.GMAWindowHandler;
import com.tech.app.windows.panels.GMAhandler;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class GMAWindow extends Window {

    private ReachabilityGraph reachabilityGraph;

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

    @Override
    protected void build() {
        GMAhandler gmah = new GMAhandler(this, reachabilityGraph.getListe_node());
        gmah.init();
    }
}
