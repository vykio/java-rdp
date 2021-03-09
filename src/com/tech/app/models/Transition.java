package com.tech.app.models;

import java.util.ArrayList;
import java.util.List;

public class Transition {

    private String name;
    private int x, y;
    private List<Arc> childrens, parents;

    public Transition(String name, int x, int y, ArrayList<Arc> childrens, ArrayList<Arc> parents) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.childrens = childrens;
        this.parents = parents;
    }

    public Transition(String name, int x, int y, ArrayList<Arc> childrens) { this(name, x, y, childrens, new ArrayList<Arc>()); }
    public Transition(String name, int x, int y) { this(name, x, y, new ArrayList<Arc>(), new ArrayList<Arc>()); }
    public Transition(String name) { this(name, 0, 0); }

    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public List<Arc> getChildrens() { return this.childrens; }
    public List<Arc> getParents() { return this.parents; }

    public void addChildren(Place p) { this.childrens.add(new Arc(p)); }
    public void addChildren(Arc a) { this.childrens.add(a); }
    public void addChildrens(ArrayList<Arc> arcs) { this.childrens.addAll(arcs); }
    public void removeChildren(Arc a) { this.childrens.remove(a); }

    public void addParent(Place p) { this.parents.add(new Arc(p)); }
    public void addParent(Arc a) { this.parents.add(a); }
    public void addParents(ArrayList<Arc> arcs) { this.parents.addAll(arcs); }
    public void removeParent(Arc a) { this.parents.remove(a); }

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
