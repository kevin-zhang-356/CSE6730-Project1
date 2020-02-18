package com.hospital.simulation;

import com.hospital.components.EmergencyDepartment;
import com.hospital.components.Generator;
import com.hospital.components.InpatientBed;
import com.hospital.components.OperatingRoom;

import java.io.*;

public class FileIO {
    private static String inputFileName = "input.txt";
    private static String outputFileName = "output.txt";

    public static void readInput () throws IOException {
        int[] parameters = new int[10];

        FileReader fr=new FileReader("input.txt");
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
}
