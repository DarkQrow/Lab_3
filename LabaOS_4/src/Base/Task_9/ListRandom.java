package Base.Task_9;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class  ListRandom{
    static LinkedList<String> list = new LinkedList<>();

    ListRandom(){
        StringBuilder str = new StringBuilder();
        try(FileReader reader = new FileReader("C:\\Users\\Павел\\IdeaProjects\\LabaOS_4\\src\\Base\\Task_9\\List.txt")){
            int c;
            while((c = reader.read())!=-1){
                str.append((char)c);
            }
        }catch (IOException e){
            System.out.println(e);
        }
        String[] strings = str.toString().split(" ");
        for(int i = 0;i<strings.length;i++){
            list.add(strings[i]);
        }
    }

    public void addRandom(){
        int num = (int)(0+ Math.random()*10);
        list.add(Integer.toString(num));
        try(FileWriter writer = new FileWriter("C:\\Users\\Павел\\IdeaProjects\\LabaOS_4\\src\\Base\\Task_9\\List.txt"))
        {
            for (int i = 0;i<list.size();i++){
                writer.write(list.get(i)+" ");
        }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }
    public void deleteRandom(){
        int num = (int)(0+Math.random()*list.size()-1);
        list.remove(num);
        try(FileWriter writer = new FileWriter("C:\\Users\\Павел\\IdeaProjects\\LabaOS_4\\src\\Base\\Task_9\\List.txt"))
        {
            for (int i = 0;i<list.size();i++){
                writer.write(list.get(i)+" ");
            }

        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[]args){

      ListRandom list = new ListRandom();
        System.out.println(list.list);
        Thread t = new Thread(() -> {
            while (true) {
                synchronized (list) {
                    int check = (int) (0 + Math.random() * 2);
                    if ((check == 1)&&(list.list.size()<10)) list.addRandom();
                    else if(list.list.size()>1)list.deleteRandom();
                    System.out.println(list.list);
                }
            }
        });
        t.start();
    }
}

