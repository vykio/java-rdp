package com.tech.app.models.v2;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public enum TYPE {
        PLACE, TRANSITION,
    }

    private List<Node> childrens;
    private List<Node> parents;

    private int x,y;
    private TYPE type;
    private String name;

    public Node(String name, int x, int y, TYPE type) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.type = type;
        this.childrens = new ArrayList<Node>();
        this.parents = new ArrayList<Node>();
    }

    public Node(String name, int x, int y) {
        this(name, x, y, Node.TYPE.PLACE);
    }

    public Node(TYPE type) {
        this("default", 0,0,type);
    }

    public String getName() { return this.name; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public String toString() {
        if (this.type == TYPE.PLACE) { String type = "Place"; }
        if (this.type == TYPE.TRANSITION) { String type = "Transition"; }
        return type + " " + this.name + " (" + this.x + ":" + this.y + ")";
    }

    public void addChildrens(ArrayList<Node> childrens) { this.childrens.addAll(childrens); }
    public void addChildren(Node children) { this.childrens.add(children); }
    public Node deleteChildren(String name) {
        Node nodeToDelete = null;
        for (Node children : childrens) {
            if (children.getName().equals(name)) {
                nodeToDelete = children;
                childrens.remove(children);
                break;
            }
        }
        return nodeToDelete;
    }
    public List<Node> getChildrens() { return this.childrens; }
    public void eraseChildrens() { this.childrens = new ArrayList<Node>(); }

    public void addParents(ArrayList<Node> parents) { this.parents.addAll(parents); }
    public void addParent(Node parent) { this.parents.add(parent); }
    public Node deleteParent(String name) {
        Node nodeToDelete = null;
        for (Node parent : parents) {
            if (parent.getName().equals(name)) {
                nodeToDelete = parent;
                parents.remove(parent);
                break;
            }
        }
        return nodeToDelete;
    }
    public List<Node> getParents() { return this.parents; }
    public void eraseParents() { this.parents = new ArrayList<Node>(); }

}
