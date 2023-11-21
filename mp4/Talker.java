import java.net.*;
import java.util.concurrent.TimeUnit;

public class Talker {
    public static void main(String[] args) {
        /*
         * if (args.length != 3) {
         * System.out.println("Usage: Talker <listenerIP> <talkerPort> <listenerPort>");
         * return;
         * }
         * 
         * String listenerIP = args[0];
         * int talkerPort = Integer.parseInt(args[1]);
         * int listenerPort = Integer.parseInt(args[2]);
         */
        String listenerIP = "127.0.0.1";
        int talkerPort = 22345;
        int listenerPort = 22346;

        try {
            DatagramSocket socket = new DatagramSocket(talkerPort);
            InetAddress listenerAddress = InetAddress.getByName(listenerIP);

            // Get user input
            /*
             * System.out.print("Enter message (up to 50 characters): ");
             * String input = System.console().readLine();
             */
            // String input = "1123123123123123123";
            String input = "11111111112222222222333333333344444444445555555555";
            int sizeOfChunk = (int) Math.ceil((double) input.length() / 5);
            // Break the message into 10-character chunks
            System.out.printf("SIZE OF CHUNK %d\n", sizeOfChunk);
            int messageIndex = 0;
            String firstMessage = "0 6 N/A";
            byte[] sendData = firstMessage.getBytes();

            // Send the message to the listener
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, listenerAddress,
                    listenerPort);
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            while (true) {
                TimeUnit.SECONDS.sleep(2);
                socket.receive(receivePacket);
                String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(data);
                int numericValue = Character.getNumericValue(data.charAt(0));
                if (numericValue == 0) {
                    messageIndex++;
                    break;
                } else {
                    socket.send(sendPacket);
                }
            }

            System.err.println("past first message \n");

            for (int i = 0; i < 5; i++) {
                int startIndex = i * sizeOfChunk;
                int endIndex = Math.min((i + 1) * sizeOfChunk, input.length());
                String message = input.substring(startIndex, endIndex);

                System.out.printf("start index %d | end index %d \n", startIndex, endIndex);
                // Create the message with sequence number
                String framedMessage = i + " " + sizeOfChunk + " " + message;
                sendData = framedMessage.getBytes();

                // Send the message to the listener
                sendPacket = new DatagramPacket(sendData, sendData.length, listenerAddress,
                        listenerPort);
                socket.send(sendPacket);

                receiveData = new byte[1024];
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                while (true) {
                    System.out.printf(" send file message w/ sequence number %d\n", i + 1);
                    TimeUnit.SECONDS.sleep(2);
                    socket.receive(receivePacket);
                    String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    int numericValue = Character.getNumericValue(data.charAt(0));
                    if (numericValue == messageIndex) {
                        messageIndex++;
                        break;
                    } else {
                        socket.send(sendPacket);
                    }
                }
                // Wait for ACK from the listener

                // Process the ACK
                String ack = new String(receivePacket.getData(), 0, receivePacket.getLength());
                int nextSeqNum = Integer.parseInt(ack);
                System.out.println("Received ACK for message " + i + ", Next sequence number: " + nextSeqNum);
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
