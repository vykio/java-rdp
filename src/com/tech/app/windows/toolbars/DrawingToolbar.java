package com.tech.app.windows.toolbars;

import com.tech.app.models.Model;
import com.tech.app.windows.GMAWindow;
import com.tech.app.windows.handlers.DrawMouse;
import com.tech.app.windows.handlers.SaveManager;
import com.tech.app.windows.panels.DrawPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class DrawingToolbar extends Toolbar {

    /* Construction de l'interface graphique pour tester à part*/
    private DrawMouse drawMouse;
    private Model model;
    private SaveManager saveManager;
    private DrawPanel dp;

    private Menu menu;

    public DrawingToolbar(JFrame frame,DrawMouse drawMouse) {
        super(frame);
        this.drawMouse = drawMouse;
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void applyMenuBridge(Menu menu) {
        this.menu = menu;
    }

    public void applyModel(Model model) { this.model = model; }
    public void applySaveManager(SaveManager sm) { this.saveManager = sm; }
    public void applyDrawPanel(DrawPanel dp) { this.dp = dp; }



    @Override
    public JToolBar getToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        /*
        JButton btnNew = new JButton();
        btnNew.setToolTipText( "New File (CTRL+N)" );
        toolbar.add( btnNew );
         */

        JButton btnOpen = new JButton();
        btnOpen.setToolTipText( "Open File (CTRL+O)" );
        btnOpen.addActionListener( this::btnOpenListener );
        toolbar.add( btnOpen );
/*
        JButton btnSave = new JButton();
        btnSave.setToolTipText( "Save (CTRL+S)" );
        toolbar.add( btnSave );
*/

        JButton btnSaveAs = new JButton();
        btnSaveAs.setToolTipText( "Save As..." );
        btnSaveAs.addActionListener( this::btnSaveAsListener);
        toolbar.add( btnSaveAs );

        toolbar.addSeparator();

        /*
        JButton btnCopy = new JButton();
        btnCopy.setToolTipText( "Copy (CTRL+C)" );
        toolbar.add( btnCopy );

        JButton btnCut = new JButton();
        btnCut.setToolTipText( "Cut (CTRL+X)" );
        toolbar.add( btnCut );

         */

        JButton btnClear = new JButton();
        btnClear.setToolTipText( "Clear board" );
        btnClear.addActionListener( this::btnClearListener );
        toolbar.add( btnClear );

        toolbar.addSeparator();

        /*
        JButton btnUndo = new JButton();
        btnUndo.setToolTipText( "Undo (CTRL+Z)" );
        toolbar.add( btnUndo );

        JButton btnRedo = new JButton();
        btnRedo.setToolTipText( "Redo" );
        toolbar.add( btnRedo );

        toolbar.addSeparator();

         */

        JToggleButton btnPlace = new JToggleButton();
        btnPlace.setToolTipText( "Place" );
        btnPlace.addActionListener( this::btnPlaceListener );
        toolbar.add( btnPlace );

        JToggleButton btnTransition = new JToggleButton();
        btnTransition.setToolTipText( "Transition" );
        btnTransition.addActionListener( this::btnTransitionListener );
        toolbar.add( btnTransition );

        JToggleButton btnArc = new JToggleButton();
        btnArc.setToolTipText( "Arc" );
        btnArc.addActionListener( this::btnArcListener );
        toolbar.add( btnArc );

        toolbar.addSeparator();

        JToggleButton btnAttributs = new JToggleButton();
        btnAttributs.setToolTipText( "Attributs" );
        btnAttributs.addActionListener( this::btnAttributsListener );
        toolbar.add( btnAttributs );

        JToggleButton btnSelect = new JToggleButton();
        btnSelect.setToolTipText( "Select" );
        btnSelect.addActionListener( this::btnSelectListener );
        toolbar.add( btnSelect );

        toolbar.addSeparator();

        /*JToggleButton btnGMA = new JToggleButton();
        btnGMA.setToolTipText("Générer le GMA");
        btnGMA.setText("GMA");
        btnGMA.addActionListener(this::btnOpenGMAWindow );
        toolbar.add(btnGMA);

        JToggleButton btnStepper = new JToggleButton();
        btnStepper.setToolTipText("Simulation pas à pas");
        btnStepper.setText("Stepper");
        btnStepper.setEnabled(false);*/
        //btnStepper.addActionListener(this::btnOpenGMAWindow );

        JToggleButton btnLabel = new JToggleButton();
        btnLabel.setToolTipText( "Select" );
        btnLabel.addActionListener( this::btnLabelListener );
        toolbar.add( btnLabel );

       // toolbar.add(btnStepper);

        // Goupe de boutons pour ne pouvoir sélectionner qu'une seule des 5 fonctionnalités uniques
        // -> place, transition, arc, attributs, select
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(btnPlace);
        btnGroup.add(btnTransition);
        btnGroup.add(btnArc);
        btnGroup.add(btnAttributs);
        btnGroup.add(btnSelect);
        btnGroup.add(btnLabel);

        //Gestion des icônes => améliorable !
        //Image imageNew = null;
        Image imageOpen = null;
        //Image imageSave = null;
        Image imageSaveAs = null;
        //Image imageUndo = null;
        //Image imageRedo = null;
        //Image imageCopy = null;
        Image imageClear = null;
        //Image imageCut = null;
        Image imagePlace = null;
        Image imageTransition = null;
        Image imageArc = null;
        Image imageAttributs = null;
        Image imageSelect = null;
        Image imageLabel = null;

        try {
            //imageNew = ImageIO.read(getClass().getResource("/icons/new.png"));
            imageOpen = ImageIO.read(getClass().getResource("/icons/open.png"));
            //imageSave = ImageIO.read(getClass().getResource("/icons/save.png"));
            imageSaveAs = ImageIO.read(getClass().getResource("/icons/save_as.png"));
            //imageUndo = ImageIO.read(getClass().getResource("/icons/undo.png"));
            //imageRedo = ImageIO.read(getClass().getResource("/icons/redo.png"));
            //imageCopy = ImageIO.read(getClass().getResource("/icons/copy.png"));
            imageClear = ImageIO.read(getClass().getResource("/icons/clear.png"));
            //imageCut = ImageIO.read(getClass().getResource("/icons/cut.png"));
            imagePlace = ImageIO.read(getClass().getResource("/icons/place.png"));
            imageTransition = ImageIO.read(getClass().getResource("/icons/transition.png"));
            imageArc = ImageIO.read(getClass().getResource("/icons/arc.png"));
            imageAttributs = ImageIO.read(getClass().getResource("/icons/attributs.png"));
            imageSelect = ImageIO.read(getClass().getResource("/icons/select.png"));
            imageLabel = ImageIO.read(getClass().getResource("/icons/label.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        //assert imageNew != null;
        assert imageOpen != null;
        //assert imageSave != null;
        assert imageSaveAs != null;
        //assert imageUndo != null;
        //assert imageRedo != null;
        //assert imageCopy != null;
        assert imageClear != null;
        //assert imageCut != null;
        assert imagePlace != null;
        assert imageTransition != null;
        assert imageArc != null;
        assert imageAttributs != null;
        assert imageSelect != null;
        assert imageLabel != null;


        //btnNew.setIcon(new ImageIcon(imageNew));
        btnOpen.setIcon(new ImageIcon(imageOpen));
        //btnSave.setIcon(new ImageIcon(imageSave));
        btnSaveAs.setIcon(new ImageIcon(imageSaveAs));
        //btnUndo.setIcon(new ImageIcon(imageUndo));
        //btnRedo.setIcon(new ImageIcon(imageRedo));
        //btnCopy.setIcon(new ImageIcon(imageCopy));
        btnClear.setIcon(new ImageIcon(imageClear));
        //btnCut.setIcon(new ImageIcon(imageCut));
        btnPlace.setIcon(new ImageIcon(imagePlace));
        btnTransition.setIcon(new ImageIcon(imageTransition));
        btnArc.setIcon(new ImageIcon(imageArc));
        btnAttributs.setIcon(new ImageIcon(imageAttributs));
        btnSelect.setIcon(new ImageIcon(imageSelect));
        btnLabel.setIcon(new ImageIcon(imageLabel));

        return toolbar;
    }

    public void btnOpenListener(ActionEvent event) {
        menu.mnuOpenListener(event);
    }

    public void btnSaveAsListener(ActionEvent event) {
        menu.mnuSaveAsListener(event);
    }

    public void btnClearListener(ActionEvent event){
        //System.out.println("Clear board button clicked");
        drawMouse.clearPanel();
    }


    public void btnPlaceListener(ActionEvent event){
        //System.out.println("Place button clicked");
        drawMouse.action(DrawMouse.MODE.PLACE);
        MouseInfo.getPointerInfo().getLocation().getX();
    }

    public void btnTransitionListener(ActionEvent event){
        //System.out.println("Transition button clicked");
        drawMouse.action(DrawMouse.MODE.TRANSITION);
    }

    public void btnArcListener(ActionEvent event){
        //System.out.println("Arc button clicked");
        drawMouse.action(DrawMouse.MODE.ARC);
    }

    public void btnAttributsListener(ActionEvent event){
        //System.out.println("Attributs button clicked");
        drawMouse.action(DrawMouse.MODE.ATTRIBUTS);
    }

    public void btnSelectListener(ActionEvent event){
        //System.out.println("Select button clicked");
        drawMouse.action(DrawMouse.MODE.SELECT);
    }

    public void btnLabelListener(ActionEvent event){
        System.out.println("Label button clicked");
        drawMouse.action(DrawMouse.MODE.LABEL);
    }

}
