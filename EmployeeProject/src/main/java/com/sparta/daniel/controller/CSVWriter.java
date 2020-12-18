package com.sparta.daniel.controller;

import com.sparta.daniel.model.EmployeeDTO;
import com.sparta.daniel.view.Printer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVWriter {

    public static void writeCorrupt(ArrayList<EmployeeDTO> corruptedData, String path) {

        try {

            Printer.printMessage("");
            Printer.printMessage("Writing....");

            File badData = new File(path);
            FileWriter myWriter = new FileWriter(path);
            for (EmployeeDTO employeeDTO : corruptedData) {
                myWriter.write(employeeDTO.toString() + "\n");

            }
            myWriter.close();

        } catch (IOException e) {
            Printer.printMessage("IO Exception found in writer");
        }

    }
}
