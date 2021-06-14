package com.tech.app.models;

import com.tech.app.functions.FUtils;
import com.tech.app.models.gma.Node;
import com.tech.app.models.gma.ReachabilityGraph;

public class ModelProperties {

    private final Model model;
    private final ReachabilityGraph gma;


    public boolean estBorne;
    public boolean estVivant;
    public boolean estReinitialisable;
    public boolean estRepetitif;

    public int borneMax = 0;

    public enum MODEL_PROPS {
        NONE,
        BORNITUDE,
        VIVACITE,
        REVERSIBILITE,
        REPETITIVITE
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
                break;
            case REPETITIVITE:
                break;
            case REVERSIBILITE:
                break;
            case NONE:
                break;
        }
    }

    // besoin du grpahe de couverture au cas ou non borné !
    private void modelBornitude(){
        System.out.println(gma.getListe_node());
        System.out.println("[!] Looking if the model is bounded.");
        System.out.println("[!] Looking if the model is bounded..");
        System.out.println("[!] Looking if the model is bounded...");

        if(gma.getListe_node().size() > 1000){
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
        System.out.print("Le système est ");

    }

    public String getModelBornitude(){
        modelBornitude();
        if(estBorne){
            if(borneMax == 1){
                return "Vrai, binaire.";
            } else {
                return "Vrai, "+borneMax+"-borné.";
            }
        }
        return "Faux";
    }

    @Override
    public String toString() {

        return  "<html><strong>Propriétés du réseau de Pétri</strong><br>Version: <strong>"+ FUtils.Program.getVersion() +
                "</strong><br>Github: <a href='https://github.com/gauthierleurette/java-rdp'>github.com/gauthierleurette/java-rdp</a><br><hr><br>" +
                "<ul>" +
                "<li>Bornitude : </li>" + this.getModelBornitude() +
                "<li>Vivacité : </li>" +
                "<li>Réversibilité</li>" +
                "<li>Répétitivité</li>" +
                "</ul>" +
                "<br>" +
                "</html>";

    }
}
