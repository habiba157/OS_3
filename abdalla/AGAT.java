package cpu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class AGAT {
    public ArrayList<Process> processes = new ArrayList<>();
    public Queue<Process> readyQueue = new LinkedList<Process>();
    public ArrayList<Process> outputTest = new ArrayList<Process>();
    public ArrayList<Process> processes2 = new ArrayList<Process>();  
    
    public float Calculate_V1() {
        float v1;
        int result = processes.get(processes.size()-1).getArrivalTime();
        if (result > 10) {
            v1 = result/10F;
        } else {
            v1 = 1;
        }
        return  v1;
    }
    /////////////////////////////////
    public float Calculate_V2() {
        float v2;
        int result=0;
        int numberOfProcesses = processes.size();
        Vector<Integer> remainingBursts = new Vector<>();
        for (Process process : processes) {
            remainingBursts.add(process.getRemainingTime());
        }
        int mx=remainingBursts.get(0);
        for(int i=0;i<numberOfProcesses;i++){
            result=Math.max(mx,remainingBursts.get(i));
        }
        if (result > 10) {
            v2 = result/10F;
        } else {
            v2 = 1;
        }
        return  v2;
    }
    ////////////////////////////////////////
      public double Calculate_AGAT_Factor(int priority, int arrivalTime, int burstTime){
        float v1= Calculate_V1();
        float v2= Calculate_V2();
        double result = ((10 - priority)+Math.ceil(arrivalTime/v1)+Math.ceil(burstTime/v2));
        //System.out.println("Factor: "+result+" ----------------V2: "+v2);
        return result;
    }
    /////////////////////////////////////////
     public boolean ProcessIsFinished(){
        for (Process process : processes) {
            if (process.getQuantumTime() != 0)
                return false;
        }
        return true;
    }
    /////////////////////////////////////////
     public Process get_Minimun_AGAT_Factor(int time){
        int min_AGAT = Integer.MAX_VALUE;
        int indx = 0;
        int i = 0;
        while( (i < this.processes.size()) && (this.processes.get(i).getArrivalTime() <= time) )
        {
            if(this.processes.get(i).getAGATFactor()  < min_AGAT && this.processes.get(i).getQuantumTime() != 0)
            {
                min_AGAT = this.processes.get(i).getAGATFactor();
                indx = i;
            }
            i++;
        }
        return this.processes.get(indx);
    }
     /////////////////////////////////////
      public int returnIndexOfProcess(Process process){
        for(int i = 0; i < processes.size(); i++){
            if(processes.get(i).getName().equals(process.getName()))
                return i;
        }
        return -1;
    }
     /////////////////////////////////////////
      

    
}
