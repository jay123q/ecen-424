import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NativeClient {
    public static int BUFSIZE = 1024;
    public static int pointer = 0;

    public static void main(String[] args) {

        // Scanner scanner = new Scanner(System.in);
        // System.out.print("Enter server ip: ");
        // String serverIp = scanner.next();
        // serverIp = "127.0.0.1";

        // System.out.print("Enter server port ");
        // int serverPort = scanner.nextInt();
        // serverPort = 51123;
        // scanner.close();

        String serverIp = "127.0.0.1";
        int serverPort = 51123;
        runConnect(serverIp, serverPort);
    }

    public static void runConnect(String IP, int serverPort) {
        System.out.printf("Server Port is %s ", serverPort);
        String modifiedSentence;
        // BufferedReader inFromUser = new BufferedReader(new
        // InputStreamReader(System.in));
        try {
            Socket clientSocket = new Socket(IP, serverPort);
            int find = -1;
            while (find == -1) {
                modifiedSentence = processTransmission(clientSocket.getInputStream(), pointer);
                pointer += modifiedSentence.length();
                System.out.println("FROM SERVER: " + modifiedSentence);
                find = modifiedSentence.indexOf('\n');
            }
            clientSocket.close();
        } catch (IOException e) {
            runConnect(IP, serverPort + 1);
        }
    }

    public static String processTransmission(InputStream inputStream, int pointer) throws IOException {
        if (pointer >= BUFSIZE) {
            BUFSIZE *= 2;
        }
        byte[] buffer = new byte[BUFSIZE]; // Adjust the buffer size as needed
        int bytesRead;
        String receivedData = "";
        bytesRead = inputStream.read(buffer);
        if (bytesRead == -1) {
            return "";
        }
        receivedData = new String(buffer, 0, bytesRead);
        // System.out.println("Received: " + receivedData);
        // Process the received data as needed
        return receivedData;

    }

}
