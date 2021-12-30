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

    public static void main(String[] arg) throws NoSuchFieldException {
        int n;
        //int RRQuantum;
        int contextSwitching;

        ArrayList<Process> processes = new ArrayList<>();

        
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

        while (true) {

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

                        System.out.println("\nWaiting Time for process " + processes.get(ps.finished.get(i)).getName() + " is -> " + processes.get(ps.finished.get(i)).getWaitingTime());
                        System.out.println("\nTurnaround Time for process " + processes.get(ps.finished.get(i)).getName() + " is -> " + processes.get(ps.finished.get(i)).getTurnaroundTime());
                        System.out.println("\n\t\tPriority of " + processes.get(ps.finished.get(i)).getName() + " is " + processes.get(ps.finished.get(i)).getPriority());

                    }
                    System.out.println("Average Waiting Time is ->  " + ps.calcAvgWT());
                    System.out.println("Average Turnaround Time is ->" + ps.calcAvgTAT() + "\n");
                    break;
                case 2:

                    SJFScheduling sjf = new SJFScheduling(processes);
                    sjf.running();
                    for (int i = 0; i < processes.size(); i++) {

                        System.out.println("Waiting Time for process " + processes.get(sjf.finished.get(i)).getName() + " is -> " + processes.get(sjf.finished.get(i)).getWaitingTime());
                        System.out.println("Turnaround Time for process " + processes.get(sjf.finished.get(i)).getName() + " is -> " + processes.get(sjf.finished.get(i)).getTurnaroundTime());

                    }
                    System.out.println("Average Waiting Time is ->  " + sjf.calcAvgWT());
                    System.out.println("Average Turnaround Time is ->" + sjf.calcAvgTAT() + "\n");
                    break;
                case 3:

                    SRTFScheduling srtf = new SRTFScheduling(processes, contextSwitching);
                    srtf.run();
                    for (int i = 0; i < processes.size(); i++) {

                        System.out.println("Waiting Time for process " + processes.get(i).getName() + " is -> " + processes.get(i).getWaitingTime());
                        System.out.println("Turnaround Time for process " + processes.get(i).getName() + " is -> " + processes.get(i).getTurnaroundTime());

                    }

                    break;

                case 5:
                    System.exit(0);
                

            }
        }
    }
}
