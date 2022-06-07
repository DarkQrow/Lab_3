package Base.Task_9_Test;
import java.util.LinkedList;
import java.util.Random;

public class first {
    LinkedList<Integer> ll = new LinkedList<Integer>();

    public first(int k){
        Random r = new Random();
        for (int i = 0; i < k; i++){
            ll.add(r.nextInt(100));
        }
    }

    public void randomInLL(){
        Random r = new Random();
        int rand = r.nextInt(100);
        if (rand % 2 == 0){
            ll.add(r.nextInt(100));
        } else {
            if (ll.size() > 0) {
                ll.remove(r.nextInt(ll.size()));
            }
        }
    }
}
