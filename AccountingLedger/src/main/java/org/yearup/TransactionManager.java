package org.yearup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class TransactionManager {
    private String fileName = "transactions.csv";

    public void clearTransactions()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            writer.write(""); // Write an empty string to the file to clear its contents
        } catch (IOException e) {
            System.err.println("Error clearing transactions from the file: " + e.getMessage());
        }
    }

    public void addTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(transaction.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                Transaction transaction = new Transaction(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]));
                transactions.add(transaction);
            }
        } catch (IOException e) {
            System.err.println("Error reading from the file: " + e.getMessage());
        }

        // Reverse the list to show newest entries first
        Collections.reverse(transactions);
        return transactions;
    }

    public List<Transaction> getDeposits() {
        return getAllTransactions().stream()
                .filter(t -> t.getAmount() > 0)
                .collect(Collectors.toList());
    }

    public List<Transaction> getPayments() {
        return getAllTransactions().stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByVendor(String vendor) {
        return getAllTransactions().stream()
                .filter(t -> t.getVendor().equalsIgnoreCase(vendor))
                .collect(Collectors.toList());
    }

    public List<Transaction> getMonthToDateTransactions() {
        LocalDate now = LocalDate.now();
        return getAllTransactions().stream()
                .filter(t -> {
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                    return transactionDate.getMonthValue() == now.getMonthValue()
                            && transactionDate.getYear() == now.getYear();
                })
                .collect(Collectors.toList());
    }

    public List<Transaction> getPreviousMonthTransactions() {
        LocalDate now = LocalDate.now();
        LocalDate previousMonth = now.minusMonths(1);
        return getAllTransactions().stream()
                .filter(t -> {
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                    return transactionDate.getMonthValue() == previousMonth.getMonthValue()
                            && transactionDate.getYear() == previousMonth.getYear();
                })
                .collect(Collectors.toList());
    }

    public List<Transaction> getYearToDateTransactions() {
        LocalDate now = LocalDate.now();
        return getAllTransactions().stream()
                .filter(t -> {
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                    return transactionDate.getYear() == now.getYear();
                })
                .collect(Collectors.toList());
    }

    public List<Transaction> getPreviousYearTransactions() {
        LocalDate now = LocalDate.now();
        LocalDate previousYear = now.minusYears(1);
        return getAllTransactions().stream()
                .filter(t -> {
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                    return transactionDate.getYear() == previousYear.getYear();
                })
                .collect(Collectors.toList());
    }
}
