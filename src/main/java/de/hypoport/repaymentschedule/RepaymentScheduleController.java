package de.hypoport.repaymentschedule;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * Simple controller which handle http-requests.
 */
@Controller
public class RepaymentScheduleController {

    private final static String USER_PARAMETER_KEY = "repaymentinformation";
    private final static String REPAYMENT_SCHEDULE_KEY = "repaymentSchedule";
    private final static String INDEX_HTML = "index.html";
    private final static String REPAYMENT_SCHEDULE_HTML = "showRepaymentSchedule.html";

    /**
     * Handle get request for base path + "/repaymentSchedule" and delegates to the index.html page
     *
     * @param model holder object
     * @return the html page
     */
    @GetMapping("/repaymentSchedule")
    public String getInitialPage(final Model model) {
        model.addAttribute(USER_PARAMETER_KEY, new RepaymentInformation());
        return INDEX_HTML;
    }

    /**
     * Handle post request for base path + "/repaymentSchedule". If the input is valid the parameters will be passed to the repayment schedule calculator.
     * The computed repayment plan will passed to the model object and than delegates to showRepaymentSchedule.html page.
     *
     * @param repaymentInformation with user parameters
     * @param model                holder object
     * @param errors               contains validation errors
     * @return showRepaymentSchedule.html page with created repayment schedule
     */
    @PostMapping("/repaymentSchedule")
    public String createRepaymentSchedule(@ModelAttribute(USER_PARAMETER_KEY) @Valid final RepaymentInformation repaymentInformation, final Errors errors, final Model model) {
        //Important that the ModelAttribute contains the given attribute name, otherwise it looks for the variable name "repaymentInformation"
        if (errors.hasErrors() || isInvalidCurrencyValue(repaymentInformation.getLoanAmount())) {
            model.addAttribute(USER_PARAMETER_KEY, repaymentInformation);
            return INDEX_HTML;
        }
        RepaymentSchedule repaymentSchedule = RepaymentScheduleCalculator.computeRepaymentSchedule(repaymentInformation);
        model.addAttribute(USER_PARAMETER_KEY, repaymentInformation);
        model.addAttribute(REPAYMENT_SCHEDULE_KEY, repaymentSchedule);
        return REPAYMENT_SCHEDULE_HTML;
    }

    /**
     * If the given value has more than 2 decimals it returns true (for invalid value).
     *
     * @param currencyValue to check currency value
     * @return true if given value is invalid
     */
    private boolean isInvalidCurrencyValue(final Double currencyValue) {
        //TODO create annotation or use pattern -> use spring bean validation (and create test suit)
        String loanAsString = String.valueOf(currencyValue);
        int index = loanAsString.indexOf(".");
        return index != -1 && loanAsString.substring(index).length() > 2;
    }
}
