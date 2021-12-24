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

    private ArrayList<Process> processes;
    private ArrayList<Integer> finished = new ArrayList<Integer>();

    sortByBT s = new sortByBT();
    
    public SJFScheduling(ArrayList<Process> processes) {
        this.processes = processes;
       
    }
    
    private int getNextProcess() {
        int nextProcess = -1;
        for (int i = 0; i < processes.size(); i++) {
            
            Collections.sort(processes, s);
            
            if (!finished.contains(i) /**&& processes.get(i).getArrivalTime() <= currentTime*/) { 
                
                if (nextProcess == -1) {
                    nextProcess = i;
                } 
                /**else if (processes.get(i).getBurstTime() < processes.get(nextProcess).getBurstTime()) {
                    nextProcess = i;
                }*/
            }
        }
        return nextProcess;
    }


    public void running() {
        
        int currentTime = 0;
        int currentProcess = 0;
        for (int i = 0; i < processes.size(); i++) {
            
            do {
                currentProcess = getNextProcess();
                if (currentProcess == -1) {
                    currentTime++;
                    //gui.AddColor( currTime , temp, new Color(255,255,255));	
                }
            } while (currentProcess == -1);

            processes.get(currentProcess).run();
            //gui.AddColor( currTime+1 , currProcess, processes.get(currProcess).getColor() ,processes.get(currProcess).getBurstTime() );

            processes.get(currentProcess).setWaitingTime(currentTime - processes.get(currentProcess).getArrivalTime());
            processes.get(currentProcess).setTurnaroundTime(processes.get(currentProcess).getWaitingTime() + processes.get(currentProcess).getBurstTime());
            currentTime += processes.get(currentProcess).getBurstTime();
            finished.add(currentProcess);

        }
    }

    
    public double calcAvgWT() {
        double sum = 0.0;
        for (Process p : processes) {
            sum = sum+p.getWaitingTime();
        }
        double avg = sum/processes.size();

        return avg;
    }

    public double calcAvgTAT() {
        double sum = 0.0;
        for (Process p : processes) {
            sum = sum+p.getTurnaroundTime();
        }
        
        double avg = sum/processes.size();

        return avg;
    }

}
