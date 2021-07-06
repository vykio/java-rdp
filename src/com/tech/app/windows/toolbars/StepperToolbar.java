package com.tech.app.windows.toolbars;

import com.tech.app.models.Model;
import com.tech.app.models.stepper.Stepper;
import com.tech.app.windows.handlers.StepperMouse;
import com.tech.app.windows.panels.StepperHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class StepperToolbar extends Toolbar{

    public Model model;
    public Stepper stepper;

    public StepperToolbar(JFrame frame, StepperMouse stepperMouse){
        super(frame);
        this.model = stepperMouse.model;
        this.stepper = stepperMouse.stepper;
    }

    @Override
    public JToolBar getToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));

        //System.out.println("stepper from toolbar hash : "+stepper.hashCode());


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

        JButton btnAutoON = new JButton();
        btnAutoON.setText("Automatique : Début");
        btnAutoON.setToolTipText("On franchit aléatoirement les transitions franchissables");
        toolBar.add(btnAutoON);

        JButton btnAutoOFF = new JButton();
        btnAutoOFF.setText("Automatique : Fin");
        btnAutoOFF.setToolTipText("Arret de la simulation aléatoire.");
        btnAutoOFF.setEnabled(false);
        toolBar.add(btnAutoOFF);

        JButton textVitesse = new JButton("Pas de simulation (ms)");
        textVitesse.setEnabled(false);
        toolBar.add(textVitesse);

        int[] vitesse = new int[]{1000};
        String[] v = {"0","100","200","300","400","500","600","700","800","900","1000"};
        JComboBox<String> vitesses =  new JComboBox<>(v);
        vitesses.setSelectedItem(v[v.length-1]);
        toolBar.add(vitesses);

        toolBar.addSeparator();

        /*
        JRadioButton btnSequence = new JRadioButton();
        btnSequence.setText("Affichage de la séquence");
        btnSequence.setToolTipText("Affichage de la séquence de transition de la simulation en cours");
        btnSequence.addActionListener(this::btnSequenceListener);
        toolBar.add(btnSequence);
         */

        JToggleButton btnSequence = new JToggleButton();
        btnSequence.setText("Affichage de la séquence");
        btnSequence.setToolTipText("Affichage de la séquence de transition de la simulation en cours");
        btnSequence.addActionListener(this::btnSequenceListener);
        toolBar.add(btnSequence);

        /* Gestion du mode automatique*/

        Timer timer = new Timer(vitesse[0], e -> {
            if(!stepper.model.getTransitionFranchissables().isEmpty()) {
                stepper.randomize();
            } else {
                ((Timer)e.getSource()).stop();
                btnAutoON.setEnabled(true);
                btnAutoOFF.setEnabled(false);
            }
        });

        vitesses.addActionListener(e -> {
            if(e.getSource() == vitesses){
                vitesse[0] = Integer.parseInt(v[vitesses.getSelectedIndex()]);
                timer.setDelay(vitesse[0]);
            }
        });

        btnAutoON.addActionListener(e -> {
            if(btnAutoON.getText().equals("Automatique : Début")){
                timer.start();
                btnAutoON.setEnabled(false);
                btnAutoOFF.setEnabled(true);
            }
        });
        btnAutoON.addActionListener(this::btnAutoONListener);

        btnAutoOFF.addActionListener(e -> {
            timer.stop();
            btnAutoON.setEnabled(true);
            btnAutoOFF.setEnabled(false);
        });

        if(model.getTransitionFranchissables().isEmpty()){
            timer.stop();
            btnAutoON.setEnabled(true);
            btnAutoOFF.setEnabled(false);
        }

        ButtonGroup  playerGroup = new ButtonGroup();
        playerGroup.add(btnOrigin);
        playerGroup.add(btnPrevious);
        playerGroup.add(btnNext);
        playerGroup.add(btnLast);

        ButtonGroup autoGroup = new ButtonGroup();
        autoGroup.add(btnAutoON);
        autoGroup.add(btnAutoOFF);


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
        if(!stepper.marquagesPasse.isEmpty()) {
            stepper.goToFirstMarquage();
        }
    }

    public void btnPreviousListener(ActionEvent event){
        if(!stepper.marquagesPasse.isEmpty()){
            stepper.goToPreviousMarquage();
        }
    }

    public void btnNextListener(ActionEvent event){
        if(!stepper.marquagesPasse.get(stepper.marquagesPasse.size() -1).equals(model.getMarquage())) {
            stepper.goToNextMarquage();
        }
    }

    public void btnLastListener(ActionEvent event){
        if(!stepper.marquagesPasse.isEmpty()) {
            stepper.goToLastMarquage();
        }
    }

    public void btnAutoONListener(ActionEvent event){

    }

    public void btnAutoOFFListener(ActionEvent event){

    }
    public void btnSequenceListener(ActionEvent event){
        //stepper.setShowSequence(((JRadioButton) event.getSource()).isSelected());
        stepper.setShowSequence(((JToggleButton) event.getSource()).isSelected());
    }


}
