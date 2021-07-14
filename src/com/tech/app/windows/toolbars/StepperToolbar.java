package com.tech.app.windows.toolbars;

import com.tech.app.models.Model;
import com.tech.app.models.stepper.Stepper;
import com.tech.app.windows.handlers.StepperMouse;
import com.tech.app.windows.panels.StepperHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;
import java.util.stream.Stream;

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

        JButton btnReset = new JButton();
        btnReset.setToolTipText("Réinitialiser le simulateur");
        btnReset.addActionListener(this::btnResetListener);
        toolBar.add(btnReset);

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

        /*
        JButton btnAutoON = new JButton();
        btnAutoON.setText("Automatique : Début");
        btnAutoON.setToolTipText("On franchit aléatoirement les transitions franchissables");
        toolBar.add(btnAutoON);
        JButton btnAutoOFF = new JButton();
        btnAutoOFF.setText("Automatique : Fin");
        btnAutoOFF.setToolTipText("Arret de la simulation aléatoire.");
        btnAutoOFF.setEnabled(false);
        toolBar.add(btnAutoOFF);
         */

        JButton btnAuto = new JButton();
        btnAuto.setText("Début du mode Auto");
        btnAuto.setToolTipText("On franchit aléatoirement les transitions franchissables");
        toolBar.add(btnAuto);


        JButton textVitesse = new JButton("Pas de simulation (ms)");
        textVitesse.setEnabled(false);
        toolBar.add(textVitesse);

        int[] vitesse = new int[]{1000};
        String[] v = {"10","100","200","300","400","500","600","700","800","900","1000"};
        JComboBox<String> vitesses =  new JComboBox<>(v);
        vitesses.setSelectedItem(v[v.length-1]);
        toolBar.add(vitesses);

        toolBar.addSeparator();

        JToggleButton btnSequence = new JToggleButton();
        btnSequence.setText("Séquence (max 20)");
        btnSequence.setToolTipText("Affichage des 20 dernières transitions franchies lors de la simulation en cours");
        btnSequence.addActionListener(this::btnSequenceListener);
        toolBar.add(btnSequence);

        JButton btnFullSequence = new JButton();
        btnFullSequence.setText("Séquence complète");
        btnFullSequence.setToolTipText("Affichage de toutes les transitions franchies lors de la simulation en cours. Ce bouton arrête le mode automatique.");
        btnFullSequence.addActionListener(this::btnFullSequenceListener);
        toolBar.add(btnFullSequence);

        ButtonGroup  playerGroup = new ButtonGroup();
        playerGroup.add(btnOrigin);
        playerGroup.add(btnPrevious);
        playerGroup.add(btnNext);
        playerGroup.add(btnLast);
        playerGroup.add(btnReset);
        Enumeration<AbstractButton> playerGroupEnum = playerGroup.getElements();

        /* Gestion du mode automatique*/

        Timer timer = new Timer(vitesse[0], e -> {
            if(!stepper.model.getTransitionFranchissables().isEmpty()) {
                stepper.randomize();
            } else {
                ((Timer)e.getSource()).stop();
                btnAuto.setText("Début du mode Auto");
                btnAuto.setSelected(false);

                btnLast.setEnabled(true);
                btnOrigin.setEnabled(true);
                btnNext.setEnabled(true);
                btnPrevious.setEnabled(true);
                btnReset.setEnabled(true);
            }
        });

        vitesses.addActionListener(e -> {
            if(e.getSource() == vitesses){
                vitesse[0] = Integer.parseInt(v[vitesses.getSelectedIndex()]);
                timer.setDelay(vitesse[0]);
            }
        });


        btnAuto.addActionListener(e -> {
            if(btnAuto.getText().equals("Début du mode Auto")){
                timer.start();
                btnAuto.setText("Arret du mode Auto");
                btnAuto.setToolTipText("Arret de la simulation aléatoire.");
                btnAuto.setSelected(false);

                btnLast.setEnabled(false);
                btnOrigin.setEnabled(false);
                btnNext.setEnabled(false);
                btnPrevious.setEnabled(false);
                btnReset.setEnabled(false);
            } else {
                timer.stop();
                btnAuto.setText("Début du mode Auto");
                btnAuto.setToolTipText("On franchit aléatoirement les transitions franchissables");
                btnAuto.setSelected(false);

                btnLast.setEnabled(true);
                btnOrigin.setEnabled(true);
                btnNext.setEnabled(true);
                btnPrevious.setEnabled(true);
                btnReset.setEnabled(true);
            }
        });

        btnFullSequence.addActionListener(e -> {
            if(btnAuto.getText().equals("Arret du mode Auto")){
                timer.stop();
                btnAuto.setText("Début du mode Auto");
                btnAuto.setToolTipText("On franchit aléatoirement les transitions franchissables");
                btnAuto.setSelected(false);

                btnLast.setEnabled(true);
                btnOrigin.setEnabled(true);
                btnNext.setEnabled(true);
                btnPrevious.setEnabled(true);
                btnReset.setEnabled(true);
            }
        });


        Image imageReset = null;
        Image imageOrigin = null;
        Image imagePrevious = null;
        Image imageNext = null;
        Image imageLast = null;

        try {
            imageReset = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/reset.png")));
            imageOrigin = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/origin.png")));
            imagePrevious = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/previous.png")));
            imageNext = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/next.png")));
            imageLast = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/last.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert imageReset != null;
        assert imageOrigin != null;
        assert imagePrevious != null;
        assert imageNext != null;
        assert imageLast != null;

        btnReset.setIcon(new ImageIcon(imageReset));
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

    public void btnResetListener(ActionEvent event){
        stepper.reset();
    }

    public void btnOriginListener(ActionEvent event){
        if(!stepper.marquagesPasse.isEmpty()) {
            stepper.goToFirstMarquage();
        }
        ((JButton) event.getSource()).setSelected(false);
    }

    public void btnPreviousListener(ActionEvent event){
        if(!stepper.marquagesPasse.isEmpty()){
            stepper.goToPreviousMarquage();
        }
        ((JButton) event.getSource()).setSelected(false);
    }

    public void btnNextListener(ActionEvent event){
        stepper.goToNextMarquage();
        ((JButton) event.getSource()).setSelected(false);

    }

    public void btnLastListener(ActionEvent event){
        if(!stepper.marquagesPasse.isEmpty()) {
            stepper.goToLastMarquage();
        }
        ((JButton) event.getSource()).setSelected(false);
    }

    public void btnSequenceListener(ActionEvent event){
        stepper.setShowSequence(((JToggleButton) event.getSource()).isSelected());
    }

    public void btnFullSequenceListener(ActionEvent event){
        if(!stepper.sequenceTransition.isEmpty()) {

            String msg = stepper.getSequenceTransitionToString(stepper.getSequenceTransition());
            String message = "<html><body width ='%1s'> <center>" +
                    "<p> Séquence : <br> { " + msg +
                    "<center/>";

            int w = (int) (1.5 * msg.length());
            int h = (int) (0.7 * msg.length());

            //UIManager.put("OptionPane.minimumSize",new Dimension(w,h));
            JOptionPane.showMessageDialog(this, String.format(message, w), "Séquence complète de simulation", JOptionPane.PLAIN_MESSAGE);
        }
    }


}