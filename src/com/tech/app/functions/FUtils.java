package com.tech.app.functions;

import java.util.Locale;

public final class FUtils {

    public static class OS {

        public static String getOs() {
            return System.getProperty("os.name");
        }

        public static boolean isMacOs() {
            return (getOs().toLowerCase(Locale.ROOT).contains("max"));
        }

    }



}
