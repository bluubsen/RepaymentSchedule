package de.hypoport.repaymentschedule;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a repayment plan. It contains all rates for the given duration.
 */
public class RepaymentSchedule {

    private int duration;
    private List<MonthlyEntry> allMonthlyEntries;

    /**
     * Returns all rates as ordered list by date.
     *
     * @return ordered list with all rates for this repayment plan
     */
    public List<MonthlyEntry> getAllMonthlyEntries() {
        return allMonthlyEntries.stream().sorted(Comparator.comparing(MonthlyEntry::getDate)).collect(Collectors.toList());
    }

    /**
     * Sets all rates for this repayment plan.
     *
     * @param allMonthlyEntries all repayment rates
     */
    public void setAllMonthlyEntries(final List<MonthlyEntry> allMonthlyEntries) {
        this.allMonthlyEntries = allMonthlyEntries;
    }

    /**
     * Returns the duration in years.
     *
     * @return duration in years
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration in years.
     *
     * @param duration duration in years
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }
}
