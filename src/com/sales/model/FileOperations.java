// Java Program to illustrate
// reading from text file
// as string in Java
package com.sales.model;

import java.nio.file.*;;

public class FileOperations {

    public static String readFileAsString(String fileName)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public static void main(String[] args) throws Exception
    {

        String[] header1 = new String[0];
        String[] line1 = new String[0];
        String data = readFileAsString(
                "C:\\Users\\user\\OneDrive\\Documents\\NetBeansProjects\\SalesInvoiceGenerator\\header.csv");      // header file location
        String[] header = data.split("\n");
        String data1 = readFileAsString(
                "C:\\Users\\user\\OneDrive\\Documents\\NetBeansProjects\\SalesInvoiceGenerator\\lines.csv");       // lines file location
        String[] lines = data1.split("\n");
        for(String head : header) {
            header1 = head.split(",");
            System.out.println("Invoice" + header1[0] + "\n" + "{");
            System.out.println(header1[1] + ", " + header1[2]);
            for(String line : lines) {
                line1 = line.split(",");
                if(Integer.parseInt(line1[0]) == Integer.parseInt(header1[0])){
                    System.out.println(line1[1] + ", " + line1[2] + ", " + line1[3]);

                }


            }
            System.out.println("}");

        }




        

    }
}