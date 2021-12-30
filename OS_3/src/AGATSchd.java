package pk1;

import java.util.*;

public class AGATSchd {
    public ArrayList<Process> processes = new ArrayList<>() ,  finishedPro = new ArrayList<Process>();
    public Queue<Process> readyQueue = new LinkedList<Process>();


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

    public boolean ProcessFinishedQn(){
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
            if(this.processes.get(i).getAGATFactor() < minAGAT && this.processes.get(i).getQuantumTime() != 0)
            {
                minAGAT = this.processes.get(i).getAGATFactor();
                indx = i;
                ///////////////////////////////
                //System.out.println("++++" +processes.get(i).getName()+ "   "+this.processes.get(i).getAGATFactor());
                ///////////////////////////////
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
            process.setAGATFactor(CalcAGATFactor( process.getPriority() , process.getArrivalTime() , process.getBurstTime()));
            //this.setProcessFactor();
        }
    }
    ///////////////////////////////////////

    public void setProcessFactor()
    {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (Process process : this.processes) {
            if(process.getRemainingTime()!=0){
                process.setAGATFactor(CalcAGATFactor( process.getPriority() , process.getArrivalTime() , process.getRemainingTime()));
                System.out.println(process.getName() + "    Arrival: "+process.getArrivalTime() +"    remaining time: " + process.getRemainingTime() );
                continue;
            }
            process.setAGATFactor(0);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public int calcNonPrem(Process p)
    {
        int nonPreemptiveAGTime = (int) Math.ceil(p.getQuantumTime() * 0.4);
        if(nonPreemptiveAGTime<p.getRemainingTime()) return nonPreemptiveAGTime;
        else      return p.getRemainingTime();
    }
    ////////////////////////////////////////////

    public int pre_emptiveAGAT(Process p, int time, int nPreTime)
    {
        int processTime = 0;
        while(p.getRemainingTime() > 0 )
        {
            Process p2 = this.getMinimumAGATFactor(time);

            if(!Objects.equals(p2.getName(), p.getName()))
                break;

            p.setRemainingTime(p.getRemainingTime()-1);
            time++;
            processTime++;

            if(p.getQuantumTime() == processTime + nPreTime)
                break;
        }

        return processTime;
    }
    /////////////////////////////////////////////




    /* public void start()
     {
         // first we need to sort processes according to arrival time in ascending order
         Collections.sort(this.processes);
         // make remaining time = burst time as we didn't start scheduling
         this.setProcessesValue();
         int time  = this.processes.get(0).getArrivalTime() , preIndex = -1 , currIndex;
         Process current = null , previous=null;
         StringBuilder output= new StringBuilder();
         output.append("time: " +time+"      " +this.processes.get(0).getName() );
         System.out.println(output);
     }*/
    public void printProcesses() {
        int totalTT = 0 , totalWT = 0;
        for(Process p: finishedPro){
            p.setTurnaroundTime(p.getWaitingTime() + p.getBurstTime());
            totalTT += p.getTurnaroundTime();
            totalWT += p.getWaitingTime();
            System.out.println(p);
        }
        System.out.println("Average Turnaround Time: " + (double) totalTT/finishedPro.size());
        System.out.println("Average Waiting Time: " + (double) totalWT/finishedPro.size());
    }

    public void start()
    {
        sortByAT s =new sortByAT();


        ////////////////////////////////////
        // first we need to sort processes according to arrival time in ascending order
        Collections.sort(this.processes , s);

        // make remaining time = burst time as we didn't start scheduling
        this.setProcessesValue();

        int preIndex = -1 , currentIndex ,  time=0;
        Process current = null ,  previous=null;




         time  = this.processes.get(0).getArrivalTime();
         while( !this.ProcessFinishedQn())
        {

            if(preIndex == -1)
            {
                currentIndex = 0;
                current = this.processes.get(currentIndex);

            }
            else
            {
                setProcessFactor();
                current = this.suitableProcess(time, preIndex);
                currentIndex = this.returnIndexOfProcess(current);

            }
            if (current==previous){
                readyQueue.add(processes.get(currentIndex));
                preIndex = this.returnIndexOfProcess(current)+1;
                continue;
            }

            if(this.processes.get(currentIndex).getQuantumTime() != 0)      this.processes.get(currentIndex).setStartTime(time);


            int nprem = this.calcNonPrem(current);
            time += nprem;

            this.processes.get(currentIndex).remainingTime -= nprem;


            int preem = this.pre_emptiveAGAT(current, time, nprem);
            time += preem;


            if(current.remainingTime == 0) {
                this.finishedPro.add(current);
                this.processes.get(currentIndex).setQuantumTime(0);
            }
            else if((nprem + preem) == current.getQuantumTime())
            {

                this.processes.get(currentIndex).quantumTime += 2;
                readyQueue.add(current);
            }
            else
            {
                int diff = (this.processes.get(currentIndex).quantumTime - (nprem + preem));
                this.processes.get(currentIndex).quantumTime += diff;
                readyQueue.add(current);
            }



            int waiting = this.processes.get(currentIndex).getStartTime() - this.processes.get(currentIndex).arrivalTime;
            this.processes.get(currentIndex).waitingTime += waiting;
            this.processes.get(currentIndex).arrivalTime = time;


            preIndex = this.returnIndexOfProcess(current);
            previous=current;


        }
        this.printProcesses();

        ///////////////////////////////////////
    }






}
