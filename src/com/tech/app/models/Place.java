package com.tech.app.models;

import java.util.ArrayList;
import java.util.List;

public class Place extends Node {

    private int nbJeton;
    private boolean active;

    public Place(int x, int y, int nbJeton, ArrayList<Transition> transitionsSuivantes) {
        super(x, y, Node.type.PLACE);
        this.nbJeton = nbJeton;
        this.active = false;
        this.addChildrens(transitionsSuivantes);

    }

    public Place(int x, int y, ArrayList<Transition> transitionsSuivantes) {
        this(x,y, 0, transitionsSuivantes);
    }

    public Place(int x, int y) {
        this(x,y, 0, new ArrayList<Transition>());
    }

    public int getNbJeton() { return this.nbJeton; }
    public void setNbJeton(int nbJeton) { this.nbJeton = nbJeton; }

    public boolean isActive() { return this.active; }
    public void setActive(boolean active) { this.active = active; }

}
