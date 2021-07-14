package com.tech.app.windows.panels;

import com.tech.app.functions.FUtils;
import com.tech.app.models.Model;
import com.tech.app.models.Place;
import com.tech.app.models.Transition;
import com.tech.app.models.stepper.Stepper;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class StepperHandler extends JPanel {

    private final JFrame frame;
    public final Model model;

    public final double MAX_ZOOM = 3;
    public final double MIN_ZOOM = 0.5;

    public final double scaleFactor;
    public double scaleX;
    public double scaleY;

    public double mouseX, mouseY;
    private double arcOriginX = 0, arcOriginY =0, arcDestX=0, arcDestY=0;

    public AffineTransform transform;

    public final Stepper stepper;


    public StepperHandler(JFrame frame, Stepper stepper){
        this.scaleFactor = FUtils.Screen.getScaleFactor();
        this.scaleX = scaleFactor;
        this.scaleY = scaleFactor;

        this.frame = frame;
        this.model=stepper.model;
        this.stepper=stepper;

        this.transform  = AffineTransform.getScaleInstance(scaleX, scaleY);

        //System.out.println("stepper from handler hash : "+stepper.hashCode());


    }

    public void applyPanel() {
        this.frame.add(this);
        this.frame.setVisible(true);
        repaint();
    }

    public void updateInitPositions(){
        for(Place p : model.placeVector){
            p.updatePosition(p.getX(),p.getY() + 20);
        }

        for(Transition t : model.transitionVector){
            t.updatePosition(t.getX(),t.getY() + 20);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // clear

        Graphics2D gr = (Graphics2D) g;

        /* Anti-aliasing : Courbes lisses, c'est beau */
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        /* Appliquer le zoom */

        gr.setTransform(transform);

        /* Afficher chaque places et transitions, qui ne sont pas sélectionnées */
        for (Place p:model.placeVector) {
            //p.updatePosition(p.getX(),p.getY() + 40);
            p.draw(g);
        }
        for (Transition t:model.transitionVector) {
            //t.updatePosition(t.getX(),t.getY() + 40);
            if(!t.estFranchissable()){
                t.drawParents(g);
                t.drawChildren(g);
                t.draw(g);
            } else {
                Color co = g.getColor();
                g.setColor(Color.RED);
                t.drawParents(g);
                g.setColor(new Color(50,205,50));
                t.drawChildren(g);
                g.setColor(Color.BLUE);
                t.draw(g);
                g.setColor(co);

            }
        }

        if(model.getTransitionFranchissables().isEmpty()){
            Color color = g.getColor();
            g.setFont(new Font("Console", Font.PLAIN, (int)(15/scaleX*scaleFactor)));
            g.setColor(Color.RED);
            g.drawString("Le RdP a atteint un blocage",(int)(10/scaleX*scaleFactor), (int)((this.frame.getContentPane().getSize().getHeight()-80)*scaleFactor/scaleY));
            g.setColor(color);
        }


        if(!stepper.getSequenceTransition().isEmpty() && stepper.showSequence) {
            drawSequence(g);
        }

    }

    public void updatePositions(double scaleX, double scaleY, double dx, double dy) {
        for (Place p : model.placeVector) {
            p.updatePosition(p.getX() + dx * 1 / scaleX, p.getY() + dy * 1 / scaleY);
            repaint();
        }
        for (Transition t : model.transitionVector) {
            t.updatePosition(t.getX() + dx * 1 / scaleX, t.getY() + dy * 1 / scaleY);
            repaint();
        }

        /* Mettre à jour les coordonnées des arcs en cours de création */
        arcOriginX += dx / scaleX*scaleFactor;
        arcOriginY += dy / scaleY*scaleFactor;
        arcDestX += dx / scaleX*scaleFactor;
        arcDestY += dy / scaleY*scaleFactor;
    }

    public Object getSelectedObject(double x, double y){
        List<Transition> transitionFranchissable = model.getTransitionFranchissables();
        for(Transition t : transitionFranchissable){
            if(t.forme.contains(x,y)){
                return t;
            }
        }
        return null;
    }

    private void drawSequence(Graphics g){
        g.setFont(new Font("Console", Font.PLAIN, (int)(15/scaleX*scaleFactor)));
        g.drawString("Séquence : {"+stepper.getSequenceTransitionToString(stepper.getLast20FromSequence()),(int)(10/scaleX*scaleFactor), (int)((this.frame.getContentPane().getSize().getHeight()-60)*scaleFactor/scaleY));    }
}
