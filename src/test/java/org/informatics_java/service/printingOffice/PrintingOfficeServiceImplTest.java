package org.informatics_java.service.printingOffice;

import org.informatics_java.data.Employee;
import org.informatics_java.data.Paper;

import org.informatics_java.data.Printer;
import org.informatics_java.data.PrintingOffice;
import org.informatics_java.data.enums.EmployeePosition;
import org.informatics_java.data.enums.PageSize;
import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.publications.Publication;
import org.informatics_java.exceptions.IllegalQuantityException;
import org.informatics_java.service.calculations.EmployeesExpensesCalculator;
import org.informatics_java.service.calculations.PaperExpensesCalculator;
import org.informatics_java.service.printers.PrinterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.junit.platform.commons.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class PrintingOfficeServiceImplTest {
    @InjectMocks
    private PrintingOfficeServiceImpl printingOfficeService;
    private BigDecimal baseEmployeeSalary;
    private double managersSalaryIncreasePercent;
    private BigDecimal incomeThresholdForSalaryIncrease;
    private double paperPriceIncreasePercent;
    private int publicationsThresholdForDiscount;
    private double discountPercent;
    private PrinterService printerService;
    private PrintingOffice printingOffice;
    private Map<Paper, Integer> paperBoughtMap;
    private Paper paper;
    private int numberOfPapers;
    private Printer printer;
    private Publication publication;
    private int numberOfCopies;
    private PaperType paperType;
    private boolean coloredPrint;


    @BeforeEach
    void setUp() {
        baseEmployeeSalary = BigDecimal.valueOf(1000);
        managersSalaryIncreasePercent = 10.;
        incomeThresholdForSalaryIncrease = BigDecimal.valueOf(1000);
        paperPriceIncreasePercent = 50.;
        publicationsThresholdForDiscount = 10;
        discountPercent = 10.;

        printerService = Mockito.mock(PrinterService.class);

        printingOffice = Mockito.mock(PrintingOffice.class);

        Paper paper1 = Mockito.mock(Paper.class);
        Paper paper2 = Mockito.mock(Paper.class);
        Paper paper3 = Mockito.mock(Paper.class);
        Paper paper4 = Mockito.mock(Paper.class);
        Paper paper5 = Mockito.mock(Paper.class);

        Mockito.when(paper1.getPaperType()).thenReturn(PaperType.STANDARD);
        Mockito.when(paper2.getPaperType()).thenReturn(PaperType.STANDARD);
        Mockito.when(paper3.getPaperType()).thenReturn(PaperType.GLOSSY);
        Mockito.when(paper4.getPaperType()).thenReturn(PaperType.NEWSPRINT);
        Mockito.when(paper5.getPaperType()).thenReturn(PaperType.NEWSPRINT);

        Mockito.when(paper1.getPageSize()).thenReturn(PageSize.A5);
        Mockito.when(paper2.getPageSize()).thenReturn(PageSize.A4);
        Mockito.when(paper3.getPageSize()).thenReturn(PageSize.A3);
        Mockito.when(paper4.getPageSize()).thenReturn(PageSize.A2);
        Mockito.when(paper5.getPageSize()).thenReturn(PageSize.A1);

        paperBoughtMap = Map.of(paper1, 30, paper2, 30, paper3, 30, paper4, 30, paper5, 30);

        paper = Mockito.mock(Paper.class);

        numberOfPapers = 20;

        printer = Mockito.mock(Printer.class);
        publication = Mockito.mock(Publication.class);
        numberOfCopies = 20;

        printingOfficeService = new PrintingOfficeServiceImpl(printingOffice, baseEmployeeSalary, managersSalaryIncreasePercent,
                incomeThresholdForSalaryIncrease,paperPriceIncreasePercent, publicationsThresholdForDiscount, discountPercent);

        printingOfficeService.setPaperTypeBasePriceEnumMap(new EnumMap<>(PaperType.class));

    }

    /*@Test
    void getExpensesTotal() {
        Mockito.when(printingOffice.getEmployeeSet()).thenReturn(Set.of());

        EmployeesExpensesCalculator employeesExpensesCalculator = Mockito.mock(EmployeesExpensesCalculator.class);
        PaperExpensesCalculator paperExpensesCalculator = Mockito.mock(PaperExpensesCalculator.class);

        Class<?> printingOfficeServiceImplClass = Class.forName("org.informatics_java.service.printingOffice.PrintingOfficeServiceImpl");
        Field field = printingOfficeServiceImplClass.getDeclaredField("income");
        field.setAccessible(true);
        field.set(BigDecimal.valueOf(10_000));
        Mockito.when(employeesExpensesCalculator.employeesExpenses(printingOffice.getEmployeeSet(), baseEmployeeSalary, managersSalaryIncreasePercent, incomeThresholdForSalaryIncrease, income))
                .thenReturn(BigDecimal.valueOf(4200.).setScale(2, RoundingMode.HALF_UP));

        Mockito.when(paperExpensesCalculator.paperExpenses(paperBoughtMap, printingOfficeService.getPaperTypeBasePriceEnumMap(), paperPriceIncreasePercent)).thenReturn(BigDecimal.valueOf(1050.20));

        BigDecimal expected = BigDecimal.valueOf(5250.20);
        assertEquals(expected, printingOfficeService.getExpensesTotal());

    }*/

    @Test
    void when_numberOfPagesIsNotPositiveInteger_then_loadPaperInPrinter_throwIllegalQuantityException() {
        numberOfPapers = -20;
        assertThrows(IllegalQuantityException.class, () -> printingOfficeService.loadPaperInPrinter(paper, numberOfPapers, printer));
    }

    @Test
    void when_thereAreNotEnoughPapersInStock_then_loadPaperInPrinter_throwIllegalQuantityException() {
        numberOfPapers = 20;
        Mockito.when(printingOffice.getPapersInStockMap()).thenReturn(Map.of(paper, 10));

        assertFalse(printingOfficeService.loadPaperInPrinter(paper, numberOfPapers, printer));
    }

    @Test
    void when_thereAreEnoughPapersInStock_then_loadPaperInPrinter_throwIllegalQuantityException() {
        numberOfPapers = 20;
        Mockito.when(printingOffice.getPapersInStockMap()).thenReturn(Map.of(paper, 30));

        assertTrue(printingOfficeService.loadPaperInPrinter(paper, numberOfPapers, printer));
    }

    @Test
    void when_incomeIsNotOverThresholdForDiscounts_then_updateIncome_returns_smallerAMount() {
        numberOfCopies = 10;
        printingOfficeService.setPublicationPrintPriceMap(Map.of(publication, BigDecimal.valueOf(20.50)));

        assertEquals(BigDecimal.valueOf(205.), printingOfficeService.updateIncome(publication, numberOfCopies));
    }

    @Test
    void when_incomeIsOverThresholdForDiscounts_then_updateIncome_returns_smallerAMount() {
        numberOfCopies = 20;
        printingOfficeService.setPublicationPrintPriceMap(Map.of(publication, BigDecimal.valueOf(20.50)));

        assertEquals(BigDecimal.valueOf(369.).setScale(2, RoundingMode.HALF_UP), printingOfficeService.updateIncome(publication, numberOfCopies));
    }

    @Test
    void when_numberOfCopiesIsNotPositiveInteger_then_printOrder_throwIllegalQuantityException() {
        numberOfCopies = -20;
        PaperType paperType = Mockito.mock(PaperType.class);
        boolean coloredPrint = true;

        assertThrows(IllegalQuantityException.class, () -> printingOfficeService.printOrder(publication, numberOfCopies, paperType, coloredPrint));
    }

    @Test
    void when_colorPrintIsTrueButThereIsNotCompatiblePrinter_then_printOrder_returns_false() {
        numberOfCopies = 20;
        PaperType paperType = Mockito.mock(PaperType.class);
        boolean coloredPrint = true;
        Mockito.when(printer.isColored()).thenReturn(false);
        Mockito.when(printingOffice.getPrinterSet()).thenReturn(Set.of(printer));

        assertFalse(printingOfficeService.printOrder(publication, numberOfCopies, paperType, coloredPrint));
    }

    @Test
    void when_colorPrintIsTrueAndThereIsCompatiblePrinterWithEnoughPapers_then_printOrder_returns_true() {
        numberOfCopies = 20;
        paperType = PaperType.STANDARD;
        coloredPrint = true;
        printingOfficeService.setPublicationPrintPriceMap(Map.of(publication, BigDecimal.valueOf(20.50)));

        Mockito.when(publication.getNumberOfPages()).thenReturn(10);
        Mockito.when(publication.getPageSize()).thenReturn(PageSize.A5);

        Mockito.when(printer.isColored()).thenReturn(true);
        Mockito.when(printer.getNumberOfPapersLoadedMap()).thenReturn(Map.of(new Paper(paperType, PageSize.A5), 300));

        Mockito.when(printingOffice.getPrinterSet()).thenReturn(Set.of(printer));

        assertTrue(printingOfficeService.printOrder(publication, numberOfCopies, paperType, coloredPrint));
    }
}
