/**
 * Application : Java 11
 * Point d'entrée de l'application : public static void main()
 */

package com.tech.app;

import com.tech.app.maths.Matrix;
import com.tech.app.models.v4.System;
import com.tech.app.windows.MainWindow;
import com.tech.app.models.v4.*;

public class App implements Runnable {
    @Override
    public void run() {
        MainWindow mainWindow = new MainWindow(500,500);

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
        t.addParent(p2);
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

        /* Mettre à jour la taille des matrices */
        system.initialize();

        /* Générer les matrices M0, W+, W- et C en fonction des places et des transitions */
        system.generate_M0();
        system.generate_W();
        system.generate_C();

        /* Affichage du système */
        java.lang.System.out.println(system);


    }

    public synchronized void start() {
        new Thread(this).start();
    }

    public static void main(String[] args) {
        new App().start();
    }
}
