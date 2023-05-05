package org.yearup;

import java.util.Scanner;

public class Main
{
    private LedgerApp ledger;
    private TransactionManager transactionManager;
    private Scanner scanner;

    public Main() {
        this.transactionManager = new TransactionManager();
        this.ledger = new LedgerApp(transactionManager);
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("\nHome Screen:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("C) Clear All Transactions");
            System.out.println("X) Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    ledger.displayAll();
                    break;
                case "C":
                    clearAllTransactions();
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void addDeposit() {
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        System.out.print("Enter the time (HH:mm:ss): ");
        String time = scanner.nextLine().trim();

        System.out.print("Enter the description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine().trim();

        System.out.print("Enter the amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character left by nextDouble()

        Transaction deposit = new Transaction(date, time, description, vendor, amount);
        transactionManager.addTransaction(deposit);
    }

    public void makePayment() {
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        System.out.print("Enter the time (HH:mm:ss): ");
        String time = scanner.nextLine().trim();

        System.out.print("Enter the description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine().trim();

        System.out.print("Enter the amount: ");
        double amount = scanner.nextDouble() * -1; // Convert the amount to a negative value
        scanner.nextLine(); // Consume the newline character left by nextDouble()

        Transaction payment = new Transaction(date, time, description, vendor, amount);
        transactionManager.addTransaction(payment);
    }

    public void clearAllTransactions() {
        transactionManager.clearTransactions();
        System.out.println("All transactions have been cleared.");
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }
}