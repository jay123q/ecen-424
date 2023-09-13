package mp1;

import java.util.Scanner;

public class calc {

    // Fields (instance variables)
    private float arg1;
    private float arg2;
    private float finalArg;
    // private char pickCalc;
    private String fullArgument;
    public Scanner scanner;
    public boolean continueRunning;
    private boolean validInput;

    // Constructors
    public calc() {
        // Default constructor
        // Initialize fields or perform other setup here
        this.continueRunning = true;
        this.scanner = new Scanner(System.in);
    }

    public String getName() {
        Thread.currentThread().setName("mycalc.group 28");
        return Thread.currentThread().getName();
    }

    public void findPickFunction() {

        System.out.println("Welcome to the Calculator designed by " + this.getName());
        System.out.println("Enter A to Add, S to Subtract, M to Multiply, and Q to quit.");
        validInput = true;
        // take user input
        String input = this.scanner.nextLine();

        // Check if the input contains exactly one character
        if (input.matches("^[AMSQ]$")) {
            // Get the first character from the input string
            char userChar = input.charAt(0);
            if (userChar == 'A') {
                this.fullArgument = "sum";
                takeBothInputs();
                this.finalArg = this.Add();
            } else if (userChar == 'S') {
                this.fullArgument = "difference";
                takeBothInputs();
                this.finalArg = this.Subtract();
                // scanner.close();
            } else if (userChar == 'M') {
                this.fullArgument = "product";
                takeBothInputs();
                this.finalArg = this.Multiply();
                // scanner.close();
            } else if (userChar == 'Q') {
                this.continueRunning = false;
                this.validInput = false;
            }
            if (validInput == true) {

                System.out.println(
                        "The " + this.fullArgument + " of " + this.arg1 + " and " + this.arg2 + " is answer "
                                + this.finalArg);
            }
        } else {
            // handles if A, and a valid input then a bad input ooccurs
            validInput = false;
        }
        // scanner.close();
        // scanner.close();
        // delete [] scanner;
        // Close the Scanner

    }

    public float Add() {
        return this.arg1 + this.arg2;

    }

    public float Subtract() {

        return this.arg1 - this.arg2;
    }

    public float Multiply() {
        return this.arg1 * this.arg2;

    }

    public void takeBothInputs() {

        // Scanner takeBothInputsScanner = new Scanner(System.in);

        System.out.println("Enter argument 1");
        String userInput = this.scanner.nextLine();
        // boolean print = true;
        try {
            // Attempt to convert the user input to an integer
            if (userInput.matches("-?\\d+")) {
                // print = false;
                this.arg1 = Float.parseFloat(userInput);
                // caught a char insererd
                // System.out.println(" char caught ");
            } else {
                this.validInput = false;
                return;
            }

            // If successful, print the integer
            // System.out.println("You entered an integer: " + number);
        } catch (NumberFormatException e) {
            // Handle the exception when the conversion fails
            // System.out.println("Invalid input. Please enter a valid integer.");
        }

        System.out.println("Enter argument 2");
        userInput = this.scanner.nextLine();
        try {
            // Attempt to convert the user input to an integer
            if (userInput.matches("-?\\d+")) {
                // print = false;
                this.arg2 = Float.parseFloat(userInput);
                // caught a char insererd
                // System.out.println(" char caught ");
            } else {
                this.validInput = false;
                return;
            }

            // If successful, print the integer
            // System.out.println("You entered an integer: " + number);
        } catch (NumberFormatException e) {
            // Handle the exception when the conversion fails
            // System.out.println("Invalid input. Please enter a valid integer.");
        }
        // takeBothInputsScanner.close();

    }

    // Getters and setters for fields (if needed)

}
