package Base;

import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.Scanner;

class ReadWriteThread extends Thread{
    String filename;

    ReadWriteThread() {
        filename = "C:\\Users\\Ирочка\\IdeaProjects\\LabaOS_4\\src\\Base\\1.txt";
    }
ReadWriteThread(String x){
    filename = "C:\\Users\\Ирочка\\IdeaProjects\\LabaOS_4\\src\\Base\\"+x+".txt";
}
@Override
    public void run(){

            StringBuilder FileText = new StringBuilder();
            try (FileReader reader = new FileReader(filename)) {
                synchronized (reader) {
                    int c;
                    while ((c = reader.read()) != -1) {
                        FileText.append((char) c);
                    }
                }
                System.out.println(FileText);
            } catch (IOException e) {
                System.out.println("Что то пошло не так при чтении файла");
            }


    }

}



public class Task_1 {
    private static final Boolean key = true;
    public static void main(String[] args) {

	ReadWriteThread test = new ReadWriteThread();
    test.start();

        StringBuilder FileText = new StringBuilder();
        try (FileReader reader = new FileReader("C:\\Users\\Ирочка\\IdeaProjects\\LabaOS_4\\src\\Base\\1.txt")) {
            int c;
            while ((c = reader.read()) != -1) {
                FileText.append((char) c);
            }
            FileText.append("test");
            System.out.println(FileText);
        } catch (IOException e) {
            System.out.println("Что то пошло не так при чтении файла");
        }
        System.out.println("Главный поток завершён...");

    }
}
