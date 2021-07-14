package com.tech.app.models;

import com.tech.app.functions.FMaths;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Cette classe permet de créer l'objet Transition.
 */
public class Transition implements Serializable {

    private final String name;
    private double x, y;
    private final List<Arc> children, parents;
    private String label;

    private int position;

    private final int LARGE_SIDE = 40, MIN_SIDE = 10;
    public int WIDTH=LARGE_SIDE, HEIGHT=MIN_SIDE;

    /**
     * Constructeur d'une Transisiton.
     * @param name : nom de la transition.
     * @param x : coordonnée x de la transition.
     * @param y : coordonnée y de la transition.
     * @param children : Liste des arcs enfants de la transition.
     * @param parents : Liste des arcs parents de la transition.
     * @param label : label de la transition.
     * @param position : position du label.
     */
    public Transition(String name, double x, double y, ArrayList<Arc> children, ArrayList<Arc> parents, String label, int position) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.children = children;
        this.parents = parents;
        this.label = label;
        this.position = position;
        this.forme = new Rectangle2D.Float((float) (this.x-(WIDTH/2)), (float) (this.y-(HEIGHT/2)), WIDTH ,HEIGHT );
    }

    /**
     * Constructeur d'une transition.
     * @param name : nom de la transition.
     * @param x : coordonnée x de la transition.
     * @param y : coordonnée y de la transition.
     * @param children : Liste des arcs enfants de la transition
     */
    public Transition(String name, double x, double y, ArrayList<Arc> children) { this(name, x, y, children, new ArrayList<>(),"",0); }

    /**
     * Constructeur d'une transition.
     * @param name : nom de la transition.
     * @param x : coordonnée x de la transition.
     * @param y : coordonnée y de la transition.
     */
    public Transition(String name, double x, double y) { this(name, x, y, new ArrayList<>(), new ArrayList<>(),"",0); }

    public Transition(String name, double x, double y, String label, int position) { this(name, x, y, new ArrayList<>(), new ArrayList<>(),label,position); }


    /**
     * Constructeur d'une transition en (0,0)
     * @param name : nom de la transition.
     */
    public Transition(String name) { this(name, 0, 0); }

    /**
     * Méthode qui permet de récupérer le nom de la transition.
     * @return nom de la transition.
     */
    public String getName() { return name; }

    /**
     * Méthode qui permet de récupérer la coordonnée x de la transition.
     * @return x.
     */
    public double getX() { return x; }

    /**
     * Méthode qui permet de récupérer la coordonnée y de la transition.
     * @return y.
     */
    public double getY() { return y; }

    /**
     * Méthode qui permet de récupérer la liste des arcs enfants de la transition.
     * @return liste des arcs enfants.
     */
    public List<Arc> getChildren() { return this.children; }

    /**
     * Méthode qui permet de récupérer la liste des arcs parents de la transition.
     * @return liste des arcs parents.
     */
    public List<Arc> getParents() { return this.parents; }

    /**
     * Méthode qui permet d'ajouter un arc à la liste des arcs enfants de la transition.
     * @param p : Place.
     */
    public void addChildren(Place p) { this.children.add(new Arc(p)); }

    /**
     * Méthode qui permet d'ajouter un arc à la liste des arcs enfants de la transition.
     * @param a : Arc.
     */
    public void addChildren(Arc a) { this.children.add(a); }

    /**
     * Méthode qui permet d'ajouter une liste d'arcs à la liste des arcs enfants de la transition.
     * @param arcs : Liste d'arcs.
     */
    public void addChildren(ArrayList<Arc> arcs) { this.children.addAll(arcs); }

    /**
     * Méthode qui permet de retirer un arc de la liste des arcs enfants de la transition.
     * @param a : Arc.
     */
    public void removeChildren(Arc a) { this.children.remove(a); }

    /**
     * Méthode qui permet d'ajouter un arc à la liste des arcs parents de la transition.
     * @param p : Place.
     */
    public void addParent(Place p) { this.parents.add(new Arc(p)); }

    /**
     * Méthode qui permet d'ajouter un arc à la liste des arcs parents de la transition.
     * @param a : Arc.
     */
    public void addParent(Arc a) { this.parents.add(a); }

    /**
     * Méthode qui permet d'ajouter une liste d'arcs à la liste des arcs parents de la transition.
     * @param arcs : Liste d'arcs.
     */
    public void addParents(ArrayList<Arc> arcs) { this.parents.addAll(arcs); }

    /**
     * Méthode qui permet de retirer un arc de la liste des arcs parents de la transition.
     * @param a : Arc
     */
    public void removeParent(Arc a) { this.parents.remove(a); }

    /**
     * Méthode qui permet d'ajouter un label à la transition
     * @param label : label.
     */
    public void addLabel(String label) { this.label = label; }

    public String getLabel() {return label;}

    /**
     * Méthode qui permet de retirer le label de la transition.
     */
    public void resetlabel() { this.label = " "; }

    /**
     * Méthode qui permet de donner la position du label de la transition.
     * @param convert : position.
     */
    public void addPosition(int convert) { this.position = convert; }

    public int getPosition(){return position;}


    /**
     * Méthode qui permet de remettre la position du label à 1
     */
    public void resetPosition() { this.position = 1; }

    public boolean estFranchissable(){
        List<Integer> marques = new ArrayList<>();
        if(this.getParents() == null || this.getParents().isEmpty()){
            return false;
        }

        for(Arc a : this.getParents()){
            // créer un vecteur pour récup les marques de chacune des places parents et faire un test comme dans couvre
            marques.add(a.place.getMarquage());
        }
        for(int i = 0 ; i < marques.size() ; i++){
            if(marques.get(i) < 1)
                return false;
        }
        return true;
    }

    /**
     * Méthode qui permet d'afficher les caractéristiques de la transition.
     * T(nom,x,y,c:[arcs enfants],p:[arcs parents])
     * @return caractéristiques de la transition.
     */
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("T(\"").append(this.name).append("\", ").append(FMaths.round(this.x, 2)).append(", ").append(FMaths.round(this.y,2 )).append(", ");

        sb.append("c:[");
        for (int i = 0; i < this.children.size(); i++) {
            sb.append(this.children.get(i));
            if (i != this.children.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("], ");

        sb.append("p:[");
        for (int i = 0; i < this.parents.size(); i++) {
            sb.append(this.parents.get(i));
            if (i != this.parents.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("])");


        return sb.toString();

    }

    /* PARTIE GRAPHIQUE */
    public Rectangle2D.Float forme;

    /**
     * Méthode qui permet d'afficher une transition et ses arcs (on appelle draw de la classe Arc).
     * @param g : Graphics.
     */
    public void draw(Graphics g) {

        /* Afficher le carré de transition au dessus de l'arc */
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(new Font("Console", Font.PLAIN, 15));

        g2.setStroke(new BasicStroke(2.0f));
        Color color = g2.getColor();
        g2.setColor(g2.getBackground());
        g2.fill(new Rectangle2D.Float((float)this.x-(forme.width)/2, (float)this.y-(forme.height)/2, (int)forme.width, (int)forme.height));
        g2.setColor(color);
        g2.draw(new Rectangle2D.Float((float)this.x-(forme.width)/2, (float)this.y-(forme.height)/2, (int)forme.width, (int)forme.height));
        g.drawString(this.name, (int) (x - (LARGE_SIDE)), (int) (y + 10));

        // Gestion des labels
        FontMetrics fontMetrics = g2.getFontMetrics();
        if (label != null) {
            Font police = new Font("TimesRoman", Font.PLAIN, 20);
            Color tempColor = g.getColor();
            Color myColor = new Color(47, 101, 202);
            g.setFont(police);
            g.setColor(myColor);

            switch (position) {
                case 1:
                    g.drawString("" + label, (int) (x - 35) - fontMetrics.stringWidth(label), (int) (y - 15));
                    break;
                case 2:
                    g.drawString("" + label, (int) (x) - (fontMetrics.stringWidth(label)/2), (int) (y - 30));
                    break;
                case 3:
                    g.drawString("" + label, (int) (x+30), (int) (y - 15));
                    break;
                case 4:
                    g.drawString("" + label, (int) (x + 30), (int) (y + 5));
                    break;
                case 5:
                    g.drawString("" + label, (int) (x + 30), (int) (y + 25));
                    break;
                case 6:
                    g.drawString("" + label, (int) (x) - (fontMetrics.stringWidth(label)/2), (int) (y + 40));
                    break;
                case 7:
                    g.drawString("" + label, (int) (x - 35) - fontMetrics.stringWidth(label), (int) (y + 25));
                    break;
                case 8:
                    g.drawString("" + label, (int) (x - 35) - fontMetrics.stringWidth(label), (int) (y + 5));
                    break;
            }
            g.setColor(tempColor);
        }
    }

    public void drawParents(Graphics g){
        for (Arc a : this.parents) {
            a.draw(g);
        }
    }

    public void drawChildren(Graphics g){
        for (Arc a : this.children) {
            a.draw(g);
        }
    }

    /**
     * Méthode qui permet de mettre à jour les coordonnées de la transition et de ses arcs (on appelle updatePosition de la classe Arc).
     * @param x : nouvelle coordonnée x.
     * @param y : nouvelle coordonnée y.
     */
    public void updatePosition(double x, double y) {
        forme.x = (float)this.x-(forme.width)/2;
        forme.y = (float)this.y-(forme.height)/2;
        this.x = x;
        this.y = y;

        for (Arc a : this.parents) {
            a.updatePosition(x, y);
        }
        for (Arc a : this.children) {
            a.updatePosition(x, y);
        }

    }

    /**
     * Méthode qui permet de changer l'orientation de la transition.
     * @param index : orientation.
     */
    public void changeOrientation(int index) {
        if (index == 0) {
            this.WIDTH = MIN_SIDE;
            this.HEIGHT = LARGE_SIDE;
        } else {
            this.HEIGHT = MIN_SIDE;
            this.WIDTH = LARGE_SIDE;
        }
        this.forme.x = (float) (this.x-(WIDTH/2));
        this.forme.y = (float) (this.y-(HEIGHT/2));
        this.forme.width = WIDTH;
        this.forme.height = HEIGHT;
    }


}
