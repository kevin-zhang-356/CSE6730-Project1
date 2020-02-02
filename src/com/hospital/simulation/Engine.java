package com.hospital.simulation;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Engine {
    // Future event list with modified comparator based on timestamp
    private static PriorityQueue<Event> fel = new PriorityQueue<Event>(new Comparator<Event>(){
        @Override
        public int compare(Event a, Event b) {
            if ((a.getTimestamp() - b.getTimestamp()) < 0)
                return -1;
            else if ((a.getTimestamp() - b.getTimestamp()) > 0)
                return 1;
            else
                return 0;
        }
    });

    // Current time variable
    private static double now = 0.0;

    // Remove function for fel
    public static Event remove() {
        return fel.poll();
    }

    // Print event list for debug
    public static void printList() {
        System.out.println("Event list:");
    }

    // Return current time
    public static double currentTime() {
        return now;
    }

    // Schedule event
    public static void schedule(double timestamp, EventData eventData) {
        Event e = new Event(timestamp, eventData);
        fel.add(e);
    }

    // Run simulation method
    public static void runSim(double endTime, double U) {
        // Generate first event for patient
        EventData eventData = new EventData();
        eventData.setEventType(1);
        Event first = new Event(0, eventData);
        printList();
        fel.add(first);

        // Run simulation based on event list
        Event e;
        while ((e = fel.poll()) != null) {
            now = e.getTimestamp();
            if (now > endTime) {
                System.out.println("The simulation has ended!");
                break;
            } else {
                e.eventHandler(now, U);
            }
        }
    }
}
