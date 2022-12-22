
package com.sales.controller;

import com.sales.model.InvoiceHeader;
import com.sales.model.InvoicesLines;
import com.sales.model.LinesTableModel;
import com.sales.model.TableModel;
import com.sales.view.InvoiceDialog;
import com.sales.view.InvoiceTable;
import com.sales.view.LineDialog;
//import java.awt.List;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class salesController implements ActionListener, ListSelectionListener {

    private InvoiceTable table;
    private InvoiceDialog invoiceDialog;
    private LineDialog lineDialog;
    public salesController (InvoiceTable table){
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);

        switch(actionCommand){
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case "createInvoiceOK":
                createInvoiceOK();
                break;
            case "createLineOK":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = table.getInvoiceTable().getSelectedRow();                     //create the header table
        if (selectedIndex != -1){
            InvoiceHeader choosenInvoice = table.getInvoices().get(selectedIndex);
            System.out.println("You have selected row: " + selectedIndex);
            table.getInvoiceNumLabel().setText(""+choosenInvoice.getNumber());
            table.getInvoiceDateText().setText(choosenInvoice.getDate());
            table.getCustomerNameText().setText(choosenInvoice.getCustomer());
            table.getInvoiceTotalLabel().setText(""+choosenInvoice.getTotal());

            LinesTableModel linesTableModel = new LinesTableModel(choosenInvoice.getLines());      //create the lines table
            table.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableChanged();
        }
    }

    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showOpenDialog(table);                           //load the header file
            if (result == JFileChooser.APPROVE_OPTION){
                File invoiceHeaderFile = fc.getSelectedFile();
                Path invoiceHeaderPath = Paths.get(invoiceHeaderFile.getAbsolutePath());
                List<String> invoiceHeaderLines = Files.readAllLines(invoiceHeaderPath);

                ArrayList<InvoiceHeader> invoicesArray = new ArrayList<>();
                for(String invoiceHeaderLine : invoiceHeaderLines){
                    try{
                        String[] invoiceHeaderParts = invoiceHeaderLine.split(",");
                        int invoiceNumber = Integer.parseInt(invoiceHeaderParts[0]);
                        String invoiceDate = invoiceHeaderParts[1];
                        String customerName = invoiceHeaderParts[2];

                        InvoiceHeader invoiceHeader = new InvoiceHeader(invoiceNumber, invoiceDate, customerName);
                        invoicesArray.add(invoiceHeader);
                    }catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(table, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }


                result = fc.showOpenDialog(table);                            //load the line file
                if (result == JFileChooser.APPROVE_OPTION){
                    File invoiceLineFile = fc.getSelectedFile();
                    Path invoiceLinePath = Paths.get(invoiceLineFile.getAbsolutePath());
                    List<String> invoiceLineLines = Files.readAllLines(invoiceLinePath);

                    for(String invoiceLineLine : invoiceLineLines){
                        try{
                            String[] invoiceLineParts = invoiceLineLine.split(",");
                            int invoiceNumber = Integer.parseInt(invoiceLineParts[0]);
                            String itemName = invoiceLineParts[1];
                            double itemPrice = Double.parseDouble(invoiceLineParts[2]);
                            int count = Integer.parseInt(invoiceLineParts[3]);
                            InvoiceHeader invH = null;
                            for (InvoiceHeader invoice : invoicesArray){
                                if (invoice.getNumber() == invoiceNumber){
                                    invH = invoice;
                                    break;
                                }

                            }
                            InvoicesLines line = new InvoicesLines(itemName, itemPrice, count, invH);
                            invH.getLines().add(line);
                        }catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(table, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }

                }

                table.setInvoices(invoicesArray);                                 //load the files in the tables
                TableModel tableModel = new TableModel(invoicesArray);
                table.setTableModel(tableModel);
                table.getInvoiceTable().setModel(tableModel);
                table.getTableModel().fireTableDataChanged();

            }
        }catch(IOException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(table, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void saveFile() {
        ArrayList<InvoiceHeader> invoices = table.getInvoices();
        String invoiceheaders = "";
        String invoicelines = "";
        for (InvoiceHeader invoice : invoices) {
            String invoiceCSV = invoice.getAsCSV();
            invoiceheaders += invoiceCSV;
            invoiceheaders += "\n";

            for (InvoicesLines line : invoice.getLines()) {
                String lineCSV = line.getAsCSV();
                invoicelines += lineCSV;
                invoicelines += "\n";
            }
        }

        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(table);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter headerfw = new FileWriter(headerFile);
                headerfw.write(invoiceheaders);
                headerfw.flush();
                headerfw.close();
                result = fc.showSaveDialog(table);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter linefw = new FileWriter(lineFile);
                    linefw.write(invoicelines);
                    linefw.flush();
                    linefw.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createNewInvoice() {
        invoiceDialog = new InvoiceDialog(table);
        invoiceDialog.setVisible(true);

    }

    private void deleteInvoice() {
        int selectedRow = table.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1){
            table.getInvoices().remove(selectedRow);
            table.getTableModel().fireTableDataChanged();
        }


    }

    private void createNewItem() {
        lineDialog = new LineDialog(table);
        lineDialog.setVisible(true);

    }

    private void deleteItem() {
        int selectedRow1 = table.getLineTable().getSelectedRow();

        if (selectedRow1 != -1) {
            LinesTableModel linesTableModel = (LinesTableModel) table.getLineTable().getModel();
            linesTableModel.getLines().remove(selectedRow1);
            linesTableModel.fireTableDataChanged();
            table.getTableModel().fireTableDataChanged();
        }

    }
    private void createInvoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }
    private void createInvoiceOK() {
        String date = invoiceDialog.getInvoiceDateField().getText();
        String customer = invoiceDialog.getCustomerNameField().getText();
        int num = table.getNextInvoiceNum();
        try {
            String[] dateParts = date.split("-");  // "22-05-2013" -> {"22", "05", "2013"}  xy-qw-20ij
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(table, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12 || year > 2100) {
                    JOptionPane.showMessageDialog(table, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    InvoiceHeader invoice = new InvoiceHeader(num, date, customer);
                    table.getInvoices().add(invoice);
                    table.getTableModel().fireTableDataChanged();
                    invoiceDialog.setVisible(false);
                    invoiceDialog.dispose();
                    invoiceDialog = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(table, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void createLineOK() {
        String item = lineDialog.getItemNameField().getText();
        String countStr = lineDialog.getItemCountField().getText();
        String priceStr = lineDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = table.getInvoiceTable().getSelectedRow();

        if (selectedInvoice != -1) {
            InvoiceHeader invoice = table.getInvoices().get(selectedInvoice);
            InvoicesLines invoicesLines = new InvoicesLines(item, price, count, invoice);
            if(price<0){JOptionPane.showMessageDialog(table, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);}
            else{
                invoice.getLines().add(invoicesLines);
                LinesTableModel linesTableModel = (LinesTableModel) table.getLineTable().getModel();

                linesTableModel.fireTableDataChanged();
                table.getTableModel().fireTableDataChanged();
            }
        }

        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void createLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }





}
