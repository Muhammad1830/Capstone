package org.yearup;

import java.util.List;
import java.util.Scanner;


public class LedgerApp
{
    private TransactionManager transactionManager;
    private Scanner scanner;

    public LedgerApp(TransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
        this.scanner = new Scanner(System.in);
    }

    public void displayTransactions(List<Transaction> transactions)
    {
        if (transactions.isEmpty())
        {
            System.out.println("No transactions found.");
        }
        else
        {
            for (Transaction transaction : transactions)
            {
                System.out.println(transaction);
            }
        }

    }

    private void displayMonthToDate()
    {
        List<Transaction> transactions = transactionManager.getMonthToDateTransactions();
        displayTransactions(transactions);
    }

    private void displayPreviousMonth()
    {
        List<Transaction> transactions = transactionManager.getPreviousMonthTransactions();
        displayTransactions(transactions);
    }

    private void displayYearToDate()
    {
        List<Transaction> transactions = transactionManager.getYearToDateTransactions();
        displayTransactions(transactions);
    }

    private void displayPreviousYear()
    {
        List<Transaction> transactions = transactionManager.getPreviousYearTransactions();
        displayTransactions(transactions);
    }

    private void displaySearchByVendor()
    {
        System.out.print("Enter the vendor name: ");
        String vendor = scanner.nextLine().trim();
        List<Transaction> transactions = transactionManager.getTransactionsByVendor(vendor);
        displayTransactions(transactions);
    }

    public void displayReports()
    {
        boolean running = true;

        while (running) {
            System.out.println("\nReports Menu:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice)
            {
                case "1":
                    displayMonthToDate();
                    break;
                case "2":
                    displayPreviousMonth();
                    break;
                case "3":
                    displayYearToDate();
                    break;
                case "4":
                    displayPreviousYear();
                    break;
                case "5":
                    displaySearchByVendor();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }



}
