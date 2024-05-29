package org.informatics_java.service.printingOffice;

import org.informatics_java.data.employees.Employee;
import org.informatics_java.data.paper.Paper;
import org.informatics_java.service.calculations.EmployeesExpensesCalculator;
import org.informatics_java.service.calculations.PaperExpensesCalculator;
import org.informatics_java.service.printers.Printer;
import org.informatics_java.data.publications.Publication;
import org.informatics_java.data.enums.PaperType;

import java.math.BigDecimal;
import java.util.*;

public class PrintingOfficeImpl implements PrintingOffice{
    private EmployeesExpensesCalculator employeesExpensesCalculator;
    private PaperExpensesCalculator paperExpensesCalculator;
    private Set<Employee> employeeSet;
    private Set<Printer> printerSet;
    private Map<Paper, Integer> papersInStockMap;
    private EnumMap<PaperType, BigDecimal> paperTypeBasePriceEnumMap;
    private Map<Publication, BigDecimal> publicationPrintPriceMap;
    private BigDecimal income;
    private BigDecimal baseEmployeeSalary;
    private BigDecimal managersSalaryIncreasePercent;
    private BigDecimal incomeThresholdForSalaryIncrease;
    private BigDecimal paperPriceIncreasePercent;
    private int publicationsThresholdForDiscount;
    private int discountPercent;

    public PrintingOfficeImpl(EnumMap<PaperType, BigDecimal> paperTypeBasePriceEnumMap, BigDecimal baseEmployeeSalary,
                              BigDecimal managersSalaryIncreasePercent, BigDecimal incomeThresholdForSalaryIncrease,
                              BigDecimal paperPriceIncreasePercent, int publicationsThresholdForDiscount, int discountPercent) {

        this.paperTypeBasePriceEnumMap.putAll(paperTypeBasePriceEnumMap);
        this.baseEmployeeSalary = baseEmployeeSalary;
        this.managersSalaryIncreasePercent = managersSalaryIncreasePercent;
        this.incomeThresholdForSalaryIncrease = incomeThresholdForSalaryIncrease;
        this.paperPriceIncreasePercent = paperPriceIncreasePercent;
        this.publicationsThresholdForDiscount = publicationsThresholdForDiscount;
        this.discountPercent = discountPercent;

        this.employeesExpensesCalculator = new EmployeesExpensesCalculator();
        this.paperExpensesCalculator = new PaperExpensesCalculator();
        this.employeeSet = new HashSet<>();
        this.printerSet = new HashSet<>();
        this.papersInStockMap = new TreeMap<>();
        this.publicationPrintPriceMap = new HashMap<>();
        this.income = BigDecimal.ZERO;
    }

    public EmployeesExpensesCalculator getEmployeesExpensesCalculator() {
        return employeesExpensesCalculator;
    }

    public PaperExpensesCalculator getPaperExpensesCalculator() {
        return paperExpensesCalculator;
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

    public EnumMap<PaperType, BigDecimal> getPaperTypeBasePriceEnumMap() {
        return paperTypeBasePriceEnumMap;
    }

    public Map<Publication, BigDecimal> getPublicationPrintPriceMap() {
        return publicationPrintPriceMap;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public BigDecimal getBaseEmployeeSalary() {
        return baseEmployeeSalary;
    }

    public BigDecimal getManagersSalaryIncreasePercent() {
        return managersSalaryIncreasePercent;
    }

    public BigDecimal getIncomeThresholdForSalaryIncrease() {
        return incomeThresholdForSalaryIncrease;
    }

    public BigDecimal getPaperPriceIncreasePercent() {
        return paperPriceIncreasePercent;
    }

    public int getPublicationsThresholdForDiscount() {
        return publicationsThresholdForDiscount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    @Override
    public String toString() {
        return "PrintingOfficeImpl{" +
                "employeeSet=" + employeeSet +
                ", printerSet=" + printerSet +
                ", papersInStockMap=" + papersInStockMap +
                ", paperTypeBasePriceEnumMap=" + paperTypeBasePriceEnumMap +
                ", publicationPrintPriceMap=" + publicationPrintPriceMap +
                ", income=" + income +
                ", baseEmployeeSalary=" + baseEmployeeSalary +
                ", managersSalaryIncreasePercent=" + managersSalaryIncreasePercent +
                ", incomeThresholdForSalaryIncrease=" + incomeThresholdForSalaryIncrease +
                ", paperPriceIncreasePercent=" + paperPriceIncreasePercent +
                ", publicationsThresholdForDiscount=" + publicationsThresholdForDiscount +
                ", discountPercent=" + discountPercent +
                '}';
    }

    @Override
    public BigDecimal getExpensesTotal() {
        BigDecimal employeesExpenses = employeesExpensesCalculator.EmployeesExpenses(employeeSet, baseEmployeeSalary,
                managersSalaryIncreasePercent, incomeThresholdForSalaryIncrease, income);
        BigDecimal paperExpenses = paperExpensesCalculator.paperExpenses(paperTypeBasePriceEnumMap, papersInStockMap, paperPriceIncreasePercent);

        return employeesExpenses.add(paperExpenses);
    }

    @Override
    public int buyPaper(Paper paper, int numberOfPapers) {
        int currNumOfPapers = this.papersInStockMap.get(paper);
        return this.papersInStockMap.put(paper, currNumOfPapers + numberOfPapers);
    }

    @Override
    public boolean loadPaperInPrinter(int numberOfPapers, Paper paper, Printer printer) {
        if (this.papersInStockMap.get(paper) >= numberOfPapers) {
            int leftPaper = this.printerSet
                    .stream()
                    .filter(printer1 -> printer1.equals(printer))
                    .mapToInt(printer1 -> printer1.loadPaper(numberOfPapers, paper))
                    .sum();

            int currNumOfPapers = this.papersInStockMap.get(paper);
            this.papersInStockMap.put(paper, (currNumOfPapers - numberOfPapers + leftPaper));
            return true;
        }
        return false;
    }

    private void updateIncome(Publication publication, int numberOfCopies) {
        BigDecimal additionalIncome = publicationPrintPriceMap.get(publication).multiply(BigDecimal.valueOf(numberOfCopies));
        if(numberOfCopies > this.publicationsThresholdForDiscount) {
            additionalIncome = additionalIncome.subtract(additionalIncome.multiply(BigDecimal.valueOf(this.discountPercent/100)));
        }
        income.add(additionalIncome);
    }
    @Override
    public boolean printOrder(Publication publication, int numberOfCopies, PaperType paperType, boolean coloredPrint) {
        //exception za not enough paper
        for (Printer printer : printerSet) {
            if(printer.print(publication, numberOfCopies, paperType, coloredPrint)) {
                this.updateIncome(publication, numberOfCopies);
                return true;
            }
        }

        return false;
    }


}
