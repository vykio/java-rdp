package com.tech.app.models;

import com.tech.app.functions.FUtils;
import com.tech.app.models.gma.Node;
import com.tech.app.models.gma.NodeStruct;
import com.tech.app.models.gma.ReachabilityGraph;

import java.util.Vector;

public class ModelProperties {

    private final Model model;
    private final ReachabilityGraph gma;


    public boolean estBorne = false;
    public boolean estVivant = false;
    public boolean estReinitialisable = false;
    public boolean estRepetitif = false;public boolean estBloque = false;

    public int borneMax = 0;

    public enum MODEL_PROPS {
        NONE,
        BORNITUDE,
        VIVACITE,
        REVERSIBILITE,
        REPETITIVITE,
        BLOCAGE
    }

    public ModelProperties(Model model) {
        model.updateMatrices();
        this.model = model;
        this.gma = new ReachabilityGraph(model);
        gma.calculateReachabilityGraph();
    }

    public ModelProperties(Model model, MODEL_PROPS props){
        this(model);
        switch (props){
            case BORNITUDE:
                this.modelBornitude();
                break;
            case VIVACITE:
                this.modelVivacite();
                break;
            case REPETITIVITE:
                this.modelRepetitivite();
                break;
            case REVERSIBILITE:
                this.modelReinitialisable();
                break;
            case BLOCAGE:
                this.modelBlocage();
                break;
            case NONE:
                break;
        }
    }

    // besoin du grpahe de couverture au cas ou non borné !
    private boolean modelBornitude(){

        if(gma.getListe_node().size() > 100){
            System.out.println("Le système n'est pas borné");
            estBorne = false;
        }

        for(Node node : gma.getListe_node()){
            for(int i = 0; i < node.m.getMarquage().size(); i++){
                if(borneMax < node.m.getMarquage().get(i)){
                    borneMax = node.m.getMarquage().get(i);
                }
            }
        }
        estBorne = true;
        System.out.println("Le système est "+borneMax+"-borné.");
        return estBorne;
    }

    private boolean modelVivacite(){
        Vector<Integer> transitionCount = new Vector<>();
        transitionCount.setSize(model.nbTransition);

        for(int i= 0; i < model.nbTransition; i ++){
            transitionCount.set(i,0);
        }

        for(int i = 0; i < gma.getListe_node().size(); i++){
            for(NodeStruct nodeStruct : gma.getListe_node().get(i).getChildren()){
                transitionCount.set(model.transitionVector.indexOf(nodeStruct.transition),transitionCount.get(model.transitionVector.indexOf(nodeStruct.transition))+ 1);
            }
        }

        System.out.println("model : "+model.transitionVector);
        System.out.println("count : "+transitionCount);

        for (int i = 0; i < transitionCount.size(); i++) {
            if (transitionCount.get(i) < 1) {
                estVivant = false;
                System.out.println("Le système n'est pas vivant.");
                return false;
            }
        }
        estVivant = true;
        System.out.println("Le système est vivant.");
        return true;
    }

    public boolean modelRepetitivite(){
        for(int i = 0; i < gma.liste_node.size(); i++){

            if(gma.liste_node.get(i).getChildren().stream().anyMatch(n -> n.getNode().getM().getMarquage().equals(model.M0))){
                estRepetitif = true;
                System.out.println("Le système possède au moins une boucle");
                return true;
            }
        }
        estRepetitif = false;
        System.out.println("Le système ne possède pas de boucle");
        return false;
    }

    public boolean modelReinitialisable(){
        for(int i = 0; i < gma.liste_node.size(); i++){
            // Si il y a un noeud dont le marquage enfant est M0 alors il est répétitif.
            // pour voir réinitialisable, il faut pour tout marquage trouver un chemin qui amène à M0.
            if(gma.liste_node.get(i).getChildren().stream().anyMatch(n -> n.getNode().getM().getMarquage().equals(model.M0))){
                estReinitialisable = true;
                System.out.println("Le système possède au moins une boucle");
                return true;
            }
        }
        estReinitialisable = false;
        System.out.println("Le système ne possède pas de boucle");
        return false;
    }

    public boolean modelBlocage(){

        for(Node n : gma.liste_node) {
            if(model.getTransitionFranchissables(n.getM().getMarquage()) != null){
                estBloque = false;
                System.out.println("Le système n'a pas de blocage");
                return false;
            }
        }

        estBloque = true;
        System.out.println("Le système a au moins un blocage");
        return true;
    }

    public String getModelBornitude(){
        if(modelBornitude()){
            if(borneMax == 1){
                return "<font color='green'>Vrai, binaire. <font/>";
            } else {
                return "<font color ='green'> Vrai, "+borneMax+"-borné. <font/>";
            }
        }
        return "<font color='red'> Faux <font/>";
    }

    public String getModelVivacite(){
        if(modelVivacite()){
            return "<font color='green'> Vrai <font/>";
        }
        return "<font color='red'> Faux <font/>";
    }

    public String getModelRepetitivite(){
        if(modelRepetitivite()){
            return "<font color='green'> Vrai <font/>";
        }
        return "<font color='red'> Faux <font/>";
    }

    public String getModelReinitialisable(){
        if(modelReinitialisable()){
            return "<font color='green'> Vrai <font/>";
        }
        return "<font color='red'> Faux <font/>";
    }

    public String getModelBlocage(){
        if(modelBlocage()){
            return "<font color='green'> Vrai <font/>";
        }
        return "<font color='red'> Faux <font/>";
    }

    @Override
    public String toString() {

        return  "<html><strong>Propriétés du réseau de Pétri</strong><br>Version: <strong>"+ FUtils.Program.getVersion() +
                "</strong><br>Github: <a href='https://github.com/gauthierleurette/java-rdp'>github.com/gauthierleurette/java-rdp</a><br><hr><br>" +
                "<ul>" +
                "<li>Bornitude : </li> " + this.getModelBornitude() +
                "<li>Vivacité : </li>" + this.getModelVivacite() +
                "<li>Réinitialisable : </li>" + this.getModelReinitialisable() +
                "<li>Répétitivité : </li>" + this.getModelRepetitivite() +
                "<li>Blocage : </li>" + this.getModelBlocage() +
                "</ul>" +
                "<br>" +
                "</html>";

    }
}
