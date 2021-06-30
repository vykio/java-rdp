package com.tech.app.models.stepper;

import com.tech.app.models.Model;
import com.tech.app.models.Transition;
import com.tech.app.models.gma.Marquage;
import com.tech.app.windows.panels.StepperHandler;

import java.util.List;
import java.util.Vector;

public class Stepper{

    private final Model model;

    public Stepper(Model model){
        this.model = model;
    }

    /**
     * Méthode qui nous permet de faire une addition entre un vecteur et une colonne d'une matrice.
     *
     * @param v : vecteur
     * @param u : matrice
     * @param t : indice de la colonne
     * @return vecteur après addition.
     */
    private Vector<Integer> addVector(Vector<Integer> v, Vector<Vector<Integer>> u, int t) {
        Vector<Integer> v_temp = new Vector<>();
        for (int i = 0; i < v.size(); i++) {
                v_temp.add(i, (v.get(i) + u.get(i).get(t)));
        }
        return v_temp;
    }

    private Transition findTransition(List<Transition> transitions, Object selectedObject){
        return transitions.stream().filter(t -> t.equals(selectedObject)).findFirst().orElse(null);
    }

    public void goToNextMarquage(Object selectedObject){

        Transition t = findTransition(model.transitionVector, selectedObject);
        int indexOfT = model.transitionVector.indexOf(t);

        Vector<Integer> newMarquage = addVector(model.getMarquage(), model.getC(), indexOfT);

        model.setMarquage(newMarquage);
        //stepperHandler.repaint();
    }


}
