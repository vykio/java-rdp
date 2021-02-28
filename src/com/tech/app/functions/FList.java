package com.tech.app.functions;

import com.tech.app.models.Arc;
import com.tech.app.models.Place;

import java.util.List;

public class FList {

    public static boolean contains(List<Arc> arcs, Place place) {
        for (int i = 0; i < arcs.size(); i++) {
            if (arcs.get(i).getPlace() == place) {
                return true;
            }
        }
        return false;
    }

    public static int poids_arc(List<Arc> arcs, Place place) {
        for (int i = 0; i < arcs.size(); i++) {
            if (arcs.get(i).getPlace() == place) {
                return arcs.get(i).getPoids();
            }
        }
        return -1;
    }

}
