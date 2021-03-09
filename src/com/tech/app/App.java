/**
 * Application : Java 11
 * Point d'entrée de l'application : public static void main()
 */

package com.tech.app;

import com.tech.app.models.Arc;
import com.tech.app.models.Place;
import com.tech.app.models.System;
import com.tech.app.models.Transition;
import com.tech.app.windows.MainWindow;

import java.awt.*;

public class App implements Runnable {
    @Override
    public void run() {
        MainWindow mainWindow = new MainWindow(900,500);

        /* Instanciation du système */
        System system = new System();

        /* Définition des places et des transitions */
        Place p = new Place("P1", 0, 0, 1);
        Place p2 = new Place("P2", 0, 0, 0);
        Place p3 = new Place("P3", 0,0, 0);
        Transition t = new Transition("t1", 0, 0);
        Transition t2 = new Transition("t2", 0, 0);

        /* Ajout des places d'entrées et de sorties */
        t.addChildren(p);
        t.addParent(new Arc(p, 2));
        t.addParent(p3);
        t2.addChildren(p2);
        t2.addChildren(p3);
        t2.addParent(p);

        /* Ajout des places et des transitions au système */
        system.addPlace(p);
        system.addPlace(p2);
        system.addPlace(p3);
        system.addTransition(t);
        system.addTransition(t2);

        /* Experimentation */
        Place p4 = new Place("P4", 0, 0, 1);
        system.addPlace(p4);
        Transition t4 = new Transition("t4", 0, 0);
        t4.addChildren(new Arc(p, 3));
        t4.addParent(p4);
        system.addTransition(t4);

        // Doit retourner une petite erreur car "p4" != "P4"
        system.removePlace("p4");

        /* Affichage du système */
        java.lang.System.out.println(system);

    }


    public synchronized void start() {
        new Thread(this).start();
    }

    public static void main(String[] args) {new App().start();}
}
