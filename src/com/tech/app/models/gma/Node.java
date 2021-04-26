package com.tech.app.models.gma;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Node {

    private Vector<Integer> m;
    private List<NodeStruct> children;
    private List<NodeStruct> parents;
    private String name;

    public Node(Vector<Integer> m, List<NodeStruct> children, List<NodeStruct> parents) {
        this(m,children,parents,null);
    }

    public Node(Vector<Integer> m, List<NodeStruct> children, List<NodeStruct> parents, String name){
        this.m = m;
        this.children = children;
        this.parents = parents;
        this.name = name;

    }

    public Node(Vector<Integer> m) {
        this(m, new ArrayList<>(), new ArrayList<>());
    }
    public Node(Vector<Integer> m, String name) {
        this(m, new ArrayList<>(), new ArrayList<>(),name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



    public Vector<Integer> getM() { return m; }
    public void setM(Vector<Integer> m) { this.m = m; }
    public List<NodeStruct> getChildren() { return children; }
    public void setChildren(List<NodeStruct> children) { this.children = children; }
    public List<NodeStruct> getParents() { return parents; }
    public void setParents(List<NodeStruct> parents) { this.parents = parents; }

    public void addParent(NodeStruct n) { this.parents.add(n); }
    public void addChildren(NodeStruct n) { this.children.add(n); }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("m:\n").append(m).append("\nChildren:\n");
        for (NodeStruct n : this.children) {
            sb.append(this.children.indexOf(n));
            sb.append("\t").append(n.node);
        }
        sb.append("\nParents:\n");
        for (NodeStruct n : this.parents) {
            sb.append(this.parents.indexOf(n));
            sb.append("\t").append(n.node);
        }
        return sb.toString();
    }

    public String getMarquage(){
        return String.valueOf(this.getM());
    }

}
