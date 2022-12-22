
package com.sales.model;


public class InvoicesLines {
    
    private String item;
    private double price;
    private int count;
    private InvoiceHeader invoice; 

    public InvoicesLines() {
    }

  

    public InvoicesLines(String item, double price, int count, InvoiceHeader invoice) {      
        this.item = item;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
    }
    public double getItemTotal(){
        return price * count;
    }

   

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "InvoicesLines{" + "number=" + invoice.getNumber() + ", item=" + item + ", price=" + price + ", count=" + count + '}';
    }

    public InvoiceHeader getInvoice() {
        return invoice;
    }
        public String getAsCSV() {
        return invoice.getNumber() + "," + item + "," + price + "," + count;
    }

    
    
}
