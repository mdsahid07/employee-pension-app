package edu.miu.cs.cs489appsd.lab2a.pensionapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.miu.cs.cs489appsd.lab2a.pensionapp.data.DataStore;
import edu.miu.cs.cs489appsd.lab2a.pensionapp.model.Employee;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PensionApp {

    public static void main(String[] args) throws Exception {
        List<Employee> employees = DataStore.getEmployees();
        printAllEmployees(employees);
        printQuarterlyUpcoming(employees);
    }

    // (1) All employees (JSON), salary DESC then lastName ASC
    public static void printAllEmployees(List<Employee> employees) throws Exception {
        employees.sort(Comparator.comparing(Employee::getYearlySalary).reversed()
                .thenComparing(Employee::getLastName));

        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        System.out.println("=== All Employees ===");
        System.out.println(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(employees));
    }

    // (2) Quarterly Upcoming Enrollees (JSON), employmentDate DESC
    public static void printQuarterlyUpcoming(List<Employee> employees) throws Exception {
        LocalDate now = LocalDate.now();
        int currentQuarter = (now.getMonthValue() - 1) / 3 + 1;
        int nextQuarter = currentQuarter == 4 ? 1 : currentQuarter + 1;
        int yearOfNextQ = currentQuarter == 4 ? now.getYear() + 1 : now.getYear();

        Month startMonth = Month.of((nextQuarter - 1) * 3 + 1);
        LocalDate startDate = LocalDate.of(yearOfNextQ, startMonth, 1);
        LocalDate endDate = startDate.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());

        List<Employee> eligible = employees.stream()
                .filter(e -> e.getPensionPlan() == null)  // not enrolled
                // qualifies if by the END of next quarter they have at least 3 years
                .filter(e -> !e.getEmploymentDate().plusYears(3).isAfter(endDate)) // <= endDate
                .sorted(Comparator.comparing(Employee::getEmploymentDate).reversed())
                .toList();


        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        System.out.println("=== Quarterly Upcoming Enrollees ===");
        System.out.println(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(eligible));
    }
}
