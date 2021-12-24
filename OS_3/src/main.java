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
import java.util.logging.Level;
import java.util.logging.Logger;

public class main {

    static public void main(String[] arg) throws NoSuchFieldException {
        int nProcesses;
        //int RRQuantum;
        int contextSwitching;

        ArrayList<Process> Processes = new ArrayList<Process>();

        //@SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);

        System.out.println("Enter n of processes :");
        nProcesses = input.nextInt();

        System.out.println("Enter context Switching : ");
        contextSwitching = input.nextInt();
        // Read Processes
        for (int i = 0; i < nProcesses; i++) {
            input = new Scanner(System.in);
            Process p = new Process();
            System.out.println((i + 1) + " Enter Process Name :");
            p.setName(input.nextLine());

            System.out.println("Enter Process Burst Time :");
            p.setBurstTime(input.nextInt());

            System.out.println("Enter Process Arrival Time :");
            p.setArrivalTime(input.nextInt());

            System.out.println("Enter Process prority :");
            p.setPriority(input.nextInt());

            input = new Scanner(System.in);
            System.out.println("Enter Process Color :");
            p.setColor(input.nextLine());

            Processes.add(p);
        }

        System.out.println("\nSelect the Scheduler you want to use : "
                + "\n3-Non-preemptive Priority Scheduling."
                + "\n2-Non-Preemptive Shortest- Job First (SJF)"
                + "\n3-Shortest-Remaining Time First (SRTF) Scheduling"
                + "\n4-AG Scheduling \n5-End");
        int choice = input.nextInt();
        switch (choice) {

            case 1:
                PriorityScheduling ps = new PriorityScheduling(Processes, contextSwitching);
                ps.running();

                for (int i = 0; i < nProcesses; i++) {
                    System.out.println("Waiting Time for process " + (i + 1) + " is -> " + Processes.get(i).getWaitingTime());
                    System.out.println("Turnaround Time for process " + (i + 1) + " is -> " + Processes.get(i).getTurnaroundTime());

                }
                System.out.println("Average Waiting Time is ->  " + ps.calcAvgWT());
                System.out.println("Average Turnaround Time is ->" + ps.calcAvgTAT() + "\n");
            case 2:
                SJFScheduling shortestJobFirst = new SJFScheduling(Processes);
                shortestJobFirst.running();
                for (int i = 0; i < nProcesses; i++) {
                    System.out.println("Waiting Time for process " + Processes.get(i).getName() + " is -> " + Processes.get(i).getWaitingTime());
                    System.out.println("Turnaround Time for process " + Processes.get(i).getName() + " is -> " + Processes.get(i).getTurnaroundTime());

                }
                System.out.println("Average Waiting Time is ->  " + shortestJobFirst.calcAvgWT());
                System.out.println("Average Turnaround Time is ->" + shortestJobFirst.calcAvgTAT() + "\n");

        }
    }
}
