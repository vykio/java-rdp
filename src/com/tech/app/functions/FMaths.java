package com.tech.app.functions;

/** Classe avec des fonctions mathématiques */
public class FMaths {

    /**
     * Arrondir le nombre "number" à la précision 1/précision
     * @param number Nombre
     * @param precision Précision inverse
     * @return Nombre à la précision 1/precision
     */
    public static double round(double number, int precision) {
        double scale = Math.pow(10, precision);
        return Math.round(number * scale) / scale;
    }

}
