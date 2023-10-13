import java.io.*;
import java.net.*;
import java.util.Scanner;

public class BufferClient {
    public static void main(String[] args) {
        /*
         * 
         * Scanner scanner = new Scanner(System.in);
         * System.out.print("Enter server ip: ");
         * String serverIp = scanner.next();
         * 
         * System.out.print("Enter server port ");
         * int serverPort = scanner.nextInt();
         * 
         * scanner.close();
         */
        String serverIp = "127.0.0.1";
        int serverPort = 51123;
        runConnect(serverIp, serverPort);
    }

    public static void runConnect(String IP, int serverPort) {
        System.out.printf("Server Port is %s \n", serverPort);
        String modifiedSentence;
        // BufferedReader inFromUser = new BufferedReader(new
        // InputStreamReader(System.in));
        try {
            Socket clientSocket = new Socket(IP, serverPort + 1);

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
            clientSocket.close();

        } catch (IOException e) {
            e.getStackTrace();
            runConnect(IP, serverPort + 1);
        }
    }
}