package Base.Task_7;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


public class Test_Client
{

    public static int[] GenerateShot(ArrayList<int[]> a){
        int [] result = new int[2];

        result[0] = (int) (0 + Math.random() * 10);
        result [1] = (int) (0 + Math.random() * 10);
        while (a.contains(result)){
            result[0] = (int) (0 + Math.random() * 10);
            result [1] = (int) (0 + Math.random() * 10);
        }

        return result;
    }
    private  static final int    serverPort = 6666;
    private  static final String localhost  = "127.0.0.1";

    public static void main(String[] ar)
    {
        Socket socket = null;
        try{
            try {
                System.out.println("Welcome to Client side\n" +
                        "Connecting to the server\n\t" +
                        "(IP address " + localhost +
                        ", port " + serverPort + ")");
                InetAddress ipAddress;
                ipAddress = InetAddress.getByName(localhost);
                socket = new Socket(ipAddress, serverPort);
                System.out.println(
                        "The connection is established.");
                System.out.println(
                        "\tLocalPort = " +
                                socket.getLocalPort() +
                                "\n\tInetAddress.HostAddress = " +
                                socket.getInetAddress()
                                        .getHostAddress() +
                                "\n\tReceiveBufferSize (SO_RCVBUF) = "
                                + socket.getReceiveBufferSize());

                // Получаем входной и выходной потоки
                // сокета для обмена сообщениями с сервером
                InputStream  sin  = socket.getInputStream();
                OutputStream sout = socket.getOutputStream();

                DataInputStream  in ;
                DataOutputStream out;
                in  = new DataInputStream (sin );
                out = new DataOutputStream(sout);

                // Создаем поток для чтения с клавиатуры.
                InputStreamReader isr;
                isr = new InputStreamReader(System.in);
                BufferedReader keyboard;
                keyboard = new BufferedReader(isr);
                String line = null;
                System.out.println("Generate shots of the players... ");
                System.out.println();

                ArrayList<int[]>playerfirst = new ArrayList<>();
                ArrayList<int[]>playersecond = new ArrayList<>();
                while (true) {
                    // Пользователь должен ввести строку
                    // и нажать Enter
                    int[]shotFirst = GenerateShot(playerfirst);
                    int[]shotSecond = GenerateShot(playersecond);
                    StringBuilder Shots = new StringBuilder();
                    for(int i = 0;i<4;i++){
                        if(i<2)
                            Shots.append(shotFirst[i]);
                        if(i>1)
                            Shots.append(shotSecond[i-2]);
                        Shots.append(" ");
                    }

                    // Отсылаем строку серверу
                    out.writeUTF(Shots.toString());
                    // Завершаем поток
                    out.flush();
                    // Ждем ответа от сервера
                    line = in.readUTF();
                    if (line.endsWith("quit"))
                        break;
                    else {
                        System.out.println(
                                "The server sent me this line :\n\t"
                                        + line);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
