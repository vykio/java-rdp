package com.tech.app.windows.handlers;

import com.tech.app.models.stepper.Stepper;
import com.tech.app.windows.panels.StepperHandler;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

public class StepperMouse extends MouseAdapter {

    public StepperHandler stepperHandler;
    public Stepper stepper;

    public boolean mousePressed = false;
    public boolean mouseClicked = false;
    public boolean mouseEntered = false;

    private double x;
    private double y;

    private Object selectedObject = null;

    public StepperMouse(StepperHandler stepperHandler) {
        this.stepperHandler = stepperHandler;
        this.stepper = new Stepper(stepperHandler.model);
        stepperHandler.addMouseListener(this);
        stepperHandler.addMouseMotionListener(this);
        stepperHandler.addMouseWheelListener(new StepperScaleHandler());
    }

    /**
     * Classe qui permet de gérer l'échelle en fonction du zoom.
     */
    class StepperScaleHandler implements MouseWheelListener {
        /**
         * Méthode qui permet de savoir si l'utilisateur utilise la molette de la souris
         * @param e : évenement de mouvement de la molette.
         */
        public void mouseWheelMoved(MouseWheelEvent e) {
            System.out.println("oui");
            final double factor = (e.getWheelRotation() < 0) ? 1.1 : 0.9;
            double scale = stepperHandler.scaleX * factor;
            scale = Math.max(stepperHandler.MIN_ZOOM, scale);
            scale = Math.min(stepperHandler.MAX_ZOOM, scale);
            stepperHandler.scaleX = scale;
            stepperHandler.scaleY = scale;
            stepperHandler.transform = AffineTransform.getScaleInstance(stepperHandler.scaleX, stepperHandler.scaleY);
            //System.out.println("Scale: " + scale);
            stepperHandler.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX() * stepperHandler.scaleX;
        y = e.getY() * stepperHandler.scaleY;

        System.out.println(x +"," + y);

        stepperHandler.mouseX = x;
        stepperHandler.mouseY = y;

        if(mouseEntered){
            if(SwingUtilities.isLeftMouseButton(e)){
                selectedObject = stepperHandler.getSelectedObject(x/stepperHandler.scaleX, y/stepperHandler.scaleY);
                System.out.println(selectedObject);
            }
            if(selectedObject!=null){
                stepper.goToNextMarquage(selectedObject);
                stepperHandler.repaint();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        mouseClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseEntered = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseEntered = false;
    }

    public void mouseDragged(MouseEvent e) {
        double dx = e.getX()*stepperHandler.scaleX - x;
        double dy = e.getY()*stepperHandler.scaleY - y;

        if (SwingUtilities.isMiddleMouseButton(e)) {

            stepperHandler.updatePositions(stepperHandler.scaleX, stepperHandler.scaleY, dx, dy);

        }

        x += dx;
        y += dy;
    }
}
