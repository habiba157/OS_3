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
public class sortByAT implements Comparator<Process>{

    @Override
    public int compare(Process p1, Process p2) {
        return p1.getArrivalTime() - p2.getArrivalTime();
    }
    
}
