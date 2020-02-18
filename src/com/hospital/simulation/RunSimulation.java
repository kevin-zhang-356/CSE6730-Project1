package com.hospital.simulation;

import java.io.IOException;

public class RunSimulation {
    public static void main(String[] args) throws IOException {
        FileIO.readInput();
        Engine.runSim(500);
    }
}
