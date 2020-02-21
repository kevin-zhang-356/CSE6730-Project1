package com.hospital.components;

import com.hospital.simulation.Engine;
import com.hospital.simulation.EventData;
import com.hospital.simulation.Helper;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OperatingRoom {
    private static int numOfRoom;
    private static double averageOperationTime;
    private static int occupiedRoom = 0;

    // Store surgery schedule in different operating rooms
    private static Map<Integer, List<double[]>> map = new HashMap<Integer, List<double[]>>();
    private static int[] roomList;      // Store room availability at current time

    public static void setNumOfRoom (int num) {
        numOfRoom = num;
        roomList = new int[numOfRoom];
    }

    public static void setAverageOperationTime (int time) {
        averageOperationTime = time;
    }

    public static void OREvent (EventData eventData, double currentTime) {
        if (eventData.getPatient().getSeverity() == 2) {
            // Urgent patient: No OR room or inpatient bed, transfer to other hospital, proceed to exit
            if (occupiedRoom >= numOfRoom || !InpatientBed.hasBed()) {
                System.out.printf("There is no available OR or bed, Patient %d will be transferred, current time: %f\n",
                        eventData.getPatient().getPID(), currentTime);

                // Schedule next arrival event to exit
                eventData.setEventType(8);  // 8 : exit
                double arrivalTS = currentTime;
                Engine.schedule(arrivalTS, eventData);
            } else {
                // Urgent patient with possible available OR room and bed
                double operatingTime = Helper.randExp(averageOperationTime);
                // Find available OR room to schedule surgery
                for (int i = 0; i < roomList.length; i++) {
                    if (roomList[i] == 0) {
                        List<double[]> list = map.get(i);
                        if (list == null) {
                            list = new ArrayList<double[]>();
                            scheduleSurgery(currentTime, list, i, eventData, operatingTime);
                            occupiedRoom++;
                            InpatientBed.setIncreaseBed();
                            roomList[i] = 1;
                            // Schedule to urgent post surgery event
                            eventData.setEventType(7);
                            double ts = currentTime + operatingTime;
                            Engine.schedule(ts, eventData);
                            return;
                        } else {
                            if (list.size() == 0) {
                                scheduleSurgery(currentTime, list, i, eventData, operatingTime);
                                occupiedRoom++;
                                InpatientBed.setIncreaseBed();
                                roomList[i] = 1;
                                // Schedule to urgent post surgery event
                                eventData.setEventType(7);
                                double ts = currentTime + operatingTime;
                                Engine.schedule(ts, eventData);
                                return;
                            } else if (currentTime + operatingTime <= list.get(0)[0]) {
                                scheduleSurgery(currentTime, list, i, eventData, operatingTime);
                                occupiedRoom++;
                                InpatientBed.setIncreaseBed();
                                roomList[i] = 1;
                                // Schedule to urgent post surgery event
                                eventData.setEventType(7);
                                double ts = currentTime + operatingTime;
                                Engine.schedule(ts, eventData);
                                return;
                            }
                        }
                    }
                    if (i == roomList.length - 1) {
                        // No available OR room, transfer patient
                        System.out.printf("There is no available OR, Patient %d will be transferred, current time: %f\n"
                                , eventData.getPatient().getPID(), currentTime);

                        // Schedule next arrival event to exit
                        eventData.setEventType(8);  // 8 : exit
                        double arrivalTS = currentTime;
                        Engine.schedule(arrivalTS, eventData);
                    }
                }
            }
        } else {
            // Normal patient enter OR room
            System.out.printf("There is an available OR, Patient %d will be operated, current time: %f\n",
                    eventData.getPatient().getPID(), currentTime);
            occupiedRoom++;
            eventData.setEventType(10);
            double ts = currentTime + eventData.getPatient().getSurgeryDuration();
            Engine.schedule(ts, eventData);
        }
    }

    public static void normalPostSurgeryEvent (EventData eventData, double currentTime) {
        // Normal patient post surgery event
        occupiedRoom--;
        int operatingRoomNum = eventData.getPatient().getOperatingRoomNum();
        roomList[operatingRoomNum] = 0;
        List<double[]> list = map.get(operatingRoomNum);
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i)[1] - currentTime) < 1e-5) {
                list.remove(i);
            }
        }
    }

    public static void urgentPostSurgeryEvent (EventData eventData, double currentTime) {
        // Urgent patient post surgery event
        occupiedRoom--;
        int operatingRoomNum = eventData.getPatient().getOperatingRoomNum();
        roomList[operatingRoomNum] = 0;
        List<double[]> list = map.get(operatingRoomNum);
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i)[1] - currentTime) < 1e-10) {
                list.remove(i);
            }
        }

        // Sent to urgent patient discharge
        eventData.setEventType(9);  // 9: urgentPatientDischarge
        double ts = currentTime + InpatientBed.getRecoveryTime();
        Engine.schedule(ts, eventData);
    }

    public static void urgentPatientDischarge (EventData eventData, double currentTime) {
        InpatientBed.setDecreaseBed();
        InpatientBed.informQueue(currentTime);

        // Schedule next arrival event to exit
        eventData.setEventType(8);  // 8 : exit
        double arrivalTS = currentTime;
        Engine.schedule(arrivalTS, eventData);
        System.out.printf("Urgent patient %d discharged, current time: %f\n",
                eventData.getPatient().getPID(), currentTime);
    }

    public static void scheduleSurgery(double currentTime, List<double[]> list,
                                           int room, EventData eventData, double operatingTime) {
        double[] startEndTime = new double[2];
        startEndTime[0] = currentTime;
        startEndTime[1] = currentTime + operatingTime;
        list.add(startEndTime);

        Collections.sort(list, new Comparator<double[]>(){
            @Override
            public int compare(double[] a, double[] b) {
                if ((a[0] - b[0]) < 0)
                    return -1;
                else if ((a[0] - b[0]) > 0)
                    return 1;
                else
                    return 0;
            }
        });

        map.put(room, list);
        eventData.getPatient().setOperatingRoomNum(room);
        System.out.printf("Patient %d is scheduled to surgery room %d, start time: %f end time: %f\n",
                eventData.getPatient().getPID(), room, currentTime, startEndTime[1]);
    }

    public static double[] findAvailableSurgeryTime (double expectedTime, EventData eventData) {
        // Find operating room with smallest number of scheduled surgery to be the one to schedule
        int minSize = Integer.MAX_VALUE;
        int minRoom = -1;
        double operatingTime = Helper.randExp(averageOperationTime);

        // If no surgery scheduled in this room, schedule the surgery. Or find the min surgery in all the rooms
        for (int i = 0; i < numOfRoom; i++) {
            List<double[]> list = map.get(i);
            if (list == null) {
                list = new ArrayList<double[]>();
                scheduleSurgery(expectedTime, list, i, eventData, operatingTime);
                return new double[] {expectedTime, expectedTime + operatingTime};
            } else {
                if (list.size() < minSize) {
                    minSize = list.size();
                    minRoom = i;
                }
            }
        }

        // Schedule the surgery based on the minRoom find before
        List<double[]> list = map.get(minRoom);
        if (list.size() == 0) {
            scheduleSurgery(expectedTime, list, minRoom, eventData, operatingTime);
            return new double[] {expectedTime, expectedTime + operatingTime};
        } else {
            if (expectedTime + operatingTime <= list.get(0)[0]) {
                scheduleSurgery(expectedTime, list, minRoom, eventData, operatingTime);
                return new double[] {expectedTime, expectedTime + operatingTime};
            } else {
                for (int i = 0; i < list.size() - 1; i++) {
                    if (expectedTime >= list.get(i)[1] && expectedTime + operatingTime <= list.get(i + 1)[0]) {
                        scheduleSurgery(expectedTime, list, minRoom, eventData, operatingTime);
                        return new double[] {expectedTime, expectedTime + operatingTime};
                    }
                }
            }
        }
        double max = Math.max(expectedTime, list.get(list.size() - 1)[1]);
        scheduleSurgery(max, list, minRoom, eventData, operatingTime);
        return new double[] {max, max + operatingTime};
    }
}
