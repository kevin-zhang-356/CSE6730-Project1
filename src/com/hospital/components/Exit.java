package com.hospital.components;

import com.hospital.simulation.EventData;

public class Exit {

    private static double maxSystemTime = Double.MIN_VALUE;
    private static double minSystemTime = Double.MAX_VALUE;
    private static double sumSystemTime = 0;
    private static int numOfPatientExit = 0;
    private static int numOfPatientTransferred = 0;

    private static double maxDiagnosisRoomWaitTime = Double.MIN_VALUE;
    private static double minDiagnosisRoomWaitTime = Double.MAX_VALUE;
    private static double sumDiagnosisRoomWaitTime = 0;
    private static double numOfNonurgentPatient = 0;

    private static double maxBedWaitTime = Double.MIN_VALUE;
    private static double minBedWaitTime = Double.MAX_VALUE;
    private static double sumBedWaitTime = 0;
    private static double numOfSurgeryNeededPatient = 0;

    private static double maxSurgeryWaitTime = Double.MIN_VALUE;
    private static double minSurgeryWaitTime = Double.MAX_VALUE;
    private static double sumSurgeryWaitTime = 0;

    public static double getMaxSystemTime() {
        return maxSystemTime;
    }

    public static double getMinSystemTime() {
        return minSystemTime;
    }

    public static double getAverageSystemTime() {
        return sumSystemTime / numOfPatientExit;
    }

    public static double getMaxDiagnosisRoomWaitTime() {
        return maxDiagnosisRoomWaitTime;
    }

    public static double getMinDiagnosisRoomWaitTime() {
        return minDiagnosisRoomWaitTime;
    }

    public static double getAverageDiagnosisRoomWaitTime() {
        return sumDiagnosisRoomWaitTime/ numOfNonurgentPatient;
    }

    public static double getMaxBedWaitTime() {
        return maxBedWaitTime;
    }

    public static double getMinBedWaitTime() {
        return minBedWaitTime;
    }

    public static double getAverageBedWaitTime() {
        return sumBedWaitTime / numOfSurgeryNeededPatient;
    }

    public static double getMaxSurgeryWaitTime() {
        return maxSurgeryWaitTime;
    }

    public static double getMinSurgeryWaitTime() {
        return minSurgeryWaitTime;
    }

    public static double getAverageSurgeryWaitTime() {
        return sumSurgeryWaitTime / numOfSurgeryNeededPatient;
    }

    public static double getPercentageOfPatientTransferred() {
        return 1.0 * numOfPatientTransferred / numOfPatientExit;
    }

    public static void exitEvent (EventData eventData, double currentTime) {
        System.out.printf("Processing patient exit: patient number: %d, exit time: %f\n",
                eventData.getPatient().getPID(), currentTime);

        Patient p = eventData.getPatient();
        p.setExitSystemTime(currentTime);

        // Remain in system time
        if (!(p.getSeverity() == 2 && p.getSurgeryStartTime() == -1)) {
            double remainSystemTime = p.getExitSystemTime() - p.getEnterSystemTime();
            maxSystemTime = Math.max(maxSystemTime, remainSystemTime);
            minSystemTime = Math.min(minSystemTime, remainSystemTime);
            sumSystemTime = sumSystemTime + remainSystemTime;
            numOfPatientExit = numOfPatientExit + 1;
        } else {
            numOfPatientTransferred = numOfPatientTransferred + 1;
        }

        // Wait time in diagnosis room queue
        if (p.getSeverity() == 1) {
            double diagnosisRoomWaitTime = p.getExitDQueueTime() - p.getEnterDQueueTime();
            maxDiagnosisRoomWaitTime = Math.max(maxDiagnosisRoomWaitTime, diagnosisRoomWaitTime);
            minDiagnosisRoomWaitTime = Math.min(minDiagnosisRoomWaitTime, diagnosisRoomWaitTime);
            sumDiagnosisRoomWaitTime = sumDiagnosisRoomWaitTime + diagnosisRoomWaitTime;
            numOfNonurgentPatient = numOfNonurgentPatient + 1;
        }

        // Wait time for bed and surgery
        if (p.getSurgeryNeeded() == 1) {
            double bedWaitTime = p.getExitBQueueTime() - p.getEnterBQueueTime();
            maxBedWaitTime = Math.max(maxBedWaitTime, bedWaitTime);
            minBedWaitTime = Math.min(minBedWaitTime, bedWaitTime);
            sumBedWaitTime = sumBedWaitTime + bedWaitTime;
            numOfSurgeryNeededPatient = numOfSurgeryNeededPatient + 1;

            double surgeryWaitTime = p.getSurgeryStartTime() - p.getEnterInpatientBedTime();
            maxSurgeryWaitTime = Math.max(maxSurgeryWaitTime, surgeryWaitTime);
            minSurgeryWaitTime = Math.min(minSurgeryWaitTime, surgeryWaitTime);
            sumSurgeryWaitTime = sumSurgeryWaitTime + surgeryWaitTime;
        }
    }
}
