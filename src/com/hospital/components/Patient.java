package com.hospital.components;

public class Patient {
    private int PID;
    private double admitTime;
    private int severity;
    private int surgeryNeeded;
    private double surgeryDuration;
    private int operatingRoomNum;
    private double enterSystemTime;
    private double exitSystemTime;
    private double enterDQueueTime;
    private double exitDQueueTime;
    private double enterBQueueTime;
    private double exitBQueueTime;
    private double enterInpatientBedTime;
    private double surgeryStartTime;

    public Patient (int PID, double admitTime, int ratio) {
        this.PID = PID;
        this.admitTime = admitTime;
        this.severity = severityDetermination(ratio);
        this.surgeryNeeded = -1;
        this.operatingRoomNum = -1;
        this.enterSystemTime = admitTime;
        this.exitSystemTime = -1;
        this.enterDQueueTime = -1;
        this.exitDQueueTime = -1;
        this.enterBQueueTime = -1;
        this.exitBQueueTime = -1;
        this.enterInpatientBedTime = -1;
        this.surgeryStartTime = -1;
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

    public int getSurgeryNeeded() {
        return surgeryNeeded;
    }

    public int getOperatingRoomNum() {
        return operatingRoomNum;
    }

    public double getSurgeryDuration() {
        return surgeryDuration;
    }

    public double getEnterSystemTime() {
        return enterSystemTime;
    }

    public double getExitSystemTime() {
        return exitSystemTime;
    }

    public double getEnterDQueueTime() {
        return enterDQueueTime;
    }

    public double getExitDQueueTime() {
        return exitDQueueTime;
    }

    public double getEnterBQueueTime() {
        return enterBQueueTime;
    }

    public double getExitBQueueTime() {
        return exitBQueueTime;
    }

    public double getEnterInpatientBedTime() {
        return enterInpatientBedTime;
    }

    public double getSurgeryStartTime() {
        return surgeryStartTime;
    }

    public void setSurgeryNeeded(int surgeryNeeded) {
        this.surgeryNeeded = surgeryNeeded;
    }

    public void setEnterSystemTime(double enterSystemTime) {
        this.enterSystemTime = enterSystemTime;
    }

    public void setExitSystemTime(double exitSystemTime) {
        this.exitSystemTime = exitSystemTime;
    }

    public void setEnterDQueueTime(double enterDQueueTime) {
        this.enterDQueueTime = enterDQueueTime;
    }

    public void setExitDQueueTime(double exitDQueueTime) {
        this.exitDQueueTime =  exitDQueueTime;
    }

    public void setEnterBQueueTime(double enterBQueueTime) {
        this.enterBQueueTime = enterBQueueTime;
    }

    public void setExitBQueueTime(double exitBQueueTime) {
        this.exitBQueueTime = exitBQueueTime;
    }

    public void setEnterInpatientBedTime(double enterInpatientBedTime) {
        this.enterInpatientBedTime = enterInpatientBedTime;
    }

    public void setSurgeryStartTime(double surgeryStartTime) {
        this.surgeryStartTime = surgeryStartTime;
    }

    public void setOperatingRoomNum(int operatingRoomNum) {
        this.operatingRoomNum = operatingRoomNum;
    }

    public void setSurgeryDuration(double surgeryDuration) {
        this.surgeryDuration = surgeryDuration;
    }

}
