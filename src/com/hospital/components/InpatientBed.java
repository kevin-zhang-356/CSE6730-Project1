package com.hospital.components;

import com.hospital.simulation.Engine;
import com.hospital.simulation.EventData;
import com.hospital.simulation.Helper;

public class InpatientBed {

    public static void InpatientBedEvent (EventData eventData, double currentTime, double U) {
        System.out.printf("Patient %d has been admitted into the hospital beds, current time: %f\n",
                eventData.getPatient().getPID(), currentTime);

        // Schedule next arrival event to exit
        eventData.setEventType(5);
        double arrivalTS = currentTime + Helper.randExp(U);
        Engine.schedule(arrivalTS, eventData);
    }
}
