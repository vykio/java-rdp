package com.tech.app.models.gma;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Node {

    private Vector<Integer> m;
    private List<Node> children;
    private List<Node> parents;

    public Node(Vector<Integer> m, List<Node> children, List<Node> parents) {
        this.m = m;
        this.children = children;
        this.parents = parents;
    }

    public Node(Vector<Integer> m) {
        this(m, new ArrayList<Node>(), new ArrayList<Node>());
    }

    public Vector<Integer> getM() { return m; }
    public void setM(Vector<Integer> m) { this.m = m; }
    public List<Node> getChildren() { return children; }
    public void setChildren(List<Node> children) { this.children = children; }
    public List<Node> getParents() { return parents; }
    public void setParents(List<Node> parents) { this.parents = parents; }

    public void addParent(Node n) { this.parents.add(n); }
    public void addChildren(Node n) { this.children.add(n); }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("m:\n").append(m).append("\nChildren:\n");
        for (Node n : this.children) {
            sb.append(this.children.indexOf(n));
            sb.append("\t").append(n);
        }
        sb.append("\nParents:\n");
        for (Node n : this.parents) {
            sb.append(this.parents.indexOf(n));
            sb.append("\t").append(n);
        }
        return sb.toString();
    }

}
