package com.sparta.daniel.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

public class EmployeeDTO {
    // Emp ID,Name Prefix,First Name,Middle Initial,Last Name,Gender,E Mail,Date of Birth,Date of Joining,Salary

    String emp_ID;
    String namePrefix;
    String firstName;
    String middleInitial;
    String lastName;
    String gender;
    String email;
    LocalDate dob;
    LocalDate dateOfJoining;
    Integer salary;

    public EmployeeDTO(String emp_ID, String namePrefix, String firstName, String middleInitial, String lastName, String gender, String email, String dob, String dateOfJoining, String salary) {
        this.emp_ID = emp_ID;
        this.namePrefix = namePrefix;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        setDob(dob);
        setDateOfJoining(dateOfJoining);
        setSalary(salary);
    }

    public String getEmp_ID() {
        return emp_ID;
    }

    public void setEmp_ID(String emp_ID) {
        this.emp_ID = emp_ID;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(String dot) {
        this.dob = LocalDate.parse(dot, DateTimeFormatter.ofPattern("M[M]/d[d]/yyyy"));
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    // Using [] makes it an option (really useful for dates)
    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = LocalDate.parse(dateOfJoining, DateTimeFormatter.ofPattern("M[M]/d[d]/yyyy"));
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = Integer.valueOf(salary);
    }

    public static EmployeeDTO employeeCreator(String[] arrayOfColumnValues) {
        EmployeeDTO employeeDTO = new EmployeeDTO(
                arrayOfColumnValues[0],
                arrayOfColumnValues[1],
                arrayOfColumnValues[2],
                arrayOfColumnValues[3],
                arrayOfColumnValues[4],
                arrayOfColumnValues[5],
                arrayOfColumnValues[6],
                arrayOfColumnValues[7],
                arrayOfColumnValues[8],
                arrayOfColumnValues[9]);

        return employeeDTO;
    }

    @Override
    public String toString() {
        return "\n" +
                "emp_ID='" + emp_ID + '\'' +
                ", namePrefix='" + namePrefix + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleInitial='" + middleInitial + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", dateOfJoining=" + dateOfJoining +
                ", salary=" + salary
                ;
    }




    public static ArrayList<EmployeeDTO> duplicateCheck (ArrayList<EmployeeDTO> employees, String cleanOrCorrupted) {
        ArrayList<EmployeeDTO> cleanEmployees = new ArrayList<>();
        ArrayList<EmployeeDTO> corruptedEmployees = new ArrayList<>();
//        ArrayList<String> validEmployeesID = new ArrayList<>();
//        ArrayList<String> nonValidEmployeesID = new ArrayList<>();
//        ArrayList<String> validEmail = new ArrayList<>();
//        ArrayList<String> nonValidEmail = new ArrayList<>();

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

        // This block is optional for removing all original employees which have been confirmed to have duplicates.
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

    public static ArrayList<EmployeeDTO> isNameTRUEOrFALSE (ArrayList<EmployeeDTO> employees, String cleanOrBad) {
        ArrayList<EmployeeDTO> cleanDataType = new ArrayList<>();
        ArrayList<EmployeeDTO> badDataType = new ArrayList<>();

        for (EmployeeDTO employee : employees) {
            if (employee.getLastName().equals("TRUE") || employee.getLastName().equals("FALSE")) {
                badDataType.add(employee);
            }
            else {
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
