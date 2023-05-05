package org.yearup;

public class Transaction

{
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(String date, String time, String description, String vendor, double amount)
    {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public String getDate()
    {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getVendor()
    {
        return vendor;
    }


    public String toString()
    {
        return String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount);
    }
}