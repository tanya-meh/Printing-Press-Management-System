package org.informatics_java.data.employees;

import org.informatics_java.data.enums.EmployeePosition;

import java.util.Objects;

public class Employee implements Comparable<Employee>{
    private String id;
    private String name;
    private EmployeePosition employeePosition;

    public Employee(String id, String name, EmployeePosition employeePosition) {
        this.id = id;
        this.name = name;
        this.employeePosition = employeePosition;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EmployeePosition getEmployeePosition() {
        return employeePosition;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", employeePosition=" + employeePosition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && employeePosition == employee.employeePosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, employeePosition);
    }

    @Override
    public int compareTo(Employee o) {
        return this.getId().compareTo(o.getId());
    }
}
