package com.tech.app.models.v4;

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

    public void generate_W_plus() {
        /*
        * Iterate through each transition
        */

        // For each transition
        for (int i = 0; i < this.transitionVector.size(); i++) {
            List<Place> listParents = this.transitionVector.get(i).getParents();

            // For each place of each transition
            for (int k = 0; k < this.placeVector.size(); k++) {
                // this.w_plus.get(k).get(i)
                if (listParents.contains(this.placeVector.get(k))) {
                    this.w_plus.get(k).set(i, 1);
                } else {
                    this.w_plus.get(k).set(i, 0);
                }

            }
        }
    }

    public void generate_W_moins() {
        /*
         * Iterate through each transition
         */

        // For each transition
        for (int i = 0; i < this.transitionVector.size(); i++) {
            List<Place> listChildrens = this.transitionVector.get(i).getChildrens();

            // For each place of each transition
            for (int k = 0; k < this.placeVector.size(); k++) {
                // this.w_plus.get(k).get(i)
                if (listChildrens.contains(this.placeVector.get(k))) {
                    this.w_moins.get(k).set(i, 1);
                } else {
                    this.w_moins.get(k).set(i, 0);
                }

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

    public void update() {
        for(int i=0;i< this.nbPlace;i++){
            Vector<Integer> r = new Vector<>();
            for(int j=0;j<this.nbTransition;j++){
                r.add(0);
            }
            this.C.add(r);
            this.w_plus.add(r);
            this.w_moins.add(r);
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

    public void testC() {

        for(int i=0;i< this.nbPlace;i++){
            Vector<Integer> r = new Vector<>();
            for(int j=0;j<this.nbTransition;j++){
                r.add((int)(Math.random() * 100));
            }
            C.add(r);
        }
        for(int i=0;i<this.nbPlace;i++){
            Vector<Integer> r = C.get(i);
            for(int j=0;j<this.nbTransition;j++){
                java.lang.System.out.print(r.get(j) + "\t");
            }
            java.lang.System.out.println();
        }
        // matrix.get(1).get(0); //2nd row 1st column
    }

}
