package com.tech.app.windows.handlers;

import com.tech.app.models.Model;

import java.io.*;

/**
 * Classe qui gère la sauvegarde et le chargement de fichier.
 */
public class SaveManager {

    /**
     * Méthode qui permet de sauvegarder le modèle actuel dans un fichier .jrdp.
     * On enregistre directement l'objet model. De ce fait à chaque changement dans le code source, les sauvegardes ne sont plus utilisables.
     * @param f : Fichier.
     * @param model : modèle à sauvegarder.
     * @return Vrai ou Faux
     */
    public boolean save(File f, Model model) {

        if (!f.getAbsolutePath().endsWith(".jrdp")) {
            f = (new File(f.getAbsolutePath() + ".jrdp"));
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(model);
            out.close();
            fileOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Méthode qui permet de charger le modèle enregisté dans un fichier .jrdp.
     * On enregistre directement l'objet model. De ce fait à chaque changement dans le code source, les sauvegardes ne sont plus utilisables.
     * @param f : Fichier à charger.
     * @param model : Modèle actuel.
     * @return modèle de la sauvegarde.
     */
    public Model load(File f, Model model) {
        Model mo;
        try {
            FileInputStream fileIn = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mo = (Model) in.readObject();
            model = mo;
            in.close();
            fileIn.close();
            return model;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return null;
        }
    }

}
