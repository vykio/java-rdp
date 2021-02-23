package com.tech.app.models.v4;

import java.util.Vector;

public class System {

    private int nbPlace;
    private int nbTransition;

    Place[] placeVector;
    Transition[] transitionVector;

    Vector<Vector<Integer>> C = new Vector<Vector<Integer>>();

    int[] M0;
    int[][] w_plus;
    int[][] w_moins;
    //int[][] C;

    public System(int nbPlace, int nbTransition) {
        this.nbPlace = nbPlace;
        this.nbTransition = nbTransition;

        this.placeVector = new Place[nbPlace];
        this.transitionVector = new Transition[nbTransition];

        this.M0 = new int[nbPlace];
        this.w_plus = new int[nbPlace][nbTransition];
        this.w_moins = new int[nbPlace][nbTransition];
        this.C = new Vector<Vector<Integer>>(); //new int[nbPlace][nbTransition];
    }

    public void addPlace(Place p) {

    }

    public String toString() {
        StringBuilder result;

        result = new StringBuilder("M0\n");
        for (int i = 0; i < M0.length; i++) {
            result.append(i).append(".\t").append(M0[i]).append("\n");
        }

        result.append("\nW+:\n");
        for (int i = 0; i < w_plus.length; i++) {
            result.append(i).append(".\t");
            for (int j = 0; j < w_plus[i].length; j++) {
                result.append(w_plus[i][j]).append("\t");
            }
            result.append("\n");
        }

        result.append("\nW-:\n");
        for (int i = 0; i < w_moins.length; i++) {
            result.append(i).append(".\t");
            for (int j = 0; j < w_moins[i].length; j++) {
                result.append(w_moins[i][j]).append("\t");
            }
            result.append("\n");
        }

        result.append("\nC:\n");
        /*for (int i = 0; i < C.length; i++) {
            result.append(i).append(".\t");
            for (int j = 0; j < C[i].length; j++) {
                result.append(C[i][j]).append("\t");
            }
            result.append("\n");
        }*/


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
