package com.tech.app.windows.panels;

import com.tech.app.functions.FUtils;
import com.tech.app.models.Model;
import com.tech.app.models.Place;
import com.tech.app.models.Transition;
import com.tech.app.windows.toolbars.StepperToolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class StepperHandler extends JPanel {

    private final JFrame frame;
    private final Model model;
    private final double scaleFactor = FUtils.Screen.getScaleFactor();


    public StepperHandler(JFrame frame, Model model){
        this.frame = frame;
        this.model=model;
    }

    public void applyPanel() {
        this.frame.add(this);
        this.frame.setVisible(true);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // clear

        Graphics2D gr = (Graphics2D) g;

        /* Anti-aliasing : Courbes lisses, c'est beau */
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        /* Appliquer le zoom */

        AffineTransform transform = AffineTransform.getScaleInstance(scaleFactor,scaleFactor);
        gr.setTransform(transform);

        /* Afficher chaque places et transitions, qui ne sont pas sélectionnées */
        for (Place p:model.placeVector) {
            p.draw(g);
        }
        for (Transition t:model.transitionVector) {
            if(!t.estFranchissable()){
                t.draw(g);
            } else {
                Color co = g.getColor();
                g.setColor(Color.RED);
                t.draw(g);
                g.setColor(co);
            }
        }
    }
}
