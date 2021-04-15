package com.tech.app.models;

import java.awt.*;
import java.awt.geom.*;

public class Arc {

    private Place place;
    private int poids;
    private boolean placeToTransition;
    private Transition transition;
    private QuadCurve2D.Double courbe;
    private PointControle pointCtr1;

    public Arc(Place place, int poids, double xOrigin, double yOrigin, boolean placeToTransition, Transition transition){
        this.place = place;
        this.poids = poids;
        this.placeToTransition = placeToTransition;
        this.transition = transition;
        this.forme = new Line2D.Double(xOrigin, yOrigin, this.place.getX(), this.place.getY());
        this.pointCtr1 = new PointControle(this);
    }

    public Arc(Place place, int poids){
        this(place, poids, 0,0, false, null);
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

    private double oX, oY, angle;
    public AffineTransform at;

    private void drawArrow(Graphics2D g2, double oX, double oY, double dX, double dY) {
        /* Variables internes */
        double dx = dX - oX, dy = dY - oY;

        double angle = Math.atan2(dy, dx);
        double len = (int) Math.sqrt(dx*dx + dy*dy);
        double start;
        int ARR_SIZE = 10;
        if (this.placeToTransition) {
            len = len - (double)Math.min(transition.WIDTH, transition.HEIGHT)/2;
            start = this.place.forme.width/2;
        } else {
            len = len - this.place.forme.width/2;
            start = 0;
        }
        this.oX = oX;
        this.oY = oY;
        this.angle = angle;

        at = AffineTransform.getTranslateInstance(oX, oY);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g2.transform(at);


        /* Ligne */
        if (!this.pointCtr1.getMoved()) {
            this.pointCtr1.setX((start + len-ARR_SIZE)/2);
            this.pointCtr1.setY(0);
        }
        //g2.draw(new Line2D.Double(start, 0, len, 0));

        /*point de controle*/
        pointCtr1.draw(g2);


        courbe = new QuadCurve2D.Double(start, 0, this.pointCtr1.getX(), this.pointCtr1.getY(), len, 0);
        /* Référentiel */

        //courbe = new CubicCurve2D.Double(start, 0, (start+len)/3, 0 ,2*(start+len)/3, 0 , len, 0);
        g2.draw(courbe);





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
        /* Antialiasing */
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

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

    public boolean containing(Point.Double origin, int size, Point.Double toCompare) {
        double a1 = Math.abs(origin.getX() - toCompare.getX());
        double a2 = Math.abs(origin.getY() - toCompare.getY());
        System.out.println("(a1:a2) = (" + a1 + ":"+ a2+")");
        return (a1 < size) && (a2 < size);
    }

    public boolean containsControlPoint1(double x, double y) {

        Point.Double point = new Point.Double(pointCtr1.getX(),pointCtr1.getY());
        Point2D.Double pointDest = new Point.Double();

        at.transform(point, pointDest);

        System.out.println("PtCtrlTRANSFORM:(" + pointDest.getX() + ":" + pointDest.getY()
                            + ") PtCtrl:(" + pointCtr1.getX() + ":" + pointCtr1.getY()+ ")");

        boolean res=  containing(pointDest, pointCtr1.getSize(), new Point.Double(x,y));
        System.out.println(res);
        return res;
    }

    public PointControle getPointCtr1() {
        return pointCtr1;
    }


    public void updatePosition(double x, double y) {
        this.forme.x1 = x;
        this.forme.y1 = y;
    }



}
