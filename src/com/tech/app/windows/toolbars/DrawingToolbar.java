package com.tech.app.windows.toolbars;

import com.tech.app.windows.handlers.DrawMouse;
import com.tech.app.windows.panels.DrawPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DrawingToolbar extends Toolbar {

    /* Construction de l'interface graphique pour tester à part*/
    DrawMouse drawMouse;

    public DrawingToolbar(JFrame frame,DrawMouse drawMouse) {
        super(frame);
        this.drawMouse = drawMouse;
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public JToolBar getToolbar() {
        JToolBar toolbar = new JToolBar();

        JButton btnNew = new JButton();
        btnNew.setToolTipText( "New File (CTRL+N)" );
        toolbar.add( btnNew );

        JButton btnOpen = new JButton();
        btnOpen.setToolTipText( "Open File (CTRL+O)" );
        btnOpen.addActionListener( this::btnOpenListener );
        toolbar.add( btnOpen );

        JButton btnSave = new JButton();
        btnSave.setToolTipText( "Save (CTRL+S)" );
        toolbar.add( btnSave );

        JButton btnSaveAs = new JButton();
        btnSaveAs.setToolTipText( "Save As..." );
        btnSaveAs.addActionListener( this::btnSaveAsListener);
        toolbar.add( btnSaveAs );

        toolbar.addSeparator();

        JButton btnCopy = new JButton();
        btnCopy.setToolTipText( "Copy (CTRL+C)" );
        toolbar.add( btnCopy );

        JButton btnCut = new JButton();
        btnCut.setToolTipText( "Cut (CTRL+X)" );
        toolbar.add( btnCut );

        JButton btnPaste = new JButton();
        btnPaste.setToolTipText( "Paste (CTRL+V)" );
        toolbar.add( btnPaste );

        toolbar.addSeparator();

        JButton btnUndo = new JButton();
        btnUndo.setToolTipText( "Undo (CTRL+Z)" );
        toolbar.add( btnUndo );

        JButton btnRedo = new JButton();
        btnRedo.setToolTipText( "Redo" );
        toolbar.add( btnRedo );

        toolbar.addSeparator();

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
        btnNew.addActionListener( this::btnArcListener );
        toolbar.add( btnArc );

        JToggleButton btnAttributs = new JToggleButton();
        btnAttributs.setToolTipText( "Attributs" );
        btnNew.addActionListener( this::btnAttributsListener );
        toolbar.add( btnAttributs );

        JToggleButton btnSelect = new JToggleButton();
        btnSelect.setToolTipText( "Select" );
        btnSelect.addActionListener( this::btnSelectListener );
        toolbar.add( btnSelect );

        // Goupe de boutons pour ne pouvoir sélectionner qu'une seule des 5 fonctionnalités uniques
        // -> place, transition, arc, attributs, select
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(btnPlace);
        btnGroup.add(btnTransition);
        btnGroup.add(btnArc);
        btnGroup.add(btnAttributs);
        btnGroup.add(btnSelect);

        //Gestion des icônes => améliorable !
        Image imageNew = null;
        Image imageOpen = null;
        Image imageSave = null;
        Image imageSaveAs = null;
        Image imageUndo = null;
        Image imageRedo = null;
        Image imageCopy = null;
        Image imagePaste = null;
        Image imageCut = null;
        Image imagePlace = null;
        Image imageTransition = null;
        Image imageArc = null;
        Image imageAttributs = null;
        Image imageSelect = null;


        try {
            imageNew = ImageIO.read(getClass().getResource("/icons/new.png"));
            imageOpen = ImageIO.read(getClass().getResource("/icons/open.png"));
            imageSave = ImageIO.read(getClass().getResource("/icons/save.png"));
            imageSaveAs = ImageIO.read(getClass().getResource("/icons/save_as.png"));
            imageUndo = ImageIO.read(getClass().getResource("/icons/undo.png"));
            imageRedo = ImageIO.read(getClass().getResource("/icons/redo.png"));
            imageCopy = ImageIO.read(getClass().getResource("/icons/copy.png"));
            imagePaste = ImageIO.read(getClass().getResource("/icons/paste.png"));
            imageCut = ImageIO.read(getClass().getResource("/icons/cut.png"));
            imagePlace = ImageIO.read(getClass().getResource("/icons/place.png"));
            imageTransition = ImageIO.read(getClass().getResource("/icons/transition.png"));
            imageArc = ImageIO.read(getClass().getResource("/icons/arc.png"));
            imageAttributs = ImageIO.read(getClass().getResource("/icons/attributs.png"));
            imageSelect = ImageIO.read(getClass().getResource("/icons/select.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        assert imageNew != null;
        assert imageOpen != null;
        assert imageSave != null;
        assert imageSaveAs != null;
        assert imageUndo != null;
        assert imageRedo != null;
        assert imageCopy != null;
        assert imagePaste != null;
        assert imageCut != null;
        assert imagePlace != null;
        assert imageTransition != null;
        assert imageArc != null;
        assert imageAttributs != null;
        assert imageSelect != null;
        btnNew.setIcon(new ImageIcon(imageNew));
        btnOpen.setIcon(new ImageIcon(imageOpen));
        btnSave.setIcon(new ImageIcon(imageSave));
        btnSaveAs.setIcon(new ImageIcon(imageSaveAs));
        btnUndo.setIcon(new ImageIcon(imageUndo));
        btnRedo.setIcon(new ImageIcon(imageRedo));
        btnCopy.setIcon(new ImageIcon(imageCopy));
        btnPaste.setIcon(new ImageIcon(imagePaste));
        btnCut.setIcon(new ImageIcon(imageCut));
        btnPlace.setIcon(new ImageIcon(imagePlace));
        btnTransition.setIcon(new ImageIcon(imageTransition));
        btnArc.setIcon(new ImageIcon(imageArc));
        btnAttributs.setIcon(new ImageIcon(imageAttributs));
        btnSelect.setIcon(new ImageIcon(imageSelect));


        return toolbar;
    }

    public void btnOpenListener(ActionEvent event) {

        System.out.println("Open File button clicked");

        JFileChooser choix = new JFileChooser();
        int retour = choix.showOpenDialog(this);
        choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(retour==JFileChooser.APPROVE_OPTION){
            choix.getSelectedFile().getName();
            choix.getSelectedFile().getAbsolutePath();
        } else {
            JOptionPane.showMessageDialog(this, "Aucun fichier choisi !");
        }
    }

    public void btnSaveAsListener(ActionEvent event) {

        System.out.println("Save as button clicked");

        JFileChooser save = new JFileChooser();
        save.showSaveDialog(this);
        File f =save.getSelectedFile();
        try {
            FileWriter fw = new FileWriter(f);
            String text = "Le fichier a été sauvegardé";
            fw.write(text);
            fw.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void btnPlaceListener(ActionEvent event){
        System.out.println("Place button clicked");
      drawMouse.action(DrawMouse.MODE.PLACE);
      MouseInfo.getPointerInfo().getLocation().getX();
    }

    public void btnTransitionListener(ActionEvent event){
        System.out.println("Transition button clicked");
    }

    public void btnArcListener(ActionEvent event){
        System.out.println("Arc button clicked");
    }

    public void btnAttributsListener(ActionEvent event){
        System.out.println("Attributs button clicked");
    }

    public void btnSelectListener(ActionEvent event){
        System.out.println("Select button clicked");
    }

}
