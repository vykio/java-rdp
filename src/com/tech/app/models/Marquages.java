package com.tech.app.models;

import com.tech.app.functions.FMaths;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Marquages {
    private String name;
    private double x;
    private double y;
    private int[] M;
    public boolean draggable;

    private final static int WIDTH = 30, HEIGHT = 30;

    public Marquages(String name, double x, double y, int[] M) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.M = M;
        this.forme= new Ellipse2D.Float((float)(this.x-(WIDTH/2)), (float)(this.y-(HEIGHT/2)), WIDTH ,HEIGHT);
    }

    public String getName() { return name; }
    public double getX() { return x; }
    public double getY() { return y; }

    public int[] initM(Model r, int[] M) {
        int i = 0;
        for(i = 0; i < r.nbPlace; i++) {
            M[i] = 0;
        }
        return M;
    }

    public int[] fillM(Model r, int[] M) {
        int i = 0;
        for(i = 0; i < r.nbPlace; i++) {
            M[i] = r.placeVector.get(i).getMarquage();
        }
        return M;
    }

    /*public int[] nextM(Model r, Transition t) {
        int[] result;
        //initM(r, result);
        int i = 0;
        for(i = 0; i < r.nbPlace; i++) {
            if(M[i] >= 1)
            {
                if(r.canTransit(t))
                    Transférer les marquages des parents vers les fils
                    Appel de fillM
            }
        }
        return result;
    }*/



    public int[] getM(Model r) {
        return M;
    }

    public void printM(Model r) {
        int i = 0;
        for(i = 0; i < r.nbPlace; i++) {
            System.out.println(M[i]);
        }
    }

    public Ellipse2D.Float forme;
}
