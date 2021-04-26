package com.tech.app.windows.toolbars;

import com.tech.app.windows.handlers.DrawMouse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class DrawingToolbar extends Toolbar {

    /* Construction de l'interface graphique pour tester à part*/
    private final DrawMouse drawMouse;

    private Menu menu;

    public DrawingToolbar(JFrame frame,DrawMouse drawMouse) {
        super(frame);
        this.drawMouse = drawMouse;
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void applyMenuBridge(Menu menu) {
        this.menu = menu;
    }


    @Override
    public JToolBar getToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        JButton btnOpen = new JButton();
        btnOpen.setToolTipText( "Open File (CTRL+O)" );
        btnOpen.addActionListener( this::btnOpenListener );
        toolbar.add( btnOpen );

        JButton btnSaveAs = new JButton();
        btnSaveAs.setToolTipText( "Save As..." );
        btnSaveAs.addActionListener( this::btnSaveAsListener);
        toolbar.add( btnSaveAs );

        toolbar.addSeparator();

        JButton btnClear = new JButton();
        btnClear.setToolTipText( "Clear board" );
        btnClear.addActionListener( this::btnClearListener );
        toolbar.add( btnClear );

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

        // Goupe de boutons pour ne pouvoir sélectionner qu'une seule des 5 fonctionnalités uniques
        // -> place, transition, arc, attributs, select
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(btnPlace);
        btnGroup.add(btnTransition);
        btnGroup.add(btnArc);
        btnGroup.add(btnAttributs);
        btnGroup.add(btnSelect);

        //Gestion des icônes => améliorable !
        Image imageOpen = null;
        Image imageSaveAs = null;
        Image imageClear = null;
        Image imagePlace = null;
        Image imageTransition = null;
        Image imageArc = null;
        Image imageAttributs = null;
        Image imageSelect = null;


        try {
            imageOpen = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/open.png")));
            imageSaveAs = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/save_as.png")));
            imageClear = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/clear.png")));
            imagePlace = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/place.png")));
            imageTransition = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/transition.png")));
            imageArc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/arc.png")));
            imageAttributs = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/attributs.png")));
            imageSelect = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/select.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        assert imageOpen != null;
        assert imageSaveAs != null;
        assert imageClear != null;
        assert imagePlace != null;
        assert imageTransition != null;
        assert imageArc != null;
        assert imageAttributs != null;
        assert imageSelect != null;

        btnOpen.setIcon(new ImageIcon(imageOpen));
        btnSaveAs.setIcon(new ImageIcon(imageSaveAs));
        btnClear.setIcon(new ImageIcon(imageClear));
        btnPlace.setIcon(new ImageIcon(imagePlace));
        btnTransition.setIcon(new ImageIcon(imageTransition));
        btnArc.setIcon(new ImageIcon(imageArc));
        btnAttributs.setIcon(new ImageIcon(imageAttributs));
        btnSelect.setIcon(new ImageIcon(imageSelect));


        return toolbar;
    }

    public void btnOpenListener(ActionEvent event) {
        menu.mnuOpenListener(event);
    }

    public void btnSaveAsListener(ActionEvent event) {
        menu.mnuSaveAsListener(event);
    }

    public void btnClearListener(ActionEvent event){
        drawMouse.clearPanel();
    }

    public void btnPlaceListener(ActionEvent event){
        drawMouse.action(DrawMouse.MODE.PLACE);
        MouseInfo.getPointerInfo().getLocation().getX();
    }

    public void btnTransitionListener(ActionEvent event){
        drawMouse.action(DrawMouse.MODE.TRANSITION);
    }

    public void btnArcListener(ActionEvent event){
        drawMouse.action(DrawMouse.MODE.ARC);
    }

    public void btnAttributsListener(ActionEvent event){
        drawMouse.action(DrawMouse.MODE.ATTRIBUTS);
    }

    public void btnSelectListener(ActionEvent event){
        drawMouse.action(DrawMouse.MODE.SELECT);
    }

}
