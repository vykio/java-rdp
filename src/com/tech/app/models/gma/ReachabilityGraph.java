package com.tech.app.models.gma;

import com.tech.app.models.Arc;
import com.tech.app.models.Model;
import com.tech.app.models.Place;
import com.tech.app.models.Transition;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ReachabilityGraph {

    private Model model;
    Vector<Integer> M0;
    public List<Vector<Integer>> marquagesAccessibles;
    public List<Vector<Integer>> marquagesATraiter;
    public List<Node> liste_node;
    public int nb_marquages;

    public ReachabilityGraph(Model model){
        this.model = model;
        this.M0 = model.getM0();
        this.marquagesAccessibles = new ArrayList<>();
        this.marquagesATraiter = new ArrayList<>();
        this.liste_node = new ArrayList<>();
    }

    public List<Node> getListe_node() { return liste_node; }

    private Vector<Integer> getM0(){
        return this.M0;
    }

    private boolean couvre(Vector<Integer> m, Vector<Vector<Integer>> pre, int t){

        for (int i = 0; i < pre.size(); i++) {

            if (m.get(i) < pre.get(i).get(t)) {
                return false;
            }
        }

        return true;
    }

    private Vector<Integer> addVector(Vector<Integer> v, Vector<Vector<Integer>> u, int t){
        Vector<Integer> v_temp = new Vector<>();
        for(int i=0; i < v.size(); i++){
            v_temp.add(i,(v.get(i)+u.get(i).get(t)));
        }
        return v_temp;
    }

    public void updateModel(Model model) {
        this.model = model;
        this.M0 = model.getM0();
        this.marquagesAccessibles = new ArrayList<>();
        this.marquagesATraiter = new ArrayList<>();
        this.liste_node = new ArrayList<>();
    }

    public void calculateReachabilityGraph(){

        marquagesAccessibles.add(M0);
        marquagesATraiter.add(M0);
        Vector<Integer> M;
        Vector<Integer> M1;

        liste_node = new ArrayList<>();

        //int i = 0;

            while (marquagesATraiter.size() != 0) {
                //System.out.println("Size-avant: " + marquagesATraiter.size());
                M = marquagesATraiter.get(0);
                marquagesATraiter.remove(0);
                //i--;
                //System.out.println("Size-après: " + marquagesATraiter.size());
                //System.out.println("Depart: " + M);

                Node m = new Node(M);
                m.setName("M"+nb_marquages);
                liste_node.add(m);
                this.nb_marquages++;

                //System.out.println("Marquages a traiter: " + marquagesATraiter);
                //System.out.println("Marquages accessible: " + marquagesAccessibles);

                for (int t = 0; t < this.model.transitionVector.size(); t++) {
                    if (couvre(M, this.model.getW_moins(), t)) {
                        M1 = addVector(M, this.model.getC(), t);

                        m.addParent(new NodeStruct(new Node(M1), this.model.transitionVector.get(t)));

                        //System.out.println("Resultante: " + M1);
                        //System.out.println("MA: " + marquagesAccessibles);
                        if (!marquagesAccessibles.contains(M1)) {
                            //System.out.println("Contains...");
                            marquagesAccessibles.add(M1);
                            marquagesATraiter.add(M1);

                        }
                    }
                }
                //i++;
            }
            //System.out.println("GMA terminé!");

        /*
        System.out.println("Liste noeuds: ");
        for (Node n : liste_node) {
            System.out.println(n);
            System.out.println("----------");
        }

         */

    }

    public static void main(String[] args){

        /* Instanciation du système */
        Model model = new Model();


        /* Définition des places et des transitions */
        Place p1 = new Place("P1", 0, 0, 1);
        Place p2 = new Place("P2", 0, 0, 0);
        Place p3 = new Place("P3", 0,0, 0);
        Transition t1 = new Transition("t1", 0, 0);
        Transition t2 = new Transition("t2", 0, 0);
        Transition t3 = new Transition("t3", 0, 0);
        Transition t4 = new Transition("t4", 0, 0);
        Transition t5 = new Transition("t5", 0, 0);

        /* Ajout des places d'entrées et de sorties */
        t1.addChildren(p1);
        t1.addParent(p2);

        t2.addChildren(p1);
        t2.addParent(p2);
        t2.addParent(p3);

        t3.addChildren(p1);
        t3.addParent(p3);

        t4.addChildren(p2);
        t4.addParent(p3);

        t5.addChildren(new Arc(p3, 2));
        t5.addParent(p1);

        /* Ajout des places et des transitions au système */
        model.addPlace(p1);
        model.addPlace(p2);
        model.addPlace(p3);
        model.addTransition(t1);
        model.addTransition(t2);
        model.addTransition(t3);
        model.addTransition(t4);
        model.addTransition(t5);


        System.out.println(model);
        /*
        System.out.println(model.M0);
        System.out.println(model.w_moins);


         */

        ReachabilityGraph reachabilityGraph = new ReachabilityGraph(model);
        reachabilityGraph.calculateReachabilityGraph();

        for (Vector<Integer> m : reachabilityGraph.marquagesAccessibles) {
            System.out.println(m);
        }




    }


}
