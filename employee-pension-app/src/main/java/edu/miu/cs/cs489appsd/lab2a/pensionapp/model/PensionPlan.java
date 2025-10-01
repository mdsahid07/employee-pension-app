package edu.miu.cs.cs489appsd.lab2a.pensionapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PensionPlan {
    private String planReferenceNumber;
    private LocalDate enrollmentDate;
    private BigDecimal monthlyContribution;

    // All-args constructor
    public PensionPlan(String planReferenceNumber, LocalDate enrollmentDate, BigDecimal monthlyContribution) {
        this.planReferenceNumber = planReferenceNumber;
        this.enrollmentDate = enrollmentDate;
        this.monthlyContribution = monthlyContribution;
    }

    // Getters & setters
    public String getPlanReferenceNumber() { return planReferenceNumber; }
    public void setPlanReferenceNumber(String planReferenceNumber) { this.planReferenceNumber = planReferenceNumber; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public BigDecimal getMonthlyContribution() { return monthlyContribution; }
    public void setMonthlyContribution(BigDecimal monthlyContribution) { this.monthlyContribution = monthlyContribution; }
}
