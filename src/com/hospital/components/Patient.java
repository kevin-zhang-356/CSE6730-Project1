package com.hospital.components;

public class Patient {
    private int PID;
    private double admitTime;
    private int severity;
    private int operatingRoomNum = -1;
    private double surgeryDuration;

    public Patient (int PID, double admitTime, int ratio) {
        this.PID = PID;
        this.admitTime = admitTime;
        this.severity = severityDetermination(ratio);
    }

    private int severityDetermination(int ratio) {
        if ((int) (Math.random() * 101) > ratio)
            return 2;   // 2 means go to urgent surgery
        else
            return 1;   // 1 means go to ED
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

    public int getOperatingRoomNum() {
        return operatingRoomNum;
    }

    public double getSurgeryDuration() {
        return surgeryDuration;
    }

    public void setOperatingRoomNum(int operatingRoomNum) {
        this.operatingRoomNum = operatingRoomNum;
    }

    public void setSurgeryDuration(double surgeryDuration) {
        this.surgeryDuration = surgeryDuration;
    }

}
