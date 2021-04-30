package com.tech.app.models.gma;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Cette classe nous permet de créer une structure d'arbre que l'on utilise dans la création du GMA.
 * Un noeud représente un marquage accessible et contient une référence de ses enfants et parents.
 */
public class Node {

    private Vector<Integer> m;
    private List<NodeStruct> children;
    private List<NodeStruct> parents;
    private String name;

    /**
     * Constructeur pour la racine de l'arbre.
     * @param m : marquage initial.
     * @param children : Liste de ses enfants.
     * @param parents : Liste de ses parents.
     */
    public Node(Vector<Integer> m, List<NodeStruct> children, List<NodeStruct> parents) {
        this(m,children,parents,null);
    }

    /**
     * Constructeur pour un noeud quelconque.
     * @param m : marquage initial.
     * @param children : Liste de ses enfants.
     * @param parents : Liste de ses parents.
     * @param name : nom du marquage.
     */
    public Node(Vector<Integer> m, List<NodeStruct> children, List<NodeStruct> parents, String name){
        this.m = m;
        this.children = children;
        this.parents = parents;
        this.name = name;

    }

    /**
     * Constructeur
     * @param m : marquage
     */
    public Node(Vector<Integer> m) {
        this(m, new ArrayList<>(), new ArrayList<>());
    }

    public Node(Vector<Integer> m, String name) {
        this(m, new ArrayList<>(), new ArrayList<>(),name);
    }

    /**
     * Méthode qui permet de donner un nom à un noeud.
     * @param name : nom du noeud.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Méthode qui permet de récupérer le nom du noeud.
     * @return name : nom du noeud.
     */
    public String getName() {
        return name;
    }


    /**
     * Méthode qui permet de récupérer le marquage du noeud.
     * @return m : marquage
     */
    public Vector<Integer> getM() { return m; }

    /**
     * Méthode qui permet de donner/modifier le marquage d'un noeud.
     * @param m : marquage
     */
    public void setM(Vector<Integer> m) { this.m = m; }

    /**
     * Méthode qui permet de récupérer la liste des enfants du noeud.
     * @return Liste des noeuds enfants (NodeStruct).
     */
    public List<NodeStruct> getChildren() { return children; }

    /**
     * Méthode qui permet de donner/modifier la liste des enfants du noeud.
     * @param children : Liste des noeuds enfants (NodeStruct)
     */
    public void setChildren(List<NodeStruct> children) { this.children = children; }

    /**
     * Méthode qui permet de récupérer la liste des parents du noeud.
     * @return Liste des parents du noeud (NodeStruct)
     */
    public List<NodeStruct> getParents() { return parents; }

    /**
     * Méthode qui permet de donner/modifier la liste des parents du noeud.
     * @param parents : Liste des parents du noeud (NodeStruct).
     */
    public void setParents(List<NodeStruct> parents) { this.parents = parents; }

    /**
     * Méthode qui permet d'ajouter un noeud (NodeStruct) à la liste des parents du noeud.
     * @param n : (NodeStruct) noeud que l'on veut ajouter à la liste des parents.
     */
    public void addParent(NodeStruct n) { this.parents.add(n); }

    /**
     * Méthode qui permet d'ajouter un noeud (NodeStruct) à la liste des enfants du noeud.
     * @param n : (NodeStruct) noeud que l'on veut ajouter à la liste des enfants.
     */
    public void addChildren(NodeStruct n) { this.children.add(n); }

    /**
     * Méthode qui permet d'afficher les différents paramètres d'un noeud.
     * @return affichage des paramètres du noeuds dans l'ordre suivant : marquage du noeud -> Liste des enfants -> Liste des parents.
     */
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

    /**
     * Méthode qui permet de récupérer le marquage d'un noeud à la verticale sous forme de String.
     * Utilisé pour l'affichage des marquages dans le GMA.
     * @return marquage du noeud sous la forme d'un string.
     */
    public String getMarquage(){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < m.size(); i++){
            s.append(this.getM().get(i));
            s.append("\n");
        }
        return s.toString();
    }

}
