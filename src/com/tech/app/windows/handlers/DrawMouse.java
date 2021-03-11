package com.tech.app.windows.handlers;

import com.tech.app.windows.panels.DrawPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class DrawMouse implements MouseListener {


    public boolean mousePressed = false;
    public boolean mouseReleased = true;
    public boolean mouseClicked = false;
    public boolean mouseEntered = false;
    public boolean mouseExited = true;
    public enum MODE {
        NONE,
        PLACE,
        TRANSITION,
        ARC,
        DEFAULT
    }
    public MODE mode;
    public DrawPanel drawPanel;
    public Graphics g;

    public DrawMouse(JFrame frame, DrawPanel drawPanel){
        this.drawPanel = drawPanel;
        drawPanel.addMouseListener(this);

    }

    public void action(MODE mode){
        this.mode=mode;
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        System.out.println("Mouse clicked");
        if(mouseEntered && mode == MODE.PLACE){
            drawPanel.addPlace(mouseEvent.getX(),mouseEvent.getY());
            System.out.println("Place mise");
        }
        mouseClicked = true;
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
        System.out.println("Mouse pressed");
        mousePressed = true;
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
        System.out.println("Mouse released");
        mousePressed = false;
        mouseClicked = false;

    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
        System.out.println("Mouse in");
        mouseEntered = true;

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
        System.out.println("Mouse out");
        mouseEntered = false;
    }
}
