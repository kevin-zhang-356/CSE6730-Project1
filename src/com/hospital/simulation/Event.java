package com.hospital.simulation;

import com.hospital.components.Exit;
import com.hospital.components.Generator;

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

    public void eventHandler(double currentTime, double U) {
        if (eventData.getEventType() == 1) {
            Generator.generatorEvent(eventData, currentTime, U);
        } else if (eventData.getEventType() == 4) {
            Exit.exitEvent(eventData, currentTime);
        }
    }
}
