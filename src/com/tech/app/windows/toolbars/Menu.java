package com.tech.app.windows.toolbars;

import com.tech.app.models.Model;
import com.tech.app.windows.GMAWindow;
import com.tech.app.windows.handlers.SaveManager;
import com.tech.app.windows.panels.DrawPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static javax.swing.JOptionPane.ERROR_MESSAGE;


public class Menu extends  MenuBar {
    /* Construction de l'interface graphique pour tester à part*/
    private Model model;

    private SaveManager saveManager;
    private DrawPanel dp;

    public Menu(JFrame frame) {
        super(frame);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void applyModel(Model model) {
        this.model = model;
    }
    public void applySaveManager(SaveManager sm) { this.saveManager = sm; }
    public void applyDrawPanel(DrawPanel dp) { this.dp = dp; }
  
    // Méthode de construction de la toolbar

    public JMenuBar getMenu() {


        JMenu mnuFile = new JMenu("File");
        mnuFile.setMnemonic('F');

        JMenuItem mnuNewFile = new JMenuItem("New File");
        mnuNewFile.setMnemonic('N');
        mnuNewFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        mnuNewFile.addActionListener(this::mnuNewListener);
        mnuNewFile.setEnabled(false);
        mnuFile.add(mnuNewFile);

        mnuFile.addSeparator();

        JMenuItem mnuOpenFile = new JMenuItem("Open File ...");
        mnuOpenFile.setMnemonic('O');
        mnuOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        mnuOpenFile.addActionListener(this::mnuOpenListener);
        mnuFile.add(mnuOpenFile);

        JMenuItem mnuSaveFile = new JMenuItem("Save File ...");
        mnuSaveFile.setMnemonic('S');
        mnuSaveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        mnuSaveFile.setEnabled(false);
        mnuFile.add(mnuSaveFile);

        JMenuItem mnuSaveFileAs = new JMenuItem("Save File As ...");
        mnuSaveFileAs.setMnemonic('A');
        mnuSaveFileAs.addActionListener(this::mnuSaveAsListener);
        mnuFile.add(mnuSaveFileAs);

        mnuFile.addSeparator();

        JMenuItem mnuExit = new JMenuItem("Exit");
        mnuExit.setMnemonic('x');
        mnuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
        mnuExit.addActionListener(this::mnuExitListener);
        mnuFile.add(mnuExit);

        toolbar.add(mnuFile);


        // Définition du menu déroulant "Edit" et de son contenu
        JMenu mnuEdit = new JMenu("Edit");
        mnuEdit.setMnemonic('E');

        JMenuItem mnuUndo = new JMenuItem("Undo");
        mnuUndo.setMnemonic('U');
        mnuUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
        mnuUndo.setEnabled(false);
        mnuEdit.add(mnuUndo);

        JMenuItem mnuRedo = new JMenuItem("Redo");
        mnuRedo.setMnemonic('R');
        mnuRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK));
        mnuRedo.setEnabled(false);
        mnuEdit.add(mnuRedo);

        mnuEdit.addSeparator();

        JMenuItem mnuCopy = new JMenuItem("Copy");
        mnuCopy.setMnemonic('C');
        mnuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        mnuCopy.setEnabled(false);
        mnuEdit.add(mnuCopy);

        JMenuItem mnuCut = new JMenuItem("Cut");
        mnuCut.setMnemonic('t');
        mnuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        mnuCut.setEnabled(false);
        mnuEdit.add(mnuCut);

        JMenuItem mnuPaste = new JMenuItem("Paste");
        mnuPaste.setMnemonic('P');
        mnuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        mnuPaste.setEnabled(false);
        mnuEdit.add(mnuPaste);

        toolbar.add(mnuEdit);


        // Définition du menu déroulant "Tools" et de son contenu

        JMenu mnuTools = new JMenu("Tools");
        mnuTools.setMnemonic('T');

        JMenuItem mnuGMA = new JMenuItem("Générer GMA");
        mnuGMA.setMnemonic('G');
        mnuGMA.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
        mnuGMA.addActionListener( this::openGMAWindow );
        mnuTools.add(mnuGMA);

        JMenuItem mnuStepper = new JMenuItem("Simulation pas à pas");
        mnuStepper.setMnemonic('F');
        mnuStepper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
        mnuStepper.setEnabled(false);
        mnuTools.add(mnuStepper);

        toolbar.add(mnuTools);

        //Gestion des icônes => améliorable !
        Image imageNew = null;
        Image imageOpen = null;
        Image imageSave = null;
        Image imageSaveAs = null;
        Image imageExit = null;
        Image imageUndo = null;
        Image imageRedo = null;
        Image imageCopy = null;
        Image imagePaste = null;
        Image imageCut = null;

        try {
            imageNew = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/new.png")));
            imageOpen = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/open.png")));
            imageSave = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/save.png")));
            imageSaveAs = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/save_as.png")));
            imageExit = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/exit.png")));
            imageUndo = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/undo.png")));
            imageRedo = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/redo.png")));
            imageCopy = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/copy.png")));
            imagePaste = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/paste.png")));
            imageCut = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/cut.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        assert imageNew != null;
        assert imageOpen != null;
        assert imageSave != null;
        assert imageSaveAs != null;
        assert imageExit != null;
        assert imageUndo != null;
        assert imageRedo != null;
        assert imageCopy != null;
        assert imagePaste != null;
        assert imageCut != null;

        mnuNewFile.setIcon(new ImageIcon(imageNew));
        mnuOpenFile.setIcon(new ImageIcon(imageOpen));
        mnuSaveFile.setIcon(new ImageIcon(imageSave));
        mnuSaveFileAs.setIcon(new ImageIcon(imageSaveAs));
        mnuExit.setIcon(new ImageIcon(imageExit));
        mnuUndo.setIcon(new ImageIcon(imageUndo));
        mnuRedo.setIcon(new ImageIcon(imageRedo));
        mnuCopy.setIcon(new ImageIcon(imageCopy));
        mnuPaste.setIcon(new ImageIcon(imagePaste));
        mnuCut.setIcon(new ImageIcon(imageCut));

        return toolbar;
    }

    private void openGMAWindow(ActionEvent actionEvent) {
        EventQueue.invokeLater(
                () -> {
                    try {
                        new GMAWindow(900,500, model);
                    } catch (UnsupportedLookAndFeelException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    // Test d'une fenêtre pop-up après une action (ici lors de la création d'un nouveau fichier)
    public void mnuNewListener(ActionEvent event) {
        JOptionPane.showMessageDialog(this, "Button clicked !");
    }

    public void mnuOpenListener(ActionEvent event) {
        FileFilter filtre = new FileNameExtensionFilter("Fichier RDP (*.jrdp)", "jrdp");
        JFileChooser choix = new JFileChooser();
        choix.setFileFilter(filtre);
        int retour = choix.showOpenDialog(this);
        choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File f= choix.getSelectedFile();
        if(retour==JFileChooser.APPROVE_OPTION){
            System.out.println(choix.getSelectedFile().getName());
            System.out.println(choix.getSelectedFile().getAbsolutePath());
            model = saveManager.load(f, model);
            if (model != null) {
                dp.model = model;
                dp.printModel();
                dp.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Fichier corrompu ou version incompatible...", "Erreur!", ERROR_MESSAGE);
                model = new Model();
                dp.model = model;
            }

        } else {
            JOptionPane.showMessageDialog(this, "Aucun fichier choisi !");
        }
    }

    public void mnuSaveAsListener(ActionEvent event) {
        FileFilter filtre = new FileNameExtensionFilter("Fichier RDP (*.jrdp)", "jrdp");
        JFileChooser save = new JFileChooser();
        save.setFileFilter(filtre);
        save.showSaveDialog(this);

        File f = save.getSelectedFile();

        saveManager.save(f, model);
    }

    public void mnuExitListener(ActionEvent event){
        String[] options ={"Quitter","Annuler"};
        int res = JOptionPane.showOptionDialog(null, "Toute modification non enregistrée sera perdue... Voulez-vous quitter l'application ?", "Attention",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch(res){
            //Case EXIT
            case 0:
                this.frame.dispose();
                System.exit(0);
                break;
            case 1:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + res);
        }

    }

}
