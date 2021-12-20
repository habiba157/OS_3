/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TRUST
 */
import java.awt.Color;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Process implements Comparable<Process> {
    String name;
    Color color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(String color) throws NoSuchFieldException {
        try {
            java.lang.reflect.Field f = Class.forName("java.awt.Color").getField(color.toLowerCase());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getQuantumTime() {
        return quantumTime;
    }

    public void setQuantumTime(int quantumTime) {
        quantumList.add(quantumTime);
        this.quantumTime = quantumTime;
    }

    

    public int getLastTime() {
        return lastTime;
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    public int getAGATFactor() {
        return AGATFactor;
    }

    public void setAGATFactor(int AGATFactor) {
        this.AGATFactor = AGATFactor;
    }

    

    
    int arrivalTime,burstTime,completionTime,waitingTime,turnAroundTime,priority,quantumTime,processTime,responseTime,lastTime,AGATFactor;
    ArrayList<Integer> quantumList = new ArrayList<>();
    public Process(){}
    public Process (String name,String color,int arrivalTime,int burstTime,int priority){
        this.name = name;
        try {
            setColor(color);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        
        setProcessTime(burstTime);
        responseTime =0;
        lastTime = arrivalTime;
        //AGATFactor ;
    }

    public int getProcessTime() {
        return processTime;
    }

    public void setProcessTime(int processTime) {
        this.processTime = processTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }
    
    public void run(){
        System.out.println("Process " + name + " is running" );
        processTime--;
    }

    @Override
    public int compareTo(Process p) {
        return this.arrivalTime - p.arrivalTime; 
    }
}
