package com.tech.app.models.gma;

import org.jgrapht.graph.DefaultEdge;

public class CustomEdge extends DefaultEdge {

    private String label;

    public CustomEdge(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

    @Override
    public String toString()
    {
        return label;
    }
}
