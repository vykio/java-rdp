package com.tech.app.functions;

import com.tech.app.models.Arc;
import com.tech.app.models.Place;

import java.util.List;

public class FList {

    /**
     * True si la liste d'arcs contient la place en deuxième argument
     * @param arcs : Liste des arcs
     * @param place : Place à checker
     * @return : True ou False
     */
    public static boolean contains(List<Arc> arcs, Place place) {
        for (Arc arc : arcs) {
            if (arc.getPlace() == place) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne le poids de l'arc qui contient la place en deuxième argument
     * @param arcs Liste des arcs
     * @param place Place à checker
     * @return Poids de l'arc
     */
    public static int poids_arc(List<Arc> arcs, Place place) {
        for (Arc arc : arcs) {
            if (arc.getPlace() == place) {
                return arc.getPoids();
            }
        }
        return -1;
    }

    /**
     * Récupérer l'objet Place de nom :name: s'il est contenue dans la liste places
     * @param places : Liste des places où l'on doit chercher
     * @param name : Nom de la place que l'on cherche
     * @return : Objet de type place, et de nom :name:
     */
    public static Place getPlaceByName(List<Place> places, String name) {
        for (Place place : places) {
            if (place.getName().equals(name)) {
                return place;
            }
        }
        return null;
    }

}
