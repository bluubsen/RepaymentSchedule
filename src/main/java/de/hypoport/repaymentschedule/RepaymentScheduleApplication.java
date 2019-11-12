package de.hypoport.repaymentschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Is the configuration class for the repayment schedule web-application.
 */
@SpringBootApplication
public class RepaymentScheduleApplication {

    /**
     * Main entry point. Starts the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(RepaymentScheduleApplication.class, args);
    }

}
