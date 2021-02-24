package com.tech.app.models.v4;

public class Place {

    private String name;
    private int x, y, marquage;

    public Place(String name, int x, int y, int marquage) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.marquage = marquage;
    }

    public Place(String name, int x, int y) { this(name, x, y, 0); }
    public Place(String name) { this(name, 0, 0, 0); }

    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getMarquage() { return marquage; }

    public void addMarquage(int marquage) { this.marquage += marquage; }
    public void removeMarquage(int marquage) { this.marquage = Math.max(this.marquage - marquage, 0); }
    public void setMarquage(int marquage) { this.marquage = Math.max(marquage, 0); }
    public void resetMarquage() { this.marquage = 0; }

    public String toString() {
        return "P(\""+ this.name + "\", " + this.x + ", " + this.y + ", m:"+ this.marquage + ")";
    }

}
