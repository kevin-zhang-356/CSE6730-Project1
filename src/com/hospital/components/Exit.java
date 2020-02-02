package com.hospital.components;

import com.hospital.simulation.EventData;

public class Exit {
    public static void exitEvent (EventData eventData, double currentTime) {
        System.out.printf("Processing patient exit: patient number: %d, exit time: %f\n",
                eventData.getPatient().getPID(), currentTime);

        /* Statistics to be calculated */
    }
}
