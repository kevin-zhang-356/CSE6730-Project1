package com.hospital.simulation;

import java.util.Random;

public class Helper {

    // Get exponential distribution pseudorandom number generator
    public static double randExp(double U) {
        Random rand = new Random();
        return Math.log(1.0 - rand.nextDouble()) * (-U);
    }
}
