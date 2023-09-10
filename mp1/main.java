package mp1;

import mp1.calc;

public class main {
    public static void main(String[] args) {

        Thread myThread = new Thread(() -> {
            // Inside the thread's run method
            // System.out.println("Thread name: " + Thread.currentThread().getName());
        });

        // Set the thread's name
        

        // Start the thread
        myThread.start();
        calc myCalc = new calc();
        // myCalc.cal
        while (myCalc.continueRunning == true) {
            myCalc.findPickFunction();
        }
        // myCalc.scanner.close();
        // Wait for the thread to finish
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Outside the thread

    }
}
