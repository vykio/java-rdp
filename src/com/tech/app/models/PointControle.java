package com.tech.app.models;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * Classe qui permet de créer un objet PointControle.
 */
public class PointControle implements Serializable {

    private double x, y;
    private boolean moved;
    private final int size;
    private final Arc parent;

    /**
     * Constructeur d'un point de controle en (0,0).
     */
    public PointControle () {
        this(0,0);
    }

    /**
     * Constructeur d'un point de controle en x, y.
     * @param x : x
     * @param y : y
     */
    public PointControle (double x, double y) {
        this.x = x;
        this.y = y;
        this.moved = false;
        this.size = 5;
        this.parent = null;
    }

    public PointControle (double x, double y, Arc a) {
        this.x = x;
        this.y = y;
        this.moved = false;
        this.size = 5;
        this.parent = a;
    }

    /**
     * Méthode qui permet de mettre à jour les coordonnées du point de controle.
     * @param x : nouvelle coordonnée x
     * @param y : nouvelle coordonnée y
     */
    public void updatePosition(double x, double y) {

        /*
        AffineTransform reverse = parent.getReverseAt();
        Point2D.Double src = new Point2D.Double(x,y);
        Point2D.Double dest = new Point2D.Double();
        reverse.transform(src,dest);
        */

        this.x = x;
        this.y = y;

    }

    /**
     * Méthode qui permet de récupérer la coordonnée x.
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * Méthode qui permet de donner/modifier la coordonnée x du point de controle.
     * @param x : x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Méthode qui permet de récupérer la coordonnée y.
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * Méthode qui permet de donner/modifier la coordonnée y du point de controle.
     * @param y : y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Méthode qui permet de savoir si le point de controle est en déplacement.
     * @return Vrai ou Faux
     */
    public boolean getMoved() { return moved; }

    /**
     * Méthode qui permet de récupérer la taille du point de controle.
     * @return size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Méthode qui permet de définir si le point de controle est en mouvement.
     * @param moved : booléen.
     */
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    /**
     * Méthode qui permet de dessiner le point de controle.
     * @param g2 : Graphics2D.
     */
    public void draw(Graphics2D g2) {
        g2.draw(new Rectangle2D.Double(this.getX(), this.getY(), size, size));
    }

    /**
     * Méthode qui permet de savoir si les coordonnées en paramètre sont dans la zone du point de controle.
     * @param x : x à tester
     * @param y : y à tester
     * @return booléen.
     */
    public boolean contains(double x, double y) {
        return (Math.abs(x - this.getX()) < size) && (Math.abs(y - this.getY()) < size);
    }

    /**
     * Méthode qui affiche les caractéristiques du point de controle.
     * PointControle{x,y,moved,size}.
     * @return caractéristiques du point de controle.
     */
    @Override
    public String toString() {
        return "PointControle{" +
                "x=" + x +
                ", y=" + y +
                ", moved=" + moved +
                ", size=" + size +
                ", parent= " + parent +
                '}';
    }
}