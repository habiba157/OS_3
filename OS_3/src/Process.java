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

public class Process {

    String name;
    Color color;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int responseTime;
    int turnaroundTime;
    int waitingTime;
    int processing;
    int priority;
    int remainingTime;
    int quantumTime;
    int AGATFactor;
    boolean done = false;
    ArrayList<Integer> historyOfQuantum = new ArrayList<>();

    public Process() {

    }

    public Process(String name, Color color, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        setColor(color);
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public int getProcessing() {
        return processing;
    }

    public void setProcessing(int processing) {
        this.processing = processing;
    }

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

    public void setColor(Color color) {
        this.color = color;
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

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
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
        this.quantumTime = quantumTime;
    }

    public int getAGATFactor() {
        return AGATFactor;
    }

    public void setAGATFactor(int AGATFactor) {
        this.AGATFactor = AGATFactor;
    }

    public void run() {
        
        processing--;
        System.out.println("Excection order is -> " + this.getName());
        
    }

}
