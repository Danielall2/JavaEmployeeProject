package com.sparta.daniel.controller;

import com.sparta.daniel.model.EmployeeDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class EmployeeDAO {

    private static final Properties properties = new Properties();
    private static Connection connection;
    private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);

    private static void createProperties() {
        try {
            properties.load(new FileReader("src/main/resources/login.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection connectToDB(String url) {
        createProperties();
        String userName = properties.getProperty("userName");
        String password = properties.getProperty("password");

        try {
            connection = DriverManager.getConnection(url, userName, password);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        logger.debug("Connected to Database");
//        System.out.println("Connected to Database");

        return connection;

    }

    public static void queryDB(String query) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            // The resultsSet represents the table you get
            ResultSet resultSet = statement.executeQuery(" SELECT * FROM employees.employeedata");
            while (resultSet.next()) {
                // SQL Columns start at 1 not 0 (Java)
                System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // For some reason this method is very inefficient. It is the only one I call in the threads so must be the reason it takes so long
    public static void insertData(List<EmployeeDTO> dataToInsert, Connection connection) {

        try {
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO `employees`.`employeedata` " +
                            "(`Emp_ID`, `Name_Prefix`, `First_Name`, `Middle_Initial`, `Last_Name`, `Gender`, `E_Mail`, `Date_Of_Birth`, `Date_Of_Joining`, `Salary`) " +
                            "VALUES " +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Need to change the data types here when needed
            for (EmployeeDTO dataRows : dataToInsert) {
                preparedStatement.setString(1, dataRows.getEmp_ID());
                preparedStatement.setString(2, dataRows.getNamePrefix());
                preparedStatement.setString(3, dataRows.getFirstName());
                preparedStatement.setString(4, dataRows.getMiddleInitial());
                preparedStatement.setString(5, dataRows.getLastName());
                preparedStatement.setString(6, dataRows.getGender());
                preparedStatement.setString(7, dataRows.getEmail());
                preparedStatement.setDate(8, Date.valueOf(dataRows.getDob()));
                preparedStatement.setDate(9, Date.valueOf(dataRows.getDateOfJoining()));
                preparedStatement.setInt(10, dataRows.getSalary());
                preparedStatement.execute();


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
