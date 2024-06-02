package org.informatics_java.service.calculations;

import org.informatics_java.data.Employee;
import org.informatics_java.data.enums.EmployeePosition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public class EmployeesExpensesCalculator {
    /**
     * The method calculates all the expenses related to the employees
     * @param employeeSet
     * @param baseEmployeeSalary
     * @param managersSalaryIncreasePercent
     * @param incomeThresholdForSalaryIncrease
     * @param income
     * @return value of total expenses for the salaries of the employees
     */
    public BigDecimal EmployeesExpenses(Set<Employee> employeeSet, BigDecimal baseEmployeeSalary, double managersSalaryIncreasePercent, BigDecimal incomeThresholdForSalaryIncrease, BigDecimal income) {
        BigDecimal expenses = BigDecimal.ZERO;
        if(isIncomeOverThreshold(income, incomeThresholdForSalaryIncrease)){
            long numberOfManagers = employeeSet
                    .stream()
                    .filter(employee -> employee.getEmployeePosition().equals(EmployeePosition.MANAGER))
                    .count();
            expenses = expenses.add(managersIncreasedSalariesTotal(numberOfManagers, baseEmployeeSalary, managersSalaryIncreasePercent));

            long numberOfPrinterOperators = employeeSet
                    .stream()
                    .filter(employee -> employee.getEmployeePosition().equals(EmployeePosition.PRINTER_OPERATOR))
                    .count();
            expenses = expenses.add(baseEmployeeSalary.multiply(BigDecimal.valueOf(numberOfPrinterOperators)));

        } else {
            expenses =  baseEmployeeSalary.multiply(BigDecimal.valueOf(employeeSet.size()));
        }
        return expenses.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @param incomeThresholdForSalaryIncrease
     * @param income
     * @return boolean showing if the income of the printing office is over the threshold
     */
    public boolean isIncomeOverThreshold(BigDecimal income, BigDecimal incomeThresholdForSalaryIncrease) {
        return income.compareTo(incomeThresholdForSalaryIncrease) > 0;
    }

    /**
     * @param numberOfManagers
     * @param baseEmployeeSalary
     * @param managersSalaryIncreasePercent
     * @return sum of the salaries with increase included of all managers
     */
    public BigDecimal managersIncreasedSalariesTotal(long numberOfManagers, BigDecimal baseEmployeeSalary, double managersSalaryIncreasePercent) {
        BigDecimal increasedSalary = baseEmployeeSalary.add(baseEmployeeSalary.multiply(BigDecimal.valueOf(managersSalaryIncreasePercent/100)));
        return increasedSalary.multiply(BigDecimal.valueOf(numberOfManagers)).setScale(2, RoundingMode.HALF_UP);
    }
}
