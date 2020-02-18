package com.hospital.components;

import com.hospital.simulation.Engine;
import com.hospital.simulation.EventData;
import com.hospital.simulation.Helper;

public class Generator {
    private static int patientNum = 0;
    private static double averageGenerateTime;
    private static int ratio;

    public static void setAverageGenerateTime(double averageGenerate) {
        averageGenerateTime = averageGenerate;
    }

    public static void setRatio(int ra) {
        ratio = ra;
    }

    public static void generatorEvent (EventData eventData, double currentTime) {
        Patient p = new Patient(patientNum, currentTime, ratio);
        eventData.setPatient(p);
        System.out.printf("This is a generator event to generate patient %d, current time: %f\n",
                patientNum, currentTime);
        patientNum++;

        // Schedule next generator event with random interarrival time based on an average time
        EventData nextEventData = new EventData();
        nextEventData.setEventType(1);  // 1 means generator event
        double ts = currentTime + Helper.randExp(averageGenerateTime);
        Engine.schedule(ts, nextEventData);

        // Determine urgent or non urgent patient
        if (p.getSeverity() == 1) {
            // Schedule next arrival event to Emergency Department
            eventData.setEventType(2);  // 2 means go to ED
            double arrivalTS = currentTime;
            Engine.schedule(arrivalTS, eventData);
            System.out.printf("This patient %d is nonurgent patient, go to ED. current time: %f\n",
                    eventData.getPatient().getPID(), currentTime);
        } else {
            // Schedule next arrival event to Operating Room
            eventData.setEventType(3);  // 3 means go to operating room
            double arrivalTS = currentTime;
            Engine.schedule(arrivalTS, eventData);
            System.out.printf("This patient %d is urgent patient, go to operating room. current time: %f\n",
                    eventData.getPatient().getPID(), currentTime);
        }
    }
}
