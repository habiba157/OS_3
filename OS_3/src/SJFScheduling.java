/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TRUST
 */
import java.util.*;

public class SJFScheduling {

    ArrayList<Process> processes;
    ArrayList<Integer> finished = new ArrayList<>();

    sortByBT s = new sortByBT();

    public SJFScheduling(ArrayList<Process> processes) {
        this.processes = processes;

    }

    private int getNextProcess(int currentTime) {

        int nextProcess = -1;
        for (int i = 0; i < processes.size(); i++) {
            if (!finished.contains(i) && processes.get(i).getArrivalTime() <= currentTime) {

                if (nextProcess == -1) {
                    nextProcess = i;
                } else if (processes.get(i).getBurstTime() - processes.get(i).getAge(currentTime) < processes.get(nextProcess).getBurstTime() - processes.get(nextProcess).getAge(currentTime)) {

                    nextProcess = i;
                }
            }
        }
        return nextProcess;
    }

    public void running() {

        int currentTime = 0;
        int currentProcess = 0;
        Collections.sort(processes, s);
        for (int i = 0; i < processes.size(); i++) {

            do {
                currentProcess = getNextProcess(currentTime);
                if (currentProcess == -1) {
                    currentTime++;

                }
            } while (currentProcess == -1);

            processes.get(currentProcess).run();

            processes.get(currentProcess).setWaitingTime(currentTime - processes.get(currentProcess).getArrivalTime());
            processes.get(currentProcess).setTurnaroundTime(processes.get(currentProcess).getWaitingTime() + processes.get(currentProcess).getBurstTime());
            currentTime += processes.get(currentProcess).getBurstTime();
            finished.add(currentProcess);

        }
    }

    public double calcAvgWT() {
        double sum = 0.0;
        for (Process p : processes) {
            sum = sum + p.getWaitingTime();
        }
        double avg = sum / processes.size();

        return avg;
    }

    public double calcAvgTAT() {
        double sum = 0.0;
        for (Process p : processes) {
            sum = sum + p.getTurnaroundTime();
        }

        double avg = sum / processes.size();

        return avg;
    }

}
