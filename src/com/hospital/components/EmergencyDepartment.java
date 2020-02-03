package com.hospital.components;

import com.hospital.simulation.Engine;
import com.hospital.simulation.EventData;
import com.hospital.simulation.Helper;

import java.util.ArrayList;
import java.util.List;

public class EmergencyDepartment {

    // Queue for Emergency Department waiting line。 To be implemented.
    private static List<Patient> EDQueue = new ArrayList<Patient>();

    public static void EDEvent (EventData eventData, double currentTime, double U) {
        System.out.printf("Patient %d has been accepted into Emergency Department, current time: %f\n",
                eventData.getPatient().getPID(), currentTime);

        // Schedule next arrival event to inpatient bed
        eventData.setEventType(3);
        double arrivalTS = currentTime + Helper.randExp(U);
        Engine.schedule(arrivalTS, eventData);
    }
}
