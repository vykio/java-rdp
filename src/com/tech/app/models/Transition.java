package com.tech.app.models;

import java.util.ArrayList;
import java.util.List;

public class Transition {

    private String name;
    private int x, y;
    private List<Place> childrens, parents;

    public Transition(String name, int x, int y, ArrayList<Place> childrens, ArrayList<Place> parents) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.childrens = childrens;
        this.parents = parents;
    }

    public Transition(String name, int x, int y, ArrayList<Place> childrens) { this(name, x, y, childrens, new ArrayList<Place>()); }
    public Transition(String name, int x, int y) { this(name, x, y, new ArrayList<Place>(), new ArrayList<Place>()); }
    public Transition(String name) { this(name, 0, 0); }

    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public List<Place> getChildrens() { return this.childrens; }
    public List<Place> getParents() { return this.parents; }

    public void addChildren(Place p) { this.childrens.add(p); }
    public void addChildrens(ArrayList<Place> places) { this.childrens.addAll(places); }
    public void removeChildren(Place p) { this.childrens.remove(p); }

    public void addParent(Place p) { this.parents.add(p); }
    public void addParents(ArrayList<Place> places) { this.parents.addAll(places); }
    public void removeParent(Place p) { this.parents.remove(p); }

    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("T(\"").append(this.name).append("\", ").append(this.x).append(", ").append(this.y).append(", ");

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

}
