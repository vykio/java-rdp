package com.tech.app.functions;

public class FMaths {

    public static double round(double number, int precision) {
        double scale = Math.pow(10, precision);
        return Math.round(number * scale) / scale;
    }

}
