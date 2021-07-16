package com.tech.app.windows.handlers;

import com.tech.app.models.Arc;
import com.tech.app.models.Model;
import com.tech.app.models.Place;
import com.tech.app.models.Transition;
import com.tech.app.models.PointControle;
import org.jgrapht.util.ArrayUtil;

import java.awt.geom.Point2D;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Classe qui gère la sauvegarde et le chargement de fichier.
 */
public class SaveManager {

    /**
     * Méthode qui permet de sauvegarder le modèle actuel dans un fichier .jrdp.
     * On enregistre directement l'objet model. De ce fait à chaque changement dans le code source, les sauvegardes ne sont plus utilisables.
     * @param f : Fichier.
     * @param model : modèle à sauvegarder.
     * @return Vrai ou Faux
     */
    public boolean save(File f, Model model) {

        if (!f.getAbsolutePath().endsWith(".jrdp")) {
            f = (new File(f.getAbsolutePath() + ".jrdp"));
        }

        try {

            FileWriter out = new FileWriter(f);

            //Ecriture des places
            for(int i = 0; i < model.placeVector.size();i++){

                // Si la place n'a pas de label
                if(model.placeVector.get(i).getLabel().equals("")){
                    // name x y marquage
                    out.write(model.placeVector.get(i).getName()+" "+ (int) model.placeVector.get(i).getX()+" "+ (int) model.placeVector.get(i).getY()+" "+model.placeVector.get(i).getMarquage()+"\n");
                } else {
                    // name x y marquage label position
                    out.write(model.placeVector.get(i).getName() + " " + (int) model.placeVector.get(i).getX() + " " + (int) model.placeVector.get(i).getY() + " " + model.placeVector.get(i).getMarquage() + " " + model.placeVector.get(i).getLabel() + " " + model.placeVector.get(i).getPosition() + "\n");
                }
            }

            //Ecriture des transitions et des arcs
            for(int i = 0; i < model.transitionVector.size();i++){

                // Si la transition n'a pas de label
                if(model.transitionVector.get(i).getLabel().equals("")){
                    out.write(model.transitionVector.get(i).getName()+" "+ (int) model.transitionVector.get(i).getX()+" "+ (int) model.transitionVector.get(i).getY()+" ");
                }else {
                    out.write(model.transitionVector.get(i).getName() + " " + (int) model.transitionVector.get(i).getX() + " " + (int) model.transitionVector.get(i).getY() + " " + model.transitionVector.get(i).getLabel() + " " + model.transitionVector.get(i).getPosition() + " ");
                }

                // Si la transition a des arcs enfants
                if(!model.transitionVector.get(i).getChildren().isEmpty()){
                    out.write("c ");
                    //ecriture des enfants
                    for(int j=0; j<model.transitionVector.get(i).getChildren().size(); j++){
                        Arc child = model.transitionVector.get(i).getChildren().get(j);
                        //Point2D.Double pt = new Point2D.Double();
                        //child.reverse.transform(new Point2D.Double(child.getPointCtr1().getX(),child.getPointCtr1().getY()), pt);
                        //out.write("a "+child.getPlace().getName()+" "+ child.getPoids()+" "+ (int) pt.getX()+" " + (int) pt.getY()+" ");
                        out.write("a "+child.getPlace().getName()+" "+ child.getPoids()+" "+ (int) child.getPointCtr1().getX()+" " + (int) child.getPointCtr1().getY()+" ");

                    }
                }

                // Si la transition a des arcs parents
                if(!model.transitionVector.get(i).getParents().isEmpty()){
                    out.write(" p ");
                    //ecriture des parents
                    for(int j=0; j<model.transitionVector.get(i).getParents().size(); j++){
                        Arc parent = model.transitionVector.get(i).getParents().get(j);
                        //Point2D.Double pt = new Point2D.Double();
                        //parent.reverse.transform(new Point2D.Double(parent.getPointCtr1().getX(),parent.getPointCtr1().getY()), pt);
                        //out.write("a "+parent.getPlace().getName()+" "+ parent.getPoids()+" " + (int) pt.getX()+" " + (int) pt.getY()+" ");
                        out.write("a "+parent.getPlace().getName()+" "+ parent.getPoids()+" " + (int) parent.getPointCtr1().getX()+" " + (int) parent.getPointCtr1().getY()+" ");

                    }
                }
                out.write("\n");
            }

            out.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


public static Place findPlaceByName(List<Place> placeVector, String name){
        //permet de recupérer une place de la liste de place du model par son attribut nom
        return placeVector.stream().filter(place -> name.equals(place.getName())).findFirst().orElse(null);
}

    /**
     * Méthode qui permet de charger le modèle enregisté dans un fichier .jrdp.
     * On enregistre directement l'objet model. De ce fait à chaque changement dans le code source, les sauvegardes ne sont plus utilisables.
     * @param f : Fichier à charger.
     * @param model : Modèle actuel.
     * @return modèle de la sauvegarde.
     */
    public Model load(File f, Model model) {

        Model mo = new Model();
        boolean childrenSection = false;
        boolean parentSection = false;

        try {

            FileReader in = new FileReader(f);

            //Lecture ligne par ligne.
            try{
                List<String> allLines = Files.readAllLines(Path.of(f.getAbsolutePath()));

                for(String line : allLines){

                    // ici on récupère dans un tableau tous les "mots" de la ligne == séparés par un espace
                    String[] words = line.split("\\s+");

                    //Si la ligne commence par un P alors on va créer une place.
                    if(line.startsWith("P")){
                        //Si il y a 6 mots dans la ligne alors la place à un label et une position.
                        if(words.length == 6){
                            mo.addPlace(new Place(words[0], Double.parseDouble(words[1]),Double.parseDouble(words[2]), Integer.parseInt(words[3]), words[4], Integer.parseInt(words[5])));
                        } else {
                            mo.addPlace(new Place(words[0], Double.parseDouble(words[1]),Double.parseDouble(words[2]), Integer.parseInt(words[3])));
                        }
                    }

                    // Si la ligne commence par un t, alors on va créer une transition.
                    if(line.startsWith("t")){
                        Transition t;

                        //Ici on cherche à récupérer l'indice où se trouve le "c" qui va nous permettre de savoir si la transition a un label et une position ou non.
                        List<String> l = Arrays.asList(words);
                        int indexofC = l.indexOf("c");
                        int indexofP = l.indexOf("p");

                        // si l'indice de c est de 5 alors la transition a un label et une position.
                        if(indexofC == 5 ||indexofP == 5) {
                            t =  new Transition(words[0], Double.parseDouble(words[1]),Double.parseDouble(words[2]), words[3], Integer.parseInt(words[4]));
                        } else {
                            t =  new Transition(words[0], Double.parseDouble(words[1]),Double.parseDouble(words[2]));
                        }

                        // on ajoute la transition au model
                        mo.addTransition(t);

                        // Pour j commencant à l'indice de c jusqu'a la fin du tableau de mots, on va chercher à ajouter les arcs enfants et parent de la transition.
                        int loopStartIndex = indexofC;
                        if(loopStartIndex == -1){
                            loopStartIndex = indexofP;
                        }
                        for(int j = loopStartIndex; j < words.length;){

                            boolean actionPerformed = false;

                            // Si on a déjà ajouter un arc enfant et que l'indice j du tableau de mot vaut "a" -> jamais vrai au premier tour.
                            if(childrenSection && words[j].equals("a")){
                                Place p = findPlaceByName(mo.placeVector, words[j+1]);
                                Arc a = new Arc(p,Integer.parseInt(words[j+2]),t.getX(), t.getY(), false, t);
                                PointControle pt = new PointControle(Integer.parseInt(words[j+3]),Integer.parseInt(words[j+4]), a);
                                t.addChildren(a);
                                mo.addArc(a);
                                actionPerformed = true;
                                j=j+5;
                            }

                            // Si on n'a pas déjà ajouter un arc dans cette itération, que l'on a déjà ajouté un premier parent et que l'indice j du tableau de mot vaut "a"
                            if(!actionPerformed && parentSection && words[j].equals("a")){
                                Place p = findPlaceByName(mo.placeVector, words[j+1]);
                                Arc a = new Arc(p,Integer.parseInt(words[j+2]),t.getX(), t.getY(), true, t);
                                PointControle pt = new PointControle(Integer.parseInt(words[j+3]),Integer.parseInt(words[j+4]), a);
                                t.addParent(a);
                                mo.addArc(a);
                                actionPerformed = true;
                                j=j+5;
                            }

                            // Si on n'a pas déjà ajouter un arc dans cette itération, que l'indice j du tableau de mot vaut "c"
                            if(!actionPerformed && words[j].equals("c")){
                                childrenSection = true;
                                parentSection = false;
                                Place p = findPlaceByName(mo.placeVector, words[j+2]);
                                Arc a = new Arc(p,Integer.parseInt(words[j+3]),t.getX(), t.getY(),false, t);
                                PointControle pt = new PointControle(Integer.parseInt(words[j+4]),Integer.parseInt(words[j+5]), a);
                                t.addChildren(a);
                                mo.addArc(a);
                                actionPerformed = true;
                                j=j+6;
                            }

                            // Si on n'a pas déjà ajouter un arc dans cette itération, l'indice j du tableau de mot vaut "p"
                            if(!actionPerformed && words[j].equals("p") ){
                                childrenSection = false;
                                parentSection = true;
                                Place p = findPlaceByName(mo.placeVector, words[j+2]);
                                Arc a  = new Arc(p,Integer.parseInt(words[j+3]),t.getX(), t.getY(),true, t);
                                PointControle pt = new PointControle(Integer.parseInt(words[j+4]),Integer.parseInt(words[j+5]), a);
                                t.addParent(a);
                                mo.addArc(a);
                                j=j+6;
                            }
                        }
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }

            model = mo;
            in.close();
            return model;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}