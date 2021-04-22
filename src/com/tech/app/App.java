
/**
 *<b> Implémentation d'un simulateur de réseaux de Pétri en Java <\b>
 *<p> Dans le cadre du projet technologique intervenant en 2ème année de cycle d'ingénieur,
 * nous avons développé un outil permetant de faire l'analyse de réseau de Pétri</p>
 *
 * @author  Gauthier Leurette, Alexandre Vasseur, Théo Plouvin
 * @version 1.0
 * @since   2021-02-01
 * Application : Java 11
 * Point d'entrée de l'application : public static void main()
 */


package com.tech.app;

import com.tech.app.functions.CheckThreadViolationRepaintManager;
import com.tech.app.models.Arc;
import com.tech.app.models.Place;
import com.tech.app.models.Model;
import com.tech.app.models.Transition;
import com.tech.app.windows.MainWindow;

import javax.swing.*;

public class App implements Runnable {
    @Override
    public void run() {
        try {
            MainWindow mainWindow = new MainWindow(900,500);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        /* Instanciation du système */
        /*Model model = new Model();*/

        /* Définition des places et des transitions */
        /*Place p = new Place("P1", 0, 0, 1);
        Place p2 = new Place("P2", 0, 0, 0);
        Place p3 = new Place("P3", 0,0, 0);
        Transition t = new Transition("t1", 0, 0);
        Transition t2 = new Transition("t2", 0, 0);
*/
        /* Ajout des places d'entrées et de sorties */
        /*t.addChildren(p);
        t.addParent(new Arc(p, 2));
        t.addParent(p3);
        t2.addChildren(p2);
        t2.addChildren(p3);
        t2.addParent(p);*/

        /* Ajout des places et des transitions au système */
       /* model.addPlace(p);
        model.addPlace(p2);
        model.addPlace(p3);
        model.addTransition(t);
        model.addTransition(t2);*/

        /* Experimentation */
        //Place p4 = new Place("P4", 0, 0, 1);
        //model.addPlace(p4);
        //Transition t4 = new Transition("t4", 0, 0);
       // t4.addChildren(new Arc(p, 3));
        //t4.addParent(p4);
       // model.addTransition(t4);

        // Doit retourner une petite erreur car "p4" != "P4"
       // model.removePlace("p4");

        /* Affichage du système */
        //java.lang.System.out.println(model);

    }


    public synchronized void start() {
        SwingUtilities.invokeLater(this);
        RepaintManager.setCurrentManager(new CheckThreadViolationRepaintManager());
    }

    public static void main(String[] args) {new App().start();}
}
