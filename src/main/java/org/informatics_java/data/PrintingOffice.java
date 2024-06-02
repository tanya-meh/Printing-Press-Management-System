package org.informatics_java.data;

import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.publications.Publication;
import org.informatics_java.service.calculations.EmployeesExpensesCalculator;
import org.informatics_java.service.calculations.PaperExpensesCalculator;

import java.math.BigDecimal;
import java.util.*;

public class PrintingOffice {
    private String id;
    private String name;
    private Set<Employee> employeeSet;
    private Set<Printer> printerSet;
    private Map<Paper, Integer> papersInStockMap;
    private Map<Publication, Integer> publicationNumberOfCopiesMap;

    public PrintingOffice(String id, String name) {
        this.id = id;
        this.name = name;
        this.employeeSet = new TreeSet<>();
        this.printerSet = new TreeSet<>();
        this.papersInStockMap = new HashMap<>();
        this.publicationNumberOfCopiesMap = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Employee> getEmployeeSet() {
        return employeeSet;
    }

    public Set<Printer> getPrinterSet() {
        return printerSet;
    }

    public Map<Paper, Integer> getPapersInStockMap() {
        return papersInStockMap;
    }

    public Map<Publication, Integer> getPublicationNumberOfCopiesMap() {
        return publicationNumberOfCopiesMap;
    }

    public void setEmployeeSet(Set<Employee> employeeSet) {
        this.employeeSet = employeeSet;
    }

    public void setPrinterSet(Set<Printer> printerSet) {
        this.printerSet = printerSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintingOffice that = (PrintingOffice) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean addEmployee(Employee employee) {
        return this.employeeSet.add(employee);
    }

    public boolean removeEmployee(Employee employee) {
        return this.employeeSet.remove(employee);
    }

    public boolean addPrinter(Printer printer) {
        return this.printerSet.add(printer);
    }

    public boolean removePrinter(Printer printer) {
        return this.printerSet.remove(printer);
    }

    public int putPapersInStock(Paper paper, int numberOfPapers) {
        return this.papersInStockMap.put(paper, numberOfPapers);
    }

    public int putPublicationsPrinted(Publication publication, int numberOfCopies) {
        return this.publicationNumberOfCopiesMap.put(publication, numberOfCopies);
    }
}
