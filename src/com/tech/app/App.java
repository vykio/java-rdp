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

        System system = new System();

        Place p = new Place("P1", 0, 0, 1);
        Place p2 = new Place("P2", 0, 0, 2);
        Transition t = new Transition("t1", 0, 0);
        Transition t2 = new Transition("t2", 0, 0);
        t.addParent(p);
        t.addChildren(p2);
        t2.addChildren(p);

        system.addPlace(p);
        system.addPlace(p2);
        system.addTransition(t);
        system.addTransition(t2);

        system.update();
        system.generate_M0();

        system.generate_W_plus();
        system.print_W_plus();
        system.print_W_moins();

        system.generate_W_moins();
        system.print_W_plus();
        system.print_W_moins();
        //java.lang.System.out.println(system);


    }

    public synchronized void start() {
        new Thread(this).start();
    }

    public static void main(String[] args) {
        new App().start();
    }
}
