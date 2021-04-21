package com.tech.app.windows;

import com.tech.app.functions.FUtils;
import com.tech.app.models.Model;
import com.tech.app.models.gma.Node;
import com.tech.app.windows.handlers.GMAWindowHandler;
import com.tech.app.windows.panels.GMAhandler;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.util.List;

public class GMAWindow extends Window {

    private List<Node> liste_nodes;

    public GMAWindow(int width, int height) throws UnsupportedLookAndFeelException {
        super(true, "GMA - Java_RDP - " + FUtils.OS.getOs(), width, height, true, true);
        UIManager.setLookAndFeel(new MetalLookAndFeel());
        setWindowHandler(new GMAWindowHandler(this));
        build();
    }

    @Override
    protected void build() {
        GMAhandler gmah = new GMAhandler(this, liste_nodes);
        gmah.init();
    }
}
