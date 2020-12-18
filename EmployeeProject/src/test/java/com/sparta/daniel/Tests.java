package com.sparta.daniel;

import com.sparta.daniel.controller.CSVReader;
import com.sparta.daniel.controller.EmployeeDAO;
import com.sparta.daniel.model.Checker;
import com.sparta.daniel.model.EmployeeDTO;
import com.sparta.daniel.model.SQLChecker;
import com.sparta.daniel.view.Printer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Tests {

    ArrayList<EmployeeDTO> testEmployees = new ArrayList<>();
    String testPath = "src/main/resources/TestEmployeeRecords.csv";
    String writerTestPath = "src/main/resources/TestCorruptedData.txt";
    Connection connection = EmployeeDAO.connectToDB();
    EmployeeDTO employeeDTO = new EmployeeDTO("198429",
            "Mrs.",
            "Serafina",
            "I",
            "Bumgarner",
            "F",
            "serafina.bumgarner@exxonmobil.com",
            "9/21/1982",
            "02/01/2008",
            "69294");

    // Good test would be to test the size of the test array with a manually inserted one

    public ArrayList<EmployeeDTO> readBeforeTests(String path) {

        try {


            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(",");


                try {
                    EmployeeDTO employeeDTO = EmployeeDTO.employeeCreator(columns);
                    testEmployees.add(employeeDTO);

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("issue");
                }
            }

            return testEmployees;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    // Need to incorporate tests for duplicateCheck, isNameTrueOrFalse, queries for the SQL table for its Count(*)
    // Also need to think about test that confirm data is correct in the table
    //

    @Test
    void getCheck() {
        Assertions.assertEquals("198429", employeeDTO.getEmp_ID());
    }

    @Test
    void checkForRemovingDuplicates() {
        EmployeeDAO.truncateTable("testemployeedata", connection);
        Assertions.assertEquals(16, Checker.duplicateCheck(readBeforeTests(testPath), "clean").size());
    }

    @Test
    void checkForLastNameErrors() {
        EmployeeDAO.truncateTable("testemployeedata", connection);
        Assertions.assertEquals(15, Checker.isNameTRUEOrFALSE(readBeforeTests(testPath), "clean").size());
    }

    @Test
    void checkerTest() {
        EmployeeDAO.truncateTable("testemployeedata", connection);
        Assertions.assertEquals(13, CSVReader.readEmployees(testPath, writerTestPath).size());
    }

    @Test
    void countSQLTest() {
        EmployeeDAO.truncateTable("testemployeedata", connection);
        ArrayList<EmployeeDTO> testInputArray = CSVReader.readEmployees(testPath, writerTestPath);
        EmployeeDAO.insertData(testInputArray, connection, "testemployeedata");

        ResultSet resultSet = EmployeeDAO.querySelectAllDB("testemployeedata", connection);
        int i = 0;

        while(true){
            try {
                if (!resultSet.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            i++;
        }

        Assertions.assertEquals(13, i);
    }

    @Test
    void testForDateType() {
        EmployeeDAO.truncateTable("testemployeedata", connection);
        ArrayList<EmployeeDTO> testInputArray = CSVReader.readEmployees(testPath, writerTestPath);
        EmployeeDAO.insertData(testInputArray, connection, "testemployeedata");
        EmployeeDTO sqlTestEmployee = SQLChecker.SQLDateChecker("testemployeedata");
        Printer.printMessage("Employee : " + sqlTestEmployee);
        // Actual value used from first entry in database
        Assertions.assertEquals("1988-07-21", sqlTestEmployee.getDob().toString());

    }


    // Need to test for all data

    @Test
    void testAllData() {
        EmployeeDAO.truncateTable("testemployeedata", connection);
        ArrayList<EmployeeDTO> testInputArray = CSVReader.readEmployees(testPath, writerTestPath);
        EmployeeDAO.insertData(testInputArray, connection, "testemployeedata");
        // Line to get resultset
        ArrayList<EmployeeDTO> employeeDTOS = SQLChecker.SQLAllDataChecker("testemployeedata");

        Collections.sort(testInputArray);
        Collections.sort(employeeDTOS);

        // AssertEquals condiiton using toString
        Assertions.assertEquals(testInputArray.toString(), employeeDTOS.toString());

    }

    // If I had more time would write a method to check data in txt file that is outputted
    // Have not currently implemented as I believe would require outputting to csv rather than txt




}
