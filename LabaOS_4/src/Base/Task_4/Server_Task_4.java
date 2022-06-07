package Base.Task_4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.LogStream;
import java.util.stream.Stream;

public class Server_Task_4 extends Thread {
    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    public static void main(String[]args) throws IOException {
try {
    try {
        server = new ServerSocket(4242);
        System.out.println("Server started Task_4");
        clientSocket = server.accept();//wait until client connect
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//stream in
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));// stream out

            String filename = in.readLine();
            String[] FileNames;
            FileNames = filename.split(" ");
            out.write("Task is affirmative. Files names are " + FileNames[0]+", "+FileNames[1]);
            out.flush();
            StringBuilder str = new StringBuilder();
            try(FileReader reader = new FileReader("C:\\Users\\Павел\\IdeaProjects\\LabaOS_4\\src\\Base\\Task_4\\"+FileNames[0]+".txt")){
                int c;
                while((c = reader.read())!=-1){
                    str.append((char)c);
                }
            }
            System.out.println(str);
            try (FileWriter writer = new FileWriter("C:\\Users\\Павел\\IdeaProjects\\LabaOS_4\\src\\Base\\Task_4\\"+FileNames[1]+".txt")) {
                writer.write(str.toString());
            }
        } finally {
            clientSocket.close();
            in.close();
            out.close();
        }
    } finally {
        System.out.println("Server Task_4 Finished his work");
    }
}catch (IOException e){
    System.out.println(e);
}
    }

}
