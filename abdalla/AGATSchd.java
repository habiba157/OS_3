package cpu;


import java.util.*;



import java.util.*;

public class AGATSchd {
    public ArrayList<Process> processes = new ArrayList<>();
    public Queue<Process> readyQueue = new LinkedList<Process>();
    public ArrayList<Process> output = new ArrayList<Process>();
    public ArrayList<Process> finishedPro = new ArrayList<Process>();

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
                System.out.println("++++" +processes.get(i).getName()+ "   "+this.processes.get(i).getAGATFactor());
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
   public void printResults() {
       int totalTurnaroundTime = 0;
       int totalWaitingTime = 0;
       for(Process p: output){
           p.setTurnaroundTime(p.getWaitingTime() + p.getBurstTime());
           System.out.println(p);
       }
       for(Process p: finishedPro){
           totalTurnaroundTime += p.getTurnaroundTime();
           totalWaitingTime += p.getWaitingTime();
       }
       System.out.println("AVG - Turnaround Time: " + (double) totalTurnaroundTime/finishedPro.size());
       System.out.println("AVG - Waiting Time: " + (double) totalWaitingTime/finishedPro.size());
   }

    public void start()
    {
        
        
        ////////////////////////////////////

        // Sort processes (arrival time - ascending order)
        Collections.sort(this.processes);

        // Set Remaining Time for all processes (Remaining Time = burstTime), AG-Factor
        this.setProcessesValue();

        int preIndex = -1;
        Process current = null;
        int currentIndex;
        int time=0;
        Process previous=null;

        for( time  = this.processes.get(0).getArrivalTime(); !this.ProcessFinishedQn(); )
        {


            if(preIndex == -1)
            {
                current = this.processes.get(0);
                currentIndex = 0;
            }
            else
            {
                System.out.println("----------------------------------------");
                 setProcessFactor();
                System.out.println("----------------------------------------");
                current = this.suitableProcess(time, preIndex);
                currentIndex = this.returnIndexOfProcess(current);

            }
            if (current==previous){
                readyQueue.add(processes.get(currentIndex));
                preIndex = this.returnIndexOfProcess(current)+1;
                continue;
            }
//                processes.get(currentIndex).setAGAT_Factor(agatFactor( processes.get(currentIndex).getPriority() , processes.get(currentIndex).getArrivalTime()
//                        , processes.get(currentIndex).getRemainingTime()));

            if(this.processes.get(currentIndex).getQuantumTime() != 0)
            {
                // Set Service Time
                this.processes.get(currentIndex).setStartTime(time);
            }


            int nonPreemptiveAGTime = this.calcNonPrem(current);
            time += nonPreemptiveAGTime;



            this.processes.get(currentIndex).remainingTime -= nonPreemptiveAGTime;


            int preemptiveAGTime = this.pre_emptiveAGAT(current, time, nonPreemptiveAGTime);
            time += preemptiveAGTime;

            // Update Quantum
            if(current.remainingTime == 0) {
                this.finishedPro.add(current);
                // The running process finished its job
                this.processes.get(currentIndex).setQuantumTime(0);
            }
            else if((nonPreemptiveAGTime + preemptiveAGTime) == current.getQuantumTime())
            {
                // The running process used all its quantum time
                this.processes.get(currentIndex).quantumTime += 2;
                // this.processes.get(currentIndex).quantumTime += getceilOfMeanQuantum();
                readyQueue.add(current);
            }
            else
            {
                int total = (this.processes.get(currentIndex).quantumTime - (nonPreemptiveAGTime + preemptiveAGTime));
                this.processes.get(currentIndex).quantumTime += total;

                readyQueue.add(current);
            }





            // Set waiting Time
            int wT = this.processes.get(currentIndex).getStartTime() - this.processes.get(currentIndex).arrivalTime;
            this.processes.get(currentIndex).waitingTime += wT;
            this.processes.get(currentIndex).arrivalTime = time;


            preIndex = this.returnIndexOfProcess(current);

//            System.out.println(processes.get(currentIndex).getProcessName()+" Quantum: "+processes.get(currentIndex).getQuantum());

            output.add(current);
            previous=current;


        }

        // Print Results with AVG
        this.printResults();
           
        ///////////////////////////////////////
    }






}