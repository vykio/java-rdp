package com.tech.app.functions;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Locale;

public final class FUtils {

    public static class OS {

        public static String getOs() {
            return System.getProperty("os.name");
        }

        public static boolean isMacOs() {
            return (getOs().toLowerCase(Locale.ROOT).contains("mac"));
        }

        public static boolean isLinux() {
            return (getOs().toLowerCase(Locale.ROOT).contains("linux"));
        }

    }

    public static class Screen {

        public static boolean isMacRetinaDisplay() {
            final GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            final AffineTransform transform = gfxConfig.getDefaultTransform();
            return !transform.isIdentity();
        }

        public static double getScaleFactor() {
            double hundredPercent = 96.0;

            double hundredPercentLinux = 90.0;

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
