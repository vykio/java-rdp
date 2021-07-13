package com.tech.app.models;

import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;

/**
 * Classe pour créer l'objet Arc.
 */
public class Arc implements Serializable {

    public Place place;
    private int poids;
    private final boolean placeToTransition;
    private final Transition transition;
    private PointControle pointCtr1;

    /**
     * Constructeur d'arc utilisé dans DrawPanel lorsque l'utilisateur clique sur la zone de dessin en ayant selectionné l'outil arc.
     * @param place : Place.
     * @param poids : poids de l'arc, par défaut 1.
     * @param xOrigin : coordonnée x de l'origine de l'arc.
     * @param yOrigin : coordonnée y de l'origine de l'arc.
     * @param placeToTransition : booléen qui indique si l'arc est dans le sens place ► transition.
     * @param transition : Transition.
     */
    public Arc(Place place, int poids, double xOrigin, double yOrigin, boolean placeToTransition, Transition transition){
        this.place = place;
        this.poids = poids;
        this.placeToTransition = placeToTransition;
        this.transition = transition;
        this.forme = new Line2D.Double(xOrigin, yOrigin, this.place.getX(), this.place.getY());
        this.pointCtr1 = new PointControle(0,0,this);
    }

    /**
     * Constructeur d'arc
     * @param place : Place.
     * @param poids : poids de l'arc.
     */
    public Arc(Place place, int poids){
        this(place, poids, 0,0, false, null);
    }

    public Arc(Place place, int poids, boolean placeToTransition, Transition t){ this(place, poids, 0,0, placeToTransition, t); }


    /**
     * Constructeur d'arc utilisé dans la classe Transition.
     * @param place : Place.
     */
    public Arc(Place place) { this(place, 1); }

    /**
     * Méthode qui permet de récupérer la place liée à l'arc.
     * @return place.
     */
    public Place getPlace() { return this.place; }

    /**
     * Méthode qui permet de récupérer le poids de l'arc.
     * @return poids.
     */
    public int getPoids() { return this.poids; }

    /**
     * Méthode qui permet de donner/modifier la place liée à l'arc.
     * @param place : Place.
     */
    public void setPlace(Place place) { this.place = place; }

    /**
     * Méthode qui permet de donner/modifier le poids d'un arc.
     * @param poids Poids de l'arc
     */
    public void setPoids(int poids) {
        if(poids >= 1) {
            this.poids = poids;
        }
    }

    /**
     * Méthode qui permet d'afficher les caractéristiques de l'arc : {place,poids}.
     * @return caractéristiques de l'arc.
     */
    @Override
    public String toString() {
        return "Arc{" +
                "place=" + place +
                ", poids=" + poids +
                ", placeToTransition = "+ placeToTransition+
                ", "+ pointCtr1+
                '}';
    }

    /* Partie Graphique */

    public Line2D.Double forme;
    public QuadCurve2D.Double courbe;
    public AffineTransform at;
    public AffineTransform reverse;
    public Path2D.Double hitbox;
    public Path2D arrowHead;


    /**
     * Méthode qui permet de dessiner un arc.
     * @param g2 : Graphics2D.
     * @param oX : coordonnée x de l'origine de l'arc
     * @param oY : coordonnée y de l'origine de l'arc
     * @param dX : coordonnée x de la destination de l'arc
     * @param dY : coordonnée y de la destination de l'arc
     */
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

        at = AffineTransform.getTranslateInstance(oX, oY);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g2.transform(at);

        try {
            reverse = at.createInverse();
        } catch (Exception e){


        /* Ligne */
        if (this.pointCtr1.getOrigin()) {
            this.pointCtr1.setX((start + len-ARR_SIZE)/2);
            this.pointCtr1.setY(0);
        }

        /*point de controle*/
        //pointCtr1.draw(g2);

        courbe = new QuadCurve2D.Double(start, 0, this.pointCtr1.getX(), this.pointCtr1.getY(), len, 0);
        g2.draw(courbe);


        hitbox = new Path2D.Double(arcHitbox(len));
        //g2.draw(hitbox);

        /* Fléche */
        arrowHead = new Path2D.Double();
        double[] xval = {len, len-ARR_SIZE, len-ARR_SIZE, len};
        double[] yval = {0, -ARR_SIZE, ARR_SIZE, 0};
        arrowHead.moveTo(xval[0], yval[0]);
        for(int i = 1; i < xval.length; ++i) {
            arrowHead.lineTo(xval[i], yval[i]);
        }
        arrowHead.closePath();
        g2.fill(arrowHead);

        /* Affichage du poids */
        if(this.poids > 1 ) {
            g2.setFont(new Font("Console", Font.PLAIN, 15));
            g2.drawString(Integer.toString(poids), (int) courbe.getCtrlX(), (int) courbe.getCtrlY() + 15);
        }
    }

    /**
     * Méthode que l'on appelle dans Transition pour dessiner un arc.
     * @param g : Graphics
     */
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

    public Path2D.Double arcHitbox(double len){
        int ecart = 7;
        Path2D.Double hitbox = new Path2D.Double();
        hitbox.moveTo(this.place.forme.width/2,-ecart);
        hitbox.quadTo(this.pointCtr1.getX(),this.pointCtr1.getY() -ecart,len,-ecart);
        hitbox.lineTo(len,+ecart);
        hitbox.quadTo(this.pointCtr1.getX(),this.pointCtr1.getY()+ecart, this.place.forme.width/2,+ecart);
        hitbox.lineTo(this.place.forme.width/2,-ecart);
        hitbox.closePath();
        return hitbox;
    }

    /**
     * Retourne true lorsque la différence absolue entre les coordonnées du point
     * d'origine et du point à comparer (toCompare) est inférieur à la valeur size
     * @param origin Point.Double d'origine
     * @param size Valeur maximale de la différence des coordonnées lors de la comparaison
     * @param toCompare Point.Double de comparaison
     * @return Booléen
     */
    public boolean containing(Point.Double origin, int size, Point.Double toCompare) {
        double a1 = Math.abs(origin.getX() - toCompare.getX());
        double a2 = Math.abs(origin.getY() - toCompare.getY());
        System.out.println("(a1:a2) = (" + a1 + ":"+ a2+")");
        return (a1 < size) && (a2 < size);
    }

    /**
     * Retourne true lorsque les coordonnées de la souris sont à peu près égales aux
     * coordonnées transformées par la transformation affine de l'arc du point de contrôle
     * <p style="font-weight: bold; color: red">Ne fonctionne pas vraiment pour l'instant</p>
     * @param x Coordonnée X de la souris
     * @param y Coordonnée Y de la souris
     * @return Vrai ou Faux
     */
    public boolean containsControlPoint1(double x, double y) {

        Point.Double point = new Point.Double(pointCtr1.getX(),pointCtr1.getY());
        Point2D.Double pointDest = new Point.Double();

        getAt().transform(point, pointDest);

        System.out.println("PtCtrlTRANSFORM:(" + pointDest.getX() + ":" + pointDest.getY() + ") PtCtrl:(" + pointCtr1.getX() + ":" + pointCtr1.getY()+ ")");

        boolean res = containing(pointDest, pointCtr1.getSize()+ pointCtr1.getSize()/2, new Point.Double(x,y));

        boolean result = pointCtr1.contains(x,y);
        System.out.println(result);
        return result;
    }

    /**
     * Méthode qui permet de récupérer le point de controle de la courbe.
     * @return point de controle.
     */
    public PointControle getPointCtr1() {
        return pointCtr1;
    }

    public AffineTransform getAt() { return at; }

    public AffineTransform getReverseAt() {
        try{
            return at.createInverse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Méthode qui permet de mettre à jour les coordonnées de l'arc.
     * @param x : x.
     * @param y : y.
     */
    public void updatePosition(double x, double y) {
        this.forme.x1 = x;
        this.forme.y1 = y;
    }
}
