package com.tech.app.models;

import com.tech.app.functions.FList;

import java.io.Serializable;
import java.util.*;

/**
 * Cette classe est le coeur du RdP. C'est dans cette classe que l'on va stocker les places, transitions et arcs du RdP et que l'on va calculer les matrices du RdP.
 */
public class Model implements Serializable {

    public int nbPlace;
    public int nbTransition;
    public int nbArc;

    public List<Place> placeVector;
    public List<Transition> transitionVector;
    public List<Arc> arcVector;

    Vector<Integer> M0;
    Vector<Vector<Integer>> w_plus, w_moins, C;

    /**
     * Constructeur du modèle.
     */
    public Model() {
        this.nbPlace = 0;
        this.nbTransition = 0;
        this.nbArc = 0;

        this.placeVector = new ArrayList<>();
        this.transitionVector = new ArrayList<>();
        this.arcVector = new ArrayList<>();

        this.M0 = new Vector<>();
        this.w_plus = new Vector<>();
        this.w_moins = new Vector<>();
        this.C = new Vector<>();
    }

    public Model(Model model){
        this.nbPlace = model.nbPlace;
        this.nbTransition = model.nbTransition;

        this.placeVector = model.placeVector;
        this.transitionVector = model.transitionVector;

        this.M0 = model.M0;
        this.w_plus = model.w_plus;
        this.w_moins = model.w_moins;
        this.C = model.C;
    }

    /**
     * Remplir M0
     * @return Vecteur d'entiers
     */
    public Vector<Integer> fill_M0() {

        for (int i = 0; i < this.placeVector.size(); i++) {
            M0.set(i, this.placeVector.get(i).getMarquage());
        }
        return M0;
    }

    /**
     * Remplir W+ et W-
     */
    public void fill_W() {
        /*
         * Iterate through each transition
         */

        // For each transition
        for (int i = 0; i < this.transitionVector.size(); i++) {
            List<Arc> listParents = this.transitionVector.get(i).getParents();
            List<Arc> listChildren = this.transitionVector.get(i).getChildren();

            // For each place of each transition
            for (int k = 0; k < this.placeVector.size(); k++) {
                // this.w_plus.get(k).get(i)

                /* pour chaque arc, on le transforme en place + poids */

                if (FList.contains(listChildren, this.placeVector.get(k))) {
                    this.w_plus.get(k).set(i, FList.poids_arc(listChildren, this.placeVector.get(k)));
                } else {
                    this.w_plus.get(k).set(i, 0);
                }

                if (FList.contains(listParents, this.placeVector.get(k))) {
                    this.w_moins.get(k).set(i, FList.poids_arc(listParents, this.placeVector.get(k)));
                } else {
                    this.w_moins.get(k).set(i, 0);
                }

            }
        }
    }

    /**
     * Remplir C
     */
    public void fill_C() {
        // Pour chaque ligne i
        for (int i = 0; i < this.C.size(); i++) {
            // Pour chaque colonne j
            for (int j = 0; j < this.C.get(i).size(); j++) {

                /* C_ij = W+_ij - W-_ij */
                this.C.get(i).set(j, (this.w_plus.get(i).get(j) - this.w_moins.get(i).get(j)));

            }
        }
    }

    /**
     * Ajouter une place, et mettre à jour les matrices
     * @param p : Place à ajouter
     */
    public void addPlace(Place p) {
        this.placeVector.add(p);
        this.nbPlace++;
        this.updateMatrices();
    }

    /**
     * Ajouter une transition, puis mettre à jour les matrices
     * @param t : Transition à ajouter
     */
    public void addTransition(Transition t) {
        this.transitionVector.add(t);
        this.nbTransition++;
        this.updateMatrices();
    }

    public void addArc(Arc a){
        this.arcVector.add(a);
        this.nbArc++;
    }

    /**
     * Supprimer une place
     * @param name : Nom de la place
     */
    public void removePlace(String name) {
        Place p = FList.getPlaceByName(this.placeVector, name);
        if (p != null) {
            this.removePlace(p);
        } //else {
        //java.lang.System.out.println("[!] Error - place \""+ name + "\" is null");
        //}

    }

    /**
     * Supprimer une place
     * @param place : Objet place à supprimer
     */
    public void removePlace(Place place) {
        this.placeVector.remove(place);
        this.nbPlace--;
        this.updateMatrices();
    }

    /**
     * Supprimer une transition
     * @param transition : Objet transition à supprimer
     */
    public void removeTransition(Transition transition){
        this.transitionVector.remove(transition);
        this.nbTransition--;
        this.updateMatrices();
    }

    public void removeArc(Arc a){
        try {
            for (Transition t : transitionVector) {             
                if(t.getChildren().contains(a)) {
                    System.out.println(t.getChildren());
                    t.removeChildren(a);
                }

                if(t.getParents().contains(a)) {
                    System.out.println(t.getParents());
                    t.removeParent(a);
                }

            }
        } catch (ConcurrentModificationException e){
            e.printStackTrace();
        }

        this.arcVector.remove(a);
        this.nbArc--;
        this.updateMatrices();
    }

    public void removeArcs(List<Arc> arcToDelete){
        //try{*/
            for(int i = arcToDelete.size() - 1; i >= 0;){
                for(int j = transitionVector.size() - 1; j >= 0;){

                    if(!transitionVector.get(j).getChildren().isEmpty() && transitionVector.get(j).getChildren().contains(arcToDelete.get(i))){
                        transitionVector.get(j).removeChildren(arcToDelete.get(i));
                        arcVector.remove(arcToDelete.get(i));
                        nbArc--;

                    }

                    if(!transitionVector.get(j).getParents().isEmpty() && transitionVector.get(j).getParents().contains(arcToDelete.get(i))){
                        transitionVector.get(j).removeParent(arcToDelete.get(i));
                        arcVector.remove(arcToDelete.get(i));
                        nbArc--;
                    }
                    j--;
                }
                arcToDelete.remove(i);
                i--;
            }
     
        System.out.println(this);
        this.updateMatrices();
        System.out.println("apres :");
        System.out.println(this);
    }

    /**
     * Mettre à jour les matrices
     * Future amélioration : Transformer les autres fonctions
     * pour ne pas avoir à vider complétement les matrices
     * afin de changer leur taille
     */
    public void updateMatrices() {
        this.clearMatrices();
        this.initialize();
        this.fillAll();
    }

    /**
     * Remplir toutes les matrices comme il faut !
     */
    private void fillAll() {
        this.fill_M0();
        this.fill_W();
        this.fill_C();
    }

    /**
     * Vider complétement les matrices : utiliser initialize() ensuite
     */
    private void clearMatrices() {
        this.M0 = new Vector<>();
        this.w_plus = new Vector<>();
        this.w_moins = new Vector<>();
        this.C = new Vector<>();
    }

    /**
     * Vider les listes de places et de transition. Utilisée pour nettoyer la zone de dessin.
     */
    public void clearAll() {
        clearMatrices();
        this.nbPlace = 0;
        this.nbTransition = 0;
        this.nbArc = 0;
        this.placeVector = new ArrayList<>();
        this.transitionVector = new ArrayList<>();
        this.arcVector = new ArrayList<>();

    }

    /**
     * Remplir les matrices à la bonne taille avec des 0
     */
    private void initialize() {
        for(int i=0;i< this.nbPlace;i++){
            Vector<Integer> rC = new Vector<>();
            Vector<Integer> rW_plus = new Vector<>();
            Vector<Integer> rW_moins = new Vector<>();

            for(int j=0;j<this.nbTransition;j++){
                rC.add(0);
                rW_plus.add(0);
                rW_moins.add(0);
            }

            this.C.add(rC);
            this.w_plus.add(rW_plus);
            this.w_moins.add(rW_moins);
        }

        for(int i = 0; i < this.nbPlace; i++) {
            this.M0.add(0);
        }
    }

    /**
     * Méthode qui permet d'afficher dans la console les caractéristiques du modèle de cette façon :
     * - marquage initial,
     * - matrice W+,
     * - matrice W-,
     * - matrice d'incidence C,
     * - Places du modèle,
     * - Transitions du modèle.
     * @return Résultat String
     */
    public String toString() {
        StringBuilder result;

        result = new StringBuilder("M0\n");
        for (int i = 0; i < this.M0.size(); i++) {
            result.append(i).append(".\t").append(this.M0.get(i)).append("\n");
        }

        result.append("\nW+:\n");
        for (int i = 0; i < this.w_plus.size(); i++) {
            result.append(i).append(".\t");
            for (int j = 0; j < this.w_plus.get(i).size(); j++) {
                result.append(this.w_plus.get(i).get(j)).append("\t");
            }
            result.append("\n");
        }

        result.append("\nW-:\n");
        for (int i = 0; i < this.w_moins.size(); i++) {
            result.append(i).append(".\t");
            for (int j = 0; j < this.w_moins.get(i).size(); j++) {
                result.append(this.w_moins.get(i).get(j)).append("\t");
            }
            result.append("\n");
        }

        result.append("\nC:\n");
        for (int i = 0; i < this.C.size(); i++) {
            result.append(i).append(".\t");
            for (int j = 0; j < this.C.get(i).size(); j++) {
                result.append(this.C.get(i).get(j)).append("\t");
            }
            result.append("\n");
        }

        result.append("\nPlaces:\n");
        for (Place place : placeVector) {
            result.append(place).append("\n");
        }

        result.append("\nTransitions:\n");
        for (Transition transition : transitionVector) {
            result.append(transition).append("\n");
        }


        return result.toString();
    }

    /**
     * Méthode qui permet d'afficher la matrice W+ dans la console.
     */
    public void print_W_plus() {
        StringBuilder result = new StringBuilder();
        result.append("\nW+:\n");
        for (int i = 0; i < this.w_plus.size(); i++) {
            result.append(i).append(".\t");
            for (int j = 0; j < this.w_plus.get(i).size(); j++) {
                result.append(this.w_plus.get(i).get(j)).append("\t");
            }
            result.append("\n");
        }
        java.lang.System.out.println(result);
    }

    /**
     * Méthode qui permet d'afficher W- dans la console.
     */
    public void print_W_moins() {
        StringBuilder result = new StringBuilder();
        result.append("\nW-:\n");
        for (int i = 0; i < this.w_moins.size(); i++) {
            result.append(i).append(".\t");
            for (int j = 0; j < this.w_moins.get(i).size(); j++) {
                result.append(this.w_moins.get(i).get(j)).append("\t");
            }
            result.append("\n");
        }
        java.lang.System.out.println(result);
    }

    /**
     * Méthode qui permet d'écrire la matrice C au format LaTeX dans un String.
     * @return String contenant la matrice C sous format LaTeX.
     */
    public String get_C() {

        //Code pour un matrice LaTeX avec labels pour lignes et colonnes

        StringBuilder result = new StringBuilder();

        // on utilise un tableau 2X2 ->
        // premiere ligne premiere colonne : rien
        // première ligne deuxieme colonne : vecteur transitions
        // deuxieme ligne première colonne : vecteur places
        // deuxieme ligne deuxieme colonne : matrice C


        // déclaration du tableau principal, on remplit la premiere case par du vide, et on commence à creer la deuxieme case
        result.append("C =\\begin{array}{c  c}\\phantom{}&\\begin{array}{");

        // centrer les éléments en fonction du nombre de colonnes
        result.append("c".repeat(this.transitionVector.size()));
        result.append("}");

        //boucle pour créer la première ligne -> liste de tous les noms des transitions
        for(int k = 0; k < this.transitionVector.size();k++){
            result.append(this.transitionVector.get(k).getName());
            if(k != this.transitionVector.size()-1) result.append("&");
        }

        result.append("\\end{array}\\\\\\begin{matrix}");

        //boucle pour mettre les noms des places
        for (Place place : this.placeVector) {
            result.append(place.getName()).append("\\\\");
        }

        result.append("\\end{matrix}&\\begin{pmatrix}");


        for (int i = 0; i < this.C.size(); i++) {
            if(i>0) result.append("\\\\");
            for (int j = 0; j < this.C.get(i).size(); j++) {
                if (this.C.get(i).get(j) >= 0) result.append("\\phantom{-}");
                result.append(this.C.get(i).get(j));
                if(j != this.C.get(i).size()-1) result.append("&");
            }

        }
        result.append("\\end{pmatrix}\\end{array}");
        //System.out.println(result.toString());
        return (result.toString());
    }

    /**
     * Méthode qui permet de donner/modifier la liste des Places.
     * @param placeVector : Liste de places.
     */
    public void setPlaceVector(List<Place> placeVector) {
        this.placeVector = placeVector;
    }

    /**
     * Méthode qui permet de donner/modifier la liste des Transitions.
     * @param transitionVector : Liste de Transistions.
     */
    public void setTransitionVector(List<Transition> transitionVector) {
        this.transitionVector = transitionVector;
    }

    /**
     * Méthode qui permet de récupérer le marquage initial
     * @return M0 : marquage initial
     */
    public Vector<Integer> getM0() { return M0; }

    /**
     * Méthode qui permet de récupérer la matrice W+.
     * @return W+.
     */
    public Vector<Vector<Integer>> getW_plus() { return w_plus; }

    /**
     * Méthode qui permet de récupérer la matrice W-.
     * @return W-.
     */
    public Vector<Vector<Integer>> getW_moins() { return w_moins; }

    /**
     * Méthode qui permet de récupérer la matrice d'incidence C.
     * @return C.
     */
    public Vector<Vector<Integer>> getC() { return C; }

    public Vector<Integer> getMarquage(){
        Vector<Integer> marquage = new Vector<>();
        for(Place p : placeVector){
            marquage.add(p.getMarquage());
        }
        return marquage;
    }

    public void setMarquage(Vector<Integer> marquage){
        if(marquage.size() == placeVector.size()) {
            for (int i = 0; i < placeVector.size(); i++){
                placeVector.get(i).setMarquage(marquage.get(i));
            }
        } else {
            System.out.println("La taille du marquage est différente de la taille du vecteur de places.");
        }
    }

    public List<Transition> getTransitionFranchissables(){
        List<Transition> transitionsFranchissables = new ArrayList<>();

        for(Transition t : transitionVector) {
            if(t.estFranchissable()){
                transitionsFranchissables.add(t);
            }
        }

        return transitionsFranchissables;
    }

    public List<Transition> getTransitionFranchissables(Vector<Integer> marquage){
        List<Transition> transitionsFranchissables = new ArrayList<>();
        Model temp = new Model(this);
        temp.setMarquage(marquage);

        for(Transition t : transitionVector){
            if(t.estFranchissable()){
                transitionsFranchissables.add(t);
            }
        }
        temp.setMarquage(this.M0);
        return transitionsFranchissables;
    }
}
