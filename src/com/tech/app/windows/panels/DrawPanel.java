package com.tech.app.windows.panels;

import com.tech.app.functions.FUtils;
import com.tech.app.models.*;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/*
 * Ordre d'affichage :
 *  1. Places
 *  2. Transitions
 *  3. Objet tenu dans la souris (pour qu'il soit placé au dessus des autres)
 *
 */

/**
 * Classe qui gère la zone de dessin
 */
public class DrawPanel extends JPanel {

    private final JFrame frame;
    public Model model;

    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final String DELETE = "delete";

    private double arcOriginX = 0, arcOriginY =0, arcDestX=0, arcDestY=0;
    private int indexOfClickArc = 0;
    public Object selectedObject = null;
    private boolean clickError = false;

    /* Variables de départ pour indexation P et T */
    private int idPlace=0;
    private int idTransition = 0;


    /**
     * Zoom maximal de la zone dessin
     */
    public final double MAX_ZOOM = 3;
    /**
     * Zoom minimal de la zone dessin
     */
    public final double MIN_ZOOM = 0.5;

    /* Variables d'agrandissement et zoom */
    public double scaleFactor;
    public double scaleX;
    public double scaleY;

    /* Conversion string --> int */
    public int convert;


    public AffineTransform transform;

    /**
     * Constructeur du Panel de dessin
     * @param frame Frame de la fenêtre
     * @param model Modèle du système
     */
    public DrawPanel(JFrame frame, Model model) {
        this.scaleFactor = FUtils.Screen.getScaleFactor();

        this.scaleX = this.scaleFactor;
        this.scaleY = this.scaleFactor;

        this.frame = frame;
        this.model = model;
        this.transform  = AffineTransform.getScaleInstance(scaleX, scaleY);

        this.getInputMap(IFW).put(KeyStroke.getKeyStroke("DELETE"), DELETE);
        this.getActionMap().put(DELETE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedObject();
            }
        });
    }

    /**
     * Méthode qui déplace tous les objets du panel de dessin, appelée
     * lors de l'appui clic-molette. Elle met à jour les positions de
     * tous les objets en fonction du facteur d'agrandissement (lié au
     * zoom et au facteur d'agrandissement de l'OS) ainsi que du
     * déplacement (dx, dy). Nous utilisons (dx,dy) car cette fonction est
     * appelée à chaque tick.
     * @param scaleX Facteur d'agrandissement sur X
     * @param scaleY Facteur d'agrandissement sur Y
     * @param dx Plus petit déplacement sur X
     * @param dy Plus petit déplacement sur Y
     */
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

    /**
     * Méthode qui déplace l'objet donné en paramètre
     * en fonction des paramètres de facteur d'agrandissement
     * et de déplacement (dx,dy) de la souris. C'est le même
     * fonctionnement que la méthode updatePositions(), mais
     * pour un seul objet. Utilisé par l'outil de sélection.
     * @param obj : Objet.
     * @param scaleX : Facteur d'agrandissement sur X
     * @param scaleY : Facteur d'agrandissement sur Y
     * @param dx : Plus petit déplacement sur X
     * @param dy : Plus petit déplacement sur Y
     */
    public void updatePosition(Object obj, double scaleX, double scaleY, double dx, double dy) {
        selectedObject = obj;
        if (obj != null) {
            if (obj instanceof Place) {
                Place p = (Place) obj;
                p.updatePosition(p.getX() + dx * 1 / scaleX, p.getY() + dy * 1 / scaleY);
            } else if (obj instanceof PointControle) {
                PointControle pt = (PointControle) obj;
                pt.updatePosition(pt.getX() + dx * 1 / scaleX, pt.getY() + dy * 1 / scaleY);
            } else {
                Transition p = (Transition) obj;
                    p.updatePosition(p.getX() + dx * 1 / scaleX, p.getY() + dy * 1 / scaleY);
            }
            repaint();
        }
    }

    /**
     * Afficher le modèle dans la console
     */
    public void printModel(){
        System.out.println(model);
    }

    public double mouseX, mouseY;

    /**
     * Afficher tous les objets dans la zone de dessin
     * @param g Objet graphique (Graphics)
     */
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

    /**
     * Utilisé pour afficher du texte dans la zone de dessin.
     * Notamment pour afficher l'information à l'utilisateur
     * que l'arc a bien été défini.
     * @param g Objet Graphics
     */
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

    /**
     * Nettoyer le modèle, et la zone de dessin.
     */
    public void clearAll() {
        model.clearAll();
        selectedObject = null;
        idTransition = 0;
        idPlace = 0;
        repaint();
    }

    /**
     * Ajouter une place au système et dans la zone de dessin
     * @param x Coordonnée X du nouvel objet
     * @param y Coordonnée Y du nouvel objet
     */
    public void addPlace(double x, double y){
        model.addPlace(new Place("P" + idPlace, x, y));
        this.idPlace++;
        repaint();
    }

    /**
     * Ajouter une arc au système d'un point d'origine
     * (x1, y1) au point d'arrivée (x2, y2). Ne fonctionne
     * pas si au moins un des deux couples de coordonnées
     * ne correspond pas à un objet déjà existant.
     * @param x1 Coordonnée X de l'objet 1
     * @param y1 Coordonnée Y de l'objet 1
     * @param x2 Coordonnée X de l'objet 2
     * @param y2 Coordonnée Y de l'objet 2
     */
    public void addArc(double x1,double y1, double x2, double y2){
        Object obj1 = getSelectedObject(x1, y1);
        Object obj2 = getSelectedObject(x2, y2);

        /* Déterminer le sens de la fleche */
        if (obj1 != null && obj2 != null) {

            if (obj1.getClass() != obj2.getClass()) {
                this.clickError = false;
                if (obj1 instanceof Transition) {
                    Arc a = new Arc((Place) obj2, 1, ((Transition) obj1).getX(), ((Transition) obj1).getY(), false, (Transition)obj1);
                    ((Transition) obj1).addChildren(a);
                    model.addArc(a);
                } else {
                    Arc b = new Arc((Place) obj1, 1, ((Transition) obj2).getX(), ((Transition) obj2).getY(), true, (Transition)obj2);
                    ((Transition) obj2).addParent(b);
                    model.addArc(b);
                }

            } else {
                this.clickError = true;
            }

        }
        repaint();

    }

    /**
     * Ajouter une transition au système et à la zone de dessin
     * @param x Coordonnée X de l'objet transition
     * @param y Coordonnée Y de l'objet transition
     */
    public void addTransition(double x, double y){
        model.addTransition(new Transition("t" + idTransition,x,y));
        idTransition++;
        repaint();
    }

    /**
     * Rendre la zone de dessin visible dans la fenêtre
     */
    public void applyPanel() {
        this.frame.add(this);
        this.frame.setVisible(true);
        repaint();
    }

    /**
     * Déterminer les deux couples de coordonnées pour créer un arc
     * @param x Coordonnée X
     * @param y Coordonnée Y
     */
    public void loadCoordinatesArc(double x, double y) {
        if (indexOfClickArc == 0 && getSelectedObject(x,y) != null) {
            /* Si il n'y a pas eu de 1er click en mode Arc */
            this.arcOriginX = x;
            this.arcOriginY = y;
            this.arcDestX = 0;
            this.arcDestY = 0;
            this.indexOfClickArc = 1;
        } else {
            /* Si nous cliquons pour la deuxieme fois en mode Arc */
            this.arcDestX = x;
            this.arcDestY = y;
            this.addArc(this.arcOriginX, this.arcOriginY, this.arcDestX, this.arcDestY);
            this.indexOfClickArc = 0;
        }
        repaint();
    }

    /**
     * Retourne l'objet sur lequel on a cliqué
     * @param x Coordonnée X du click
     * @param y Coordonnée Y du click
     * @return Objet
     */
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
            for (Arc a : t.getChildren()) {
                System.out.println("Pt1 > " + a.getPointCtr1());
                if(a.containsControlPoint1(x,y)) {
                    a.getPointCtr1().setMoved(true);
                    return a.getPointCtr1();
                }
            }
        }
        return null;
    }

    /**
     * Définir la variable selectedObject à l'objet passé en paramètre
     * @param obj Transition, Arc ou Place
     */
    public void selectObject(Object obj) {
        selectedObject = obj;
        this.clickError = false;
        repaint();
    }

    /**
     * Non utilisé: Supprimer l'objet sélectionné
     */
    public void deleteSelectedObject() {
        if (selectedObject != null) {
            if (selectedObject instanceof Place) {
                // Suppression des arcs liés à la place supprimée
                List<Arc> arcToDelete = new ArrayList<>();
                for(Arc a : this.model.arcVector){
                    if(((Place) selectedObject) == a.getPlace()){
                        arcToDelete.add(a);
                    }
                }
                this.model.removeArcs(arcToDelete);
                this.model.removePlace((Place) selectedObject);
                selectedObject = null;
                repaint();
            }
            if (selectedObject instanceof Transition){
                this.model.removeTransition((Transition) selectedObject);
            }
            selectedObject = null;
            repaint();
        }
    }

    /**
     * Afficher le modèle dans une popup en utilisant LaTeX
     */
    public void showModel() {
        if(model.placeVector.size() != 0 && model.transitionVector.size() != 0){
            model.updateMatrices();
            //System.out.println(model);

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

    /**
     * Afficher les options de l'objet passé en paramètre.
     * Permet à l'utilisateur de définir le marquage d'une place
     * ainsi que l'orientation d'une transition via l'affichage
     * d'une boite de dialogue.
     * @param obj Transition ou Place
     */
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

    /**
     * Afficher une popup de sélection du Label ainsi que de
     * la position du label pour une place ou une transition.
     * Une fois les valeurs remplies, un label est créé à la
     * position souhaitée par l'utilisateur.
     * @param obj Place ou Transition
     */
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
                } else {
                    ((Transition) obj).addLabel(Label.getText());
                    ((Transition) obj).addPosition(convert);
                }

            } catch (Exception e){
                JOptionPane.showMessageDialog(frame.getContentPane(), "Error: only string are allowed");
            }
        }
        repaint();
    }

    /**
     * Lorsque l'utilisateur sélectionne ni un place, ni une transition
     * lors de la modification du Label
     */
    public void errorSelect(){
        JOptionPane.showMessageDialog(frame.getContentPane(), "Veuillez sélectionner une place ou transition");
        repaint();
    }
}
