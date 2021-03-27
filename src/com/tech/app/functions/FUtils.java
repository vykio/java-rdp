package com.tech.app.functions;

import java.awt.*;
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

        public static boolean hasRetinaDisplay() {
            Object obj = Toolkit.getDefaultToolkit()
                    .getDesktopProperty(
                            "apple.awt.contentScaleFactor");
            if (obj instanceof Float) {
                Float f = (Float) obj;
                int scale = f.intValue();
                return (scale == 2); // 1 indicates a regular mac display.
            }
            return false;
        }

        public static double getScaleFactor() {
            double hundredPercent = 96.0;
            if (OS.isMacOs()) {
                if (hasRetinaDisplay()) {
                    return 2.0;
                } else {
                    return 1.0;
                }
            }
            return java.awt.Toolkit.getDefaultToolkit().getScreenResolution() / hundredPercent;
        }

    }

}
