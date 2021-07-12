package com.tech.app.windows;

import com.tech.app.functions.FUtils;
import com.tech.app.models.Model;

import com.tech.app.models.stepper.Stepper;
import com.tech.app.windows.handlers.DrawMouse;
import com.tech.app.windows.handlers.StepperMouse;
import com.tech.app.windows.handlers.StepperWindowHandler;
import com.tech.app.windows.panels.StepperHandler;
import com.tech.app.windows.toolbars.StepperToolbar;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.*;

public class StepperWindow extends Window{

    private final Model model;

    public StepperWindow (int width, int height, Model model) throws UnsupportedLookAndFeelException {
        super(true, "Simulation pas à pas - Java_RDP - " + FUtils.OS.getOs(), width, height, true, true);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            //e.printStackTrace();
            System.out.println("Thème système non trouvé");
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        }
        this.model = model;
        model.updateMatrices();
        setWindowHandler(new StepperWindowHandler(this));
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                model.setMarquage(model.getM0());
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        build();

    }

    public void build() {

        Stepper stepper = new Stepper(model);

        StepperHandler stepperHandler = new StepperHandler(this, stepper);
        stepper.setStepperHandler(stepperHandler);

        //System.out.println("stepper from window hash : "+stepper.hashCode());

        StepperMouse stepperMouse = new StepperMouse(stepperHandler);

        StepperToolbar stepperToolbar = new StepperToolbar(this,stepperMouse);
        stepperToolbar.applyToolbar();

        stepperHandler.applyPanel();
        /*
        try {
            shiftPanelDown(stepperHandler);
        } catch (AWTException e){
            e.printStackTrace();
        }



         */
        //stepperHandler.updateInitPositions();
    }

    private static void shiftPanelDown(StepperHandler stepperHandler) throws AWTException {
        Robot robot = new Robot();
        robot.mouseMove(stepperHandler.getWidth()/2, stepperHandler.getHeight()/2);
        stepperHandler.updatePositions(stepperHandler.scaleX,stepperHandler.scaleY,(double) (stepperHandler.getWidth()/2)+1, (double)(stepperHandler.getHeight()/2)+1);
        //robot.mouseMove((stepperHandler.getWidth()/2)+1,(stepperHandler.getHeight()/2)+1);
        //robot.mouseRelease(MouseEvent.MOUSE_WHEEL);
    }
}
