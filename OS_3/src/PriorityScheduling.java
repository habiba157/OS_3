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

public class PriorityScheduling {

    int currentTime = 0;
    int contextSwitching;

    public int getContextSwitching() {
        return contextSwitching;
    }

    public void setContextSwitching(int contextSwitching) {
        this.contextSwitching = contextSwitching;
    }

    ArrayList<Process> process = new ArrayList<>();
    ArrayList<Integer> finished = new ArrayList<>();
    ArrayList<Process> temp = new ArrayList<>();
    
    sortByPriority s = new sortByPriority();

    public PriorityScheduling(ArrayList<Process> process, int contextSwitching) {
        this.process = process;
        this.contextSwitching = contextSwitching;
    }

    private int getNextProcess() {
        int nextProcess = -1;
        Collections.sort(process,s);
        for (int i = 0; i < process.size(); i++) {
            if (!finished.contains(i)) {

                if (nextProcess == -1) {
                    nextProcess = i;
                } /**else if (process.get(i).getPriority() < process.get(nextProcess).getPriority()) {
                    nextProcess = i;
                }*/
            }
        }
        return nextProcess;
    }

    public void running() {
        
        int currentProcess = 0;
        for (int i = 0; i < process.size(); i++) {
            do {
                currentProcess  = getNextProcess();
                if (currentProcess == -1) {
                    currentTime++;
                }
            } while (currentProcess == -1);

            process.get(currentProcess).run();
            // process.get(0).setWaitingTime(currentTime - process.get(0).getArrivalTime());

           // process.get(currentProcess).setWaitingTime(currentTime  - process.get(currentProcess-1).getArrivalTime());
            
            process.get(currentProcess).setWaitingTime(currentTime - process.get(currentProcess).getArrivalTime());

            process.get(currentProcess).setTurnaroundTime(process.get(currentProcess).getWaitingTime() + process.get(currentProcess).getBurstTime());
            currentTime = currentTime + process.get(currentProcess).getBurstTime() + contextSwitching;
            finished.add(currentProcess);
        }

    }

    public double calcAvgWT() {
        double sum = 0.0;
        for (Process p : process) {
            sum = sum + p.getWaitingTime();
        }
        double avg = sum / finished.size();

        return avg;
    }

    public double calcAvgTAT() {
        double sum = 0.0;
        for (Process p : process) {
            sum = sum + p.getTurnaroundTime();
        }

        double avg = sum / finished.size();

        return avg;
    }

}
