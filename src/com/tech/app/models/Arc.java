package com.tech.app.models;

public class Arc {

    private Place place;
    private int poids;

    public Arc(Place place, int poids){
        this.place = place;
        this.poids = poids;
    }

    public Arc(Place place) { this(place, 1); }

    public Place getPlace() { return this.place; }
    public int getPoids() { return this.poids; }

    public void setPlace(Place place) { this.place = place; }
    public void setPoids(int poids) { this.poids = poids; }

    @Override
    public String toString() {
        return "Arc{" +
                "place=" + place +
                ", poids=" + poids +
                '}';
    }
}
