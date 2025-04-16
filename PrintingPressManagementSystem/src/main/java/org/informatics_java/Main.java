package org.informatics_java;

import org.informatics_java.data.Employee;
import org.informatics_java.data.Printer;
import org.informatics_java.data.PrintingOffice;
import org.informatics_java.data.enums.EmployeePosition;
import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.Paper;
import org.informatics_java.data.publications.Book;
import org.informatics_java.data.publications.Newspaper;
import org.informatics_java.data.publications.Poster;
import org.informatics_java.data.publications.Publication;
import org.informatics_java.data.enums.PageSize;
import org.informatics_java.exceptions.IllegalAmountOfMoneyException;
import org.informatics_java.exceptions.IllegalPercentageException;
import org.informatics_java.exceptions.IllegalQuantityException;
import org.informatics_java.service.printingOffice.PrintingOfficeServiceImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //publications
        Publication publication1 = new Book("Kniga1", 325, PageSize.A5);
        Publication publication2 = new Newspaper("Spisanie1", 30, PageSize.A4);
        Publication publication3 = new Poster("Plakat1", 1, PageSize.A2);

        //papers
        Paper paperGlossyA2 = new Paper(PaperType.GLOSSY, PageSize.A2);
        Paper paperNewsprintA4 = new Paper(PaperType.NEWSPRINT, PageSize.A4);
        Paper paperStandardA5 = new Paper(PaperType.STANDARD, PageSize.A5);

        //printing office 1
        //printers
        Printer printer1 = new Printer("prId1", 1000, true, 15);
        Printer printer2 = new Printer("prId2", 200, false, 20);
        Printer printer3 = new Printer("prId3", 1000, false, 30);
        Set<Printer> printerSet1 = new TreeSet<>();
        printerSet1.add(printer1);
        printerSet1.add(printer2);
        printerSet1.add(printer3);

        //employees
        Employee employee1 = new Employee("e01", "Ivan Ivanov", EmployeePosition.MANAGER);
        Employee employee2 = new Employee("e02", "Georgi Georgiev", EmployeePosition.PRINTER_OPERATOR);
        Employee employee3 = new Employee("e03", "Marina Marinova", EmployeePosition.PRINTER_OPERATOR);
        Set<Employee> employeeSet1 = new TreeSet<>();
        employeeSet1.add(employee1);
        employeeSet1.add(employee2);
        employeeSet1.add(employee3);

        //printing office
        PrintingOffice printingOffice1 = new PrintingOffice("po1", "Super print");

        //adding printers and employees to printing office
        printingOffice1.setPrinterSet(printerSet1);
        printingOffice1.setEmployeeSet(employeeSet1);

        //needed for computing the employee expenses
        BigDecimal baseEmployeeSalary1 = BigDecimal.valueOf(1000);
        double managersSalaryIncreasePercent1 = 10.;
        BigDecimal incomeThresholdForSalaryIncrease1 = BigDecimal.valueOf(20_000);

        //needed for computing the paper expenses
        EnumMap<PaperType, BigDecimal> paperTypeBasePriceEnumMap1 = new EnumMap<>(
                Map.of(PaperType.STANDARD, BigDecimal.valueOf(0.05), PaperType.GLOSSY, BigDecimal.valueOf(0.50), PaperType.NEWSPRINT, BigDecimal.valueOf(0.03)));
        double paperPriceIncreasePercent1 = 20.;

        //needed for computing the total income
        int publicationsThresholdForDiscount1 = 10;
        double discountPercent1 = 15.;

        Map<Publication, BigDecimal> publicationPrintPriceMap1 = new HashMap<>();
        publicationPrintPriceMap1.put(publication1, BigDecimal.valueOf(20));
        publicationPrintPriceMap1.put(publication2, BigDecimal.valueOf(7));
        publicationPrintPriceMap1.put(publication3, BigDecimal.valueOf(10));

        try {
            PrintingOfficeServiceImpl printingOfficeService1 = new PrintingOfficeServiceImpl(printingOffice1, baseEmployeeSalary1, managersSalaryIncreasePercent1, incomeThresholdForSalaryIncrease1, paperPriceIncreasePercent1, publicationsThresholdForDiscount1, discountPercent1);
            printingOfficeService1.setPaperTypeBasePriceEnumMap(paperTypeBasePriceEnumMap1);
            printingOfficeService1.setPublicationPrintPriceMap(publicationPrintPriceMap1);

            try {
                printingOfficeService1.buyPaper(paperStandardA5, 2000);
                printingOfficeService1.buyPaper(paperGlossyA2, 500);
                printingOfficeService1.buyPaper(paperNewsprintA4, 1000);
            } catch (IllegalQuantityException e) {
                System.out.println(e.getCause());
            }

            try {
                printingOfficeService1.loadPaperInPrinter(paperGlossyA2, 100, printer1);
                printingOfficeService1.loadPaperInPrinter(paperNewsprintA4, 100, printer2);
                printingOfficeService1.loadPaperInPrinter(paperStandardA5, 800, printer3);
            } catch(IllegalQuantityException e) {
                System.out.println(e.getCause());
            }

            try {
                System.out.println("printOrder: " + printingOfficeService1.printOrder(publication1, 2, PaperType.STANDARD, false));
                System.out.println("printOrder: " + printingOfficeService1.printOrder(publication2, 3, PaperType.NEWSPRINT, false));
                System.out.println("printOrder: " + printingOfficeService1.printOrder(publication3, 40, PaperType.GLOSSY, true));
            } catch (IllegalQuantityException e) {
                System.out.println(e.getCause());
            }

            System.out.println();

            System.out.println("Total expenses of printing office " + printingOfficeService1.getPrintingOffice().getName() + " (id:" + printingOfficeService1.getPrintingOffice().getId() + "): " + printingOfficeService1.getExpensesTotal());
            System.out.println("Income of printing office " + printingOfficeService1.getPrintingOffice().getName() + " (id:" + printingOfficeService1.getPrintingOffice().getId() + "): " + printingOfficeService1.getIncome());

            System.out.println();

            printingOfficeService1.printPublicationsNumberOfPrints();

            printingOfficeService1.writeStatement("src/main/resources/printingOfficeService1Statement.txt");

        } catch (IllegalPercentageException | IllegalAmountOfMoneyException | IllegalQuantityException e){
            throw new RuntimeException(e);
        }

        System.out.println();

        //printing office 2
        //printers
        Printer printer4 = new Printer("prId4", 800, true, 10);
        Printer printer5 = new Printer("prId5", 200, true, 15);
        Printer printer6 = new Printer("prId6", 1000, false, 20);
        Set<Printer> printerSet2 = new TreeSet<>();
        printerSet2.add(printer4);
        printerSet2.add(printer5);
        printerSet2.add(printer6);

        //employees
        Employee employee4 = new Employee("e04", "Nikola Nikolov", EmployeePosition.PRINTER_OPERATOR);
        Employee employee5 = new Employee("e05", "Daniela Ivanova", EmployeePosition.MANAGER);

        Set<Employee> employeeSet2 = new TreeSet<>();
        employeeSet2.add(employee4);
        employeeSet2.add(employee5);

        //printing office
        PrintingOffice printingOffice2 = new PrintingOffice("po2", "Print mania");

        //adding printers and employees to printing office
        printingOffice2.setPrinterSet(printerSet2);
        printingOffice2.setEmployeeSet(employeeSet2);

        //needed for computing the employee expenses
        BigDecimal baseEmployeeSalary2 = BigDecimal.valueOf(1100);
        double managersSalaryIncreasePercent2 = 10.;
        BigDecimal incomeThresholdForSalaryIncrease2 = BigDecimal.valueOf(10_000);

        //needed for computing the paper expenses
        EnumMap<PaperType, BigDecimal> paperTypeBasePriceEnumMap2 = new EnumMap<>(Map.of(PaperType.STANDARD, BigDecimal.valueOf(0.05), PaperType.GLOSSY, BigDecimal.valueOf(0.40), PaperType.NEWSPRINT, BigDecimal.valueOf(0.02)));
        double paperPriceIncreasePercent2 = 15.;

        //needed for computing the total income
        int publicationsThresholdForDiscount2 = 20;
        double discountPercent2 = 10.;

        Map<Publication, BigDecimal> publicationPrintPriceMap2 = new HashMap<>();
        publicationPrintPriceMap2.put(publication1, BigDecimal.valueOf(25));
        publicationPrintPriceMap2.put(publication2, BigDecimal.valueOf(8));
        publicationPrintPriceMap2.put(publication3, BigDecimal.valueOf(12));

        try {
            PrintingOfficeServiceImpl printingOfficeService2 = new PrintingOfficeServiceImpl(printingOffice2, baseEmployeeSalary2, managersSalaryIncreasePercent2, incomeThresholdForSalaryIncrease2, paperPriceIncreasePercent2, publicationsThresholdForDiscount2, discountPercent2);
            printingOfficeService2.setPaperTypeBasePriceEnumMap(paperTypeBasePriceEnumMap2);
            printingOfficeService2.setPublicationPrintPriceMap(publicationPrintPriceMap2);

            try {
                printingOfficeService2.buyPaper(paperStandardA5, 3000);
                printingOfficeService2.buyPaper(paperGlossyA2, 600);
                printingOfficeService2.buyPaper(paperNewsprintA4, 1000);
            } catch (IllegalQuantityException e) {
                System.out.println(e.getCause());
            }

            try {
                printingOfficeService2.loadPaperInPrinter(paperGlossyA2, 700, printer4); //not enough papers in stock => false
                printingOfficeService2.loadPaperInPrinter(paperNewsprintA4, 300, printer6);
                printingOfficeService2.loadPaperInPrinter(paperStandardA5, 1000, printer6); //will only load 700
            } catch(IllegalQuantityException e) {
                System.out.println(e.getCause());
            }

            try {
                System.out.println("printOrder: " + printingOfficeService2.printOrder(publication1, 3, PaperType.STANDARD, false)); //not enough STANDARD paper loaded in any of the printers => false
                System.out.println("printOrder: " + printingOfficeService2.printOrder(publication2, 3, PaperType.NEWSPRINT, false));
                System.out.println("printOrder: " + printingOfficeService2.printOrder(publication3, 40, PaperType.GLOSSY, true)); //no GLOSSY paper loaded in any of the printers => false
            } catch (IllegalQuantityException e) {
                System.out.println(e.getCause());
            }

            System.out.println();

            System.out.println("Total expenses of printing office " + printingOfficeService2.getPrintingOffice().getName() + " (id:" + printingOfficeService2.getPrintingOffice().getId() + "): " + printingOfficeService2.getExpensesTotal());
            System.out.println("Income of printing office " + printingOfficeService2.getPrintingOffice().getName() + " (id:" + printingOfficeService2.getPrintingOffice().getId() + "): " + printingOfficeService2.getIncome());

            System.out.println();

            printingOfficeService2.printPublicationsNumberOfPrints();

            printingOfficeService2.writeStatement("src/main/resources/printingOfficeService2Statement.txt");


        } catch (IllegalPercentageException | IllegalAmountOfMoneyException | IllegalQuantityException e){
            throw new RuntimeException(e);
        }


    }
}