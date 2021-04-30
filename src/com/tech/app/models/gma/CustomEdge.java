package com.tech.app.models.gma;

import org.jgrapht.graph.DefaultEdge;

/**
 *  CustomEdge est une classe qui est utilisée dans l'affichage du GMA. Cette classe hérite de la classe DefaultEdge qui provient de la librairie JGraphT.
 *  Cette classe permet l'affichage d'un label sur un arc du GMA.
 */
public class CustomEdge extends DefaultEdge {

    private final String label;

    /**
     * Cette méthode permet d'ajouter un label à un arc.
     * @param label : label que l'on veut ajouter à l'arc.
     */
    public CustomEdge(String label){
        this.label = label;
    }

    /**
     * Cette méthode permet de récupérer le label d'une instance de CustomEdge.
     * @return label de l'instance de CustomEdge.
     */
    public String getLabel(){
        return this.label;
    }

    /**
     * Affichage d'une instance de CustomEdge.
     * @return label de l'instance de CustomEdge.
     */
    @Override
    public String toString()
    {
        return label;
    }
}
