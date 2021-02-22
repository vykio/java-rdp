package com.tech.app.models;

import java.util.ArrayList;
import java.util.List;

public class Transition extends Node {

    private boolean validated;

    private List<Place> following;

    public Transition(int x, int y, ArrayList<Place> placesSuivantes) {
        super(x, y, Node.type.TRANSITION);
        this.validated = false;
        this.following = placesSuivantes;
    }

    public Transition(int x, int y) {
        this(x, y, new ArrayList<Place>());
    }

    public boolean isValidated() { return this.validated; }
    public void setValidated(boolean validated) { this.validated = validated; }

    public List<Place> getFollowing() { return this.following; }
    public void setFollowing(List<Place> following) { this.following = following; }
    public void addPlace(Place place) { this.following.add(place); }
    public void addPlaces(ArrayList<Place> places) { this.following.addAll(places); }

}
