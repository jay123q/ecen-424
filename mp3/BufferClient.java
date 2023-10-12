import java.io.*;
import java.net.*;
import java.util.Scanner;

public class BufferClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server ip: ");
        String serverIp = scanner.next();
        serverIp = "127.0.0.1";

        System.out.print("Enter server port ");
        int serverPort = scanner.nextInt();
        serverPort = 51123;

        String modifiedSentence;
        try {
            Socket clientSocket = new Socket(serverIp, serverPort);

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();
    }

}
