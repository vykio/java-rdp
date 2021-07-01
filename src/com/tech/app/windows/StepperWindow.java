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
        build();

    }

    public void build(){

        StepperHandler stepperHandler = new StepperHandler(this,model);
        stepperHandler.applyPanel();

        StepperMouse stepperMouse = new StepperMouse(stepperHandler);

        StepperToolbar stepperToolbar = new StepperToolbar(this,model,stepperMouse, stepperHandler);
        stepperToolbar.applyToolbar();

        Stepper stepper = new Stepper(model,stepperHandler);

    }
}
