package com.tech.app.models;

import com.tech.app.functions.FMaths;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Transition implements Serializable {

    private final String name;
    private double x, y;
    private final List<Arc> childrens, parents;
    private String label;
    private int position;

    private final int LARGE_SIDE = 40, MIN_SIDE = 10;
    public int WIDTH=LARGE_SIDE, HEIGHT=MIN_SIDE;

    public Transition(String name, double x, double y, ArrayList<Arc> childrens, ArrayList<Arc> parents, String label, int position) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.childrens = childrens;
        this.parents = parents;
        this.label = label;
        this.position = position;
        this.forme = new Rectangle2D.Float((float) (this.x-(WIDTH/2)), (float) (this.y-(HEIGHT/2)), WIDTH ,HEIGHT );
    }

    public Transition(String name, double x, double y, ArrayList<Arc> childrens) { this(name, x, y, childrens, new ArrayList<Arc>(),"",0); }
    public Transition(String name, double x, double y) { this(name, x, y, new ArrayList<>(), new ArrayList<>(),"",0); }
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

    public void addLabel(String label) { this.label = label; }
    public void resetlabel() { this.label = " "; }

    public void addPosition(int convert) { this.position = convert; }
    public void resetPosition() { this.position = 1; }

    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("T(\"").append(this.name).append("\", ").append(FMaths.round(this.x, 2)).append(", ").append(FMaths.round(this.y,2 )).append(", ");

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
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(new Font("Console", Font.PLAIN, 15));

        g2.setStroke(new BasicStroke(2.0f));
        Color color = g2.getColor();
        g2.setColor(g2.getBackground());
        g2.fill(new Rectangle2D.Float((float)this.x-(forme.width)/2, (float)this.y-(forme.height)/2, (int)forme.width, (int)forme.height));
        g2.setColor(color);
        g2.draw(new Rectangle2D.Float((float)this.x-(forme.width)/2, (float)this.y-(forme.height)/2, (int)forme.width, (int)forme.height));
        g.drawString(this.name, (int)(x-(forme.width)/4) ,(int)(y+5));

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
