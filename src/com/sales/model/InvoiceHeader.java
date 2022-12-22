
package com.sales.model;

import java.util.ArrayList;


public class InvoiceHeader {
    private int number;
    private String date;
    private String customer;
    private ArrayList<InvoicesLines> lines;
   

    public InvoiceHeader() {
    }

    public InvoiceHeader(int number, String date, String customer) {
        this.number = number;
        this.date = date;
        this.customer = customer;
    }
    public double getTotal(){
        double total = 0.0;
        for (InvoicesLines line : getLines()){
            total += line.getItemTotal();
        }
        
        return total;
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" + "number=" + number + ", date=" + date + ", customer=" + customer + '}';
    }

    public ArrayList<InvoicesLines> getLines() {
        if (lines == null){
            lines = new ArrayList<>();
        }
        return lines;
    }
        public String getAsCSV() {
        return number + "," + date + "," + customer;
    }

   
    
}
