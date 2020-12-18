package com.sparta.daniel.model;

import java.util.ArrayList;
import java.util.HashSet;

public class Checker {
    public static ArrayList<EmployeeDTO> duplicateCheck(ArrayList<EmployeeDTO> employees, String cleanOrCorrupted) {
        ArrayList<EmployeeDTO> cleanEmployees = new ArrayList<>();
        ArrayList<EmployeeDTO> corruptedEmployees = new ArrayList<>();

        HashSet<String> validEmployeesID = new HashSet<>();
        HashSet<String> nonValidEmployeesID = new HashSet<>();
        HashSet<String> validEmail = new HashSet<>();
        HashSet<String> nonValidEmail = new HashSet<>();

        // Code which checks whether new entries have the same ID or email that has been used before
        // In this block, the original row is kept but any other duplicates are sent to the corruptedEmployees table
        for (EmployeeDTO employeeDTO : employees) {
            if (validEmployeesID.contains(employeeDTO.getEmp_ID()) || validEmail.contains(employeeDTO.getEmail())) {
                nonValidEmployeesID.add(employeeDTO.getEmp_ID());
                nonValidEmail.add(employeeDTO.getEmail());
                corruptedEmployees.add(employeeDTO);

            } else {
                validEmployeesID.add(employeeDTO.getEmp_ID());
                validEmail.add(employeeDTO.getEmail());
                cleanEmployees.add(employeeDTO);
            }
        }

        for (EmployeeDTO employeeDTO : employees) {
            for (String ID : nonValidEmployeesID) {
                if (employeeDTO.getEmp_ID().contains(ID) && cleanEmployees.contains(employeeDTO)) {
                    cleanEmployees.remove(employeeDTO);
                    corruptedEmployees.add(employeeDTO);
                }
            }
            for (String email : nonValidEmail) {
                if (employeeDTO.getEmail().contains(email) && cleanEmployees.contains(employeeDTO)) {
                    cleanEmployees.remove(employeeDTO);
                    corruptedEmployees.add(employeeDTO);
                }
            }
        }


        if (cleanOrCorrupted.equals("clean")) {
            return cleanEmployees;
        } else {
            return corruptedEmployees;
        }
    }

    public static ArrayList<EmployeeDTO> isNameTRUEOrFALSE(ArrayList<EmployeeDTO> employees, String cleanOrBad) {
        ArrayList<EmployeeDTO> cleanDataType = new ArrayList<>();
        ArrayList<EmployeeDTO> badDataType = new ArrayList<>();

        for (EmployeeDTO employee : employees) {
            if (employee.getLastName().equals("TRUE") || employee.getLastName().equals("FALSE")) {
                badDataType.add(employee);
            } else {
                cleanDataType.add(employee);
            }
        }

        if (cleanOrBad.equals("clean")) {
            return cleanDataType;
        } else {
            return badDataType;
        }
    }


}
