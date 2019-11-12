package de.hypoport.repaymentschedule;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Symbolizes a request object with all parameters to calculate a repayment schedule. Stores all user parameters for the repayment schedule.
 */
public class RepaymentInformation {

    @Min(1)
    @NotNull
    private Double loanAmount;
    @NotNull
    private Double debitInterestInPercentage;
    @Min(1)
    @Max(100)
    @NotNull
    private Double initialRepaymentInPercentage;
    @Min(1)
    @Max(60)
    @NotNull
    private Integer durationInYears;

    /**
     * Returns the full loan amount in euro.
     *
     * @return loan amount in euro
     */
    public Double getLoanAmount() {
        return loanAmount;
    }

    /**
     * Sets the loan amount in euro.
     *
     * @param loanAmount loan amount in euro
     */
    public void setLoanAmount(final Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     * Returns the debit interests in percentage.
     *
     * @return debit interests in percentage
     */
    public Double getDebitInterestInPercentage() {
        return debitInterestInPercentage;
    }

    /**
     * Sets the debit interests in percentage.
     *
     * @param debitInterestInPercentage debit interests in percentage
     */
    public void setDebitInterestInPercentage(final Double debitInterestInPercentage) {
        this.debitInterestInPercentage = debitInterestInPercentage;
    }

    /**
     * Returns the initial repayment in percentage.
     *
     * @return initial repayment in percentage
     */
    public Double getInitialRepaymentInPercentage() {
        return initialRepaymentInPercentage;
    }

    /**
     * Sets the initial repayment in percentage.
     *
     * @param initialRepaymentInPercentage initial repayment in percentage
     */
    public void setInitialRepaymentInPercentage(final Double initialRepaymentInPercentage) {
        this.initialRepaymentInPercentage = initialRepaymentInPercentage;
    }

    /**
     * Returns the duration of repayment schedule in years.
     *
     * @return duration in years
     */
    public Integer getDurationInYears() {
        return durationInYears;
    }

    /**
     * Sets the duration of repayment schedule in years.
     *
     * @param durationInYears duration in years
     */
    public void setDurationInYears(final Integer durationInYears) {
        this.durationInYears = durationInYears;
    }

    /**
     * Returns the loan amount in cent.
     *
     * @return loan amount in cent
     */
    public long getLoanAmountInCent() {
        return (long) (loanAmount * 100);
    }
}
