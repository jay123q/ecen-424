import java.net.*;
import java.util.Random;

public class Listener {
    public static void main(String[] args) {
        /*
         * if (args.length != 2) {
         * System.out.println("Usage: Listener <talkerPort> <listenerPort>");
         * return;
         * }
         * int talkerPort = Integer.parseInt(args[0]);
         * int listenerPort = Integer.parseInt(args[1]);
         */
        int talkerPort = 22345;
        int listenerPort = 22346;
        try {
            DatagramSocket socket = new DatagramSocket(listenerPort);
            String finalData = "";

            // Wait for Talker to request a connection
            System.out.println("Waiting for connection from Talker...");
            DatagramPacket connectPacket = new DatagramPacket(new byte[1024], 1024);
            socket.receive(connectPacket);
            Random random = new Random();

            InetAddress talkerAddress = connectPacket.getAddress();
            int talkerPortConnect = connectPacket.getPort();

            // Send ACK to Talker to establish connection
            DatagramPacket ackPacket = new DatagramPacket("ACK".getBytes(), 3, talkerAddress, talkerPortConnect);
            while (true) {
                // Randomly drop ACKs
                if (random.nextDouble() > 0.5) {
                    socket.send(ackPacket);
                    System.out.println("Sent ACK for message " + 0);
                    break;
                } else {
                    System.out.println("Dropped ACK for message " + 0);
                }

            }

            /* */
            // Receive the number of messages to be sent
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

            finalData += message + " ";

            // Randomly drop ACKs with a probability of 0.5

            // Receive and ACK each message
            for (int i = 0; i < 5; i++) {
                byte[] sendData = Integer.toString(i).getBytes();
                DatagramPacket ackSendPacket = new DatagramPacket(sendData, sendData.length, talkerAddress,
                        talkerPortConnect);

                while (true) {
                    // Randomly drop ACKs
                    if (random.nextDouble() > 0.5) {
                        socket.send(ackSendPacket);
                        System.out.println("Sent ACK for message " + i);
                        break;
                    } else {
                        System.out.println("Dropped ACK for message " + i);
                    }

                }

                // Receive the message from Talker
                receiveData = new byte[1024];
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                // Process the message
                message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                finalData += message + " ";
                System.out.println("Received message " + i + ": " + message);
            }

            System.out.println("All messages received. Closing connection.");
            socket.close();
            System.out.printf("All messages are %s\n", finalData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
