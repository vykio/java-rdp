package com.tech.app.models.stepper;

import com.tech.app.models.Model;
import com.tech.app.models.Transition;
import com.tech.app.models.gma.Marquage;
import com.tech.app.windows.panels.StepperHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Stepper{

    public final Model model;
    private StepperHandler stepperHandler;
    public List<Vector<Integer>> marquagesPasse;
    public List<String> sequenceTransition;

    public boolean showSequence = false;
    public int currentMarquageIndex;

    public Stepper(Model model, StepperHandler stepperHandler){
        this.model = model;
        this.stepperHandler = stepperHandler;
        this.marquagesPasse = new ArrayList<>();
        this.sequenceTransition = new ArrayList<>();
        marquagesPasse.add(model.getM0());
    }

    public Stepper(Model model){
        this.model = model;
        this.marquagesPasse = new ArrayList<>();
        this.sequenceTransition = new ArrayList<>();
        marquagesPasse.add(model.getM0());
        currentMarquageIndex=0;
    }

    public void setStepperHandler(StepperHandler stepperHandler){
        this.stepperHandler = stepperHandler;
    }

    public void setShowSequence(boolean showSequence) {
        this.showSequence = showSequence;
        stepperHandler.repaint();
    }

    public List<String> getSequenceTransition(){
        return sequenceTransition;
    }

    public List<String> getLast20FromSequence(){
        List<String> res = new ArrayList<>();

        if(getSequenceTransition().size() > 20) {
            for (int i = getSequenceTransition().size() - 20; i < getSequenceTransition().size(); i++) {
                res.add(getSequenceTransition().get(i));
            }
        } else {
            res.addAll(getSequenceTransition());
        }

        return res;
    }

    public String getSequenceTransitionToString(List<String> sequenceTransition){
        StringBuilder msg = new StringBuilder();
        for(int i =0; i< sequenceTransition.size(); i++){
            if(i == sequenceTransition.size() - 1){
                msg.append(sequenceTransition.get(i)).append("}");
            } else {
                msg.append(sequenceTransition.get(i)).append(", ");
            }
        }
        return msg.toString();
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

        int indexOfT = model.transitionVector.indexOf(selectedObject);

        Vector<Integer> nextMarquage = addVector(model.getMarquage(), model.getC(), indexOfT);

        marquagesPasse.add(nextMarquage);
        currentMarquageIndex++;

        sequenceTransition.add(model.transitionVector.get(indexOfT).getName());

        model.setMarquage(nextMarquage);
        stepperHandler.repaint();
    }

    public List<Vector<Integer>> getMarquagesPasse() {
        return marquagesPasse;
    }

    public void goToLastMarquage(){
        model.setMarquage(getMarquagesPasse().get(getMarquagesPasse().size() -1));
        currentMarquageIndex = getMarquagesPasse().size()-1;
        stepperHandler.repaint();

    }

    public void goToNextMarquage() {

        if(currentMarquageIndex < marquagesPasse.size()-1){
            currentMarquageIndex++;
            Vector<Integer> nextMarquage = getMarquagesPasse().get(currentMarquageIndex);
            model.setMarquage(nextMarquage);
        }

        stepperHandler.repaint();
    }

    public void goToPreviousMarquage(){

        if(currentMarquageIndex -1 >= 0) {
            currentMarquageIndex--;
            Vector<Integer> previousMarquage = getMarquagesPasse().get(currentMarquageIndex);
            model.setMarquage(previousMarquage);
        }

        stepperHandler.repaint();
    }

    public void goToFirstMarquage(){
        currentMarquageIndex = 0;
        model.setMarquage(getMarquagesPasse().get(0));
        stepperHandler.repaint();
    }

    public void randomize(){

        try {
            List<Transition> transitionList = model.getTransitionFranchissables();

            Random random = new Random();

            if (!transitionList.isEmpty()) {
                int indexOfT = model.transitionVector.indexOf(transitionList.get(random.nextInt(transitionList.size())));

                Vector<Integer> nextMarquage = addVector(model.getMarquage(), model.getC(), indexOfT);

                marquagesPasse.add(nextMarquage);
                currentMarquageIndex++;

                sequenceTransition.add(model.transitionVector.get(indexOfT).getName());

                model.setMarquage(nextMarquage);
                stepperHandler.repaint();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void reset(){
        model.setMarquage(marquagesPasse.get(0));
        this.marquagesPasse = new ArrayList<>();
        this.sequenceTransition = new ArrayList<>();
        this.currentMarquageIndex = 0;
        this.showSequence = false;
        stepperHandler.repaint();
    }
}
