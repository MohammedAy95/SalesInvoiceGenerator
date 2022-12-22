
package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class LinesTableModel extends AbstractTableModel {
    private ArrayList<InvoicesLines> lines;
    private String[] columns = {"No.", "item Name", "itemP rice", "count","item Total"};

    public LinesTableModel(ArrayList<InvoicesLines> lines) {
        this.lines = lines;
    }

    public ArrayList<InvoicesLines> getLines() {
        return lines;
    }
    
   
     
    @Override
    public int getRowCount() {
        return lines.size();
        
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
        InvoicesLines line = lines.get(rowIndex);
        
        switch (columnIndex){
            case 0: return line.getInvoice().getNumber();
            case 1: return line.getItem();
            case 2: return line.getPrice();
            case 3: return line.getCount();
            case 4: return line.getItemTotal();
            default : return "";
        }
        
    }

    public void fireTableChanged() {
       
    }
    
}
