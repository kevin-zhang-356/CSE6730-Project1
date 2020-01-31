package com.hospital.simulation;

import com.hospital.components.Patient;

public class EventData {
    private int eventType;
    private Patient p;
    private int componentId;

    public int getEventType() {
        return eventType;
    }

    public Patient getPatient() {
        return p;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public void setPatient(Patient p) {
        this.p = p;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

}
