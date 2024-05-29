package org.informatics_java.service.calculations;

import org.informatics_java.data.employees.Employee;
import org.informatics_java.data.enums.EmployeePosition;

import java.math.BigDecimal;
import java.util.Set;

public class EmployeesExpensesCalculator {
    public BigDecimal EmployeesExpenses(Set<Employee> employeeSet, BigDecimal baseEmployeeSalary, BigDecimal managersSalaryIncreasePercent, BigDecimal incomeThresholdForSalaryIncrease, BigDecimal income) {
        BigDecimal expenses = BigDecimal.ZERO;
        if(isIncomeOverThreshold(incomeThresholdForSalaryIncrease, income)){
            long numberOfManagers = employeeSet
                    .stream()
                    .filter(employee -> employee.getEmployeePosition().equals(EmployeePosition.MANAGER))
                    .count();
            expenses = expenses.add(managersIncreasedSalaries(numberOfManagers, baseEmployeeSalary, managersSalaryIncreasePercent));

            long numberOfPrinterOperators = employeeSet
                    .stream()
                    .filter(employee -> employee.getEmployeePosition().equals(EmployeePosition.PRINTER_OPERATOR))
                    .count();
            expenses = expenses.add(baseEmployeeSalary.multiply(BigDecimal.valueOf(numberOfPrinterOperators)));

        } else {
            expenses =  baseEmployeeSalary.multiply(BigDecimal.valueOf(employeeSet.size()));
        }
        return expenses;
    }

    private boolean isIncomeOverThreshold(BigDecimal incomeThresholdForSalaryIncrease, BigDecimal income) {
        return income.compareTo(incomeThresholdForSalaryIncrease) > 1;
    }

    private BigDecimal managersIncreasedSalaries(long numberOfManagers, BigDecimal baseEmployeeSalary, BigDecimal managersSalaryIncreasePercent) {
        BigDecimal increasedSalary = baseEmployeeSalary.add(baseEmployeeSalary.multiply(managersSalaryIncreasePercent.divide(BigDecimal.valueOf(100))));
        return increasedSalary.multiply(BigDecimal.valueOf(numberOfManagers));
    }
}
