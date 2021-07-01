package com.tech.app.models.stepper;

import com.tech.app.models.Model;
import com.tech.app.models.Transition;
import com.tech.app.models.gma.Marquage;
import com.tech.app.windows.panels.StepperHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Stepper{

    private final Model model;
    private final StepperHandler stepperHandler;
    public List<Vector<Integer>> marquagesPasse;

    public Stepper(Model model, StepperHandler stepperHandler){
        this.model = model;
        this.stepperHandler = stepperHandler;
        this.marquagesPasse = new ArrayList<>();
        marquagesPasse.add(model.getM0());
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

    private Vector<Integer> findMarquage(List<Vector<Integer>> marquagesPasse, Vector<Integer> currentMarquage){
        return marquagesPasse.stream().filter(m -> m.equals(currentMarquage)).findFirst().orElse(null);
    }

    public void clickToNextMarquage(Object selectedObject){

        int indexOfT = model.transitionVector.indexOf((Transition) selectedObject);

        Vector<Integer> nextMarquage = addVector(model.getMarquage(), model.getC(), indexOfT);

        marquagesPasse.add(nextMarquage);
        System.out.println("from click =" +marquagesPasse);

        model.setMarquage(nextMarquage);
        stepperHandler.repaint();
    }

    public List<Vector<Integer>> getMarquagesPasse() {
        return marquagesPasse;
    }

    public void goToLastMarquage(){
        model.setMarquage(getMarquagesPasse().get(getMarquagesPasse().size() -1));
        stepperHandler.repaint();

    }

    public void goToNextMarquage() {
        if(!getMarquagesPasse().isEmpty()) {

            int indexOfCurrentMarquage = marquagesPasse.indexOf(findMarquage(marquagesPasse, model.getMarquage()));

            Vector<Integer> nextMarquage = getMarquagesPasse().get(indexOfCurrentMarquage+1);

            model.setMarquage(nextMarquage);
        }
        stepperHandler.repaint();
    }

    public void goToPreviousMarquage(){
        System.out.println(getMarquagesPasse());
        if(!getMarquagesPasse().isEmpty()) {

            int indexOfCurrentMarquage = marquagesPasse.indexOf(findMarquage(marquagesPasse, model.getMarquage()));

            Vector<Integer> previousMarquage = getMarquagesPasse().get(indexOfCurrentMarquage - 1);

            model.setMarquage(previousMarquage);
        }
        stepperHandler.repaint();
    }

    public void goToFirstMarquage(){
        model.setMarquage(getMarquagesPasse().get(0));
        stepperHandler.repaint();
    }
}
