package com.tech.app.windows.handlers;

import com.tech.app.models.Place;
import com.tech.app.windows.panels.DrawPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;


public class DrawMouse extends MouseAdapter {

    public boolean mousePressed = false;
    public boolean mouseReleased = true;
    public boolean mouseClicked = false;
    public boolean mouseEntered = false;
    public boolean mouseExited = true;

    private double x;
    private double y;

    public enum MODE {
        NONE,
        PLACE,
        TRANSITION,
        ARC,
        ATTRIBUTS,
        SELECT,
        LABEL
    }

    public MODE mode;
    public DrawPanel drawPanel;
    public Graphics g;

    private Object objectDragged = null;

    public DrawMouse(JFrame frame, DrawPanel drawPanel){
        this.drawPanel = drawPanel;
        drawPanel.addMouseListener(this);
        drawPanel.addMouseMotionListener(this);
        drawPanel.addMouseWheelListener(new ScaleHandler());
        this.mode = MODE.NONE;
    }

    class ScaleHandler implements MouseWheelListener {
        public void mouseWheelMoved(MouseWheelEvent e) {
            final double factor = (e.getWheelRotation() < 0) ? 1.1 : 0.9;
            double scale = drawPanel.scaleX * factor;
            scale = Math.max(drawPanel.MIN_ZOOM, scale);
            scale = Math.min(drawPanel.MAX_ZOOM, scale);
            drawPanel.scaleX = scale;
            drawPanel.scaleY = scale;
            drawPanel.transform = AffineTransform.getScaleInstance(drawPanel.scaleX, drawPanel.scaleY);
            //System.out.println("Scale: " + scale);
            drawPanel.repaint();
        }
    }

    public void action(MODE mode){
        this.mode=mode;
    }

    public void clearPanel() {
        this.drawPanel.clearAll();
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        //System.out.println("Mouse clicked");
        mouseClicked = true;
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
        //System.out.println("Mouse pressed");
        x = mouseEvent.getX() * (double)drawPanel.scaleFactor;
        y = mouseEvent.getY() * (double)drawPanel.scaleFactor;
        System.out.println(drawPanel.scaleFactor);

        drawPanel.mouseX = x;
        drawPanel.mouseY = y;

        if(mouseEntered){

            if (SwingUtilities.isLeftMouseButton(mouseEvent)){
                switch(mode){
                    case PLACE:
                        drawPanel.addPlace(x/drawPanel.scaleX,y/ drawPanel.scaleY);
                        System.out.println("Place mise");
                        break;
                    case ARC:
                        drawPanel.loadCoordinatesArc(x /drawPanel.scaleX, y /drawPanel.scaleY);
                        System.out.println("Arc test");
                        break;
                    case TRANSITION:
                        drawPanel.addTransition(x / drawPanel.scaleX,y/ drawPanel.scaleY);
                        System.out.println("Transition mise");
                        break;
                    case ATTRIBUTS:
                        objectDragged = drawPanel.getSelectedObject(x / drawPanel.scaleX, y / drawPanel.scaleY);
                        if (objectDragged != null) {
                            drawPanel.showOptions(objectDragged);
                        } else {
                            drawPanel.showModel();
                        }
                        break;
                    case SELECT:
                        objectDragged = drawPanel.getSelectedObject(x/drawPanel.scaleX, y/drawPanel.scaleY);
                        System.out.println(objectDragged);
                        drawPanel.selectObject(objectDragged);
                        break;
                    case LABEL:
                        objectSelected = drawPanel.getSelectedObject(x / drawPanel.scaleX, y / drawPanel.scaleY);
                        if (objectSelected != null) {
                            drawPanel.showOptionsLabel(objectSelected);
                        } else {
                            drawPanel.errorSelect();
                        }
                        break;
                }
            }


        }

        mousePressed = true;
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
        //System.out.println("Mouse released");
        mousePressed = false;
        mouseClicked = false;
        if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
            objectDragged = null;
        }

    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
        //System.out.println("Mouse in");
        mouseEntered = true;

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
        //System.out.println("Mouse out");
        mouseEntered = false;
    }

    public void mouseDragged(MouseEvent e) {
        double dx = e.getX()*drawPanel.scaleFactor - x;
        double dy = e.getY()*drawPanel.scaleFactor - y;

        if (SwingUtilities.isMiddleMouseButton(e)) {

            drawPanel.updatePositions(drawPanel.scaleX, drawPanel.scaleY, dx, dy);

        } else if (SwingUtilities.isLeftMouseButton(e)) {
            if (mode == MODE.SELECT) {

                drawPanel.updatePosition(objectDragged ,x/drawPanel.scaleX,y/drawPanel.scaleY, drawPanel.scaleX, drawPanel.scaleY, dx, dy);
            }
        }

        x += dx;
        y += dy;
    }

}
