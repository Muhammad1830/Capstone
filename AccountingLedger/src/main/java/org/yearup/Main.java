package org.yearup;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main

{
    private LedgerApp ledger;
    private TransactionManager transactionManager;
    private Scanner scanner;

    public Main()
    {
        this.transactionManager = new TransactionManager();
        this.ledger = new LedgerApp(transactionManager);
        this.scanner = new Scanner(System.in);
    }


    public void run()

    {
        boolean running = true;

        while (running)
        {
            System.out.println("\nHome Screen:");
            System.out.println("A) All Transactions");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("C) Clear Transactions by DateTime");
            System.out.println("CA) Clear All Transactions");
            System.out.println("R) Reports");
            System.out.println("X) Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice)
            {
                case "A":
                    ledger.displayTransactions(transactionManager.getAllTransactions());
                    break;
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "C":
                    clearTransactionsByDateTime();
                    break;
                case "CA":
                    clearAllTransactions();
                    break;
                case "R":
                    ledger.displayReports();
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public void addDeposit()

    {
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
        scanner.nextLine();

        Transaction deposit = new Transaction(date, time, description, vendor, amount);
        transactionManager.addTransaction(deposit);
    }


    public void makePayment()

    {
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        System.out.print("Enter the time (HH:mm:ss): ");
        String time = scanner.nextLine().trim();

        System.out.print("Enter the description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine().trim();

        System.out.print("Enter the amount: ");
        double amount = scanner.nextDouble() * -1;
        scanner.nextLine();

        Transaction payment = new Transaction(date, time, description, vendor, amount);
        transactionManager.addTransaction(payment);
    }

    private void clearTransactionsByDateTime()
    {
        System.out.print("Enter start date and time (yyyy-MM-dd HH:mm:ss): ");
        String startDateTimeStr = scanner.nextLine().trim();
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.print("Enter end date and time (yyyy-MM-dd HH:mm:ss): ");
        String endDateTimeStr = scanner.nextLine().trim();
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        transactionManager.clearTransactionsByDateTime(startDateTime, endDateTime);
        System.out.println("Transactions cleared between " + startDateTimeStr + " and " + endDateTimeStr);
    }

    private void clearAllTransactions()
    {
        transactionManager.clearAllTransactions();
        System.out.println("All transactions have been cleared.");
    }


    public static void main(String[] args)
    {
        Main app = new Main();
        app.run();
    }


}