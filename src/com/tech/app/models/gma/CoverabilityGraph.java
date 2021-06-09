package com.tech.app.models.gma;

import com.tech.app.models.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CoverabilityGraph {

    private Model model;
    Vector<Integer> M0;
    public List<Vector<Integer>> marquagesAccessibles;
    public List<Vector<Integer>> marquagesATraiter;
    public List<Node> liste_node;
    public int nb_marquages;

    public CoverabilityGraph(Model model){
        this.model = model;
        this.M0 = model.getM0();
        this.marquagesAccessibles = new ArrayList<>();
        this.marquagesATraiter = new ArrayList<>();
        this.liste_node = new ArrayList<>();
    }

    // sera utilisé dans le GMA pour prendre le relai en cas de rdp non borné
    public CoverabilityGraph(Model model, Vector<Integer> M0, List<Vector<Integer>> marquagesAccessibles, List<Vector<Integer>> marquagesATraiter, List<Node> liste_node) {
        this.model = model;
        this.M0 = model.getM0();
        this.marquagesAccessibles = new ArrayList<>();
        this.marquagesATraiter = new ArrayList<>();
        this.liste_node = new ArrayList<>();
    }

    public List<Node> getListe_node() {
        return liste_node;
    }

    public int getNb_marquages() {
        return nb_marquages;
    }

    /**
     * Méthode qui nous permet de faire une addition entre un vecteur et une colonne d'une matrice.
     * @param v : vecteur
     * @param u : matrice
     * @param t : indice de la colonne
     * @return vecteur après addition.
     */
    private Vector<Integer> addVector(Vector<Integer> v, Vector<Vector<Integer>> u, int t){
        Vector<Integer> v_temp = new Vector<>();
        for(int i=0; i < v.size(); i++){
            v_temp.add(i,(v.get(i)+u.get(i).get(t)));
        }
        return v_temp;
    }

    /**
     * Cette méthode est utilisée dans l'algorithme de création du GMA. Elle permet de vérifier si le marquage du noeud actuel
     * couvre une colonne de la matrice W_moins ou Pré. On va tester si le marquage du noeud est inférieur à la colonne t de la matrice pré.
     * @param m : marquage du noeud.
     * @param pre : matrice Pre du modèle.
     * @param t : indice de la transition.
     * @return Vrai ou Faux
     */
    private boolean couvre(Vector<Integer> m, Vector<Vector<Integer>> pre, int t){

        for (int i = 0; i < pre.size(); i++) {
            // on teste sur toutes les lignes de la colonne t
            if (m.get(i) < pre.get(i).get(t)) {
                return false;
            }
        }
        return true;
    }

    public void calculateCoverabilityGraph(){
        /* On ajoute le marquage initial aux deux listes */
        marquagesAccessibles.add(M0);
        marquagesATraiter.add(M0);
        Vector<Integer> M;
        Vector<Integer> M1;

        /* On initialise la liste des noeuds */
        liste_node = new ArrayList<>();

        /* Tant qu'il y a des marquages à traiter */
        while (marquagesATraiter.size() != 0) {

            /* On charge dans M le premier élément de la liste des marquages à traiter, puis retire cet élément de la liste. */
            M = marquagesATraiter.get(0);
            marquagesATraiter.remove(0);


            /* On créé un noeud avec le marquage que l'on vient de charger. On donne un nom au noeud.*/
            Node m = new Node(M);
            m.setName("M"+nb_marquages);

            /* On ajoute ce noeud à la liste des noeuds. On incrémente le compteur de marquages */
            liste_node.add(m);
            this.nb_marquages++;


            /* Pour toutes les transitions du RdP */
            for (int t = 0; t < this.model.transitionVector.size(); t++) {

                /* Si le marquage M couvre la colonne t de la matrice pré alors : */
                if (couvre(M, this.model.getW_moins(), t)) {

                    /* On calcule le prochain marquage accessible à partir de la transition t. */
                    M1 = addVector(M, this.model.getC(), t);

                    /* On ajoute ce nouveau marquage à la liste des enfants du noeud m. */
                    m.addChildren(new NodeStruct(new Node(M1), this.model.transitionVector.get(t)));

                    /* Si le marquage M1 n'est pas déjà dans la liste des marquages accessibles alors : */
                    if (!marquagesAccessibles.contains(M1)) {
                        /* On ajoute le marquage M1 aux deux listes : marquages accessibles et marquages à traiter. */
                        marquagesAccessibles.add(M1);
                        marquagesATraiter.add(M1);
                    }
                }
            }
        }
    }
}
