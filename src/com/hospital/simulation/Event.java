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
    public void eventHandler(double currentTime) {
        int a = 1;
        if (eventData.getEventType() == 1) {
            Generator.generatorEvent(eventData, currentTime);
        } else if (eventData.getEventType() == 2) {
            EmergencyDepartment.EDEvent(eventData, currentTime);
        } else if (eventData.getEventType() == 3) {
            OperatingRoom.OREvent(eventData, currentTime);
        } else if (eventData.getEventType() == 4) {
            EmergencyDepartment.diagnosisExitEvent(eventData, currentTime);
        } else if (eventData.getEventType() == 5) {
            InpatientBed.InpatientBedEvent(eventData, currentTime);
        } else if (eventData.getEventType() == 6) {
            InpatientBed.InpatientBedExitEvent(eventData, currentTime);
        } else if (eventData.getEventType() == 7) {
            OperatingRoom.urgentPostSurgeryEvent(eventData, currentTime);
        } else if (eventData.getEventType() == 8) {
            Exit.exitEvent(eventData, currentTime);
        } else if (eventData.getEventType() == 9) {
            OperatingRoom.urgentPatientDischarge(eventData, currentTime);
        } else if (eventData.getEventType() == 10) {
            OperatingRoom.normalPostSurgeryEvent(eventData, currentTime);
        }
    }
}
