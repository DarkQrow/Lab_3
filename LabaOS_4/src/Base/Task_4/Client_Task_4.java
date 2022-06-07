package Base.Task_4;

import java.io.*;
import java.net.Socket;

public class Client_Task_4 {

    private static Socket clientSocket;
    private static BufferedReader readerFileName;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[]args){
        try{
            try{
                clientSocket= new Socket("localhost",4242);//adress - localhost,port - the same as server
                //asking for connection from the server
                readerFileName = new BufferedReader(new InputStreamReader(System.in));//reading filenames
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//reading info from the server
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));//write to the server
                System.out.println("Write names of the copying files(Use Space): ");
                String FileName = readerFileName.readLine();//Wait until user write something
                out.write(FileName+"\n");
                out.flush();
                String serverWord = in.readLine();
                System.out.println(serverWord);
            }finally {
                System.out.println("Client was closed");
                clientSocket.close();
                in.close();
                out.close();
            }
        }catch (IOException e){
            System.err.println(e);
        }

    }
}
