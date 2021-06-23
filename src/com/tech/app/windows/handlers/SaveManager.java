package com.tech.app.windows.handlers;

import com.tech.app.models.Arc;
import com.tech.app.models.Model;
import com.tech.app.models.Place;
import com.tech.app.models.Transition;
import org.jgrapht.util.ArrayUtil;

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

        /*
        try {
            FileOutputStream fileOut = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(model);
            out.close();
            fileOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        */


        try {
            /*
            FileOutputStream fileOut = new FileOutputStream(f);
            DataOutputStream out = new DataOutputStream(fileOut);

             */
            FileWriter out = new FileWriter(f);

            //Ecriture des places
            for(int i = 0; i < model.placeVector.size();i++){
                // name x y marquage
                if(model.placeVector.get(i).getLabel().equals("")){
                    out.write(model.placeVector.get(i).getName()+" "+ (int) model.placeVector.get(i).getX()+" "+ (int) model.placeVector.get(i).getY()+" "+model.placeVector.get(i).getMarquage()+"\n");
                } else {
                    out.write(model.placeVector.get(i).getName() + " " + (int) model.placeVector.get(i).getX() + " " + (int) model.placeVector.get(i).getY() + " " + model.placeVector.get(i).getMarquage() + " " + model.placeVector.get(i).getLabel() + " " + model.placeVector.get(i).getPosition() + "\n");
                }
            }

            //Ecriture des transitions et des arcs
            for(int i = 0; i < model.transitionVector.size();i++){
                // name x y c[a(name_place, poids),...] p[a(name_place, poids),...]

                if(model.transitionVector.get(i).getLabel().equals("")){
                    out.write(model.transitionVector.get(i).getName()+" "+ (int) model.transitionVector.get(i).getX()+" "+ (int) model.transitionVector.get(i).getY()+" ");
                }else {
                    out.write(model.transitionVector.get(i).getName() + " " + (int) model.transitionVector.get(i).getX() + " " + (int) model.transitionVector.get(i).getY() + " " + model.transitionVector.get(i).getLabel() + " " + model.transitionVector.get(i).getPosition() + " ");
                }

                if(!model.transitionVector.get(i).getChildren().isEmpty()){
                    out.write("c ");
                    //ecriture des enfants
                    for(int j=0; j<model.transitionVector.get(i).getChildren().size(); j++){
                        out.write("a "+model.transitionVector.get(i).getChildren().get(j).getPlace().getName()+" "+ model.transitionVector.get(i).getChildren().get(j).getPoids()+" ");
                    }
                }

                if(!model.transitionVector.get(i).getParents().isEmpty()){
                    out.write(" p ");
                    //ecriture des parents
                    for(int j=0; j<model.transitionVector.get(i).getParents().size(); j++){
                        out.write("a "+model.transitionVector.get(i).getParents().get(j).getPlace().getName()+" "+ model.transitionVector.get(i).getParents().get(j).getPoids()+" ");
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
        return placeVector.stream().filter(place -> name.equals(place.getName())).findFirst().orElse(null);
}

public static Transition findTransitionByName(List<Transition> transitionVector, String name){
    return transitionVector.stream().filter(transition -> name.equals(transition.getName())).findFirst().orElse(null);
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
        /*
        try {
            FileInputStream fileIn = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mo = (Model) in.readObject();
            model = mo;
            in.close();
            fileIn.close();
            return model;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return null;
        }

         */

        try {

            FileReader in = new FileReader(f);

            //Lecture ligne par ligne.
            try{
                List<String> allLines = Files.readAllLines(Path.of(f.getAbsolutePath()));

                for(String line : allLines){
                    System.out.println(line);

                    String[] words = line.split("\\s+");

                    if(line.startsWith("P")){
                        if(words.length == 6){
                            mo.addPlace(new Place(words[0], Double.parseDouble(words[1]),Double.parseDouble(words[2]), Integer.parseInt(words[3]), words[4], Integer.parseInt(words[5])));
                        } else {
                            mo.addPlace(new Place(words[0], Double.parseDouble(words[1]),Double.parseDouble(words[2]), Integer.parseInt(words[3])));
                        }
                    }

                    if(line.startsWith("t")){
                        Transition t;

                        List<String> l = Arrays.asList(words);
                        int indexofC = l.indexOf("c");

                        if(indexofC == 5) {
                            t =  new Transition(words[0], Double.parseDouble(words[1]),Double.parseDouble(words[2]), words[3], Integer.parseInt(words[4]));
                        } else {
                            t =  new Transition(words[0], Double.parseDouble(words[1]),Double.parseDouble(words[2]));
                        }

                        mo.addTransition(t);

                        for(int j = indexofC; j < words.length;){

                            boolean actionPerformed = false;

                            if(!actionPerformed && childrenSection && words[j].equals("a")){
                                Place p = findPlaceByName(mo.placeVector, words[j+1]);
                                t.addChildren(new Arc(p,Integer.parseInt(words[j+2]),t.getX(), t.getY(), false, t));
                                actionPerformed = true;
                                j=j+3;
                            }

                            if(!actionPerformed && parentSection && words[j].equals("a")){
                                Place p = findPlaceByName(mo.placeVector, words[j+1]);
                                t.addParent(new Arc(p,Integer.parseInt(words[j+2]),t.getX(), t.getY(), true, t));
                                actionPerformed = true;
                                j=j+3;
                            }

                            // Si il ya un arc enfant
                            if(!actionPerformed && words[j].equals("c") && words[j+1].equals("a")){
                                childrenSection = true;
                                parentSection = false;
                                Place p = findPlaceByName(mo.placeVector, words[j+2]);
                                t.addChildren(new Arc(p,Integer.parseInt(words[j+3]),t.getX(), t.getY(),false, t));
                                actionPerformed = true;
                                j=j+4;
                            }

                            // Si il y a un arc parent
                            if(!actionPerformed && words[j].equals("p") && words[j+1].equals("a")){
                                childrenSection = false;
                                parentSection = true;
                                Place p = findPlaceByName(mo.placeVector, words[j+2]);
                                t.addParent(new Arc(p,Integer.parseInt(words[j+3]),t.getX(), t.getY(),true, t));
                                actionPerformed = true;
                                j=j+4;
                            }
                        }
                    }
                    System.out.println(Arrays.toString(words));
                    System.out.println(mo);
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

