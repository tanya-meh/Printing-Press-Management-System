package org.informatics_java.service.printingOffice;

import org.informatics_java.data.Employee;
import org.informatics_java.data.Printer;
import org.informatics_java.data.PrintingOffice;
import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.Paper;
import org.informatics_java.data.publications.Publication;
import org.informatics_java.exceptions.IllegalAmountOfMoneyException;
import org.informatics_java.exceptions.IllegalPercentageException;
import org.informatics_java.exceptions.IllegalQuantityException;
import org.informatics_java.exceptions.IncompatiblePrinterException;
import org.informatics_java.service.calculations.EmployeesExpensesCalculator;
import org.informatics_java.service.calculations.PaperExpensesCalculator;
import org.informatics_java.service.printers.PrinterService;
import org.informatics_java.service.printers.PrinterServiceImpl;

import java.math.BigDecimal;
import java.util.*;

public class PrintingOfficeServiceImpl implements PrintingOfficeService {
    private final PrinterService printerService;
    private PrintingOffice printingOffice;
    private EmployeesExpensesCalculator employeesExpensesCalculator;
    private PaperExpensesCalculator paperExpensesCalculator;
    private EnumMap<PaperType, BigDecimal> paperTypeBasePriceEnumMap;
    private Map<Publication, BigDecimal> publicationPrintPriceMap;
    private Map<Paper, Integer> paperBoughtMap;
    private BigDecimal income;
    private BigDecimal baseEmployeeSalary;
    private double managersSalaryIncreasePercent;
    private BigDecimal incomeThresholdForSalaryIncrease;
    private double paperPriceIncreasePercent;
    private int publicationsThresholdForDiscount;
    private double discountPercent;

    public PrintingOfficeServiceImpl(PrintingOffice printingOffice, BigDecimal baseEmployeeSalary,
                                     double managersSalaryIncreasePercent, BigDecimal incomeThresholdForSalaryIncrease,
                                     double paperPriceIncreasePercent, int publicationsThresholdForDiscount, double discountPercent) {

        if (managersSalaryIncreasePercent < 0 || managersSalaryIncreasePercent > 100) {
            throw new IllegalPercentageException();
        }

        if (paperPriceIncreasePercent < 0 || paperPriceIncreasePercent > 100) {
            throw new IllegalPercentageException();
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalPercentageException();
        }

        if (baseEmployeeSalary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalAmountOfMoneyException("Base employee salary must be positive.");
        }

        if (incomeThresholdForSalaryIncrease.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalAmountOfMoneyException("Income threshold for salary increase must be positive.");
        }

        if (publicationsThresholdForDiscount <= 0) {
            throw new IllegalQuantityException("Publications threshold for discount must be a positive integer.");
        }

        this.printingOffice = printingOffice;
        this.baseEmployeeSalary = baseEmployeeSalary;
        this.managersSalaryIncreasePercent = managersSalaryIncreasePercent;
        this.incomeThresholdForSalaryIncrease = incomeThresholdForSalaryIncrease;
        this.paperPriceIncreasePercent = paperPriceIncreasePercent;
        this.publicationsThresholdForDiscount = publicationsThresholdForDiscount;
        this.discountPercent = discountPercent;

        this.income = BigDecimal.ZERO;
        employeesExpensesCalculator = new EmployeesExpensesCalculator();
        paperExpensesCalculator = new PaperExpensesCalculator();
        paperTypeBasePriceEnumMap = new EnumMap<>(PaperType.class);
        publicationPrintPriceMap = new HashMap<>();
        paperBoughtMap = new HashMap<>();

        printerService = new PrinterServiceImpl();
    }

    public PrintingOffice getPrintingOffice() {
        return printingOffice;
    }

    public EmployeesExpensesCalculator getEmployeesExpensesCalculator() {
        return employeesExpensesCalculator;
    }

    public PaperExpensesCalculator getPaperExpensesCalculator() {
        return paperExpensesCalculator;
    }

    public EnumMap<PaperType, BigDecimal> getPaperTypeBasePriceEnumMap() {
        return paperTypeBasePriceEnumMap;
    }

    public Map<Publication, BigDecimal> getPublicationPrintPriceMap() {
        return publicationPrintPriceMap;
    }

    public Map<Paper, Integer> getPaperBoughtMap() {
        return paperBoughtMap;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public BigDecimal getBaseEmployeeSalary() {
        return baseEmployeeSalary;
    }

    public double getManagersSalaryIncreasePercent() {
        return managersSalaryIncreasePercent;
    }

    public BigDecimal getIncomeThresholdForSalaryIncrease() {
        return incomeThresholdForSalaryIncrease;
    }

    public double getPaperPriceIncreasePercent() {
        return paperPriceIncreasePercent;
    }

    public int getPublicationsThresholdForDiscount() {
        return publicationsThresholdForDiscount;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setPaperTypeBasePriceEnumMap(EnumMap<PaperType, BigDecimal> paperTypeBasePriceEnumMap) {
        this.paperTypeBasePriceEnumMap = paperTypeBasePriceEnumMap;
    }

    public void setPublicationPrintPriceMap(Map<Publication, BigDecimal> publicationPrintPriceMap) {
        this.publicationPrintPriceMap = publicationPrintPriceMap;
    }

    public BigDecimal putPublicationPrintPrice(Publication publication, BigDecimal printPrice) {
        return this.publicationPrintPriceMap.put(publication, printPrice);
    }

    /**
     * The method calculates the total expenses for the Printing office
     * @return sum of the expenses related to the employees and the expenses related to the paper
     */
    @Override
    public BigDecimal getExpensesTotal() {
        BigDecimal employeesExpenses = employeesExpensesCalculator.EmployeesExpenses(printingOffice.getEmployeeSet(), baseEmployeeSalary,
                managersSalaryIncreasePercent, incomeThresholdForSalaryIncrease, income);

        BigDecimal paperExpenses = paperExpensesCalculator.paperExpenses(this.paperBoughtMap, paperTypeBasePriceEnumMap,
                paperPriceIncreasePercent);

        return employeesExpenses.add(paperExpenses);
    }

    /**
     * The method increases the number of a certain paper in stock and the number of the paper bought by the Printing office
     * @param paper
     * @param numberOfPapers
     * @return the number of papers in stock before the changes
     * @throws IllegalQuantityException
     */
    @Override
    public int buyPaper(Paper paper, int numberOfPapers) throws IllegalQuantityException {
        if (numberOfPapers <= 0){
            throw new IllegalQuantityException("Number of papers must be a positive integer.");
        }

        int currNumOfPapersBought = this.paperBoughtMap.get(paper);
        this.paperBoughtMap.put(paper, currNumOfPapersBought + numberOfPapers);

        int currNumOfPapersInStock = this.printingOffice.getPapersInStockMap().get(paper);
        return this.printingOffice.putPapersInStock(paper, currNumOfPapersInStock + numberOfPapers);
    }

    /**
     * The method updates, usually decreases, the number of paper in stock. It is used after loading paper in a printer.
     * @param paper
     * @param numberOfPapers
     * @param leftPaper
     * @return the number of papers in stock before the changes
     */
    private int retrievePaperInStock(Paper paper, int numberOfPapers, int leftPaper) {
        int currNumOfPapers = this.printingOffice.getPapersInStockMap().get(paper);
        return this.printingOffice.putPapersInStock(paper, (currNumOfPapers - numberOfPapers + leftPaper));
    }

    /**
     * The method loads paper in a printer.
     * It increases the number of papers in the printer and decreases the number of papers in stock.
     * leftPaper is the number of papers that could not be loaded in the printer, because of its max limit
     * If the number of papers in stock is not enough, the loading would not be successful
     * @param paper
     * @param numberOfPapers
     * @param printer
     * @return a boolean value showing if the loading was successful
     * @throws IllegalQuantityException
     */
    @Override
    public boolean loadPaperInPrinter(Paper paper, int numberOfPapers, Printer printer) throws IllegalQuantityException {
        if (numberOfPapers <= 0){
            throw new IllegalQuantityException("Number of papers must be a positive integer.");
        }

        int leftPaper = 0;
        if (this.printingOffice.getPapersInStockMap().get(paper) >= numberOfPapers) {
            for (Printer printer1 : this.printingOffice.getPrinterSet()){
                if (printer1.equals(printer)){
                    leftPaper = printerService.loadPaper(printer1, numberOfPapers, paper);
                    break;
                }
            }

            this.retrievePaperInStock(paper, numberOfPapers, leftPaper);

            return true;
        }

        return false;
    }

    /**
     * The method adds to the total income the additional income from the printing of numberOfCopies of publication
     * @param publication
     * @param numberOfCopies
     * @return the additional income
     */
    private BigDecimal updateIncome(Publication publication, int numberOfCopies) {
        BigDecimal additionalIncome = this.publicationPrintPriceMap.get(publication).multiply(BigDecimal.valueOf(numberOfCopies));
        if(numberOfCopies > this.publicationsThresholdForDiscount) {
            additionalIncome = additionalIncome.subtract(additionalIncome.multiply(BigDecimal.valueOf(this.discountPercent/100)));
        }
        this.income = income.add(additionalIncome);
        return additionalIncome;
    }

    /**
     * The method looks for a printer in the Printing office that has enough papers loaded
     * If it finds one it calls for the method print() for this printer and updates the income, returns true
     * If there is not a printer with enough papers, it returns false
     * @param publication
     * @param numberOfCopies
     * @param paperType
     * @param coloredPrint
     * @return boolean value showing if the order was printed
     * @throws IllegalQuantityException
     */
    @Override
    public boolean printOrder(Publication publication, int numberOfCopies, PaperType paperType, boolean coloredPrint) throws IllegalQuantityException {
        if(numberOfCopies <= 0) {
            throw new IllegalQuantityException("Number of copies must be a positive integer.");
        }

        for (Printer printer : this.printingOffice.getPrinterSet()) {
            try {
                if(printerService.print(printer, publication, numberOfCopies, paperType, coloredPrint)) {
                    this.updateIncome(publication, numberOfCopies);
                    return true;
                }
            } catch (IncompatiblePrinterException e) {
                return false;
            }
        }

        return false;
    }


}
