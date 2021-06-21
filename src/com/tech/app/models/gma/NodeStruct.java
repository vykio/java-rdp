package com.tech.app.models.gma;

import com.tech.app.models.Transition;

/**
 * Cette classe permet de faire le lien entre un noeud et la transition qui mène à ce noeud.
 * Cette classe est utilisée dans la création du GMA pour pouvoir afficher la transition qui mène d'un marquage à l'autre.
 */
public class NodeStruct {

    public Node node;
    public Transition transition;

    /**
     * Constructeur
     * @param node : noeud
     * @param transition : transition
     */
    public NodeStruct(Node node, Transition transition) {
        this.node = node;
        this.transition = transition;
    }

    public Node getNode() {
        return node;
    }
}
