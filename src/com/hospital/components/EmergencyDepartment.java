package com.hospital.components;

import com.hospital.simulation.Engine;
import com.hospital.simulation.EventData;
import com.hospital.simulation.Helper;

import java.util.LinkedList;
import java.util.Queue;

public class EmergencyDepartment {

    // Queue for Emergency Department waiting line。 To be implemented.
    private static Queue<Patient> EDQueue = new LinkedList<Patient>();
    private static int numOfDiagnosisRoom = 2;
    private static int ratio = 90;
    private static int currentPatient = 0;

//    public EmergencyDepartment(int numOfDiagnosisRoom, int ratio) {
//        this.numOfDiagnosisRoom = numOfDiagnosisRoom;
//        this.ratio = ratio;
//    }

    private static int surgeryDetermination() {
        if ((int) (Math.random() * 101) > ratio)
            return 2;   // 2 means to exit
        else
            return 1;   // 1 means go to inpatient bed
    }

    public static void EDEvent (EventData eventData, double currentTime, double averageDiagnosisTime) {
        System.out.printf("Patient %d has arrived into Emergency Department, current time: %f\n",
                eventData.getPatient().getPID(), currentTime);

        if (currentPatient >= numOfDiagnosisRoom) {
            System.out.printf("Diagnosis room is currently full. Patient %d added to queue, current time: %f\n",
                    eventData.getPatient().getPID(), currentTime);
            EDQueue.add(eventData.getPatient());
        } else {
            System.out.printf("Diagnosis room is available. Patient %d enters diagnosis room, current time: %f\n",
                    eventData.getPatient().getPID(), currentTime);
            currentPatient++;
            // Schedule next arrival event to diagnosis room
            eventData.setEventType(4);  // 4: diagnosis room exit
            double arrivalTS = currentTime + Helper.randExp(averageDiagnosisTime);
            Engine.schedule(arrivalTS, eventData);
        }
    }

    public static void diagnosisExitEvent (EventData eventData, double currentTime, double averageDiagnosisTime) {
        System.out.printf("Patient %d has been released from diagnosis room, current time: %f\n",
                eventData.getPatient().getPID(), currentTime);

        currentPatient--;

        if (surgeryDetermination() == 1) {
            // Schedule next arrival event to inpatient bed
            eventData.setEventType(5);  // 5 : inpatient bed
            double arrivalTS = currentTime;
            Engine.schedule(arrivalTS, eventData);
        } else {
            // Schedule next arrival event to exit
            eventData.setEventType(8);  // 8 : exit
            double arrivalTS = currentTime;
            Engine.schedule(arrivalTS, eventData);
        }

        if (!EDQueue.isEmpty()) {
            Patient p = EDQueue.poll();
            EventData nextEventData = new EventData();
            nextEventData.setEventType(4);  // 4 means generator event
            nextEventData.setPatient(p);
            currentPatient++;
            double ts = currentTime + Helper.randExp(averageDiagnosisTime);
            Engine.schedule(ts, nextEventData);
        }
    }
}
