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
        // String serverIp = "127.0.0.1";
        // int serverPort = 51123;
        String serverIp = args[0];
        int serverPort = Integer.parseInt(args[1]);
        runConnect(serverIp, serverPort);
    }

    public static void runConnect(String IP, int serverPort) {
        System.out.printf("Server Port is %s \n", serverPort);
        String modifiedSentence;
        try {
            Socket clientSocket = new Socket(IP, serverPort);

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
            clientSocket.close();

        } catch (IOException e) {
            System.out.println(" connection refused, OR you are running sockets too fast \n");
        }
    }
}