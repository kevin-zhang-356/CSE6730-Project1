package com.hospital.simulation;

import java.io.IOException;

public class RunSimulation {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            FileIO.readInput();
            Engine.runSim(5000);
            FileIO.writeOutput();
        } else if (args.length == 3) {
            FileIO.setInputFileName(args[0]);
            FileIO.setOutputFileName(args[2]);
            FileIO.readInput();
            Engine.runSim(Integer.parseInt(args[1]));
            FileIO.writeOutput();
        } else {
            System.out.println("Incorrect number of input parameter, please check!\n");
        }
    }
}
