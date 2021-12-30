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

public class sortByPriority implements Comparator<Process> {

    @Override
    public int compare(Process t, Process t1) {

        if (t.getPriority() - t1.getPriority() == 0) {
            return t.getArrivalTime() - t1.getArrivalTime();
        } else {
            return t.getPriority() - t1.getPriority();
        }
    }

}
