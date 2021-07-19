package com.tech.app.models;

import com.tech.app.functions.FMaths;
import com.tech.app.functions.FUtils;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

/**
 * Cette classe permet de créer l'objet Place.
 */
public class Place implements Serializable {

    private final String name;
    private double x;
    private double y;
    private int marquage;
    private int capacite;
    private String label;
    private int position;
    private final static int WIDTH = 40, HEIGHT = 40;

    /**
     * Constructeur d'une place.
     * @param name : nom de la Place.
     * @param x : coordonnée x du centre de la place.
     * @param y : coordonnée y du centre de la place.
     * @param marquage : marquage de la place.
     * @param label : label de la place.
     * @param position : position du label.
     */
    public Place(String name, double x, double y, int marquage, int capacite, String label, int position) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.marquage = marquage;
        this.label = label;
        this.capacite = capacite;
        this.position = position;
        this.forme = new Ellipse2D.Float((float)(this.x-(WIDTH/2)), (float)(this.y-(HEIGHT/2)), WIDTH ,HEIGHT);
    }

    /**
     * Constructeur d'une place.
     * @param name : nom de la place.
     * @param x : coordonnée x du centre de la place.
     * @param y : coordonnée y du centre de la place.
     */
    public Place(String name, double x, double y) { this(name, x, y, 0,Integer.MAX_VALUE,"",0); }

    public Place(String name, double x, double y, int marquage) { this(name, x, y, marquage,Integer.MAX_VALUE,"",0); }

    public Place(String name, double x, double y, int marquage, int capacite) { this(name, x, y, marquage,capacite,"",0); }


    /**
     * Méthode qui permet de récupérer le nom de la place.
     * @return nom de la place.
     */
    public String getName() { return name; }

    /**
     * Méthode qui permet de récupérer la coordonnée x du centre de la place.
     * @return x
     */
    public double getX() { return x; }

    /**
     * Méthode qui permet de récupérer la coordonnée y du centre de la place.
     * @return y
     */
    public double getY() { return y; }

    /**
     * Méthode qui permet de récupérer le marquage de la place.
     * @return marquage
     */
    public int getMarquage() { return marquage; }

    /**
     * Méthode qui permet d'ajouter une ou plusieurs marques à la place.
     * @param marquage : nombre de marques à ajouter.
     */
    public void addMarquage(int marquage) { this.marquage += marquage; }

    /**
     * Méthode qui permet de retirer une ou plusieurs marques à la place.
     * @param marquage : nombre de marques à retirer.
     */
    public void removeMarquage(int marquage) { this.marquage = Math.max(this.marquage - marquage, 0); }

    /**
     * Méthode qui permet de donner/modifier le marquage de la place.
     * @param marquage Marquage de la place
     */
    public void setMarquage(int marquage) { this.marquage = Math.max(marquage, 0); }

    /**
     * Méthode qui permet de remettre à 0 le nombre de marque dans la place.
     */
    public void resetMarquage() { this.marquage = 0; }

    public int getCapacite() { return capacite; }

    public void setCapacite(int capacite) { this.capacite = capacite; }

    /**
     * Métholde qui permet d'ajouter un label à la place.
     * @param label : label
     */
    public void addLabel(String label) { this.label = label; }

    /**
     * Méthode qui permet de retirer le label de la place.
     */
    public void resetlabel() { this.label = " "; }

    public String getLabel() {return label;}

    /**
     * Méthode qui permet de donner la position du label de la place.
     * @param convert : position
     */
    public void addPosition(int convert) { this.position = convert; }

    public int getPosition(){return position;}

    /**
     * Méthode qui permet de remettre la position du label à 1.
     */
    public void resetPosition() { this.position = 1; }

    /**
     * Méthode qui permet d'afficher les caractéristiques de la place.
     * P(nom,x,y,marquage)
     * @return caractéristiques de la place.
     */
    public String toString() {
        return "P(\""+ this.name + "\", " + FMaths.round(this.x, 2) + ", " + FMaths.round(this.y, 2) + ", m:"+ this.marquage + ")";
    }

    /* PARTIE GRAPHIQUE */
    public Ellipse2D.Float forme;

    /**
     * Méthode qui permet d'afficher une place.
     * @param g : Graphics.
     */
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(new Font("Console", Font.PLAIN, 15));

        FontMetrics fontMetrics = g2.getFontMetrics();

        Color color = g2.getColor();
        g2.setColor(g2.getBackground());
        g2.setStroke(new BasicStroke(2.0f));
        // Définition de la forme de la place : cercle.
        g2.fill(new Ellipse2D.Float((float)this.x-(forme.width)/2, (float)this.y-(forme.height)/2, (int)forme.width, (int)forme.height));
        g2.setColor(color);
        g2.draw(new Ellipse2D.Float((float)this.x-(forme.width)/2, (float)this.y-(forme.height)/2, (int)forme.width, (int)forme.height));

        g.drawString(this.name, (int)(x-(forme.width)/4) ,(int)(y-25));

        if(this.capacite!=Integer.MAX_VALUE){
            g.drawString("Cap=("+this.capacite+")",(int) (x-(forme.width)/2),(int)(y+35));
        }

        //Gestion de l'affichage des marquages
        if (marquage == 1) {
            g2.setColor(Color.black);
            g2.fill(new Ellipse2D.Float((float)this.x-(forme.width)/6, (float)this.y-(forme.height)/6, forme.width/3, forme.height/3));
        } else if( marquage == 2) {
            g2.setColor(Color.black);
            g2.fill(new Ellipse2D.Float((float)this.x+2, (float)this.y-(forme.height)/6, forme.width/3, forme.height/3));
            g2.fill(new Ellipse2D.Float((float)this.x-15, (float)this.y-(forme.height)/6, forme.width/3, forme.height/3));
        } else if( marquage == 3) {
            g2.setColor(Color.black);
            g2.fill(new Ellipse2D.Float((float)this.x+2, (float)this.y, forme.width/3, forme.height/3));
            g2.fill(new Ellipse2D.Float((float)this.x-15, (float)this.y, forme.width/3, forme.height/3));
            g2.fill(new Ellipse2D.Float((float)this.x-7, (float)this.y-15, forme.width/3, forme.height/3));
        }else if( marquage == 4) {
            g2.setColor(Color.black);
            g2.fill(new Ellipse2D.Float((float)this.x+3, (float)this.y, forme.width/3, forme.height/3));
            g2.fill(new Ellipse2D.Float((float)this.x-14, (float)this.y, forme.width/3, forme.height/3));
            g2.fill(new Ellipse2D.Float((float)this.x+3, (float)this.y-14, forme.width/3, forme.height/3));
            g2.fill(new Ellipse2D.Float((float)this.x-14, (float)this.y-14, forme.width/3, forme.height/3));
        }else if( marquage > 4) {
            Font police = new Font ("TimesRoman", Font.BOLD, 20);
            g.setFont(police);

            int xStart = (int)x-WIDTH/2;
            int yStart = (int)y-HEIGHT/2;
            FUtils.Graphics.drawCenteredString(g, String.valueOf(marquage), new Rectangle(xStart,yStart,WIDTH,HEIGHT), police);
        }

        // Gestion de la position du label.
        if (label != null) {
            Font police = new Font("TimesRoman", Font.PLAIN, 20);
            Color tempColor = g.getColor();
            Color myColor = new Color(47, 101, 202);
            g.setFont(police);
            g.setColor(myColor);

            switch (position) {
                case 1:
                    g.drawString("" + label, (int) (x - 30) - fontMetrics.stringWidth(label), (int) (y - 30));
                    break;
                case 2:
                    g.drawString("" + label, (int) (x) - (fontMetrics.stringWidth(label)/2), (int) (y - 30));
                    break;
                case 3:
                    g.drawString("" + label, (int) (x + 25), (int) (y - 30));
                    break;
                case 4:
                    g.drawString("" + label, (int) (x + 25), (int) (y + 5));
                    break;
                case 5:
                    g.drawString("" + label, (int) (x + 25), (int) (y + 40));
                    break;
                case 6:
                    g.drawString("" + label, (int) (x) - (fontMetrics.stringWidth(label)/2), (int) (y + 40));
                    break;
                case 7:
                    g.drawString("" + label, (int) (x - 30) - fontMetrics.stringWidth(label), (int) (y + 40));
                    break;
                case 8:
                    g.drawString("" + label, (int) (x - 30) - fontMetrics.stringWidth(label), (int) (y + 5));
                    break;
            }
            g.setColor(tempColor);
        }
    }

    /**
     * Méthode qui permet de mettre à jour les coordonnées de la place.
     * @param x : nouvelle coordonnée x.
     * @param y : nouvelle coordonnée y.
     */
    public void updatePosition(double x, double y) {
        forme.x = (float)this.x-(forme.width)/2;
        forme.y = (float)this.y-(forme.height)/2;
        this.x = x;
        this.y = y;
    }


}
