package org.informatics_java.service.calculations;

import org.informatics_java.data.Employee;
import org.informatics_java.data.enums.EmployeePosition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class EmployeesExpensesCalculatorTest {
    private EmployeesExpensesCalculator employeesExpensesCalculator;
    private Set<Employee> employeesAllPositions;
    private Set<Employee> employeesOnlyPrinterOperators;
    private BigDecimal baseEmployeeSalary;
    private double managersSalaryIncreasePercent;
    private BigDecimal incomeThresholdForSalaryIncrease;
    private BigDecimal incomeBelowThreshold;
    private BigDecimal incomeOverThreshold;
    private int numberOfManagers;

    @BeforeEach
    void setUp() {
        employeesExpensesCalculator = new EmployeesExpensesCalculator();

        Employee employee1 = Mockito.mock(Employee.class);
        Employee employee2 = Mockito.mock(Employee.class);
        Employee employee3 = Mockito.mock(Employee.class);
        Employee employee4 = Mockito.mock(Employee.class);

        Mockito.when(employee1.getEmployeePosition()).thenReturn(EmployeePosition.MANAGER);
        Mockito.when(employee2.getEmployeePosition()).thenReturn(EmployeePosition.MANAGER);
        Mockito.when(employee3.getEmployeePosition()).thenReturn(EmployeePosition.PRINTER_OPERATOR);
        Mockito.when(employee4.getEmployeePosition()).thenReturn(EmployeePosition.PRINTER_OPERATOR);

        employeesAllPositions = Set.of(employee1, employee2, employee3, employee4);
        employeesOnlyPrinterOperators = Set.of(employee3, employee4);

        baseEmployeeSalary = BigDecimal.valueOf(1000);

        managersSalaryIncreasePercent = 10.;

        incomeThresholdForSalaryIncrease = BigDecimal.valueOf(10000);

        incomeBelowThreshold = BigDecimal.valueOf(5000);
        incomeOverThreshold = BigDecimal.valueOf(15000);

        numberOfManagers = 2;

    }

    @Test
    void when_incomeOverThreshold_then_returnTrue() {
        assertTrue(employeesExpensesCalculator.isIncomeOverThreshold(incomeOverThreshold, incomeThresholdForSalaryIncrease));
    }

    @Test
    void when_incomeBelowThreshold_then_returnFalse() {
        assertFalse(employeesExpensesCalculator.isIncomeOverThreshold(incomeBelowThreshold, incomeThresholdForSalaryIncrease));
    }

    void sum_managersIncreasedSalariesTotal() {
        BigDecimal expected = BigDecimal.valueOf(2200.).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, employeesExpensesCalculator.managersIncreasedSalariesTotal(numberOfManagers, baseEmployeeSalary, managersSalaryIncreasePercent));
    }

    @Test
    void when_incomeBelowThreshold_and_EmployeesAllPositions_then_returnNumberOfEmployeesMultipliedBaseSalary() {
        BigDecimal expected = BigDecimal.valueOf(4000).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, employeesExpensesCalculator.EmployeesExpenses(employeesAllPositions, baseEmployeeSalary,
                managersSalaryIncreasePercent, incomeThresholdForSalaryIncrease, incomeBelowThreshold));
    }

    @Test
    void when_incomeOverThreshold_and_EmployeesAllPositions_then_returnNumberOfEmployeesMultipliedBaseSalary() {
        BigDecimal expected = BigDecimal.valueOf(4200.).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, employeesExpensesCalculator.EmployeesExpenses(employeesAllPositions, baseEmployeeSalary,
                managersSalaryIncreasePercent, incomeThresholdForSalaryIncrease, incomeOverThreshold));
    }

    @Test
    void when_incomeBelowThreshold_and_EmployeesOnlyPrintOperators_then_returnNumberOfEmployeesMultipliedBaseSalary() {
        BigDecimal expected = BigDecimal.valueOf(2000).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, employeesExpensesCalculator.EmployeesExpenses(employeesOnlyPrinterOperators, baseEmployeeSalary,
                managersSalaryIncreasePercent, incomeThresholdForSalaryIncrease, incomeBelowThreshold));
    }

    @Test
    void when_incomeOverThreshold_and_EmployeesOnlyPrintOperators_then_returnNumberOfEmployeesMultipliedBaseSalary() {
        BigDecimal expected = BigDecimal.valueOf(2000).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, employeesExpensesCalculator.EmployeesExpenses(employeesOnlyPrinterOperators, baseEmployeeSalary,
                managersSalaryIncreasePercent, incomeThresholdForSalaryIncrease, incomeBelowThreshold));
    }
}