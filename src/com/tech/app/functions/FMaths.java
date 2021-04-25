package com.tech.app.functions;

public class FMaths {

    /**
     * Arrondir le nombre "number" à la précision 1/précision
     * @param number
     * @param precision
     * @return
     */
    public static double round(double number, int precision) {
        double scale = Math.pow(10, precision);
        return Math.round(number * scale) / scale;
    }

}
