package com.tech.app.models;

import com.tech.app.functions.FUtils;
import com.tech.app.models.gma.CoverabilityGraph;
import com.tech.app.models.gma.Node;
import com.tech.app.models.gma.NodeStruct;
import com.tech.app.models.gma.ReachabilityGraph;

import java.util.Vector;

/**
 * Classe dans laquelle sont définies quelques propriétés des réseaux de Pétri.
 */
public class ModelProperties {

    private final Model model;
    //private final ReachabilityGraph gma;
    private final CoverabilityGraph coverabilityGraph;


    public boolean estBorne = false;
    public boolean estVivant = false;
    public boolean estReinitialisable = false;
    public boolean estRepetitif = false;
    public boolean estBloque = false;

    public int borneMax = 0;

    public enum MODEL_PROPS {
        NONE,
        BORNITUDE,
        VIVACITE,
        REVERSIBILITE,
        REPETITIVITE,
        BLOCAGE
    }

    /**
     * Constructeur
     * @param model : modèle
     */
    public ModelProperties(Model model) {
        model.updateMatrices();
        this.model = model;
        this.coverabilityGraph = new CoverabilityGraph(model);
        coverabilityGraph.calculateCoverabilityGraph();
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

    /**
     * Méthode qui permet de savoir si le RdP est borné.
     * Si le graphe de couverture contient un w -> non borné.
     * @return Vrai ou Faux.
     */
    private boolean modelBornitude(){

        for(Node node : coverabilityGraph.getListe_node()){
            for(int i = 0; i < node.m.getMarquage().size(); i++){
                if(node.m.getMarquage().get(i) == Integer.MAX_VALUE){
                    System.out.println("Le système n'est pas borné");
                    return false;
                }
                if(borneMax < node.m.getMarquage().get(i)){
                    borneMax = node.m.getMarquage().get(i);
                }
            }
        }
        estBorne = true;
        System.out.println("Le système est "+borneMax+"-borné.");
        return estBorne;
    }

    /**
     * Méthode qui permet de savoir si le RdP est vivant.
     * @return Vrai ou Faux.
     */
    private boolean modelVivacite(){
        Vector<Integer> transitionCount = new Vector<>();
        transitionCount.setSize(model.nbTransition);

        for(int i= 0; i < model.nbTransition; i ++){
            transitionCount.set(i,0);
        }

        for(int i = 0; i < coverabilityGraph.getListe_node().size(); i++){
            for(NodeStruct nodeStruct : coverabilityGraph.getListe_node().get(i).getChildren()){
                transitionCount.set(model.transitionVector.indexOf(nodeStruct.transition),transitionCount.get(model.transitionVector.indexOf(nodeStruct.transition))+ 1);
            }
        }

        //System.out.println("model : "+model.transitionVector);
        //System.out.println("count : "+transitionCount);

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

    /**
     * Méthode qui permet de savoir si le RdP contient une boucle.
     * Ne fonctionne pas.
     * @return Vrai ou Faux
     */
    public boolean modelRepetitivite(){
        for(int i = 0; i < coverabilityGraph.liste_node.size(); i++){

            if(coverabilityGraph.liste_node.get(i).getChildren().stream().anyMatch(n -> n.getNode().getM().getMarquage().equals(model.M0))){
                estRepetitif = true;
                System.out.println("Le système possède au moins une boucle");
                return true;
            }
        }
        estRepetitif = false;
        System.out.println("Le système ne possède pas de boucle");
        return false;
    }

    /**
     * Méthode qui permet de savoir si le RdP est réinitialisable.
     * @return Vrai ou Faux.
     */
    public boolean modelReinitialisable(){
        for(int i = 0; i < coverabilityGraph.liste_node.size(); i++){
            // Si il y a un noeud dont le marquage enfant est M0 alors il est répétitif.
            // pour voir réinitialisable, il faut pour tout marquage trouver un chemin qui amène à M0.
            if(coverabilityGraph.liste_node.get(i).getChildren().stream().anyMatch(n -> n.getNode().getM().getMarquage().equals(model.M0))){
                estReinitialisable = true;
                System.out.println("Le système possède au moins une boucle");
                return true;
            }
        }
        estReinitialisable = false;
        System.out.println("Le système ne possède pas de boucle");
        return false;
    }

    /**
     * Méthode qui permet de savoir si le RdP contient un blocage.
     * @return Vrai ou Faux.
     */
    public boolean modelBlocage(){

        for(Node n : coverabilityGraph.liste_node) {
            if(n.getChildren().isEmpty()){
                estBloque = true;
                System.out.println("Le système a au moins un blocage");
                return true;
            }
        }
        estBloque = false;
        System.out.println("Le système n'a pas de blocage");

        return false;
    }

    /**
     * Méthode qui permet d'afficher le résultat de @modelBornitude.
     * @return String
     */
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

    /**
     * Méthode qui permet d'afficher le résultat de @modelVivacite.
     * @return String
     */
    public String getModelVivacite(){
        if(modelVivacite()){
            return "<font color='green'> Vrai <font/>";
        }
        return "<font color='red'> Faux <font/>";
    }

    /**
     * Méthode qui permet d'afficher le résultat de @modelRepetitivite.
     * @return String
     */
    public String getModelRepetitivite(){
        if(modelRepetitivite()){
            return "<font color='green'> Vrai <font/>";
        }
        return "<font color='red'> Faux <font/>";
    }

    /**
     * Méthode qui permet d'afficher le résultat de @modelReinitialisable.
     * @return String
     */
    public String getModelReinitialisable(){
        if(modelReinitialisable()){
            return "<font color='green'> Vrai <font/>";
        }
        return "<font color='red'> Faux <font/>";
    }

    /**
     * Méthode qui permet d'afficher le résultat de @modelBlocage.
     * @return String
     */
    public String getModelBlocage(){
        if(modelBlocage()){
            return "<font color='green'> Vrai <font/>";
        }
        return "<font color='red'> Faux <font/>";
    }

    /**
     * Méthode qui permet d'afficher le résultat de toutes les fonctions.
     * @return String
     */
    @Override
    public String toString() {

        return  "<html><strong>Propriétés du réseau de Pétri</strong><br>Version: <strong>"+ FUtils.Program.getVersion() +
                "</strong><br>Github: <a href='https://github.com/gauthierleurette/java-rdp'>github.com/gauthierleurette/java-rdp</a><br><hr><br>" +
                "<ul>" +
                "<li>Bornitude : </li> " + this.getModelBornitude() +
                "<li>Vivacité : </li>" + this.getModelVivacite() +
                "<li>Réinitialisable : </li>" + this.getModelReinitialisable() +
                //"<li>Répétitivité : </li>" + this.getModelRepetitivite() +
               "<li>Blocage : </li>" + this.getModelBlocage() +
                "</ul>" +
                "<br>" +
                "</html>";

    }
}
