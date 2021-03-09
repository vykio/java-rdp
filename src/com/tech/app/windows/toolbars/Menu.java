package com.tech.app.windows.toolbars;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.JOptionPane.DEFAULT_OPTION;


public class Menu extends  MenuBar {
    /* Construction de l'interface graphique pour tester à part*/

    public Menu(JFrame frame) {
        super(frame);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Méthode de construction de la toolbar

    public JMenuBar getMenu() {

        JMenu mnuFile = new JMenu("File");
        mnuFile.setMnemonic('F');

        JMenuItem mnuNewFile = new JMenuItem("New File");
        mnuNewFile.setMnemonic('N');
        mnuNewFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        mnuNewFile.addActionListener(this::mnuNewListener);
        mnuFile.add(mnuNewFile);

        mnuFile.addSeparator();

        JMenuItem mnuOpenFile = new JMenuItem("Open File ...");
        //mnuOpenFile.setIcon(new ImageIcon("icons/open.png"));
        mnuOpenFile.setMnemonic('O');
        mnuOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        mnuOpenFile.addActionListener(this::mnuOpenListener);
        mnuFile.add(mnuOpenFile);

        JMenuItem mnuSaveFile = new JMenuItem("Save File ...");
        //mnuSaveFile.setIcon(new ImageIcon("icons/save.png"));
        mnuSaveFile.setMnemonic('S');
        mnuSaveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        mnuFile.add(mnuSaveFile);

        JMenuItem mnuSaveFileAs = new JMenuItem("Save File As ...");
        //mnuSaveFileAs.setIcon(new ImageIcon("icons/save_as.png"));
        mnuSaveFileAs.setMnemonic('A');
        mnuSaveFileAs.addActionListener(this::mnuSaveAsListener);
        mnuFile.add(mnuSaveFileAs);

        mnuFile.addSeparator();

        JMenuItem mnuExit = new JMenuItem("Exit");
        //mnuExit.setIcon(new ImageIcon("icons/exit.png"));
        mnuExit.setMnemonic('x');
        mnuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
        mnuExit.addActionListener(this::mnuExitListener);
        mnuFile.add(mnuExit);

        toolbar.add(mnuFile);

        // Définition du menu déroulant "Edit" et de son contenu
        JMenu mnuEdit = new JMenu("Edit");
        mnuEdit.setMnemonic('E');

        JMenuItem mnuUndo = new JMenuItem("Undo");
        //mnuUndo.setIcon(new ImageIcon("icons/undo.png"));
        mnuUndo.setMnemonic('U');
        mnuUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
        mnuEdit.add(mnuUndo);

        JMenuItem mnuRedo = new JMenuItem("Redo");
        //mnuRedo.setIcon(new ImageIcon("icons/redo.png"));
        mnuRedo.setMnemonic('R');
        mnuRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK));
        mnuEdit.add(mnuRedo);

        mnuEdit.addSeparator();

        JMenuItem mnuCopy = new JMenuItem("Copy");
        //mnuCopy.setIcon(new ImageIcon("icons/copy.png"));
        mnuCopy.setMnemonic('C');
        mnuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        mnuEdit.add(mnuCopy);

        JMenuItem mnuCut = new JMenuItem("Cut");
        //mnuCut.setIcon(new ImageIcon("icons/cut.png"));
        mnuCut.setMnemonic('t');
        mnuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        mnuEdit.add(mnuCut);

        JMenuItem mnuPaste = new JMenuItem("Paste");
        //mnuPaste.setIcon(new ImageIcon("icons/paste.png"));
        mnuPaste.setMnemonic('P');
        mnuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        mnuEdit.add(mnuPaste);

        toolbar.add(mnuEdit);

        // Définition du menu déroulant "Mode" et de son contenu
        JMenu mnuMode = new JMenu("Mode");
        mnuMode.setMnemonic('M');

        JMenuItem mnuText = new JMenuItem("Mode textuel");
        mnuText.setMnemonic('T');
        mnuText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
        mnuMode.add(mnuText);

        JMenuItem mnuGraphic = new JMenuItem("Mode graphique");
        mnuGraphic.setMnemonic('G');
        mnuGraphic.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
        mnuMode.add(mnuGraphic);

        toolbar.add(mnuMode);


        // Définition du menu déroulant "Tools" et de son contenu
        JMenu mnuTools = new JMenu("Tools");
        mnuTools.setMnemonic('T');

        JMenuItem mnuGMA = new JMenuItem("GMA");
        mnuGMA.setMnemonic('G');
        mnuGMA.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
        mnuTools.add(mnuGMA);

        JMenuItem mnuStepper = new JMenuItem("Stepper");
        mnuStepper.setMnemonic('F');
        mnuStepper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
        mnuTools.add(mnuStepper);

        toolbar.add(mnuTools);


        // Définition du menu déroulant "Help" et de son contenu
        JMenu mnuHelp = new JMenu("Help");
        mnuHelp.setMnemonic('H');

        toolbar.add(mnuHelp);

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
        Image imageAbout = null;
        try {
            imageNew = ImageIO.read(getClass().getResource("/icons/new.png"));
            imageOpen = ImageIO.read(getClass().getResource("/icons/open.png"));
            imageSave = ImageIO.read(getClass().getResource("/icons/save.png"));
            imageSaveAs = ImageIO.read(getClass().getResource("/icons/save_as.png"));
            imageExit = ImageIO.read(getClass().getResource("/icons/exit.png"));
            imageUndo = ImageIO.read(getClass().getResource("/icons/undo.png"));
            imageRedo = ImageIO.read(getClass().getResource("/icons/redo.png"));
            imageCopy = ImageIO.read(getClass().getResource("/icons/copy.png"));
            imagePaste = ImageIO.read(getClass().getResource("/icons/paste.png"));
            imageCut = ImageIO.read(getClass().getResource("/icons/cut.png"));
            imageAbout = ImageIO.read(getClass().getResource("/icons/about.png"));


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
        assert imageAbout != null;
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
        mnuHelp.setIcon(new ImageIcon(imageAbout));

        return toolbar;
    }

    // Test d'une fenêtre pop-up après une action (ici lors de la création d'un nouveau fichier)
    public void mnuNewListener(ActionEvent event) {
        JOptionPane.showMessageDialog(this, "Button clicked !");
    }

    public void mnuOpenListener(ActionEvent event) {
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

    public void mnuSaveAsListener(ActionEvent event) {
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

    public void mnuExitListener(ActionEvent event){
        String options[]={"Exit","Save and Exit","Cancel"};
        int res = JOptionPane.showOptionDialog(null, "Voules vous vraiment quitter l'application ?", "Attention",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        System.out.println(res);

        switch(res){
            //Case EXIT
            case 0:
                this.frame.dispose();
                //Pourquoi ça m'affiche la fenêtre de dialogue pour enregistrer ??

                //Case SAVE_EXITù
                break;
            case 1:
                //Si le fichier n'a jamais été enregistré (=> Il n'a pas de nom)
                JFileChooser save = new JFileChooser();
                save.showSaveDialog(this);
                File f =save.getSelectedFile();
                try {
                    FileWriter fw = new FileWriter(f);
                    String text = "Le fichier a été sauvegardé, je vais quitter le programme";
                    fw.write(text);
                    fw.close();
                }
                catch (IOException e) {
                    System.out.println(e);
                }
                //SI le fichier a déja été enregistrer
                // ????????????

                //Exit
                this.frame.dispose();

                break;

                //case CANCEL
            case 2:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + res);
        }

    }

}
