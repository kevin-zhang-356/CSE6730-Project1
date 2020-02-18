package com.hospital.components;

import com.hospital.simulation.Engine;
import com.hospital.simulation.EventData;
import com.hospital.simulation.Helper;

import java.util.LinkedList;
import java.util.Queue;

public class InpatientBed {

    private static Queue<Patient> BedQueue = new LinkedList<Patient>();
    private static int numOfBed;
    private static int occupiedBed = 0;
    private static double averagePrepTime;
    private static double recoveryTime;

    public static boolean hasBed () {
        return occupiedBed < numOfBed;
    }

    public static double getRecoveryTime() {
        return recoveryTime;
    }

    public static void setNumOfBed(int num) {
        numOfBed = num;
    }

    public static void setAveragePrepTime(int time) {
        averagePrepTime = time;
    }

    public static void setRecoveryTime(int time) {
        recoveryTime = time;
    }

    public static void setIncreaseBed() {
        occupiedBed++;
    }

    public static void setDecreaseBed() {
        occupiedBed--;
    }

    public static void informQueue (double currentTime) {
        if (!BedQueue.isEmpty()) {
            Patient p = BedQueue.poll();
            EventData nextEventData = new EventData();
            nextEventData.setEventType(5);
            nextEventData.setPatient(p);
            double ts = currentTime;
            Engine.schedule(ts, nextEventData);
            System.out.printf("Release the first patient in Bedqueue, patient %d, queue size: %d current time: %f\n",
                    p.getPID(),
                    BedQueue.size(),
                    currentTime);
        }
    }

    public static void InpatientBedEvent (EventData eventData, double currentTime) {

        if (occupiedBed >= numOfBed) {
            System.out.printf("Inpatient bed is currently full. Patient %d added to queue, current time: %f\n",
                    eventData.getPatient().getPID(), currentTime);
            BedQueue.add(eventData.getPatient());
        } else {
            System.out.printf("Inpatient Bed is available. Patient %d admitted, current time: %f\n",
                    eventData.getPatient().getPID(), currentTime);
            occupiedBed++;
            double[] startEndTime = OperatingRoom.findAvailableSurgeryTime(currentTime + averagePrepTime,
                    eventData);

            eventData.getPatient().setSurgeryDuration(startEndTime[1] - startEndTime[0]);

            // Schedule next arrival event to operating room
            eventData.setEventType(3);
            double arrivalTS = startEndTime[0];
            Engine.schedule(arrivalTS, eventData);

            // Schedule next arrival event to Inpatient Bed exit
            EventData currentEventData = new EventData();
            currentEventData.setEventType(6);
            currentEventData.setPatient(eventData.getPatient());
            double arrivalExit = startEndTime[1] + recoveryTime;
            Engine.schedule(arrivalExit, currentEventData);
        }

    }

    public static void InpatientBedExitEvent (EventData eventData, double currentTime) {

        occupiedBed--;
        informQueue(currentTime);

        // Schedule next arrival event to exit
        eventData.setEventType(8);
        double arrivalExit = currentTime;
        Engine.schedule(arrivalExit, eventData);
    }
}
