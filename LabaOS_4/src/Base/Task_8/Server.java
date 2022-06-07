package Base.Task_8;


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


public class Server extends Thread
{
    private static final int port   = 6666;
    private String TEMPL_MSG = "The client '%d' sent me message : \n\t";
    private String TEMPL_CONN = "The client '%d' closed the connection";

    private  Socket socket;
    private  int    num;

    public Server() {}
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

            ArrayList<int[]> coordinate = new ArrayList<>();
            JFrame frame = new JFrame("Task_9");
            while(true) {

                line = dis.readUTF();
                String[] coordinates;
                coordinates = line.split(" ");
                System.out.println(String.format(TEMPL_MSG, num) + line);
                System.out.println("I'm sending it back...");

                var a = Integer.parseInt(coordinates[0]);
                var b = Integer.parseInt(coordinates[1]);



                int []c = {a,b};
                coordinate.add(c);
                System.out.println("x: "+a+" y: "+b);

                dos.writeUTF("Server receive coordinates  x: " + coordinates[0]+" y: "+coordinates[1]);
                dos.flush();
                //вот тута рисуем
                synchronized (frame) {
                    Test.initAndShowGUI(coordinate);
                }
                System.out.println();
                if (line.equalsIgnoreCase("quit")) {
                    socket.close();
                    System.out.println(
                            String.format(TEMPL_CONN, num));
                    break;
                }

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
                    new Server().setSocket(i++, socket);

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
