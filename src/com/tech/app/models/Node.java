package com.tech.app.models;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public enum type {
        PLACE,
        TRANSITION,
    }

    private int x, y;
    private type type;

    private List<Node> childrens;
    private List<Node> parents;

    public Node(int x, int y, type type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public List<Node> getChildrens() { return this.childrens; }
    public void setChildrens(ArrayList<Node> childrens) { this.childrens = childrens; }
    public <T extends Node> void addChildrens(ArrayList<T> childrens) { this.childrens.addAll(childrens); }
    public <T extends Node> void addChildren(T children) { this.childrens.add(children); }

    public List<Node> getParents() { return this.parents; }
    public void setParents(ArrayList<Node> parents) { this.parents = parents; }
    public <T extends Node> void addParents(ArrayList<T> parents) { this.childrens.addAll(parents); }
    public <T extends Node> void addParent(T parent) { this.childrens.add(parent); }

}
