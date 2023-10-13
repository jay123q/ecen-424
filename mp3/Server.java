
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
        // System.out.println(args);
        // Scanner scanner = new Scanner(System.in);
        //
        // System.out.print("Enter server port: ");
        // int serverPort = scanner.nextInt();
        // serverPort = 51123;
        //
        // System.out.print("Enter the maximum number of clients: ");
        // System.out.print("Enter the string to transmit: ");
        // int maxClients = scanner.nextInt();
        // String strToSend = scanner.next();
        //
        // System.out.print("Enter the number of repetitions: ");
        // int repetitions = scanner.nextInt();
        // System.out.println(
        // "Server listening on port " + serverPort + " for a maximum of " + maxClients
        // + " clients.");
        // scanner.close();
        //

        String strToSend = "12,";
        int serverPort = 51123;
        int maxClients = 10;
        int repetitions = 10;
        while (true) {

            while (clientCount < maxClients) {
                System.out.printf(" the amount of clients BEFORE threads %d \n", clientCount);
                runServer(serverPort, strToSend, repetitions);
                System.out.printf(" the amount of clients AFTER threads %d \n", clientCount);

            }
            System.out.println(" figure where stralled \n ");
        }
    }

    public static void runServer(int serverPort, String strToSend, int repetitions) {

        try {
            System.out.printf("Server Port is: %d \n", serverPort);
            ServerSocket serverSocket = new ServerSocket(serverPort);
            // PortCheckList.add(serverPort);
            Socket clientSocket = serverSocket.accept();
            clientCount++;
            System.out.printf(" count client threads increment %d \n", clientCount);
            System.out.println("Accepted connection from " + clientSocket.getInetAddress());
            ClientHandler clientHandler = new ClientHandler(clientSocket, strToSend, repetitions);
            clientHandler.run();
            clientHandler.stop();
            // new Thread(clientHandler).start();
            System.out.printf("\n\n ended thread currently running clients = %d \n\n", serverPort);
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.printf(" port collison @ %d \n", serverPort);
            runServer(serverPort + 1, strToSend, repetitions);
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
        exit = false;
        t.start();
    }

    public void stop() {
        exit = true;
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
            while (!exit) {

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
                System.out.println("111111111111111");
                clientSocket.close();
                System.out.println("2222222222222222");
                // Server.clientCount--;
                System.out.printf(" count client threads decrement %d \n", Server.clientCount);
                clientSocket.close();
                System.out.printf(" Close socket ");
                t.join();
                System.out.printf(" Close threads ");
                Server.clientCount--;
                return;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("333333333333333");
            e.printStackTrace();
        }
    }
}
