package cpu;

import java.util.Scanner;

public class ee{

public static void main(String[] args){
        System.out.println("\nAGAT Scheduling:");
        AGATSchd agScheduling = new AGATSchd();
        agScheduling.processes.add(new Process("P1",0, 17, 4, 4));
        agScheduling.processes.add(new Process("P2",3,6, 9, 3));
        agScheduling.processes.add(new Process("P3", 4, 10, 3, 5));
        agScheduling.processes.add(new Process("P4",  29, 4, 8, 2));
        agScheduling.start();

    }
}