package com.tech.app.models;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Transition {

    private String name;
    private double x, y;
    private List<Arc> childrens, parents;

    public final static int WIDTH=40, HEIGHT=10;

    public Transition(String name, double x, double y, ArrayList<Arc> childrens, ArrayList<Arc> parents) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.childrens = childrens;
        this.parents = parents;
        this.forme = new Rectangle2D.Float((float) (this.x-(WIDTH/2)), (float) (this.y-(HEIGHT/2)), WIDTH ,HEIGHT );
    }

    public Transition(String name, double x, double y, ArrayList<Arc> childrens) { this(name, x, y, childrens, new ArrayList<Arc>()); }
    public Transition(String name, double x, double y) { this(name, x, y, new ArrayList<Arc>(), new ArrayList<Arc>()); }
    public Transition(String name) { this(name, 0, 0); }

    public String getName() { return name; }
    public double getX() { return x; }
    public double getY() { return y; }
    public List<Arc> getChildrens() { return this.childrens; }
    public List<Arc> getParents() { return this.parents; }

    public void addChildren(Place p) { this.childrens.add(new Arc(p)); }
    public void addChildren(Arc a) { this.childrens.add(a); }
    public void addChildrens(ArrayList<Arc> arcs) { this.childrens.addAll(arcs); }
    public void removeChildren(Arc a) { this.childrens.remove(a); }

    public void addParent(Place p) { this.parents.add(new Arc(p)); }
    public void addParent(Arc a) { this.parents.add(a); }
    public void addParents(ArrayList<Arc> arcs) { this.parents.addAll(arcs); }
    public void removeParent(Arc a) { this.parents.remove(a); }

    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("T(\"").append(this.name).append("\", ").append(this.x).append(", ").append(this.y).append(", ");

        sb.append("c:[");
        for (int i = 0; i < this.childrens.size(); i++) {
            sb.append(this.childrens.get(i));
            if (i != this.childrens.size() - 1) {
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

    public void draw(Graphics g) {
        for (Arc a : this.parents) {
            a.draw(g);
        }

        for (Arc a : this.childrens) {
            a.draw(g);
        }

        /* Afficher le carré de transition au dessus de l'arc */
        String label = this.name;
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(new Font("Console", Font.PLAIN, 15));

        g2.setStroke(new BasicStroke(2.0f));
        Color color = g2.getColor();
        g2.setColor(g2.getBackground());
        g2.fill(new Rectangle2D.Float((float)this.x-(forme.width)/2, (float)this.y-(forme.height)/2, (int)forme.width, (int)forme.height));
        g2.setColor(color);
        g2.draw(new Rectangle2D.Float((float)this.x-(forme.width)/2, (float)this.y-(forme.height)/2, (int)forme.width, (int)forme.height));
        g.drawString(label, (int)(x-(forme.width)/4) ,(int)(y+5));

    }

    public void updatePosition(double x, double y) {
        forme.x = (float)this.x-(forme.width)/2;
        forme.y = (float)this.y-(forme.height)/2;
        this.x = x;
        this.y = y;

        for (Arc a : this.parents) {
            a.updatePosition(x, y);
        }
        for (Arc a : this.childrens) {
            a.updatePosition(x, y);
        }

    }


}
