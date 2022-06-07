package Base.Task_9;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class ListSort {

public static void main(String[]args){
    Thread t = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            StringBuilder str = new StringBuilder();
            try (FileReader reader = new FileReader("C:\\Users\\Павел\\IdeaProjects\\LabaOS_4\\src\\Base\\Task_9\\List.txt")) {
                int c;
                while ((c = reader.read()) != -1) {
                    str.append((char) c);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
            LinkedList<String> list = new LinkedList<>();
            String[] strings = str.toString().split(" ");
            for (int i = 0; i < strings.length; i++) {
                list.add(strings[i]);
            }
            Collections.sort(list);
            System.out.println(list);
            try (FileWriter writer = new FileWriter("C:\\Users\\Павел\\IdeaProjects\\LabaOS_4\\src\\Base\\Task_9\\List.txt")) {
                for (int i = 0; i < list.size(); i++) {
                    writer.write(list.get(i) + " ");
                }
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    });
    t.start();
}

}
