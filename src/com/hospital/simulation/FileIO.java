package com.hospital.simulation;

import com.hospital.components.*;

import java.io.*;

public class FileIO {
    private static String inputFileName = "input.txt";
    private static String outputFileName = "output.txt";

    public static void setInputFileName(String input) {
        inputFileName = input;
    }

    public static void setOutputFileName(String output) {
        outputFileName = output;
    }

    public static void readInput() throws IOException {
        int[] parameters = new int[10];

        FileReader fr=new FileReader(inputFileName);
        BufferedReader br=new BufferedReader(fr);

        int i = 0;
        while((br.read()) != -1){
            parameters[i] = Integer.valueOf(br.readLine().split(": ")[1]);
            i++;
        }
        br.close();
        fr.close();

        Generator.setAverageGenerateTime(1.0 / parameters[0]);
        Generator.setRatio(parameters[1]);
        EmergencyDepartment.setRatio(parameters[2]);
        OperatingRoom.setNumOfRoom(parameters[3]);
        OperatingRoom.setAverageOperationTime(parameters[4]);
        InpatientBed.setNumOfBed(parameters[5]);
        InpatientBed.setAveragePrepTime(parameters[6]);
        InpatientBed.setRecoveryTime(parameters[7]);
        EmergencyDepartment.setNumOfDiagnosisRoom(parameters[8]);
        EmergencyDepartment.setAverageDiagnosisTime(parameters[9]);
    }

    public static void writeOutput() throws IOException {
        FileWriter fr = new FileWriter(outputFileName);
        PrintWriter br = new PrintWriter(fr);

        br.printf("For total system remain time, Max: %f, Min: %f, Average: %f\n", Exit.getMaxSystemTime(),
                Exit.getMinSystemTime(), Exit.getAverageSystemTime());
        br.printf("For surgery needed patient system remain time, Max: %f, Min: %f, Average: %f\n",
                Exit.getMaxSurgeryNeededSystemTime(),
                Exit.getMinSurgeryNeededSystemTime(), Exit.getAverageSurgeryNeededSystemTime());
        br.printf("For surgery not needed patient system remain time, Max: %f, Min: %f, Average: %f\n",
                Exit.getMaxSurgeryNotNeededSystemTime(),
                Exit.getMinSurgeryNotNeededSystemTime(), Exit.getAverageSurgeryNotNeededSystemTime());
        br.printf("For diagnosis room queue wait time, Max: %f, Min: %f, Average: %f\n",
                Exit.getMaxDiagnosisRoomWaitTime(),
                Exit.getMinDiagnosisRoomWaitTime(), Exit.getAverageDiagnosisRoomWaitTime());
        br.printf("For inpatient bed wait time, Max: %f, Min: %f, Average: %f\n", Exit.getMaxBedWaitTime(),
                Exit.getMinBedWaitTime(), Exit.getAverageBedWaitTime());
        br.printf("For surgery wait time, Max: %f, Min: %f, Average: %f\n", Exit.getMaxSurgeryWaitTime(),
                Exit.getMinSurgeryWaitTime(), Exit.getAverageSurgeryWaitTime());
        br.printf("Percentage of patient transferred: %f", Exit.getPercentageOfPatientTransferred());

        br.close();
    }
}
