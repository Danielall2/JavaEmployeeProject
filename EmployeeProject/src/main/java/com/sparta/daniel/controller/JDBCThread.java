package com.sparta.daniel.controller;

import com.sparta.daniel.model.EmployeeDTO;
import com.sparta.daniel.view.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class JDBCThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(JDBCThread.class);
    private final List<EmployeeDTO> data;
    private Connection connection;


    public JDBCThread(List<EmployeeDTO> arrayListConstructor) {
        data = arrayListConstructor;
    }

    @Override
    public void run() {

        connection = EmployeeDAO.connectToDB("jdbc:mysql://localhost:3306/employees?serverTimezone=GMT");

        logger.debug("Starting the thread for data of size : " + data.size());

        insertTaskByThread(data, connection);

        logger.debug("Ending the thread for data of size : " + data.size());

    }

    private void insertTaskByThread(List<EmployeeDTO> arrayList, Connection connection) {

        //connection = EmployeeDAO.connectToDB("jdbc:mysql://localhost:3306/employees?serverTimezone=GMT");

        long start = System.nanoTime();

        EmployeeDAO.insertData(arrayList, connection);

        long finish = System.nanoTime();
        Printer.printMessage("");
        Printer.printMessage("Time taken to insert data : " + (finish - start) / 1_000_000_000 + " " + "second(s)");

    }
}
