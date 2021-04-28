package com.tech.app.models.gma;

import com.tech.app.models.Transition;

public class NodeStruct {

    public Node node;
    public Transition transition;

    public NodeStruct(Node node, Transition transition) {
        this.node = node;
        this.transition = transition;
    }

}
