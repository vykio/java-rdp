package com.tech.app.windows.panels;

import com.tech.app.models.Model;
import com.tech.app.models.Place;
import com.tech.app.models.Transition;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawPanel extends JPanel {

    private final JFrame frame;
    private final Model model;

    public final double MAX_ZOOM = 3;
    public final double MIN_ZOOM = 0.5;

    public double scaleX = 1;
    public double scaleY = 1;
    public AffineTransform transform = AffineTransform.getScaleInstance(scaleX, scaleY);

    public DrawPanel(JFrame frame, Model model) {
        this.frame = frame;
        this.model = model;
    }

    public void updatePositions(double scaleX, double scaleY, int dx, int dy) {
        for (Place p : model.placeVector) {
            //if (p.forme.getBounds2D().contains(x, y)) {
            p.updatePosition(p.getX() + dx * 1 / scaleX, p.getY() + dy * 1 / scaleY);
            repaint();
            //}
        }
        for (Transition t : model.transitionVector) {
            //if (p.forme.getBounds2D().contains(x, y)) {
            t.updatePosition(t.getX() + dx * 1 / scaleX, t.getY() + dy * 1 / scaleY);
            repaint();
            //}
        }
    }

    public void updatePosition(double x, double y, double scaleX, double scaleY, int dx, int dy) {
        for (Place p : model.placeVector) {
            if (p.forme.getBounds2D().contains(x, y)) {
                p.updatePosition(p.getX() + dx * 1 / scaleX, p.getY() + dy * 1 / scaleY);
                repaint();
            }
        }
        for (Transition t : model.transitionVector) {
            if (t.forme.getBounds2D().contains(x, y)) {
                t.updatePosition(t.getX() + dx * 1 / scaleX, t.getY() + dy * 1 / scaleY);
                repaint();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // clear
        Graphics2D gr = (Graphics2D) g;
        gr.setTransform(transform);

        for (Place p:model.placeVector) {
            System.out.println("Nom "+p.getName()+"X "+p.getX()+"Y " + p.getY());
            //paintPlace(g,p.getName(),p.getX(),p.getY());
            p.draw(g);
        }

        for (Transition t:model.transitionVector) {
            System.out.println("Nom "+t.getName()+"X "+t.getX()+"Y " + t.getY());
            t.draw(g);
        }

        //g.fillRect((int)myRect.x, (int)myRect.y, (int)myRect.width, (int)myRect.height);
        
    }

    public void clearAll() {
        model.clearAll();
        repaint();
    }

    public void addPlace(double x, double y){
        //System.out.println("Je suis dans la fn addPLace");
        //System.out.println(x + " " + y);
        model.addPlace(new Place("p0", x, y));
        repaint();
    }

    public void addArc(int x1,int y1, int x2, int y2){
        System.out.println("on verra apres");
    }

    public void addTransition(double x, double y){
        System.out.println("Je suis dans la fn addTransition");
        model.addTransition(new Transition("t0",x,y));
        repaint();
    }

    public void applyPanel() {
        this.frame.add(this);
        this.frame.setVisible(true);
        repaint();
    }



}
