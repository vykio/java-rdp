package com.tech.app.models;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class Arc {

    private Place place;
    private int poids;
    private boolean placeToTransition;

    public Arc(Place place, int poids, double xOrigin, double yOrigin, boolean placeToTransition){
        this.place = place;
        this.poids = poids;
        this.placeToTransition = placeToTransition;
        this.forme = new Line2D.Double(xOrigin, yOrigin, this.place.getX(), this.place.getY());
    }

    public Arc(Place place, int poids){
        this(place, poids, 0,0, false);
    }

    public Arc(Place place) { this(place, 1); }

    public Place getPlace() { return this.place; }
    public int getPoids() { return this.poids; }

    public void setPlace(Place place) { this.place = place; }
    public void setPoids(int poids) { this.poids = poids; }

    @Override
    public String toString() {
        return "Arc{" +
                "place=" + place +
                ", poids=" + poids +
                '}';
    }

    /* Partie Graphique */
    public Line2D.Double forme;

    private void drawArrow(Graphics2D g2, double oX, double oY, double dX, double dY) {
        /* Variables internes */
        double dx = dX - oX, dy = dY - oY;
        double angle = Math.atan2(dy, dx);
        double len = (int) Math.sqrt(dx*dx + dy*dy);
        double start;
        int ARR_SIZE = 10;
        if (this.placeToTransition) {
            len = len - (double)Math.min(Transition.WIDTH, Transition.HEIGHT)/2;
            start = this.place.forme.width/2;
        } else {
            len = len - this.place.forme.width/2;
            start = 0;
        }


        /* Référentiel */
        AffineTransform at = AffineTransform.getTranslateInstance(oX, oY);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g2.transform(at);

        /* Ligne */
        g2.draw(new Line2D.Double(start, 0, len, 0));

        /* Fléche */
        Path2D path = new Path2D.Double();
        double xval[] = {len, len-ARR_SIZE, len-ARR_SIZE, len};
        double yval[] = {0, -ARR_SIZE, ARR_SIZE, 0};
        path.moveTo(xval[0], yval[0]);
        for(int i = 1; i < xval.length; ++i) {
            path.lineTo(xval[i], yval[i]);
        }
        path.closePath();
        g2.fill(path);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setStroke(new BasicStroke(1f));

        /* Orientation de la flêche */
        double oX, oY, dX, dY;
        if (!this.placeToTransition) {
            oX = this.forme.x1;
            oY = this.forme.y1;
            dX = this.place.getX();
            dY = this.place.getY();
        } else {
            oX = this.place.getX();
            oY = this.place.getY();
            dX = this.forme.x1;
            dY = this.forme.y1;
        }

        //g2.draw(new Line2D.Double(this.forme.x1, this.forme.y1, this.place.getX(), this.place.getY()));
        this.drawArrow(g2, oX, oY, dX, dY);

    }

    public void updatePosition(double x, double y) {
        this.forme.x1 = x;
        this.forme.y1 = y;
    }


}
