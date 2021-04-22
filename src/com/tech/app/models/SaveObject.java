package com.tech.app.models;

import java.io.Serializable;
import java.util.List;

public class SaveObject implements Serializable {

    public List<Place> placeVector;
    public List<Transition> transitionVector;

    public SaveObject(List<Place> placeVector, List<Transition> transitionVector) {
        this.placeVector = placeVector;
        this.transitionVector = transitionVector;
    }

    public List<Place> getPlaceVector() { return placeVector; }
    public List<Transition> getTransitionVector() { return transitionVector; }
}
