package com.tech.app.windows.toolbars;

import com.tech.app.windows.handlers.DrawMouse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

/**
 * Classe pour créer la barre de dessin (où l'on sélectionne les outils)
 */
public class DrawingToolbar extends Toolbar {

    /* Construction de l'interface graphique pour tester à part*/
    private final DrawMouse drawMouse;

    private Menu menu;

    /**
     * Constructeur de la DrawingToolbar.
     * @param frame Fenêtre d'appel
     * @param drawMouse Handler de la souris pour récupérer les events
     */
    public DrawingToolbar(JFrame frame,DrawMouse drawMouse) {
        super(frame);
        this.drawMouse = drawMouse;
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Permet d'utiliser les fonctions/méthodes publiques du menu
     * passé en paramètre directement dans cette classe.
     * @param menu Menu
     */
    public void applyMenuBridge(Menu menu) {
        this.menu = menu;
    }


    /**
     * Retourne l'objet Toolbar avec nos paramètres
     * @return JToolBar
     */
    @Override
    public JToolBar getToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        JButton btnOpen = new JButton();
        btnOpen.setToolTipText( "Ouvrir un fichier (CTRL+O)" );
        btnOpen.addActionListener( this::btnOpenListener );
        toolbar.add( btnOpen );

        JButton btnSaveAs = new JButton();
        btnSaveAs.setToolTipText( "Sauvegarder en tant que..." );
        btnSaveAs.addActionListener( this::btnSaveAsListener);
        toolbar.add( btnSaveAs );

        toolbar.addSeparator();

        JButton btnClear = new JButton();
        btnClear.setToolTipText( "Nettoyer l'espace de travail" );
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

        JToggleButton btnLabel = new JToggleButton();
        btnLabel.setToolTipText( "Label" );
        btnLabel.addActionListener( this::btnLabelListener );
        toolbar.add( btnLabel );

        toolbar.addSeparator();

        JToggleButton btnSelect = new JToggleButton();
        btnSelect.setToolTipText( "Sélectionner" );
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
        btnGroup.add(btnLabel);

        //Gestion des icônes => améliorable !
        Image imageOpen = null;
        Image imageSaveAs = null;
        Image imageClear = null;
        Image imagePlace = null;
        Image imageTransition = null;
        Image imageArc = null;
        Image imageAttributs = null;
        Image imageSelect = null;
        Image imageLabel = null;

        try {
            imageOpen = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/open.png")));
            imageSaveAs = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/save_as.png")));
            imageClear = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/clear.png")));
            imagePlace = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/place.png")));
            imageTransition = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/transition.png")));
            imageArc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/arc.png")));
            imageAttributs = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/attributs.png")));
            imageSelect = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/select.png")));
            imageLabel = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/label.png")));
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
        assert imageLabel != null;

        btnOpen.setIcon(new ImageIcon(imageOpen));
        btnSaveAs.setIcon(new ImageIcon(imageSaveAs));
        btnClear.setIcon(new ImageIcon(imageClear));
        btnPlace.setIcon(new ImageIcon(imagePlace));
        btnTransition.setIcon(new ImageIcon(imageTransition));
        btnArc.setIcon(new ImageIcon(imageArc));
        btnAttributs.setIcon(new ImageIcon(imageAttributs));
        btnSelect.setIcon(new ImageIcon(imageSelect));
        btnLabel.setIcon(new ImageIcon(imageLabel));

        return toolbar;
    }

    /**
     * Listener exécuté quand on fait l'action d'ouvrir un fichier
     * @param event ActionEvent
     */
    public void btnOpenListener(ActionEvent event) { menu.mnuOpenListener(event); }

    /**
     * Listener exécuté quand on fait l'action de sauvegarder sous, un fichier
     * @param event ActionEvent
     */
    public void btnSaveAsListener(ActionEvent event) {
        menu.mnuSaveAsListener(event);
    }

    /**
     * Listener exécuté quand on fait l'action de nettoyer la zone de dessin
     * @param event ActionEvent
     */
    public void btnClearListener(ActionEvent event){
        drawMouse.clearPanel();
    }

    /**
     * Listener exécuté quand on fait choisit le mode Création de Place
     * @param event ActionEvent
     */
    public void btnPlaceListener(ActionEvent event){ drawMouse.action(DrawMouse.MODE.PLACE); }

    /**
     * Listener exécuté quand on fait choisit le mode Création de Transition
     * @param event ActionEvent
     */
    public void btnTransitionListener(ActionEvent event){
        drawMouse.action(DrawMouse.MODE.TRANSITION);
    }

    /**
     * Listener exécuté quand on fait choisit le mode Création d'Arc
     * @param event ActionEvent
     */
    public void btnArcListener(ActionEvent event){
        drawMouse.action(DrawMouse.MODE.ARC);
    }

    /**
     * Listener exécuté quand on fait choisit le mode Sélection d'attributs
     * @param event ActionEvent
     */
    public void btnAttributsListener(ActionEvent event){
        drawMouse.action(DrawMouse.MODE.ATTRIBUTS);
    }

    /**
     * Listener exécuté quand on fait choisit le mode Sélection d'objet sur la zone de dessin
     * @param event ActionEvent
     */
    public void btnSelectListener(ActionEvent event){
        drawMouse.action(DrawMouse.MODE.SELECT);
    }

    /**
     * Listener exécuté quand on fait choisit le mode Modification du Label
     * @param event ActionEvent
     */
    public void btnLabelListener(ActionEvent event){
        drawMouse.action(DrawMouse.MODE.LABEL);
    }

}
