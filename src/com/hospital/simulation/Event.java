package com.hospital.simulation;

import com.hospital.components.EmergencyDepartment;
import com.hospital.components.Exit;
import com.hospital.components.Generator;
import com.hospital.components.InpatientBed;
import com.hospital.components.OperatingRoom;

public class Event {
    private double timestamp;
    private EventData eventData;

    public Event(double timestamp, EventData eventData) {
        this.timestamp = timestamp;
        this.eventData = eventData;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public EventData getEventData() {
        return eventData;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }

    // Event handler method process events based on their types
    public void eventHandler(double currentTime, double U) {
        if (eventData.getEventType() == 1) {
            Generator.generatorEvent(eventData, currentTime, U);
        } else if (eventData.getEventType() == 2) {
            EmergencyDepartment.EDEvent(eventData, currentTime, U);
        } else if (eventData.getEventType() == 3) {
            InpatientBed.InpatientBedEvent(eventData, currentTime, U);
        } else if (eventData.getEventType() == 4) {
            /* To be implemented */
        } else if (eventData.getEventType() == 5) {
            Exit.exitEvent(eventData, currentTime);
        }
    }
}
