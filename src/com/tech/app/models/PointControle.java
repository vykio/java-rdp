package com.tech.app.models;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class PointControle implements Serializable {

    private double x, y;
    private boolean moved;
    private int size;

    public PointControle () {
        this(0,0);
    }
    public PointControle (double x, double y) {
        this.x = x;
        this.y = y;
        this.moved = false;
        this.size = 5;
    }

    public void updatePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public boolean getMoved() { return moved; }

    public int getSize() {
        return size;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public void draw(Graphics2D g2) {
        g2.draw(new Rectangle2D.Double(this.getX(), this.getY(), size, size));
    }

    public boolean contains(double x, double y) {
        return (Math.abs(x - this.getX()) < size) && (Math.abs(y - this.getY()) < size);
    }

    @Override
    public String toString() {
        return "PointControle{" +
                "x=" + x +
                ", y=" + y +
                ", moved=" + moved +
                ", size=" + size +
                '}';
    }
}
