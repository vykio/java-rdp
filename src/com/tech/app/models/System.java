package com.tech.app.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class System {

    private int nbPlace;
    private int nbTransition;

    List<Place> placeVector;
    List<Transition> transitionVector;

    Vector<Integer> M0;
    Vector<Vector<Integer>> w_plus, w_moins, C;

    //int[] M0;
    //int[][] w_plus;
    //int[][] w_moins;
    //int[][] C;

    public System() {
        this.nbPlace = 0;
        this.nbTransition = 0;

        this.placeVector = new ArrayList<Place>();
        this.transitionVector = new ArrayList<Transition>();

        this.M0 = new Vector<Integer>();
        this.w_plus = new Vector<Vector<Integer>>();
        this.w_moins = new Vector<Vector<Integer>>();
        this.C = new Vector<Vector<Integer>>(); //new int[nbPlace][nbTransition];
    }

    public void generate_M0() {
        for (int i = 0; i < this.placeVector.size(); i++) {
            M0.set(i, this.placeVector.get(i).getMarquage());
        }
    }

    public void generate_W() {
        /*
        * Iterate through each transition
        */

        // For each transition
        for (int i = 0; i < this.transitionVector.size(); i++) {
            List<Place> listParents = this.transitionVector.get(i).getParents();
            List<Place> listChildrens = this.transitionVector.get(i).getChildrens();

            // For each place of each transition
            for (int k = 0; k < this.placeVector.size(); k++) {
                // this.w_plus.get(k).get(i)
                if (listParents.contains(this.placeVector.get(k))) {
                    this.w_plus.get(k).set(i, 1);
                } else {
                    this.w_plus.get(k).set(i, 0);
                }

                if (listChildrens.contains(this.placeVector.get(k))) {
                    this.w_moins.get(k).set(i, 1);
                } else {
                    this.w_moins.get(k).set(i, 0);
                }

            }
        }
    }

    public void generate_C() {
        // Pour chaque ligne i
        for (int i = 0; i < this.C.size(); i++) {
            // Pour chaque colonne j
            for (int j = 0; j < this.C.get(i).size(); j++) {

                /* C_ij = W+_ij - W-_ij */
                this.C.get(i).set(j, (int)(this.w_plus.get(i).get(j) - this.w_moins.get(i).get(j)));

            }
        }
    }

    public void addPlace(Place p) {
        this.placeVector.add(p);
        this.nbPlace++;
    }

    public void addTransition(Transition t) {
        this.transitionVector.add(t);
        this.nbTransition++;
    }

    public void removePlace(String name) {

    }

    public void initialize() {
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
        for (int i = 0; i < placeVector.size(); i++ ) {
            result.append(placeVector.get(i)).append("\n");
        }

        result.append("\nTransitions:\n");
        for (int i = 0; i < transitionVector.size(); i++ ) {
            result.append(transitionVector.get(i)).append("\n");
        }


        return result.toString();
    }

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
        java.lang.System.out.println(result.toString());
    }

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
        java.lang.System.out.println(result.toString());
    }

}
