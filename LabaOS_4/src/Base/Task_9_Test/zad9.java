package Base.Task_9_Test;

public class zad9 {
    public static void main(String[] args){
        int time = 0;
        first f = new first(50);
        while (time < 60){
            f.randomInLL();
            if (time % 10 == 0){
                second.sortLL(f.ll);
                second.printLL(f.ll);
            }
            time++;
        }
    }
}
