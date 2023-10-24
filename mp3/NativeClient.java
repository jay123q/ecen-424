import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NativeClient {
    public static int BUFSIZE = 1024;
    public static int pointer = 0;

    public static void main(String[] args) {

        // serverIp = "127.0.0.1";
        // serverPort = 51123;
        

        String serverIp = args[0];
        int serverPort = Integer.parseInt(args[1]);
        while (true) {
            try {
                Socket clientSocket = new Socket(serverIp, serverPort);
                runConnect(clientSocket, serverIp, serverPort);

                break;
            } catch (IOException e) {
                System.out.println(" connection refused, OR you are running sockets too fast \n");

                // e.printStackTrace();
                // serverPort++;
            }
        }
    }

    public static void runConnect(Socket clientSocket, String IP, int serverPort) {
        System.out.printf("Server Port is %s \n", serverPort);
        String modifiedSentence;
        try {
            int find = -1;
            while (find == -1) {
                modifiedSentence = processTransmission(clientSocket.getInputStream(), pointer);
                pointer += modifiedSentence.length();
                System.out.println("FROM SERVER: " + modifiedSentence);
                find = modifiedSentence.indexOf('\n');
            }
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(" connection refused, OR you are running sockets too fast \n");

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
        return receivedData;

    }

}
