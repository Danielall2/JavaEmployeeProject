package com.sparta.daniel.view;


import com.sparta.daniel.controller.CSVReader;
import com.sparta.daniel.controller.EmployeeDAO;
import com.sparta.daniel.model.CreatingNThreads;
import com.sparta.daniel.model.EmployeeDTO;
import com.sparta.daniel.model.SQLChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

public class Starter {

    private static Logger logger = LogManager.getLogger(Starter.class);

    public static void start(String csvLocation) {
        Connection connection = EmployeeDAO.connectToDB();

        // WARNING: Uncommenting the below line of code will reset the entire database
        // EmployeeDAO.truncateTable("employeedata", connection);

        long beginning = System.nanoTime();

        //ArrayList<EmployeeDTO> testEmployees = CSVReader.readEmployees("src/main/resources/EmployeeRecords.csv");
        ArrayList<EmployeeDTO> testEmployees = CSVReader.readEmployees(csvLocation, "src/main/resources/CorruptedData.txt");

        int sizeBefore = testEmployees.size();

        testEmployees = SQLChecker.IDAndEmailSQLCheck(testEmployees, "employeedata");

        int sizeAfter = testEmployees.size();

        Printer.printMessage("");
        Printer.printMessage("Duplicates found in SQL : " + (sizeBefore-sizeAfter));
        Printer.printMessage("");

        if (testEmployees.size() != 0) {

            // Assign a condition for number of threads
            // 150 is the maximum number of possible connections so cannot go over this
            int numberOfThreads = Math.min(100, testEmployees.size() / 250);

            ArrayList<Thread> threadList = CreatingNThreads.createThreads(numberOfThreads, testEmployees);

            long start = System.nanoTime();

            for (Thread thread : threadList) {
                thread.setName(UUID.randomUUID().toString());
                thread.start();
            }

            for (Thread thread : threadList) {
                while (thread.isAlive()) {

                }
            }

            long finish = System.nanoTime();
            Printer.printMessage("Time taken for all threads to finish : " + (finish - start) / 1_000_000_000 + " second(s)");

        } else {
            Printer.printMessage("No new entries found. \n\nPlease update your LargeEmployeeRecords.csv file and save in the main/resource folder");
            logger.info("Attempted to upload either an empty CSV or a CSV with all entries already in the database");
        }

        Printer.printMessage("");
        Printer.printMessage("Number of entries in SQL : " + EmployeeDAO.queryCountDB(connection, "employeedata"));

        long end = System.nanoTime();
        Printer.printMessage("");
        Printer.printMessage("Total time taken for program : " + (end-beginning) / 1_000_000_000 + " seconds(s)");

    }
}
