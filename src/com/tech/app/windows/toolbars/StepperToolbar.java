package com.tech.app.windows.toolbars;

import com.tech.app.models.Model;
import com.tech.app.models.stepper.Stepper;
import com.tech.app.windows.handlers.StepperMouse;
import com.tech.app.windows.panels.StepperHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class StepperToolbar extends Toolbar{

    public Model model;
    public Stepper stepper;
    public StepperMouse stepperMouse;
    public StepperHandler stepperHandler;

    public StepperToolbar(JFrame frame, Model model, StepperMouse stepperMouse, StepperHandler stepperHandler){
        super(frame);
        this.model = model;
        this.stepper = stepperMouse.stepper;
        this.stepperHandler = stepperHandler;
    }

    @Override
    public JToolBar getToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton btnOrigin = new JButton();
        btnOrigin.setToolTipText("Retourner au marquage d'origine");
        btnOrigin.addActionListener(this::btnOriginListener);
        toolBar.add(btnOrigin);

        JButton btnPrevious = new JButton();
        btnPrevious.setToolTipText("Retourner au marquage précédent");
        btnPrevious.addActionListener(this::btnPreviousListener);
        toolBar.add(btnPrevious);

        JButton btnNext = new JButton();
        btnNext.setToolTipText("Aller au marquage suivant");
        btnNext.addActionListener(this::btnNextListener);
        toolBar.add(btnNext);


        JButton btnLast = new JButton();
        btnLast.setToolTipText("Aller au dernier marquage généré");
        btnLast.addActionListener(this::btnLastListener);
        toolBar.add(btnLast);

        toolBar.addSeparator();

        JToggleButton btnAuto = new JToggleButton();
        btnAuto.setText("Automatique");
        btnAuto.setToolTipText("On franchit aléatoirement les transitions franchissables");
        ItemListener itemListener = itemEvent -> {
            int state = itemEvent.getStateChange();
            do {
                btnAuto.setText("Stop");
                state = itemEvent.getStateChange();
                stepper.randomize();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                //stepperHandler.repaint();
            }while(state == ItemEvent.SELECTED);


        };
        btnAuto.addItemListener(itemListener);
        btnAuto.addActionListener(this::btnAutoListener);
        toolBar.add(btnAuto);

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(btnOrigin);
        btnGroup.add(btnPrevious);
        btnGroup.add(btnNext);
        btnGroup.add(btnLast);


        Image imageOrigin = null;
        Image imagePrevious = null;
        Image imageNext = null;
        Image imageLast = null;

        try {
            imageOrigin = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/origin.png")));
            imagePrevious = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/previous.png")));
            imageNext = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/next.png")));
            imageLast = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/last.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert imageOrigin != null;
        assert imagePrevious != null;
        assert imageNext != null;
        assert imageLast != null;

        btnOrigin.setIcon(new ImageIcon(imageOrigin));
        btnPrevious.setIcon(new ImageIcon(imagePrevious));
        btnNext.setIcon(new ImageIcon(imageNext));
        btnLast.setIcon(new ImageIcon(imageLast));

        return toolBar;
    }

    @Override
    public void applyToolbar() {
        frame.getContentPane();
        frame.add( this.getToolbar(), BorderLayout.PAGE_START);
        this.frame.setVisible(true);

    }

    public void btnOriginListener(ActionEvent event){
        stepper.goToFirstMarquage();
    }

    public void btnPreviousListener(ActionEvent event){
        stepper.goToPreviousMarquage();
    }

    public void btnNextListener(ActionEvent event){
        stepper.goToNextMarquage();
    }

    public void btnLastListener(ActionEvent event){
        stepper.goToLastMarquage();
    }

    public void btnAutoListener(ActionEvent event){

    }
}
