package com.tech.app.models.gma;

import java.util.Vector;

public class Marquage {

    private Vector<Integer> marquage;
    private boolean dead_end;
    private boolean nouveau;

    //Constructeur pour un nouveau marquage
    public Marquage(Vector<Integer> marquage){
        this.marquage= marquage;
        this.dead_end = false;
        this.nouveau=true;
    }


    public Vector<Integer> getMarquage() {
        return marquage;
    }

    public void setMarquage(Vector<Integer> marquage) {
        this.marquage = marquage;
    }

    public boolean isDead_end() {
        return dead_end;
    }

    public void setDead_end(boolean dead_end) {
        this.dead_end = dead_end;
    }

    public boolean isNouveau() {
        return nouveau;
    }

    public void setNouveau(boolean nouveau) {
        this.nouveau = nouveau;
    }

    @Override
    public String toString() {
        return "Marquage{" + marquage + '}';
    }
}
