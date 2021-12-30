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

public class SRTFScheduling {

    private ArrayList<Process> processes;
    private ArrayList<Integer> totalBT = new ArrayList<>();
    private ArrayList<Integer> executionOrder = new ArrayList<>();
    private int currentTime;
    private int contextSwitching;
    private int numOfProcesses;
    private int currentProcess;
    private int minimumRemaining;

    public SRTFScheduling(ArrayList<Process> processes, int contextSwitching) {
        this.processes = processes;
        this.currentTime = 0;
        this.contextSwitching = contextSwitching;
        numOfProcesses = processes.size();
        Collections.sort(processes, Comparator.comparing(Process::getArrivalTime));
        for (int i = 0; i < numOfProcesses; i++) {
            totalBT.add(processes.get(i).getBurstTime());
        }
        currentProcess = 0;
        minimumRemaining = Integer.MAX_VALUE;
    }

    public void run() {
        boolean cond = false;
        int count = 0;
        int prevProcess = 0;
        int min = Integer.MAX_VALUE;
        while (numOfProcesses > 0) {
            for (int i = 0; i < processes.size(); i++) {
                if (processes.get(i).getArrivalTime() <= currentTime && totalBT.get(i) > 0&& totalBT.get(i) < minimumRemaining) {
                    minimumRemaining = totalBT.get(i);
                    currentProcess = i;
                    cond = true;
                }
            }
            if (!cond) {
                currentTime++;
                continue;
            }
            count++;
            if (count > 1 && prevProcess != currentProcess) {
                currentTime += contextSwitching;
                executionOrder.add(prevProcess);
            }
            totalBT.set(currentProcess, (totalBT.get(currentProcess)) - 1);
            minimumRemaining = totalBT.get(currentProcess);
            if (minimumRemaining == 0) {
                minimumRemaining = min;
            }
            prevProcess = currentProcess;
            if (totalBT.get(currentProcess) == 0) {
                numOfProcesses--;
                cond = false;
                int newWaitingTime = currentTime + 1 - processes.get(currentProcess).getBurstTime() - processes.get(currentProcess).getArrivalTime();
                if (newWaitingTime > 0) {
                    processes.get(currentProcess).setWaitingTime(newWaitingTime);
                } else {
                    processes.get(currentProcess).setWaitingTime(0);
                }
                processes.get(currentProcess).setTurnaroundTime(processes.get(currentProcess).getWaitingTime() + processes.get(currentProcess).getBurstTime());
            }
            currentTime++;
        }
        executionOrder.add(prevProcess);
        displayProcesses(executionOrder);
    }

    private void displayProcesses(ArrayList<Integer> printingOrder) {
        for (int i = 0; i < printingOrder.size(); i++) {
            System.out.print("\tExection order is -> " + processes.get(printingOrder.get(i)).getName());
            if (i == printingOrder.size() - 1) {
                System.out.println("\n");
            } else {
                System.out.print(" \n ");
            }
        }
        double totalWT = 0.0;
        double totalTAT = 0.0;
        for (int i = 0; i < processes.size(); i++) {
            System.out.println("Waiting Time for process " + processes.get(i).getName() + " is -> " + processes.get(i).getWaitingTime());
            System.out.println("Turnaround Time for process " + processes.get(i).getName() + " is -> " + processes.get(i).getTurnaroundTime());
            totalWT += processes.get(i).getWaitingTime();
            totalTAT += processes.get(i).getTurnaroundTime();
        }
        double avgWT = totalWT / processes.size();
        double avgTAT = totalTAT / processes.size();
        System.out.println("Average waiting time is -> " + avgWT);
        System.out.println("Average turnaround time is -> " + avgTAT);
    }

}
