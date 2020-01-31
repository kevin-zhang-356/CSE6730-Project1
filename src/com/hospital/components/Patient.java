package com.hospital.components;

public class Patient {
    private int PID;
    private double admitTime;
    private int severity;

    public Patient (int PID, double admitTime) {
        this.PID = PID;
        this.admitTime = admitTime;
        this.severity = severityDetermination();
    }

    private int severityDetermination() {
        if ((int) (Math.random() * 101) > 90)
            return 2;
        else
            return 1;
    }

    public int getPID() {
        return PID;
    }

    public double getAdmitTime() {
        return admitTime;
    }

    public int getSeverity() {
        return severity;
    }
}
