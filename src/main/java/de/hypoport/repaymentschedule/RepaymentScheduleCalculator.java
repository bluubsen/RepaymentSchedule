package de.hypoport.repaymentschedule;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculates repayment schedules.
 */
public class RepaymentScheduleCalculator {

    /**
     * Calculates a repayment plan by given {@link RepaymentInformation}. Each monthly entry is at the last day in a month.
     * The first monthly entry symbolizes a full repayment of given loan. Next month the regular repayment starts. At the end of the duration all values will be accumulated.
     *
     * @param repaymentInformation given parameters for calculation
     * @return calculated repayment schedule with monthly entries
     */
    public static RepaymentSchedule computeRepaymentSchedule(final RepaymentInformation repaymentInformation) {
        RepaymentSchedule schedule = new RepaymentSchedule();
        long annuityMonth = computeAnnuityMonth(repaymentInformation);
        schedule.setDuration(repaymentInformation.getDurationInYears());
        List<MonthlyEntry> allMonthlyEntries = new ArrayList<>();
        //first entry symbolised full payment of loan
        allMonthlyEntries.add(calculateFullPaymentOfLoan(repaymentInformation));
        //calculate first and last date
        YearMonth initialDate = YearMonth.now();
        YearMonth firstMonth = initialDate.plusMonths(1); // first repayment starts next month
        YearMonth lastMonth = initialDate.plusMonths(1).plusYears(repaymentInformation.getDurationInYears());
        // compute all payments
        long accumulatedAnnuity = 0;
        long accumulatedInterests = 0;
        long accumulatedRepayment = 0;
        long remainingLoan = repaymentInformation.getLoanAmountInCent();
        //calculates all rates
        Double debitInterestInPercentage = repaymentInformation.getDebitInterestInPercentage();
        for (YearMonth month = firstMonth; month.isBefore(lastMonth); month = month.plusMonths(1)) {
            long interestPortion = calculateDebitInterestMonth(remainingLoan, debitInterestInPercentage);
            long repayment = annuityMonth - interestPortion;
            remainingLoan -= repayment;

            MonthlyEntry monthlyEntry = setMonthlyEntryValues(month, annuityMonth, interestPortion, repayment, remainingLoan);
            allMonthlyEntries.add(monthlyEntry);

            accumulatedAnnuity += annuityMonth;
            accumulatedInterests += interestPortion;
            accumulatedRepayment += repayment;
        }
        //calculate last entry (interests end)
        MonthlyEntry lastMonthlyEntry = setMonthlyEntryValues(lastMonth, accumulatedAnnuity, accumulatedInterests, accumulatedRepayment, remainingLoan);
        allMonthlyEntries.add(lastMonthlyEntry);
        schedule.setAllMonthlyEntries(allMonthlyEntries);
        return schedule;
    }

    private static MonthlyEntry setMonthlyEntryValues(YearMonth lastMonth, long accumulatedAnnuity, long accumulatedInterests, long accumulatedRepayment, long remainingLoan) {
        MonthlyEntry monthlyEntry = new MonthlyEntry();
        monthlyEntry.setDate(lastMonth.atEndOfMonth());
        monthlyEntry.setAnnuityRateInCent(accumulatedAnnuity);
        monthlyEntry.setInterestsInCent(accumulatedInterests);
        monthlyEntry.setRepaymentInCent(accumulatedRepayment);
        monthlyEntry.setRemainingLoanInCent(-remainingLoan);
        return monthlyEntry;
    }

    private static MonthlyEntry calculateFullPaymentOfLoan(final RepaymentInformation information) {
        MonthlyEntry fullRepayment = new MonthlyEntry();
        fullRepayment.setDate(YearMonth.now().atEndOfMonth());
        fullRepayment.setInterestsInCent(0);
        fullRepayment.setRepaymentInCent(-information.getLoanAmountInCent());
        fullRepayment.setAnnuityRateInCent(-information.getLoanAmountInCent());
        fullRepayment.setRemainingLoanInCent(-information.getLoanAmountInCent());
        return fullRepayment;
    }

    private static long calculateDebitInterestMonth(final long loan, final double debitInterest) {
        double interestMonth = (debitInterest / 100) / 12;
        return Math.round(loan * interestMonth);
    }

    private static long computeAnnuityMonth(final RepaymentInformation information) {
        double debitInterest = information.getDebitInterestInPercentage();
        double initialRepayment = information.getInitialRepaymentInPercentage();
        return (long) ((debitInterest + initialRepayment) / 100 * information.getLoanAmountInCent()) / 12; // always shorten and receive the remaining amount at the end
    }
}
