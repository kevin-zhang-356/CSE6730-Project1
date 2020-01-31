package com.hospital.components;

import com.hospital.simulation.Engine;
import com.hospital.simulation.EventData;
import com.hospital.simulation.Helper;

public class Generator {
    private static int patientNum = 0;

    public static void generatorEvent (EventData eventData, double currentTime, double U) {
        Patient p = new Patient(patientNum, currentTime);
        eventData.setPatient(p);
        System.out.printf("This is a generator event to generate patient %d, current time: %f\n", patientNum, currentTime);
        patientNum++;

        // Schedule next generator event with random interarrival time based on an average time
        EventData nextEventData = new EventData();
        nextEventData.setEventType(1);
        double ts = currentTime + Helper.randExp(U);
        Engine.schedule(ts, nextEventData);
    }
}
