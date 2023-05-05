package org.yearup;

import java.util.*;
import java.io.*;
public class TransactionManager
{
    private String filePath = "transactions.csv";


    public void addTransaction(Transaction transaction)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true)))
        {
            String line = transaction.toCSV();
            writer.write(line);
            writer.newLine();
        }

        catch (IOException e)
        {
            System.out.println("Error writing transaction to file: " + e.getMessage());
        }
    }


    public List<Transaction> getAllTransactions()
    {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                Transaction transaction = Transaction.fromCSV(line);
                transactions.add(transaction);
            }
        }

        catch (IOException e)
        {
            System.out.println("Error reading transactions from file: " + e.getMessage());
        }

        return transactions;
    }


    public List<Transaction> filterDeposits(List<Transaction> transactions)
    {
        List<Transaction> deposits = new ArrayList<>();
        for (Transaction transaction : transactions)
        {
            if (transaction.getAmount() > 0)
            {
                deposits.add(transaction);
            }
        }
        return deposits;
    }


    public List<Transaction> filterPayments(List<Transaction> transactions)
    {
        List<Transaction> payments = new ArrayList<>();
        for (Transaction transaction : transactions)
        {
            if (transaction.getAmount() < 0)
            {
                payments.add(transaction);
            }
        }
        return payments;
    }


    public List<Transaction> filterByVendor(List<Transaction> transactions, String vendor)
    {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions)
        {
            if (transaction.getVendor().equalsIgnoreCase(vendor))
            {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

}
