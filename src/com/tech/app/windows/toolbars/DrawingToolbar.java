package com.tech.app.windows.toolbars;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class DrawingToolbar extends Toolbar {

    /* Construction de l'interface graphique pour tester à part*/

    public DrawingToolbar(JFrame frame) {
        super(frame);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public JToolBar getToolbar() {
        JToolBar toolbar = new JToolBar();

        JButton btnNew = new JButton( new ImageIcon( "icons/new.png") );
        btnNew.setToolTipText( "New File (CTRL+N)" );
        btnNew.addActionListener( this::btnNewListener );
        toolbar.add( btnNew );

        JButton btnOpen = new JButton( new ImageIcon( "icons/open.png") );
        btnNew.setToolTipText( "New File (CTRL+O)" );
        btnNew.addActionListener( this::btnNewListener );
        toolbar.add( btnOpen );

        JButton btnSave = new JButton( new ImageIcon( "icons/save.png" ) );
        btnSave.setToolTipText( "Save (CTRL+S)" );
        toolbar.add( btnSave );

        JButton btnSaveAs = new JButton( new ImageIcon( "icons/save_as.png" ) );
        btnSaveAs.setToolTipText( "Save As..." );
        toolbar.add( btnSaveAs );

        toolbar.addSeparator();

        JButton btnCopy = new JButton( new ImageIcon( "icons/copy.png") );
        btnCopy.setToolTipText( "Copy (CTRL+C)" );
        toolbar.add( btnCopy );

        JButton btnCut = new JButton( new ImageIcon( "icons/cut.png") );
        btnCut.setToolTipText( "Cut (CTRL+X)" );
        toolbar.add( btnCut );

        JButton btnPaste = new JButton( new ImageIcon( "icons/paste.png") );
        btnPaste.setToolTipText( "Paste (CTRL+V)" );
        toolbar.add( btnPaste );

        toolbar.addSeparator();

        JButton btnUndo = new JButton( new ImageIcon( "icons/undo.png") );
        btnUndo.setToolTipText( "Undo (CTRL+Z)" );
        toolbar.add( btnUndo );

        JButton btnRedo = new JButton( new ImageIcon( "icons/redo.png") );
        btnRedo.setToolTipText( "Redo" );
        toolbar.add( btnRedo );

        toolbar.addSeparator();

        JButton btnPlace = new JButton( new ImageIcon( "icons/place.png") );
        btnPlace.setToolTipText( "Place" );
        toolbar.add( btnPlace );

        JButton btnTransition = new JButton( new ImageIcon( "icons/transition.png") );
        btnTransition.setToolTipText( "Transition" );
        toolbar.add( btnTransition );

        JButton btnArc = new JButton( new ImageIcon( "icons/arc.png") );
        btnArc.setToolTipText( "Arc" );
        toolbar.add( btnArc );

        JButton btnAttributs = new JButton( new ImageIcon( "icons/attributs.png") );
        btnAttributs.setToolTipText( "Attributs" );
        toolbar.add( btnAttributs );

        JButton btnSelect = new JButton( new ImageIcon( "icons/select.png") );
        btnSelect.setToolTipText( "Select" );
        toolbar.add( btnSelect );


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

    private void btnNewListener( ActionEvent event ) {
        JOptionPane.showMessageDialog( this, "Button clicked !" );
    }
    /*
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel( new NimbusLookAndFeel() );
        DrawingToolbar frame = new DrawingToolbar(this);
        frame.setVisible( true );
    }
    */
}
