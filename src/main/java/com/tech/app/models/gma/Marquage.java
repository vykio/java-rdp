package com.tech.app.models.gma;

import java.util.Vector;

/**
 * Classe qui permet d'ajouter des attributs à un marquage (@CoverabiltyGraph).
 */
public class Marquage {

    private Vector<Integer> marquage;
    private boolean nouveau;

    //Constructeur pour un nouveau marquage

    /**
     * Constructeur pour un nouveau marquage.
     * @param marquage : le vecteur du marquage.
     */
    public Marquage(Vector<Integer> marquage){
        this.marquage= marquage;
        this.nouveau=true;
    }

    /**
     * Méthode qui retourne le vecteur du marquage.
     * @return vecteur du marquage.
     */
    public Vector<Integer> getMarquage() {
        return marquage;
    }

    /**
     * Méthode qui permet de donner un autre vecteur de marquage au marquage.
     * @param marquage : nouveau vecteur de marquage.
     */
    public void setMarquage(Vector<Integer> marquage) {
        this.marquage = marquage;
    }

    /**
     * Méthode qui permet de savoir si le marquage est un nouveau marquage (si on y a accedé ou non dans le graphe de couverture).
     * @return Vrai ou Faux.
     */
    public boolean isNouveau() {
        return nouveau;
    }

    /**
     * Méthode qui permet de passer l'attribut nouveau du marquage à la valeur faux.
     */
    public void setOld() {
        this.nouveau = false;
    }

    @Override
    public String toString() {
        return "{" + marquage + '}';
    }
}
