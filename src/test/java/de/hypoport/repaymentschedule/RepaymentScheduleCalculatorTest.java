package de.hypoport.repaymentschedule;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Simple test class which checks the calculated result with given example from the task.
 */
@SpringBootTest
public class RepaymentScheduleCalculatorTest {

    private final static int IN_CENT = 100;

    /**
     * Calculates a repayment schedule and and checks the first and last rates.
     */
    @Test
    void testRepaymentSchedule() {
        // creates test parameters from given task
        RepaymentInformation parameters = new RepaymentInformation();
        parameters.setLoanAmount(100000.0);
        parameters.setDurationInYears(10);
        parameters.setDebitInterestInPercentage(2.12);
        parameters.setInitialRepaymentInPercentage(2.0);

        // Creates the repayment schedule of given task, result has to be the same.
        RepaymentSchedule repaymentSchedule = RepaymentScheduleCalculator.computeRepaymentSchedule(parameters);

        List<MonthlyEntry> allMonthlyEntries = repaymentSchedule.getAllMonthlyEntries();
        // checks the first rate which symbolizes full repayment
        MonthlyEntry firstMonthlyEntry = allMonthlyEntries.get(0);
        assertEquals(firstMonthlyEntry.getDate(), YearMonth.now().atEndOfMonth());
        assertEquals(firstMonthlyEntry.getAnnuityRateInCent(), -100000.0 * IN_CENT);
        assertEquals(firstMonthlyEntry.getInterestsInCent(), 0);
        assertEquals(firstMonthlyEntry.getRepaymentInCent(), -100000.0 * IN_CENT);
        assertEquals(firstMonthlyEntry.getRemainingLoanInCent(), -100000.0 * IN_CENT);
        // checks second rate
        MonthlyEntry secondMonthlyEntry = allMonthlyEntries.get(1);
        assertEquals(secondMonthlyEntry.getDate(), YearMonth.now().plusMonths(1).atEndOfMonth()); // first repayment is in the next month at the end
        assertEquals(secondMonthlyEntry.getAnnuityRateInCent(), 343.33 * IN_CENT);
        assertEquals(secondMonthlyEntry.getInterestsInCent(), 176.67 * IN_CENT);
        assertEquals(secondMonthlyEntry.getRepaymentInCent(), 166.66 * IN_CENT);
        assertEquals(secondMonthlyEntry.getRemainingLoanInCent(), -99833.34 * IN_CENT);
        //checks third rate
        MonthlyEntry thirdMonthlyEntry = allMonthlyEntries.get(2);
        assertEquals(thirdMonthlyEntry.getDate(), YearMonth.now().plusMonths(2).atEndOfMonth());
        assertEquals(thirdMonthlyEntry.getAnnuityRateInCent(), 343.33 * IN_CENT);
        assertEquals(thirdMonthlyEntry.getInterestsInCent(), 176.37 * IN_CENT);
        assertEquals(thirdMonthlyEntry.getRepaymentInCent(), 166.96 * IN_CENT);
        assertEquals(thirdMonthlyEntry.getRemainingLoanInCent(), -99666.38 * IN_CENT);
        //now checks the 2 last rates
        MonthlyEntry secondLast = allMonthlyEntries.get(allMonthlyEntries.size() - 2);
        assertEquals(secondLast.getDate(), YearMonth.now().plusYears(10).atEndOfMonth());
        assertEquals(secondLast.getAnnuityRateInCent(), 343.33 * IN_CENT);
        assertEquals(secondLast.getInterestsInCent(), 137.71 * IN_CENT);
        assertEquals(secondLast.getRepaymentInCent(), 205.62 * IN_CENT);
        assertEquals(secondLast.getRemainingLoanInCent(), -77744.14 * IN_CENT);
        //last rate
        MonthlyEntry lastMonthlyEntry = allMonthlyEntries.get(allMonthlyEntries.size() - 1); // repayment of remaining loan
        assertEquals(lastMonthlyEntry.getDate(), YearMonth.now().plusYears(10).plusMonths(1).atEndOfMonth());
        assertEquals(lastMonthlyEntry.getAnnuityRateInCent(), 41199.60 * IN_CENT);
        assertEquals(lastMonthlyEntry.getInterestsInCent(), 1894374);
        assertEquals(lastMonthlyEntry.getRepaymentInCent(), 22255.86 * IN_CENT);
        assertEquals(lastMonthlyEntry.getRemainingLoanInCent(), -77744.14 * IN_CENT);
    }
}
