package com.sparta.daniel.controller;

import com.sparta.daniel.model.Checker;
import com.sparta.daniel.model.EmployeeDTO;
import com.sparta.daniel.view.Printer;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class CSVReader {


    public static ArrayList<EmployeeDTO> readEmployees(String path, String writerPath) {

        Printer.printMessage("");
        Printer.printMessage("Reading....");
        Printer.printMessage("");

        long start = System.nanoTime();

        ArrayList<EmployeeDTO> employees = new ArrayList<>();
        ArrayList<EmployeeDTO> cleanData = new ArrayList<>();
        ArrayList<EmployeeDTO> corruptedData = new ArrayList<>();

        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            // \n is a line feed while \r is a carriage return
            // By using bufferedReader here, it won't read the first line in the while loop (good when there are headers).
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(",");

                int tag = 0;


                // I need a way of recording employeeDTO even when exception is caught (issue when Salary is blank)
                // Not an issue at the moment but could have a think about it
                try {
                    EmployeeDTO employeeDTO = EmployeeDTO.employeeCreator(columns);

                    for (String columnData : columns) {
                        if (columnData.isBlank()) {
                            tag = 1;
                            break;
                        }
                    }
                    if (tag == 1) {
                        corruptedData.add(employeeDTO);
                    } else {
                        cleanData.add(employeeDTO);
                    }
                    employees.add(employeeDTO);

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("issue");
                }
            }

            Printer.printMessage("Total employees given : " + employees.size());
            Printer.printMessage("");

            Printer.printMessage("Clean Data size after partial data checker : " + cleanData.size());
            Printer.printMessage("Corrupt Data size after partial data checker : " + corruptedData.size());

            // Be careful of the order these go in
            corruptedData.addAll(Checker.duplicateCheck(cleanData, "corrupt"));
            cleanData = Checker.duplicateCheck(cleanData, "clean");

            Printer.printMessage("");
            Printer.printMessage("Clean Data size after duplicates of Emp_ID or E_Mail removed : " + cleanData.size());
            Printer.printMessage("Corrupt data size after duplicates of Emp_ID or E_Mail added : " + corruptedData.size());

            corruptedData.addAll(Checker.isNameTRUEOrFALSE(cleanData, "corrupted"));
            cleanData = Checker.isNameTRUEOrFALSE(cleanData, "clean");

            Printer.printMessage("");
            Printer.printMessage("Clean Data size after removing Last Names of TRUE or FALSE : " + cleanData.size());
            Printer.printMessage("Corrupted Data size after removing Last Names of TRUE or FALSE : " + corruptedData.size());

            CSVWriter.writeCorrupt(corruptedData,writerPath);

            bufferedReader.close();

            long finish = System.nanoTime();
            Printer.printMessage("");
            Printer.printMessage("Time taken to read and write : " + (finish - start) / 1_000_000_000 + " " + "second(s)");


        } catch (IOException e) {
            e.printStackTrace();
        }


        return cleanData;
    }
}
