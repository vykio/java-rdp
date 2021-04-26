package com.tech.app.functions;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Locale;

public final class FUtils {

    public static class OS {

        /**
         * @return Retourne le nom de l'OS
         */
        public static String getOs() {
            return System.getProperty("os.name");
        }

        /**
         *
         * @return True si nous sommes sur MacOS
         */
        public static boolean isMacOs() {
            return (getOs().toLowerCase(Locale.ROOT).contains("mac"));
        }

        /**
         *
         * @return True si nous sommes sur Linux
         */
        public static boolean isLinux() {
            return (getOs().toLowerCase(Locale.ROOT).contains("linux"));
        }

    }

    public static class Screen {

        /**
         *
         * @return True si le Mac a un écran Retina
         */
        public static boolean isMacRetinaDisplay() {
            final GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            final AffineTransform transform = gfxConfig.getDefaultTransform();
            return !transform.isIdentity();
        }

        /**
         *
         * @return Retourne le facteur d'agrandissement
         */
        public static double getScaleFactor() {
            double hundredPercent = 96.0;

            double hundredPercentLinux = 96.0;

            if (OS.isMacOs()) {
                if (isMacRetinaDisplay()) {
                    return 2.0;
                } else {
                    return 1.0;
                }
            } else if (OS.isLinux()) {
                return java.awt.Toolkit.getDefaultToolkit().getScreenResolution() / hundredPercentLinux;
            }
            return java.awt.Toolkit.getDefaultToolkit().getScreenResolution() / hundredPercent;
        }

    }

}
