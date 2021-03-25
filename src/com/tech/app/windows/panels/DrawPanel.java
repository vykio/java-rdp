package com.tech.app.windows.panels;

import com.tech.app.models.Arc;
import com.tech.app.models.Model;
import com.tech.app.models.Place;
import com.tech.app.models.Transition;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Ordre d'affichage :
 *  1. Places
 *  2. Transitions
 *  3. Objet tenu dans la souris (pour qu'il soit placé au dessus des autres)
 *
 * */

public class DrawPanel extends JPanel {

    private final JFrame frame;
    private final Model model;

    private double arcOriginX = 0, arcOriginY =0, arcDestX=0, arcDestY=0;
    public Object draggedObject = null;

    /* Variables de départ pour indexation P et T */
    private int idPlace=0;
    private int idTransition = 0;

    /* Zoom du canvas */
    public final double MAX_ZOOM = 3;
    public final double MIN_ZOOM = 0.5;

    /* Variables d'agrandissement et zoom */
    public double scaleX = 1;
    public double scaleY = 1;
    public AffineTransform transform = AffineTransform.getScaleInstance(scaleX, scaleY);

    public DrawPanel(JFrame frame, Model model) {
        this.frame = frame;
        this.model = model;
    }

    /* Utilisé pour déplacer tous les objets (click-molette) */
    public void updatePositions(double scaleX, double scaleY, int dx, int dy) {
        for (Place p : model.placeVector) {
            p.updatePosition(p.getX() + dx * 1 / scaleX, p.getY() + dy * 1 / scaleY);
            repaint();
        }
        for (Transition t : model.transitionVector) {
            t.updatePosition(t.getX() + dx * 1 / scaleX, t.getY() + dy * 1 / scaleY);
            repaint();
        }
    }

    /* Bouger un objet donné en paramètre */
    public void updatePosition(Object obj, double x, double y, double scaleX, double scaleY, int dx, int dy) {
        draggedObject = obj;
        if (obj != null) {
            if (obj instanceof Place) {
                Place p = (Place) obj;
                if (p.forme.getBounds2D().contains(x, y)) {
                    p.updatePosition(p.getX() + dx * 1 / scaleX, p.getY() + dy * 1 / scaleY);
                    repaint();
                }
            } else {
                Transition p = (Transition) obj;
                if (p.forme.getBounds2D().contains(x, y)) {
                    p.updatePosition(p.getX() + dx * 1 / scaleX, p.getY() + dy * 1 / scaleY);
                    repaint();
                }
            }
        }
    }

    /* Méthode pour afficher à l'écran */
    @Override
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
            if (p != draggedObject) {
                p.draw(g);
            }
        }
        for (Transition t:model.transitionVector) {
            if (t != draggedObject) {
                t.draw(g);
            }
        }

        /* Afficher l'objet sélectionné au dessus des autres:
        * donc affichage en dernier */
        if (draggedObject != null) {
            if (draggedObject instanceof Place) {
                ((Place)draggedObject).draw(g);
            } else {
                ((Transition)draggedObject).draw(g);
            }
        }

    }

    /* Nettoyer tout ! (model et canvas) */
    public void clearAll() {
        model.clearAll();
        draggedObject = null;
        repaint();
    }

    /* Ajouter une place au système */
    public void addPlace(double x, double y){
        model.addPlace(new Place("p" + idPlace, x, y));
        this.idPlace++;
        repaint();
    }

    /* Ajouter un arc au système */
    public void addArc(double x1,double y1, double x2, double y2){
        Object obj1 = getSelectedObject(x1, y1);
        Object obj2 = getSelectedObject(x2, y2);

        /* Déterminer le sens de la fleche */
        if (obj1 != null && obj2 != null) {

            if (obj1.getClass() != obj2.getClass()) {

                if (obj1 instanceof Transition) {
                    ((Transition) obj1).addParent(new Arc((Place) obj2, 1, ((Transition) obj1).getX(), ((Transition) obj1).getY(), false));
                } else {
                    ((Transition) obj2).addChildren(new Arc((Place) obj1, 1, ((Transition) obj2).getX(), ((Transition) obj2).getY(), true));
                }

            }

        }
        repaint();

    }

    /* Ajouter l'objet transition au système */
    public void addTransition(double x, double y){
        model.addTransition(new Transition("t" + idTransition,x,y));
        idTransition++;
        repaint();
    }

    /* Rendre le JPanel visible sur la fenêtre */
    public void applyPanel() {
        this.frame.add(this);
        this.frame.setVisible(true);
        repaint();
    }

    /* Déterminer les deux couples de coordonnées pour créer un arc */
    public void loadCoordinatesArc(double x, double y) {
        if (this.arcDestX == 0 && this.arcOriginX == 0) {
            this.arcOriginX = x;
            this.arcOriginY = y;
        } else if (this.arcDestX == 0) {
            this.arcDestX = x;
            this.arcDestY = y;
            this.addArc(this.arcOriginX, this.arcOriginY, this.arcDestX, this.arcDestY);
        } else {
            this.arcOriginX = x;
            this.arcOriginY = y;
            this.arcDestX = 0;
            this.arcDestY = 0;
        }
    }

    /* Retourner l'objet sur lequel on a cliqué */
    public Object getSelectedObject(double x, double y) {
        for (Place p:model.placeVector) {
            if (p.forme.contains(x,y)) {
                return p;
            }
        }
        for (Transition t:model.transitionVector) {
            if (t.forme.contains(x,y)) {
                return t;
            }
        }
        return null;
    }

    /* Afficher dans la console le système */
    public void showModel() {
        System.out.println(model);
    }

}
