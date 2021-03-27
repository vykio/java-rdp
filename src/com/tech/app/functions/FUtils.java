package com.tech.app.functions;

import java.util.Locale;

public final class FUtils {

    public static class OS {

        public static String getOs() {
            return System.getProperty("os.name");
        }

        public static boolean isMacOs() {
            return (getOs().toLowerCase(Locale.ROOT).contains("mac"));
        }

    }

    public static class Screen {

        public static double getScaleFactor() {
            double hundredPercent = 96.0;
            double res = java.awt.Toolkit.getDefaultToolkit().getScreenResolution() / hundredPercent;
            if (OS.isMacOs()) {
                return 2*res;
            }
            return res;
        }

    }

}
