package com.tech.app.models.gma;

import com.tech.app.models.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Cette classe nous permet de calculer l'arbre de couverture. Celui-ci est ensuite converti en graphe de couverture.
 * Nous n'utilisons plus la classe ReachabilityGraph pour le GMA mais celle-ci. En effet, lorsqu'un RdP est borné, son graphe de couverture
 * est le GMA.
 */
public class CoverabilityGraph {

    private final Model model;
    Marquage M0;
    public List<Marquage> marquagesAccessibles;
    public List<Marquage> marquagesATraiter;
    public List<Node> liste_node;
    public int nb_marquages;

    /**
     * Constructeur
     * @param model : modèle actuel, au moment du clic sur la fonction "Générer le GMA / Graphe de couverture".
     */
    public CoverabilityGraph(Model model) {
        this.model = model;
        this.M0 = new Marquage(model.getM0());
        this.marquagesAccessibles = new ArrayList<>();
        this.marquagesATraiter = new ArrayList<>();
        this.liste_node = new ArrayList<>();
    }

    /**
     * Méthode qui nous permet de récupérer la liste des noeuds
     * @return List de Node
     */
    public List<Node> getListe_node() {
        return liste_node;
    }

    public int getNb_marquages() {
        return nb_marquages;
    }

    /**
     * Méthode qui nous permet de faire une addition entre un vecteur et une colonne d'une matrice.
     *
     * @param v : vecteur
     * @param u : matrice
     * @param t : indice de la colonne
     * @return vecteur après addition.
     */
    private Marquage addVector(Marquage v, Vector<Vector<Integer>> u, int t) {
        Vector<Integer> v_temp = new Vector<>();
        Marquage m_temp = new Marquage(v_temp);
        for (int i = 0; i < v.getMarquage().size(); i++) {
            if (v.getMarquage().get(i) == Integer.MAX_VALUE) {
                v_temp.add(i, (v.getMarquage().get(i)));
            } else {
                v_temp.add(i, (v.getMarquage().get(i) + u.get(i).get(t)));
            }
        }
        m_temp.setMarquage(v_temp);
        return m_temp;
    }

    /**
     * Méthode qui nous permet de faire une soustraction entre deux vecteurs. Utilisé pour le test de couverture.
     * @param v : vecteur
     * @param u : vecteur
     * @return le vecteur de différence.
     */
    private Marquage subVector(Marquage v, Marquage u) {
        Vector<Integer> v_temp = new Vector<>();
        Marquage m_temp = new Marquage(v_temp);
        for (int i = 0; i < v.getMarquage().size(); i++) {
            if (v.getMarquage().get(i) == Integer.MAX_VALUE) {
                v_temp.add(i, (v.getMarquage().get(i)));
            } else {
                v_temp.add(i, (v.getMarquage().get(i) - u.getMarquage().get(i)));
            }
        }
        m_temp.setMarquage(v_temp);
        return m_temp;
    }

    /**
     * Cette méthode permet de vérifier si le marquage du noeud actuel couvre une colonne de la matrice W_moins ou Pré.
     * On va tester si le marquage du noeud est inférieur à la colonne t de la matrice pré.
     *
     * @param m   : marquage du noeud.
     * @param pre : matrice Pre du modèle.
     * @param t   : indice de la transition.
     * @return Vrai ou Faux.
     */
    private boolean couvre(Marquage m, Vector<Vector<Integer>> pre, int t) {

        for (int i = 0; i < pre.size(); i++) {
            // on teste sur toutes les lignes de la colonne t
            if (m.getMarquage().get(i) < pre.get(i).get(t)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cette méthode permet de determiner si le marquage M1 couvre le marquage M.
     * Si M1 = M -> M1 ne couvre pas M.
     * Si la différence entre M1 et M contient au moins un élément négatif et aucun positif (>0) -> M1 couvre M.
     *
     * @param M : marquage actuel.
     * @param M1 : marquage suivant.
     * @return Vrai ou Faux.
     */
    public boolean couverture(Marquage M, Marquage M1) {

        if (M.getMarquage().equals(M1.getMarquage())) {
            return false;
        }

        Marquage M2 = subVector(M1, M);

        if (M2.getMarquage().stream().allMatch(i -> i == 0)) {
            return true;
        }
        // Si la différence entre M1 et M contient au moins un élément négatif et aucun positif (>0) alors M1 couvre M
        boolean a = M2.getMarquage().stream().anyMatch(i -> i > 0 & i != Integer.MAX_VALUE);
        boolean b = M2.getMarquage().stream().noneMatch(i -> i < 0);
        return (a & b);
    }

    /**
     * Méthode qui permet de savoir si un marquage donné est dans la liste de marquages donné.
     * @param marquageList : liste de marquages.
     * @param marquage : marquage.
     * @return Vrai ou Faux.
     */
    public boolean containsMarquage(final List<Marquage> marquageList, final Vector<Integer> marquage) {
        return marquageList.stream().anyMatch(a -> a.getMarquage().equals(marquage));
    }

    /**
     * Méthode essentiel pour le calcul du graphe de couverture. Elle permet d'ajouter les omegas lorsque M1 couvre M.
     * @param m : marquage
     * @param m1 : marquage
     */
    public void tryToAddOmegas(final Marquage m, final Marquage m1) {
        for (int i = 0; i < m.getMarquage().size(); i++) {
            if (m1.getMarquage().get(i) > m.getMarquage().get(i)) {
                m1.getMarquage().set(i, Integer.MAX_VALUE);
            }
        }
    }

    /**
     * Méthode qui permet de calculer le GMA / graphe de couverture.
     */
    public void calculateCoverabilityGraph() {
        /* On ajoute le marquage initial aux deux listes */
        marquagesAccessibles.add(M0);
        marquagesATraiter.add(M0);
        Marquage M;
        Marquage M1;

        /* On initialise la liste des noeuds */
        liste_node = new ArrayList<>();

        /* Tant qu'il y a des marquages à traiter */
        while (marquagesATraiter.size() != 0) {

            /* On charge dans M le premier élément de la liste des marquages à traiter, puis retire cet élément de la liste. */
            M = marquagesATraiter.get(0);
            marquagesATraiter.remove(0);

            /* On créé un noeud avec le marquage que l'on vient de charger. On donne un nom au noeud.*/
            Node m = new Node(M);
            m.setName("M" + nb_marquages);

            /* On ajoute ce noeud à la liste des noeuds. On incrémente le compteur de marquages */
            liste_node.add(m);
            this.nb_marquages++;

            for (int t = 0; t < this.model.transitionVector.size(); t++) {
                /* Si le marquage M couvre la colonne t de la matrice pré alors : */
                if (couvre(M, this.model.getW_moins(), t)) {

                    M1 = addVector(M, this.model.getC(), t);

                    if (couverture(M, M1)) {
                        tryToAddOmegas(M, M1);
                    } else {
                        for (Node n : liste_node) {
                            if (couverture(n.getM(), M1)) {
                                if(!containsMarquage(marquagesAccessibles,M1.getMarquage()))
                                tryToAddOmegas(n.getM(), M1);
                            }
                        }
                    }

                    m.addChildren(new NodeStruct(new Node(M1), this.model.transitionVector.get(t)));

                    /* Si le marquage M1 n'est pas déjà dans la liste des marquages accessibles alors : */
                    if (!containsMarquage(marquagesAccessibles, M1.getMarquage())) {
                        /* On ajoute le marquage M1 aux deux listes : marquages accessibles et marquages à traiter. */
                        M1.setOld();
                        marquagesAccessibles.add(M1);
                        marquagesATraiter.add(M1);
                    }
                }
            }
        }
    }
}

