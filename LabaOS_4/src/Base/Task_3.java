package Base;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;



public class Task_3 {

    public static void main(String[] args) throws InterruptedException {
        Semaphore sem = new Semaphore(4); // 4 парковочных места
        Thread t = new Thread(new Runnable() {
            ParkPlace[] Park = new ParkPlace[4];
                @Override
                public void run () {
                    CommonResource res = new CommonResource();
                    res.x = 0;
                    for (int i = 0;i<4;i++){
                        Park[i] = new ParkPlace("Парковное место "+(i+1));
                    }
                try {
                    while (true) {
                        long ParkPlaceTime = (long) (1000 + Math.random() * 2000);
                        int random = (int) (0 + Math.random() * 4);
                        if (Park[random].busy == false) {
                            Park[random].busy = true;
                            sem.acquire();
                        } else {
                            res.x++;
                            System.out.println("Мимо нашей быстрой парковки проехало: "+res.x);
                        }
                        Thread.sleep(ParkPlaceTime);
                    }
                }
                    catch(InterruptedException e){
                        System.out.println("");
                    }
                System.out.println("Check");
                sem.release();
                }
        });
        t.start();
        Thread.sleep(10000);
        t.interrupt();
    }
}
class CommonResource{
    int x=0;
}
class ParkPlace{
    boolean busy;
    String name;
    ParkPlace(String name){
        busy = false;
        this.name = name;
    }
}

