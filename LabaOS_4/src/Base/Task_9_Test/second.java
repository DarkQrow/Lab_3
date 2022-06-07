package Base.Task_9_Test;

import java.util.Collections;
import java.util.LinkedList;

public class second {

    public static void sortLL(LinkedList ll){
        Collections.sort(ll);
    }

    public static void printLL(LinkedList ll){
        for (int i = 0; i < ll.size(); i++){
            System.out.print(ll.get(i) + " ");
        }
        System.out.println();
    }
}
