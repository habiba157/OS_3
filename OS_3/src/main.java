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

public class main {

    static public void main(String[] arg) throws NoSuchFieldException {
        int n;
        //int RRQuantum;
        int contextSwitching;

        ArrayList<Process> processes = new ArrayList<>();

        //@SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);

        System.out.println("Enter number of processes :");
        n = input.nextInt();

        System.out.println("Enter context Switching : ");
        contextSwitching = input.nextInt();
        for (int i = 0; i < n; i++) {
            input = new Scanner(System.in);
            Process p = new Process();
            System.out.println("\n\tFor process  " + (i + 1) + " :");
            System.out.println("\nEnter Process Name :");
            p.setName(input.nextLine());

            System.out.println("Enter Process Arrival Time :");
            p.setArrivalTime(input.nextInt());

            System.out.println("Enter Process Burst Time :");
            p.setBurstTime(input.nextInt());

            System.out.println("Enter Process priority :");
            p.setPriority(input.nextInt());

            input = new Scanner(System.in);
            System.out.println("Enter Process Color :");
            p.setColor(input.nextLine());

            processes.add(p);
        }

        System.out.println("\n\tSelect the Scheduler you want to use : ");
        System.out.println("\n\t1-Non-preemptive Priority Scheduling.");
        System.out.println("\n\t2-Non-Preemptive Shortest- Job First (SJF)");
        System.out.println("\n\t3-Shortest-Remaining Time First (SRTF) Scheduling");
        System.out.println("\n\t4-AG Scheduling");
        System.out.println("\n\t5-End");
        int choice = input.nextInt();

        switch (choice) {

            case 1:

                PriorityScheduling ps = new PriorityScheduling(processes, contextSwitching);
                ps.running();

                for (int i = 0; i < n; i++) {
                    System.out.println("Waiting Time for process " + (i + 1) + " is -> " + processes.get(i).getWaitingTime());
                    System.out.println("Turnaround Time for process " + (i + 1) + " is -> " + processes.get(i).getTurnaroundTime());

                }
                System.out.println("Average Waiting Time is ->  " + ps.calcAvgWT());
                System.out.println("Average Turnaround Time is ->" + ps.calcAvgTAT() + "\n");
                break;
            case 2:

                SJFScheduling shortestJobFirst = new SJFScheduling(processes);
                shortestJobFirst.running();
                for (int i = 0; i < n; i++) {
                    System.out.println("Waiting Time for process " + processes.get(i).getName() + " is -> " + processes.get(i).getWaitingTime());
                    System.out.println("Turnaround Time for process " + processes.get(i).getName() + " is -> " + processes.get(i).getTurnaroundTime());

                }
                System.out.println("Average Waiting Time is ->  " + shortestJobFirst.calcAvgWT());
                System.out.println("Average Turnaround Time is ->" + shortestJobFirst.calcAvgTAT() + "\n");
                break;

            case 5:
                System.exit(0);
            //break;

        }
    }
}
