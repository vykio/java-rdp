package com.tech.app.windows.handlers;

import com.tech.app.windows.panels.DrawPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;


public class DrawMouse extends MouseAdapter {


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
        ATTRIBUTS,
        SELECT
    }
    public MODE mode;
    public DrawPanel drawPanel;
    public Graphics g;

    public DrawMouse(JFrame frame, DrawPanel drawPanel){
        this.drawPanel = drawPanel;
        drawPanel.addMouseListener(this);
        this.mode = MODE.NONE;
    }

    public void action(MODE mode){
        this.mode=mode;
    }

    public void clearPanel() {
        this.drawPanel.clearAll();
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        System.out.println("Mouse clicked");


        mouseClicked = true;
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
        System.out.println("Mouse pressed");

        if(mouseEntered){
            if (SwingUtilities.isLeftMouseButton(mouseEvent)){
                switch(mode){
                    case PLACE:
                        drawPanel.addPlace(mouseEvent.getX(),mouseEvent.getY());
                        System.out.println("Place mise");
                        break;
                    case ARC:
                        break;
                    case TRANSITION:
                        drawPanel.addTransition(mouseEvent.getX(),mouseEvent.getY());
                        System.out.println("Transition mise");
                        break;
                    case ATTRIBUTS:
                        break;
                    case SELECT:
                        break;
                }
            }


        }

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
