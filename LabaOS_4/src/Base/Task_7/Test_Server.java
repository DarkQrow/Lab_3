package Base.Task_7;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Test_Server extends Thread
{
    private static final int port   = 6666;
    private String TEMPL_MSG = "The client '%d' sent me message : \n\t";
    private String TEMPL_CONN = "The client '%d' closed the connection";

    private  Socket socket;
    private  int    num;

    public Test_Server() {}
    public void setSocket(int num, Socket socket)
    {
        this.num    = num;
        this.socket = socket;
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    public void run()
    {
        try {
            InputStream  sin  = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream  dis = new DataInputStream (sin );
            DataOutputStream dos = new DataOutputStream(sout);
            String line = "";
            Memory mem = new Memory();
            int countTurns = 0;

            while(!mem.CheckWin()) {
                line = dis.readUTF();
                String[] coordinates;
                coordinates = line.split(" ");
                System.out.println(String.format(TEMPL_MSG, num) + line);
                System.out.println("I'm sending it back...");

                mem.Shot(mem.first,Integer.parseInt(coordinates[0]),Integer.parseInt(coordinates[1]));
                mem.Shot(mem.second,Integer.parseInt(coordinates[2]),Integer.parseInt(coordinates[3]));
                System.out.println("x1: "+coordinates[0]+" y1: "+coordinates[1]+" x2: "+coordinates[2]+" y2: "+coordinates[3]);

                dos.writeUTF("Player 1 Shot: " + coordinates[0] + ", " + coordinates[1] + " Player 2 Shot: " + coordinates[2] + ", " + coordinates[3]);
                dos.flush();
                //вот тута рисуем
                System.out.println();
                if (line.equalsIgnoreCase("quit")) {
                    socket.close();
                    System.out.println(
                            String.format(TEMPL_CONN, num));
                    break;
                }
              //  countTurns++;

            }
        } catch(Exception e) {
            System.out.println("Exception : " + e);
        }
    }
    public static void main(String[] ar)
    {
        ServerSocket srvSocket = null;
        try {
            try {
                int i = 0;
                InetAddress ia;
                ia = InetAddress.getByName("localhost");
                srvSocket = new ServerSocket(port, 0, ia);

                System.out.println("Server started\n\n");

                while(true) {
                    Socket socket = srvSocket.accept();
                    System.err.println("Client accepted");
                    new Test_Server().setSocket(i++, socket);

                }


            } catch(Exception e) {
                System.out.println("Exception : " + e);
            }
        } finally {
            try {
                if (srvSocket != null)
                    srvSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }
}
