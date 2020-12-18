package com.sparta.daniel.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

public class EmployeeDTO implements Comparable<EmployeeDTO>{
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

    public EmployeeDTO(){}

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

    // Overwritten so that comparing the Strings of data works in test (CSV ArrayList vs SQL ArrayList)
    @Override
    public int compareTo(EmployeeDTO obj) {
        return this.salary-obj.salary;

    }


}
