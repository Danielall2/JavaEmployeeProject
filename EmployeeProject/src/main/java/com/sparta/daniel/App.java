package com.sparta.daniel;


import com.sparta.daniel.controller.CSVReader;
import com.sparta.daniel.model.CreatingNThreads;
import com.sparta.daniel.model.EmployeeDTO;


import java.util.ArrayList;
import java.util.UUID;

public class App {

//    Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        //ArrayList<EmployeeDTO> testEmployees = CSVReader.readEmployees("src/main/resources/EmployeeRecords.csv");
        ArrayList<EmployeeDTO> testEmployees = CSVReader.readEmployees("src/main/resources/LargeEmployeeRecords.csv");

        // Need to create a condition here for assigning value to numberOfThreads

        ArrayList<Thread> threadList = CreatingNThreads.createThreads(10, testEmployees);

        for (Thread thread : threadList) {
            // thread.setName(UUID.randomUUID()) Gives the thread a random name as can't assign separate inside for loop
            thread.setName(UUID.randomUUID().toString());
            thread.start();
        }
    }
}
