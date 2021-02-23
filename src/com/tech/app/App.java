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

        System system = new System(3,2);
        java.lang.System.out.println(system);

        system.testC();

    }

    public synchronized void start() {
        new Thread(this).start();
    }

    public static void main(String[] args) {
        new App().start();
    }
}
