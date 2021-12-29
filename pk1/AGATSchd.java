package pk1;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class AGATSchd {
    public ArrayList<Process> processes = new ArrayList<>();
    public Queue<Process> readyQueue = new LinkedList<Process>();
    //public ArrayList<Process> outputTest = new ArrayList<Process>();
    //public ArrayList<Process> processes2 = new ArrayList<Process>();

    public double V1() {
        double v1;
        int result = processes.get(processes.size()-1).getArrivalTime();
        if (result > 10)    v1 = result/10F;
         else   v1 = 1;
        return  v1;
    }
    /////////////////////////////////

    public double V2() {
        double v2;
        int result=0 , numberOfProcesses = processes.size();
        Vector<Integer> remainingTime = new Vector<>();
        for (Process process : processes) {
            remainingTime.add(process.getRemainingTime());
        }
        int max=remainingTime.get(0);
        for(int i=0;i<numberOfProcesses;i++){
            result=Math.max(max,remainingTime.get(i));
        }
        if (result > 10)    v2 = result/10F;
        else      v2 = 1;

        return  v2;
    }
    ////////////////////////////////////////

    public double CalcAGATFactor(int priority, int arrivalTime, int burstTime){
        double v1= V1()  , v2= V2() ,result = ((10 - priority)+Math.ceil(arrivalTime/v1)+Math.ceil(burstTime/v2));
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

    public Process getMinimumAGATFactor(int time){
        double minAGAT = Integer.MAX_VALUE ;
        int indx = 0 , i = 0;
        while( (i < this.processes.size()) && (this.processes.get(i).getArrivalTime() <= time) )
        {
            if(this.processes.get(i).getAGATFactor()  < minAGAT && this.processes.get(i).getQuantumTime() != 0)
            {
                minAGAT = this.processes.get(i).getAGATFactor();
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

    public boolean processIsLast(Process p, int time)
    {
        int index = this.returnIndexOfProcess(p);

        if(index == this.processes.size() - 1)
            return true;

        index++;

        while((index < this.processes.size()) && this.processes.get(index).getArrivalTime() <= time)
        {
            if (this.processes.get(index).getAGATFactor() != 0) {
                return false;
            }
            index++;
        }

        return true;
    }
    ///////////////////////////////////////////

    public Process suitableProcess(int time, int index)
    {
        Process p = processes.get(index);

        if(this.processIsLast(p, time) && readyQueue.size() > 0)
        {
            Process temp ;
            while(true)
            {
                temp = readyQueue.poll();
                if (temp != null && temp.getRemainingTime() != 0)   return temp;
            }
        }
        else    return getMinimumAGATFactor(time);
    }
    ////////////////////////////////////////////////

    public void setProcessesValue()
    {
        for (Process process : this.processes) {
            process.setRemainingTime(process.getBurstTime());
            //process.setAGATFactor(CalcAGATFactor( process.getPriority() , process.getArrivalTime() , process.getBurstTime()));
            this.setProcessFactor();
        }
    }
    ///////////////////////////////////////////////

    public void setProcessFactor()
    {
        for (Process process : this.processes) {
            if(process.getRemainingTime()!=0){
                process.setAGATFactor(CalcAGATFactor( process.getPriority() , process.getArrivalTime() , process.getRemainingTime()));
                continue;
            }
            process.setAGATFactor(0);
        }
    }






}
