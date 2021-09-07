package com.tech.app.models.stepper;

import com.tech.app.models.Model;
import com.tech.app.models.Transition;
import com.tech.app.models.gma.Marquage;
import com.tech.app.windows.panels.StepperHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Classe qui permet de simuler pas à pas le RdP actuel.
 */
public class Stepper{

    public final Model model;
    private StepperHandler stepperHandler;
    public List<Vector<Integer>> marquagesPasse;
    public List<String> sequenceTransition;


    public boolean showSequence = false;
    public int currentMarquageIndex;
    public int lastTransitionFranchieIndex;

    /**
     * Constructeur
     * @param model : model actuel.
     * @param stepperHandler : gestionnaire graphique du stepper.
     */
    public Stepper(Model model, StepperHandler stepperHandler){
        this.model = model;
        this.stepperHandler = stepperHandler;
        this.marquagesPasse = new ArrayList<>();
        this.sequenceTransition = new ArrayList<>();
        marquagesPasse.add(model.getM0());
    }

    /**
     * Constructeur
     * @param model : model actuel.
     */
    public Stepper(Model model){
        this.model = model;
        this.marquagesPasse = new ArrayList<>();
        this.sequenceTransition = new ArrayList<>();
        marquagesPasse.add(model.getM0());
        currentMarquageIndex=0;
    }

    /**
     * Méthode qui permet d'affecter un gestionnaire graphique au stepper.
     * @param stepperHandler
     */
    public void setStepperHandler(StepperHandler stepperHandler){
        this.stepperHandler = stepperHandler;
    }

    /**
     * Méthode qui permet d'afficher la séquence de simulation ou non.
     * Méthode appelée depuis @StepperToolbar/btnSequenceListener
     * @param showSequence : vrai ou faux.
     */
    public void setShowSequence(boolean showSequence) {
        this.showSequence = showSequence;
        stepperHandler.repaint();
    }

    /**
     * Méthode qui permet de récuperer la séquence de simulation
     * @return séquence de simulation.
     */
    public List<String> getSequenceTransition(){
        return sequenceTransition;
    }

    /**
     * Méthode qui permet de récupérer les 20 dernières transitions franchies de la séquence de simulation.
     * @return 20 dernières transitions franchies de la séquence de simulation.
     */
    public List<String> getLast20FromSequence(){
        List<String> res = new ArrayList<>();

        if(lastTransitionFranchieIndex > 20) {
            for (int i = lastTransitionFranchieIndex - 20; i < lastTransitionFranchieIndex; i++) {
                res.add(getSequenceTransition().get(i));
            }
        } else {
            for(int i = 0; i < lastTransitionFranchieIndex; i ++)
            res.add(getSequenceTransition().get(i));
        }

        return res;
    }

    /**
     * Méthode qui permet de convertir la sequence de simulation (Liste de string) en un string
     * @param sequenceTransition : sequence de simulation
     * @return string
     */
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

    /**
     * Méthode qui permet de passer au marquage suivant en fonction de la transition sur laquelle on clique.
     * L'utilisateur ne peut cliquer que sur une transition franchissable.
     * @param selectedObject : transition sur laquelle l'utilisateur a cliqué.
     */
    public void clickToNextMarquage(Object selectedObject){

        int indexOfT = model.transitionVector.indexOf(selectedObject);

        Vector<Integer> nextMarquage = addVector(model.getMarquage(), model.getC(), indexOfT);

        marquagesPasse.add(nextMarquage);
        currentMarquageIndex++;
        lastTransitionFranchieIndex++;
        System.out.println(lastTransitionFranchieIndex);

        sequenceTransition.add(model.transitionVector.get(indexOfT).getName());

        model.setMarquage(nextMarquage);
        stepperHandler.repaint();
    }

    /**
     * Méthode qui permet de récupérer la liste des marquages passés.
     * @return liste des marquages passés.
     */
    public List<Vector<Integer>> getMarquagesPasse() {
        return marquagesPasse;
    }

    /**
     * Méthode qui permet d'aller au dernier marquage atteint dans la simulation (dernier marquage passé).
     * Appelée depuis @StepperToolbar/btnLastListener.
     */
    public void goToLastMarquage(){
        model.setMarquage(getMarquagesPasse().get(getMarquagesPasse().size() -1));
        currentMarquageIndex = getMarquagesPasse().size()-1;
        lastTransitionFranchieIndex = sequenceTransition.size()-1;
        stepperHandler.repaint();

    }

    /**
     * Méthode qui permet d'aller au marquage suivant si le marquage actuel n'est pas le dernier.
     * Appelée depuis @StepperToolbar/btnNextListener.
     */
    public void goToNextMarquage() {

        if(currentMarquageIndex < marquagesPasse.size()-1){
            currentMarquageIndex++;
            lastTransitionFranchieIndex++;
            System.out.println(lastTransitionFranchieIndex);

            Vector<Integer> nextMarquage = getMarquagesPasse().get(currentMarquageIndex);
            model.setMarquage(nextMarquage);
        }

        stepperHandler.repaint();
    }

    /**
     * Méthode qui permet d'aller au marquage précédent si le marquage actuel n'est pas le premier marquage de la liste marquage passé.
     * Appelée depuis @StepperToolbar/btnPreviousListener.
     */
    public void goToPreviousMarquage(){

        if(currentMarquageIndex -1 >= 0) {
            currentMarquageIndex--;
            lastTransitionFranchieIndex--;
            System.out.println(lastTransitionFranchieIndex);

            Vector<Integer> previousMarquage = getMarquagesPasse().get(currentMarquageIndex);
            model.setMarquage(previousMarquage);
        }

        stepperHandler.repaint();
    }

    /**
     * Méthode qui permet d'aller au premier marquage de la liste de marquage passé.
     * Appelée depuis @StepperToolbar/btnFirstListener.
     */
    public void goToFirstMarquage(){
        currentMarquageIndex = 0;
        lastTransitionFranchieIndex=0;
        System.out.println(lastTransitionFranchieIndex);

        model.setMarquage(getMarquagesPasse().get(0));
        stepperHandler.repaint();
    }

    /**
     * Méthode qui permet de passer aléatoirement une transition franchissable.
     * Appelée depuis @StepperToolbar/getToolbar.
     */
    public void randomize(){

        try {
            List<Transition> transitionList = model.getTransitionFranchissables();

            Random random = new Random();

            if (!transitionList.isEmpty()) {
                int indexOfT = model.transitionVector.indexOf(transitionList.get(random.nextInt(transitionList.size())));

                Vector<Integer> nextMarquage = addVector(model.getMarquage(), model.getC(), indexOfT);

                marquagesPasse.add(nextMarquage);
                currentMarquageIndex++;
                lastTransitionFranchieIndex++;
                System.out.println(lastTransitionFranchieIndex);


                sequenceTransition.add(model.transitionVector.get(indexOfT).getName());

                model.setMarquage(nextMarquage);
                stepperHandler.repaint();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui permet de réinitialiser le stepper. On remet à 0 les différents index et listes.
     */
    public void reset(){
        model.setMarquage(model.getM0());
        this.marquagesPasse = new ArrayList<>();
        this.sequenceTransition = new ArrayList<>();
        this.currentMarquageIndex = 0;
        lastTransitionFranchieIndex = 0;
        this.showSequence = false;
        stepperHandler.repaint();
    }
}