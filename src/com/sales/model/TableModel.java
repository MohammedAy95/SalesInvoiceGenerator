
package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class TableModel extends AbstractTableModel {
    private ArrayList<InvoiceHeader> invoices;

    /*public ArrayList<InvoiceHeader> getInvoices() {
        return invoices; 
    }*/
    private String[] columns = {"No.", "Date", "Customer", "Total"};

    public TableModel(ArrayList<InvoiceHeader> invoices) {
        this.invoices = invoices;
    }
   
     
    @Override
    public int getRowCount() {
        return invoices.size();
        
    }

    @Override
    public int getColumnCount() {
        return columns.length;    
    }
    public String getColumnName(int column){
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader invoice = invoices.get(rowIndex);
        
        switch (columnIndex){
            case 0: return invoice.getNumber();
            case 1: return invoice.getDate();
            case 2: return invoice.getCustomer();
            case 3: return invoice.getTotal();
            default : return "";
        }
        
    }

    public int getSelectedRow() {
        return 0;
        
    }

    
    
}
