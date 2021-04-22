package com.tech.app.windows.panels;

import com.tech.app.functions.FUtils;
import com.tech.app.models.*;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Ordre d'affichage :
 *  1. Places
 *  2. Transitions
 *  3. Objet tenu dans la souris (pour qu'il soit placé au dessus des autres)
 *
 * */

public class DrawPanel extends JPanel {

    private final JFrame frame;
    public Model model;

    private double arcOriginX = 0, arcOriginY =0, arcDestX=0, arcDestY=0;
    private int indexOfClickArc = 0;
    public Object selectedObject = null;
    private boolean clickError = false;

    /* Variables de départ pour indexation P et T */
    private int idPlace=0;
    private int idTransition = 0;

    /* Zoom du canvas */
    public final double MAX_ZOOM = 3;
    public final double MIN_ZOOM = 0.5;

    /* Variables d'agrandissement et zoom */
    public double scaleFactor;
    public double scaleX;
    public double scaleY;

    /* Conversion string --> int */
    public int convert;


    public AffineTransform transform;

    String list[] = new String[30];

    public DrawPanel(JFrame frame, Model model) {
        this.scaleFactor = FUtils.Screen.getScaleFactor();
        //System.out.println("Scale Factor: " + this.scaleFactor);
        this.scaleX = this.scaleFactor;
        this.scaleY = this.scaleFactor;

        this.frame = frame;
        this.model = model;
        this.transform  = AffineTransform.getScaleInstance(scaleX, scaleY);
        //this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
    }

    /* Utilisé pour déplacer tous les objets (click-molette) */
    public void updatePositions(double scaleX, double scaleY, double dx, double dy) {
        for (Place p : model.placeVector) {
            p.updatePosition(p.getX() + dx * 1 / scaleX, p.getY() + dy * 1 / scaleY);
            repaint();
        }
        for (Transition t : model.transitionVector) {
            t.updatePosition(t.getX() + dx * 1 / scaleX, t.getY() + dy * 1 / scaleY);
            /*for (Arc a : t.getChildrens()) {
                a.getPointCtr1().updatePosition();
            }*/
            repaint();
        }

        /* Mettre à jour les coordonnées des arcs en cours de création */
        arcOriginX += dx / scaleX*scaleFactor;
        arcOriginY += dy / scaleY*scaleFactor;
        arcDestX += dx / scaleX*scaleFactor;
        arcDestY += dy / scaleY*scaleFactor;
    }

    /* Bouger un objet donné en paramètre */
    public void updatePosition(Object obj, double x, double y, double scaleX, double scaleY, double dx, double dy) {
        selectedObject = obj;
        if (obj != null) {
            if (obj instanceof Place) {
                Place p = (Place) obj;
                //if (p.forme.getBounds2D().contains(x, y)) {
                    p.updatePosition(p.getX() + dx * 1 / scaleX, p.getY() + dy * 1 / scaleY);
                    repaint();
                //}
            } else if (obj instanceof PointControle) {
                PointControle pt = (PointControle) obj;
                pt.updatePosition(pt.getX() + dx * 1 / scaleX, pt.getY() + dy * 1 / scaleY);
                repaint();
            } else {
                Transition p = (Transition) obj;
                //if (p.forme.getBounds2D().contains(x, y)) {
                    p.updatePosition(p.getX() + dx * 1 / scaleX, p.getY() + dy * 1 / scaleY);
                    repaint();
                //}
            }
        }
    }

    public void printModel(){
        System.out.println(model);
    }

    public double mouseX, mouseY;

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
            if (p != selectedObject) {
                p.draw(g);
            }
        }
        for (Transition t:model.transitionVector) {
            if (t != selectedObject) {
                t.draw(g);
            }
        }

        drawTooltips(g);

        /* Afficher l'objet sélectionné au dessus des autres:
        * donc affichage en dernier */
        if (selectedObject != null) {
            Color co = g.getColor();
            g.setColor(Color.BLUE);

            if (selectedObject instanceof Place) {
                ((Place) selectedObject).draw(g);
            } else if (selectedObject instanceof Transition) {
                ((Transition) selectedObject).draw(g);
            }
            g.setColor(co);

            Color color = g.getColor();
            g.setColor(Color.BLACK);
            g.setFont(new Font("Console", Font.PLAIN, (int)(scaleFactor*15/scaleX)));
            g.drawString(selectedObject.toString(), (int)(10/scaleX), (int)((this.frame.getContentPane().getSize().getHeight()-50)*scaleFactor/scaleY));
            g.setColor(color);
        }

    }

    private void drawTooltips(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.BLUE);
        g.setFont(new Font("Console", Font.PLAIN, (int)(15/scaleX*scaleFactor)));
        if (this.indexOfClickArc == 1) {
            g.drawString("Arc origin set", (int)(10/scaleX*scaleFactor), (int)((this.frame.getContentPane().getSize().getHeight()-80)*scaleFactor/scaleY));
        } else if (this.clickError) {
            g.setColor(Color.RED);
            g.drawString("Arc illegal", (int)(10/scaleX*scaleFactor), (int)((this.frame.getContentPane().getSize().getHeight()-80)*scaleFactor/scaleY));
        }
        //g.drawString("X:" + FMaths.round(mouseX/scaleX,2) + "-Y:" + FMaths.round(mouseY/scaleY, 2), (int)(10/scaleX*scaleFactor), (int)(50/scaleY*scaleFactor));
        g.setColor(color);
    }

    /* Nettoyer tout ! (model et canvas) */
    public void clearAll() {
        model.clearAll();
        selectedObject = null;
        idTransition = 0;
        idPlace = 0;
        repaint();
    }

    /* Ajouter une place au système */
    public void addPlace(double x, double y){
        model.addPlace(new Place("P" + idPlace, x, y));
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
                this.clickError = false;
                if (obj1 instanceof Transition) {
                    //System.out.println(obj1);
                    //System.out.println(obj2);
                    ((Transition) obj1).addParent(new Arc((Place) obj2, 1, ((Transition) obj1).getX(), ((Transition) obj1).getY(), false, (Transition)obj1));
                } else {
                    //System.out.println(obj1);
                    //System.out.println(obj2);
                    ((Transition) obj2).addChildren(new Arc((Place) obj1, 1, ((Transition) obj2).getX(), ((Transition) obj2).getY(), true, (Transition)obj2));
                }

            } else {
                this.clickError = true;
            }

        }
        //System.out.println("ce : " + clickError);
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
        /* Si il n'y a pas eu de 1er click en mode Arc */
        if (indexOfClickArc == 0 && getSelectedObject(x,y) != null) {
            this.arcOriginX = x;
            this.arcOriginY = y;
            this.arcDestX = 0;
            this.arcDestY = 0;
            this.indexOfClickArc = 1;
        } else {
            // Si nous cliquons pour la deuxieme fois en mode Arc
            this.arcDestX = x;
            this.arcDestY = y;
            this.addArc(this.arcOriginX, this.arcOriginY, this.arcDestX, this.arcDestY);
            this.indexOfClickArc = 0;
        }
        repaint();
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
        for (Transition t : model.transitionVector) {
            for (Arc a : t.getChildrens()) {
                System.out.println("Pt1 > " + a.getPointCtr1());
                if(a.containsControlPoint1(x,y)) {
                    a.getPointCtr1().setMoved(true);
                    return a.getPointCtr1();
                }
            }
        }
        return null;
    }

    public void selectObject(Object obj) {
        selectedObject = obj;
        this.clickError = false;
        repaint();
    }

    public void deleteSelectedObject() {
        if (selectedObject != null) {
            if (selectedObject instanceof Place) {
                this.model.removePlace((Place)selectedObject);
            } else {
                this.model.removeTransition((Transition) selectedObject);
            }
            repaint();
        }
    }

    /* Afficher dans la console le système */
    public void showModel() {
        if(model.placeVector.size() != 0 && model.transitionVector.size() != 0){
            model.updateMatrices();
            System.out.println(model);

            // mise au format latex de la matrice
            TeXFormula formula = new TeXFormula(String.valueOf(model.get_C()));
            TeXIcon ti = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY,20);
            BufferedImage b = new BufferedImage(ti.getIconWidth(), ti.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            ti.paintIcon(new JOptionPane(), b.getGraphics(), 0, 0);

            JOptionPane.showMessageDialog(frame.getContentPane(), null, "Matrice C", JOptionPane.PLAIN_MESSAGE,ti);
        } else {
            JOptionPane.showMessageDialog(frame.getContentPane(), "Veuillez créer un RDP pour pouvoir créer une matrice", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void showOptions(Object obj) {
        if (obj instanceof Place) {
            try {
                String result = JOptionPane.showInputDialog("Marquage :");
                int num = Integer.parseInt(result);
                ((Place)obj).setMarquage(num);
            } catch (Exception e){
                JOptionPane.showMessageDialog(frame.getContentPane(), "Error: only integers are allowed");
            }

        } else {
            try {
                Object[] orientation = { "Verticale", "Horizontale" };
                JComboBox comboBox = new JComboBox(orientation);
                JOptionPane.showMessageDialog(null, comboBox, "Orientation de la transition", JOptionPane.QUESTION_MESSAGE);
                ((Transition)obj).changeOrientation(comboBox.getSelectedIndex());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame.getContentPane(), "Error...");
            }
        }
        repaint();
    }

    public void showOptionsLabel(Object obj) {
        if (obj instanceof Place || obj instanceof Transition ) {
            try {

             JPanel panel = new JPanel(new BorderLayout(5, 5));
             JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
             label.add (new JLabel("Label : ", SwingConstants.RIGHT));
             label.add (new JLabel("Position :", SwingConstants.LEFT));
             panel.add(label, BorderLayout.WEST);

             ImageIcon icon = new ImageIcon (getClass().getResource("/icons/position.png"));

            JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
            JTextField Label = new JTextField();
            controls.add(Label);
            JTextField Position = new JTextField("4");
            controls.add(Position);
            panel.add(controls, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(frame, panel, "Label / Position", JOptionPane.QUESTION_MESSAGE, icon);

            convert = Integer.parseInt(Position.getText());
            System.out.println(Label.getText());
            System.out.println(Position.getText());

                if (obj instanceof Place) {
                    ((Place) obj).addLabel(Label.getText());
                    ((Place) obj).addPosition(convert);
                } else if(obj instanceof Transition){
                    ((Transition) obj).addLabel(Label.getText());
                    ((Transition) obj).addPosition(convert);
                }

            } catch (Exception e){
                JOptionPane.showMessageDialog(frame.getContentPane(), "Error: only string are allowed");
            }
        }
        repaint();
    }

    public void errorSelect(){
        JOptionPane.showMessageDialog(frame.getContentPane(), "Veuillez sélectionner une place ou transition");
        repaint();
    }
}
