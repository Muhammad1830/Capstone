package org.yearup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Transaction
{
    private LocalDateTime dateTime;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDateTime dateTime, String description, String vendor, double amount)
    {
        this.dateTime = dateTime;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime)
    {
        this.dateTime = dateTime;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getVendor()
    {
        return vendor;
    }

    public void setVendor(String vendor)
    {
        this.vendor = vendor;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public String toCSV()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s|%s|%s|%.2f", dateTime.format(formatter), description, vendor, amount);
    }

    public static Transaction fromCSV(String csvLine)
    {
        String[] parts = csvLine.split("\\|");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(parts[0], formatter);
        String description = parts[1];
        String vendor = parts[2];

        double amount = Double.parseDouble(parts[3]);

        return new Transaction(dateTime, description, vendor, amount);
    }


    public String toString()

    {
        return dateTime.toLocalDate() + "|" + dateTime.toLocalTime() + "|"
                + description + "|" + vendor + "|" + amount;
    }


}