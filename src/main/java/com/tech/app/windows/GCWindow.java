package com.tech.app.windows;

import com.tech.app.functions.FUtils;
import com.tech.app.models.Model;
import com.tech.app.models.gma.CoverabilityGraph;
import com.tech.app.models.gma.ReachabilityGraph;
import com.tech.app.windows.handlers.GCWindowHandler;
import com.tech.app.windows.handlers.GMAWindowHandler;
import com.tech.app.windows.panels.GChandler;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class GCWindow extends Window{

    private final CoverabilityGraph coverabilityGraph;


    public GCWindow(int width, int height, Model model) throws UnsupportedLookAndFeelException {
        super(true, "Graphe de couverture - Java_RDP - " + FUtils.OS.getOs(), width, height, true, true);

        model.updateMatrices();
        this.coverabilityGraph = new CoverabilityGraph(model);
        coverabilityGraph.calculateCoverabilityGraph();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            //e.printStackTrace();
            System.out.println("Thème système non trouvé");
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        }
        setWindowHandler(new GCWindowHandler((this)));
        build();
    }




    @Override
    protected void build() {
        GChandler gChandler = new GChandler(this, coverabilityGraph.getListe_node());
        gChandler.init();
    }
}
