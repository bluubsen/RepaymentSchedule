package de.hypoport.repaymentschedule;

import java.time.LocalDate;

/**
 * Represents a montly repayment of a {@link RepaymentSchedule}.
 */
public class MonthlyEntry {

    private LocalDate date;
    private long remainingLoanInCent;
    private long interestsInCent;
    private long repaymentInCent;
    private long annuityRateInCent;

    /**
     * Returns the full remaining loan in cent.
     *
     * @return full remaining loan
     */
    public long getRemainingLoanInCent() {
        return remainingLoanInCent;
    }

    /**
     * Sets the remaining loan in cent.
     *
     * @param remainingLoanInCent remaining loan in cent
     */
    public void setRemainingLoanInCent(final long remainingLoanInCent) {
        this.remainingLoanInCent = remainingLoanInCent;
    }

    /**
     * Returns the repayment date for this rate.
     *
     * @return repayment date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the repayment date.
     *
     * @param date repayment date
     */
    public void setDate(final LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the interests portion in cent of the annuity.
     *
     * @return interests in cent
     */
    public long getInterestsInCent() {
        return interestsInCent;
    }

    /**
     * Sets the interests portion in cent of the annuity.
     *
     * @param interestsInCent interests in cent
     */
    public void setInterestsInCent(final long interestsInCent) {
        this.interestsInCent = interestsInCent;
    }

    /**
     * Returns the repayment portion in cent of the annuity
     *
     * @return repayment in cent
     */
    public long getRepaymentInCent() {
        return repaymentInCent;
    }

    /**
     * Sets the repayment portion in cent of the annuity.
     *
     * @param repaymentInCent repayment in cent
     */
    public void setRepaymentInCent(final long repaymentInCent) {
        this.repaymentInCent = repaymentInCent;
    }

    /**
     * Returns the annuity rate in cent.
     *
     * @return annuity rate in cent
     */
    public long getAnnuityRateInCent() {
        return annuityRateInCent;
    }

    /**
     * Sets the annuity rate in cent
     *
     * @param annuityRate annuity rate in cent
     */
    public void setAnnuityRateInCent(final long annuityRate) {
        this.annuityRateInCent = annuityRate;
    }
}
