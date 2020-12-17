package com.sparta.daniel.controller;

import com.sparta.daniel.model.EmployeeDTO;
import com.sparta.daniel.view.Printer;

import java.io.*;
import java.util.ArrayList;

public class CSVReader {



    public static ArrayList<EmployeeDTO> readEmployees(String path) {

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
            corruptedData.addAll(EmployeeDTO.duplicateCheck(cleanData, "corrupt"));
            cleanData = EmployeeDTO.duplicateCheck(cleanData, "clean");

            Printer.printMessage("");
            Printer.printMessage("Clean Data size after duplicates of Emp_ID or E_Mail removed : " + cleanData.size());
            Printer.printMessage("Corrupt data size after duplicates of Emp_ID or E_Mail added : " + corruptedData.size());


            // Issue with this as the method changes the array and then calling it again
            corruptedData.addAll(EmployeeDTO.isNameTRUEOrFALSE(cleanData, "corrupted"));
            cleanData = EmployeeDTO.isNameTRUEOrFALSE(cleanData, "clean");

            Printer.printMessage("");
            Printer.printMessage("Clean Data size after removing Last Names of TRUE or FALSE : " + cleanData.size());
            Printer.printMessage("Corrupted Data size after removing Last Names of TRUE or FALSE : " + corruptedData.size());

            Printer.printMessage("");
            Printer.printMessage("Writing....");

            File badData = new File("src/main/resources/CorruptedData.txt");
            FileWriter myWriter = new FileWriter("src/main/resources/CorruptedData.txt");
            for (EmployeeDTO employeeDTO : corruptedData) {
                    myWriter.write(employeeDTO.toString() + "\n");

            }
            myWriter.close();

            bufferedReader.close();

            long finish = System.nanoTime();
            Printer.printMessage("");
            Printer.printMessage("Time taken to read and write : " + (finish - start) / 1_000_000_000 + " " + "second(s)");


        } catch (IOException e) {
            e.printStackTrace();
        }

//        Printer.printMessage("Total employees given : " + employees.size());
//        Printer.printMessage("Clean Data size after duplicates removed : " + cleanData.size());
//        Printer.printMessage("Corrupt data size after duplicates added : " + corruptedData.size());
//
//
//        Printer.printMessage("Employees after removed TRUE or FALSE Last Names : " + EmployeeDTO.isNameTRUEOrFALSE(cleanData, "clean").size());
//        Printer.printMessage("Employees removed  after checking for TRUE or FALSE Last Names : " + EmployeeDTO.isNameTRUEOrFALSE(cleanData, "bad").size());


        return cleanData;
    }
}
