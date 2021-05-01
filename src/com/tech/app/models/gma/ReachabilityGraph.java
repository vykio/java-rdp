package com.tech.app.models.gma;

import com.tech.app.models.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Cette classe nous permet de créer le GMA.
 */
public class ReachabilityGraph {

    private Model model;
    Vector<Integer> M0;
    public List<Vector<Integer>> marquagesAccessibles;
    public List<Vector<Integer>> marquagesATraiter;
    public List<Node> liste_node;
    public int nb_marquages;

    /**
     * Constructeur
     * @param model : modèle actuel, au moment du clic sur la fonction "Générer le GMA".
     */
    public ReachabilityGraph(Model model){
        this.model = model;
        this.M0 = model.getM0();
        this.marquagesAccessibles = new ArrayList<>();
        this.marquagesATraiter = new ArrayList<>();
        this.liste_node = new ArrayList<>();
    }

    /**
     * Méthode qui nous permet de récupérer la liste des noeuds
     * @return List de Node
     */
    public List<Node> getListe_node() { return liste_node; }

    /**
     * Méthode qui permet de récupérer le marquage initial M0 du modèle.
     * C'est-à-dire l'état dans lequel il a été dessiné.
     * @return Vecteur d'entiers
     */
    private Vector<Integer> getM0(){ return this.M0; }

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
     * Méthode qui permet de mettre à jour le modèle.
     * @param model : modèle actuel.
     */
    public void updateModel(Model model) {
        this.model = model;
        this.M0 = model.getM0();
        this.marquagesAccessibles = new ArrayList<>();
        this.marquagesATraiter = new ArrayList<>();
        this.liste_node = new ArrayList<>();
    }

    /**
     * Méthode qui permet de calculer le GMA. Cette méthode utilise l'algorithme énoncé dans le cours de Systèmes à Evénements Discrets (2020) de Mr LHERBIER.
     * Inconvénient : Il n'y a pas de point d'arret. Si le GMA est non borné, la méthode boucle à l'infini.
     */
    public void calculateReachabilityGraph(){

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
