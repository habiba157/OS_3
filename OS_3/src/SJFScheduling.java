/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TRUST
 */
//import java.awt.Color;
import java.util.*;

public class SJFScheduling {

    ArrayList<Process> processes;
    ArrayList<Integer> finished = new ArrayList<>();

    public SJFScheduling(ArrayList<Process> processes) {
        this.processes = processes;

    }

    public void running() {
        int currentTime = 1;
        int currentProcess = 0;
        for (int i = 0; i < processes.size(); i++) {
            //int temp_Process = currentProcess;
            while (currentProcess == -1) {
                currentProcess = getNextProcess(currentTime);
                if (currentProcess == -1) {
                    currentTime++;

                }
            }

            processes.get(currentProcess).run();

            int WT = currentTime - processes.get(currentProcess).getArrivalTime();
            int TAT = processes.get(currentProcess).getWaitingTime() + processes.get(currentProcess).getBurstTime();
            processes.get(currentProcess).setWaitingTime(WT);
            processes.get(currentProcess).setTurnAroundTime(TAT);
            currentTime += processes.get(currentProcess).getBurstTime();
            finished.add(currentProcess);

        }
    }

    private int getNextProcess(int currentTime) {
        int next = -1;
        for (int i = 0; i < processes.size(); i++) {
            if (!finished.contains(i) && (processes.get(i).getArrivalTime() <= currentTime)) {

                if (next == -1) {
                    next = i;
                } else if (processes.get(i).getBurstTime() < processes.get(next).getBurstTime()) {
                    next = i;
                }
            }
        }
        return next;
    }

    public double getAverageWT() {
        double sum = 0.0;
        for (Process p : processes) {
            sum = sum + p.getWaitingTime();
        }
        return sum / processes.size();
    }

    public double getAverageTAT() {
        double sum = 0.0;
        for (Process p : processes) {
            sum = sum + p.getTurnAroundTime();
        }

        return sum / processes.size();
    }
}
