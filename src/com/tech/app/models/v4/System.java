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
    Vector<Vector<Integer>> w_plus;
    Vector<Vector<Integer>> w_moins;
    Vector<Vector<Integer>> C;

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
            C.add(r);
            w_plus.add(r);
            w_moins.add(r);
        }

        for(int i = 0; i < this.nbPlace; i++) {
            M0.add(0);
        }
    }

    public String toString() {
        StringBuilder result;

        result = new StringBuilder("M0\n");
        for (int i = 0; i < M0.size(); i++) {
            result.append(i).append(".\t").append(M0.get(i)).append("\n");
        }

        result.append("\nW+:\n");
        for (int i = 0; i < w_plus.size(); i++) {
            result.append(i).append(".\t");
            for (int j = 0; j < w_plus.get(i).size(); j++) {
                result.append(w_plus.get(i).get(j)).append("\t");
            }
            result.append("\n");
        }

        result.append("\nW-:\n");
        for (int i = 0; i < w_moins.size(); i++) {
            result.append(i).append(".\t");
            for (int j = 0; j < w_moins.get(i).size(); j++) {
                result.append(w_moins.get(i).get(j)).append("\t");
            }
            result.append("\n");
        }

        result.append("\nC:\n");
        for (int i = 0; i < C.size(); i++) {
            result.append(i).append(".\t");
            for (int j = 0; j < C.get(i).size(); j++) {
                result.append(C.get(i).get(j)).append("\t");
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
