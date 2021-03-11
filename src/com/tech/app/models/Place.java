package com.tech.app.models;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Place {

    private String name;
    private double x;
    private double y;
    private int marquage;

    public Place(String name, double x, double y, int marquage) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.marquage = marquage;
        this.forme = new Ellipse2D.Float((float)this.x, (float)this.y, 40 ,40);
    }

    public Place(String name, double x, double y) { this(name, x, y, 0); }
    public Place(String name) { this(name, 0, 0, 0); }

    public String getName() { return name; }
    public double getX() { return x; }
    public double getY() { return y; }
    public int getMarquage() { return marquage; }

    public void addMarquage(int marquage) { this.marquage += marquage; }
    public void removeMarquage(int marquage) { this.marquage = Math.max(this.marquage - marquage, 0); }
    public void setMarquage(int marquage) { this.marquage = Math.max(marquage, 0); }
    public void resetMarquage() { this.marquage = 0; }

    public String toString() {
        return "P(\""+ this.name + "\", " + this.x + ", " + this.y + ", m:"+ this.marquage + ")";
    }

    /* PARTIE GRAPHIQUE */
    public Ellipse2D.Float forme;

    public void draw(Graphics g) {
        String label = this.name;
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        g.drawString(label, (int)(x-(forme.width)/4) ,(int)(y+5));
        g2.setStroke(new BasicStroke(5.0f));
        g2.draw(new Ellipse2D.Float((float)this.x-(forme.width)/2, (float)this.y-(forme.height)/2, (int)forme.width, (int)forme.height));
    }

    public void updatePosition(double x, double y) {
        forme.x = (float)this.x-(forme.width)/2;
        forme.y = (float)this.y-(forme.height)/2;
        this.x = x;
        this.y = y;
    }

}
