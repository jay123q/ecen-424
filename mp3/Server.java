
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static int clientCount = 0;
    public static List<Integer> PortCheckList = new ArrayList<Integer>();

    public static void main(String[] args) {
        //
        //

        String strToSend = args[0];
        int serverPort = Integer.parseInt(args[1]);
        int maxClients = Integer.parseInt(args[2]);
        int repetitions = Integer.parseInt(args[3]);
        int countToBusy = 0;
        // String strToSend = "12,";
        // int serverPort = 51123;
        // int maxClients = 2;
        // int repetitions = 20;
        boolean printSupression = true;
        while (true) {

            while (clientCount < maxClients) {
                runServer(serverPort, strToSend, repetitions);
                printSupression = true;

            }
            if (printSupression) {

                System.out.println(" waiting for threads \n ");
                printSupression = false;
            }
            if (countToBusy == 10000) {

                System.out.printf(" busy print to hold thread here, this will be winnowed otherwise client count %d \n",
                        clientCount);
            }

            countToBusy++;
        }
    }

    public static void runServer(int serverPort, String strToSend, int repetitions) {

        try {
            // System.out.printf("Server Port is: %d \n", serverPort);
            ServerSocket serverSocket = new ServerSocket(serverPort);
            Socket clientSocket = serverSocket.accept();
            // System.out.printf(" count client threads increment %d \n", clientCount);
            System.out.println("Accepted connection from " + clientSocket.getInetAddress());
            ClientHandler clientHandler = new ClientHandler(clientSocket, strToSend, repetitions);
            // System.out.printf(" do we return \n");
        } catch (IOException e) {
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final String strToSend;
    private final int repetitions;
    private boolean exit;
    Thread t;

    public ClientHandler(Socket clientSocket, String strToSend, int repetitions) {
        this.clientSocket = clientSocket;
        this.strToSend = strToSend;
        this.repetitions = repetitions;
        t = new Thread(this);
        t.start();
    }

    @Override
    // hi josh here https://www.geeksforgeeks.org/overriding-in-java/#
    // i barely understand what this does more than parroting what geeks for geeks
    // say
    // I could not figure out a way to make a thread to handle each connection, AND
    // then run a child function, so made a class to do it
    // thus override is used to mash the new class into the thread and pass in the
    // params, AND run the new main. I think ????
    // this is likely I just dont use java much and this is my second time using it
    // ever.
    public void run() {
        try {
            Server.clientCount++;
            OutputStream outputStream = clientSocket.getOutputStream();
            for (int i = 0; i < repetitions; i++) {
                System.out.println(i);
                if (i == repetitions - 1) {
                    String modifiedMessage = strToSend + '\n';
                    // System.out.println("string to send! " + strToSend);
                    outputStream.write(modifiedMessage.getBytes());
                } else {
                    // System.out.println("string to send! " + strToSend);
                    outputStream.write(strToSend.getBytes());
                }
                Thread.sleep(1000); // Sleep for one second
            }

            // The last transmission without sleep
            // outputStream.write(strToSend.getBytes());

            System.out.printf(" Close socket ");
            clientSocket.close();
            Server.clientCount--;
            System.out.printf(" Running threads left is %d \n", Server.clientCount);
            // System.out.printf(" Close threads ");

        } catch (IOException |

                InterruptedException e) {
            System.out.println("333333333333333");
            e.printStackTrace();
        }
    }
}