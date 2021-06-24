package com.tech.app.windows.handlers;

import com.tech.app.models.PointControle;
import com.tech.app.windows.panels.DrawPanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

/**
 * Cette classe permet de récupérer les actions de la souris sur l'application, plus particulièrement dans la zone de dessin.
 * Cette classe hérite de l'interface MouseAdapter de Java Event.
 */
public class DrawMouse extends MouseAdapter {

    public boolean mousePressed = false;
    public boolean mouseClicked = false;
    public boolean mouseEntered = false;

    private double x;
    private double y;

    /**
     * Modes : NONE, PLACE, TRANSITION, ARC, ATTRIBUTS, SELECT, LABEL.
     */
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
    private Object objectDragged = null;

    /**
     * Constructeur de la souris
     * @param drawPanel : drawPanel
     */
    public DrawMouse(DrawPanel drawPanel){
        this.drawPanel = drawPanel;
        drawPanel.addMouseListener(this);
        drawPanel.addMouseMotionListener(this);
        drawPanel.addMouseWheelListener(new ScaleHandler());
        this.mode = MODE.NONE;
    }

    /**
     * Classe qui permet de gérer l'échelle en fonction du zoom.
     */
    class ScaleHandler implements MouseWheelListener {
        /**
         * Méthode qui permet de savoir si l'utilisateur utilise la molette de la souris
         * @param e : évenement de mouvement de la molette.
         */
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

    /**
     * Classe qui permet de modifier le mode de la souris en fonction de l'action voulue par l'utilisateur.
     * @param mode : MODE
     */
    public void action(MODE mode){
        this.mode=mode;
    }

    /**
     * Méthode qui appelle la fonction clearAll de la classe DrawPanel.
     */
    public void clearPanel() {
        this.drawPanel.clearAll();
    }

    /**
     * Méthode qui permet de savoir si l'utilisateur à cliquer.
     * @param mouseEvent : évènement de la souris.
     */
    @Override
    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        //System.out.println("Mouse clicked");
        mouseClicked = true;
    }

    /**
     * Méthode qui permet de savoir si l'utilisateur a un bouton de la souris enfoncé.
     * @param mouseEvent : évènement de la souris.
     */
    @Override
    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
        //System.out.println("Mouse pressed");
        x = mouseEvent.getX() * drawPanel.scaleFactor;
        y = mouseEvent.getY() * drawPanel.scaleFactor;
        System.out.println(drawPanel.scaleFactor);

        drawPanel.mouseX = x;
        drawPanel.mouseY = y;

        // Si la souris est sur l'application
        if(mouseEntered){
            // Si c'est un clic gauche
            if (SwingUtilities.isLeftMouseButton(mouseEvent)){
                // En fonction du mode sélectionné :
                switch(mode){
                    case PLACE:
                        drawPanel.addPlace(x/drawPanel.scaleX,y/ drawPanel.scaleY);
                        //System.out.println("Place mise");
                        break;
                    case ARC:
                        drawPanel.loadCoordinatesArc(x /drawPanel.scaleX, y /drawPanel.scaleY);
                        //System.out.println("Arc test");
                        break;
                    case TRANSITION:
                        drawPanel.addTransition(x / drawPanel.scaleX,y/ drawPanel.scaleY);
                        //System.out.println("Transition mise");
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
                        //System.out.println(objectDragged);
                        drawPanel.selectObject(objectDragged);
                        break;
                    case LABEL:
                        objectDragged = drawPanel.getSelectedObject(x / drawPanel.scaleX, y / drawPanel.scaleY);
                        if (objectDragged != null) {
                            drawPanel.showOptionsLabel(objectDragged);
                        } else {
                            drawPanel.errorSelect();
                        }
                        break;
                }
            }
        }
        mousePressed = true;
    }

    /**
     * Méthode qui permet de savoir si l'utilisateur a relaché un bouton de la souris qui était enfoncé.
     * @param mouseEvent : évènement de la souris.
     */
    @Override
    public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
        //System.out.println("Mouse released");
        mousePressed = false;
        mouseClicked = false;
        if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
            objectDragged = null;
        }

    }

    /**
     * Méthode qui permet de savoir si la souris est sur la fenêtre de l'application.
     * @param mouseEvent : évènement de la souris.
     */
    @Override
    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
        //System.out.println("Mouse in");
        mouseEntered = true;

    }

    /**
     * Méthode qui permet de savoir si la souris quitte la fenêtre de l'application.
     * @param mouseEvent : évènement de la souris.
     */
    @Override
    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
        //System.out.println("Mouse out");
        mouseEntered = false;
    }

    /**
     * Méthode qui permet de savoir si l'utilisateur est en train d'essayer de déplacer un objet.
     * @param e : évènement de la souris.
     */
    public void mouseDragged(MouseEvent e) {
        double dx = e.getX()*drawPanel.scaleFactor - x;
        double dy = e.getY()*drawPanel.scaleFactor - y;

        if (SwingUtilities.isMiddleMouseButton(e)) {

            drawPanel.updatePositions(drawPanel.scaleX, drawPanel.scaleY, dx, dy);

        } else if (SwingUtilities.isLeftMouseButton(e)) {
            if (mode == MODE.SELECT) {

                if(objectDragged instanceof PointControle){
                    drawPanel.updatePosition(objectDragged,drawPanel.scaleX, drawPanel.scaleY, e.getX() - x, e.getY() - y);
                }

                drawPanel.updatePosition(objectDragged ,drawPanel.scaleX, drawPanel.scaleY, dx, dy);
            }
        }

        x += dx;
        y += dy;
    }

}
