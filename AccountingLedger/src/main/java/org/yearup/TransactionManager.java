package org.yearup;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.List;
import java.io.*;
import java.util.ArrayList;

public class TransactionManager
{
    private static final String FILENAME = "transactions.csv";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private String fileName = "transactions.csv";


    public void addTransaction(Transaction transaction)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true)))
        {
            writer.write(transaction.toString());
            writer.newLine();
        }

        catch (IOException e)
        {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    public List<Transaction> getAllTransactions()
    {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String line;

            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\|");
                Transaction transaction = new Transaction(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]));
                transactions.add(transaction);
            }
        }

        catch (IOException e)
        {
            System.err.println("Error reading from the file: " + e.getMessage());
        }

        return transactions;
    }

    public void clearAllTransactions()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME)))
        {

        }

        catch (IOException e) {
            System.out.println("Error while clearing the transactions file: " + e.getMessage());
        }
    }

    public void clearTransactionsByDateTime(LocalDateTime start, LocalDateTime end)
    {
        List<Transaction> transactions = getAllTransactions();
        List<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : transactions)
        {
            LocalDateTime transactionDateTime = LocalDateTime.parse(
                    transaction.getDate() + "T" + transaction.getTime(),
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME
            );

            if (transactionDateTime.isBefore(start) || transactionDateTime.isAfter(end))
            {
                filteredTransactions.add(transaction);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME)))
        {
            for (Transaction transaction : filteredTransactions)
            {
                writer.write(transaction.toString());
                writer.newLine();
            }
        }

        catch (IOException e)
        {
            System.out.println("Error while writing to the file: " + e.getMessage());
        }
    }



    public List<Transaction> getTransactionsByVendor(String vendor)
    {
        return getAllTransactions().stream()
                .filter(t -> t.getVendor().equalsIgnoreCase(vendor))
                .collect(Collectors.toList());
    }

    public List<Transaction> getMonthToDateTransactions()
    {
        LocalDate now = LocalDate.now();
        return getAllTransactions().stream()
                .filter(t ->
                {
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                    return transactionDate.getMonthValue() == now.getMonthValue()
                            && transactionDate.getYear() == now.getYear();
                })
                .collect(Collectors.toList());
    }

    public List<Transaction> getPreviousMonthTransactions()
    {
        LocalDate now = LocalDate.now();
        LocalDate previousMonth = now.minusMonths(1);
        return getAllTransactions().stream()
                .filter(t ->
                {
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                    return transactionDate.getMonthValue() == previousMonth.getMonthValue()
                            && transactionDate.getYear() == previousMonth.getYear();
                })
                .collect(Collectors.toList());
    }

    public List<Transaction> getYearToDateTransactions()
    {
        LocalDate now = LocalDate.now();
        return getAllTransactions().stream()
                .filter(t ->
                {
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                    return transactionDate.getYear() == now.getYear();
                })
                .collect(Collectors.toList());
    }

    public List<Transaction> getPreviousYearTransactions()
    {
        LocalDate now = LocalDate.now();
        LocalDate previousYear = now.minusYears(1);
        return getAllTransactions().stream()
                .filter(t ->
                {
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                    return transactionDate.getYear() == previousYear.getYear();
                })
                .collect(Collectors.toList());
    }

}
