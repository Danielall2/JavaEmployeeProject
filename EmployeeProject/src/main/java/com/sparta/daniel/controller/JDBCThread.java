package com.sparta.daniel.controller;

import com.sparta.daniel.model.EmployeeDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class JDBCThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(JDBCThread.class);
    private final List<EmployeeDTO> data;

    public JDBCThread(List<EmployeeDTO> arrayListConstructor) {
        data = arrayListConstructor;
    }

    @Override
    public void run() {

        Connection connection = EmployeeDAO.connectToDB();

        logger.debug("Starting the thread for data of size : " + data.size());

        EmployeeDAO.insertData(data, connection, "employeedata");
        //insertTaskByThread(data, connection);

        logger.debug("Ending the thread for data of size : " + data.size());

    }

    // Method below simply used for debugging purposes for looking at time taken for each thread

//    private void insertTaskByThread(List<EmployeeDTO> arrayList, Connection connection) {
//
//        //connection = EmployeeDAO.connectToDB("jdbc:mysql://localhost:3306/employees?serverTimezone=GMT");
//
//        long start = System.nanoTime();
//
//        EmployeeDAO.insertData(arrayList, connection, "employeedata");
//
//        long finish = System.nanoTime();
//
////        logger.debug("Time taken to insert data for one thread : " + (finish - start) / 1_000_000_000 + " " + "second(s)");
//
//        logger.debug("Time taken to insert data for one thread : " + (finish - start) + " " + "nanosecond(s)");
//
//    }
}
