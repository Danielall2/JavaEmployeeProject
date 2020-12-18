package com.sparta.daniel.model;

import com.sparta.daniel.controller.EmployeeDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

public class SQLChecker {

    public static EmployeeDTO SQLDateChecker(String table) {

        Connection connection = EmployeeDAO.connectToDB();

        ResultSet resultSet = EmployeeDAO.querySelectAllDB(table, connection);

        EmployeeDTO tempEmployee = new EmployeeDTO();

        try {
            if (resultSet.next()) {

                tempEmployee.setEmp_ID((resultSet.getString(1)));
                tempEmployee.setNamePrefix(resultSet.getString(2));
                tempEmployee.setFirstName(resultSet.getString(3));
                tempEmployee.setMiddleInitial(resultSet.getString(4));
                tempEmployee.setLastName(resultSet.getString(5));
                tempEmployee.setGender(resultSet.getString(6));
                tempEmployee.setEmail(resultSet.getString(7));

                LocalDate dob = resultSet.getDate(8).toLocalDate();
                LocalDate doj = resultSet.getDate(9).toLocalDate();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                tempEmployee.setDob(dob.format(dateFormatter));
                tempEmployee.setDateOfJoining(doj.format(dateFormatter));

                tempEmployee.setSalary(Integer.toString(resultSet.getInt(10)));


                return tempEmployee;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public static ArrayList<EmployeeDTO> SQLAllDataChecker(String table) {

        Connection connection = EmployeeDAO.connectToDB();

        ResultSet resultSet = EmployeeDAO.querySelectAllDB(table, connection);

        EmployeeDTO tempEmployee;

        ArrayList<EmployeeDTO> testEmployeesList = new ArrayList<>();

        try {
            while (resultSet.next()) {

                tempEmployee = new EmployeeDTO();

                tempEmployee.setEmp_ID((resultSet.getString(1)));
                tempEmployee.setNamePrefix(resultSet.getString(2));
                tempEmployee.setFirstName(resultSet.getString(3));
                tempEmployee.setMiddleInitial(resultSet.getString(4));
                tempEmployee.setLastName(resultSet.getString(5));
                tempEmployee.setGender(resultSet.getString(6));
                tempEmployee.setEmail(resultSet.getString(7));

                LocalDate dob = resultSet.getDate(8).toLocalDate();
                LocalDate doj = resultSet.getDate(9).toLocalDate();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                tempEmployee.setDob(dob.format(dateFormatter));
                tempEmployee.setDateOfJoining(doj.format(dateFormatter));

                tempEmployee.setSalary(Integer.toString(resultSet.getInt(10)));

                testEmployeesList.add(tempEmployee);
            }

            return testEmployeesList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }


    public static ArrayList<EmployeeDTO> IDAndEmailSQLCheck(ArrayList<EmployeeDTO> employees, String table) {
        Connection connection = EmployeeDAO.connectToDB();

        ResultSet resultSet = EmployeeDAO.querySelectAllDB(table, connection);

        ArrayList<EmployeeDTO> correctedEmployees = new ArrayList<>();
        HashSet<String> IDList = new HashSet<>();
        HashSet<String> EmailList = new HashSet<>();

        try {
            while (resultSet.next()) {
                IDList.add(resultSet.getString(1));
                EmailList.add(resultSet.getString(7));
            }

            for (EmployeeDTO employeeDTO : employees) {
                if (IDList.contains(employeeDTO.getEmp_ID()) || EmailList.contains(employeeDTO.getEmp_ID())) {
                } else {
                    correctedEmployees.add(employeeDTO);
                }
            }

            return correctedEmployees;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}
