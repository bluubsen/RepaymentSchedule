package de.hypoport.repaymentschedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.Assert;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the {@link RepaymentScheduleController}. BeanValidation will not be executed at this point.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RepaymentScheduleController.class)
public class RepaymentScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private RepaymentInformation repaymentInformation;

    @BeforeEach
    void setUp() {
        repaymentInformation = new RepaymentInformation();
        repaymentInformation.setDurationInYears(20);
        repaymentInformation.setInitialRepaymentInPercentage(3.0);
        repaymentInformation.setDebitInterestInPercentage(4.0);
        repaymentInformation.setLoanAmount(1000.0);
    }

    /**
     * Tests a valid get request for basePath + /repaymentSchedule. Expects that the view name is index.html and the holding object contains a dto.
     *
     * @throws Exception
     */
    @Test
    void validGetRequest_IndexHtml() throws Exception {
        ResultActions perform = mockMvc.perform(get("/repaymentSchedule"));
        perform.andExpect(status().isOk())
                .andExpect(mvcResult -> mvcResult.getModelAndView().getViewName().equals("index.html"))
                .andExpect(mvcResult -> Assert.notNull(mvcResult.getModelAndView().getModel().get("repaymentinformation"), "should contain a dto"));
    }

    /**
     * Tests a valid post request for basePath + /repaymentSchedule. Expects the html page showRepaymentSchedule.html, the passed dto object and the repayment plan.
     *
     * @throws Exception
     */
    @Test
    void validPostRequest_ShowRepaymentSchedule() throws Exception {
        ResultActions perform = mockMvc.perform(post("/repaymentSchedule").flashAttr("repaymentinformation", repaymentInformation));
        perform.andExpect(status().isOk())
                .andExpect(mvcResult -> mvcResult.getModelAndView().getViewName().equals("showRepaymentSchedule.html"))
                .andExpect(mvcResult -> Assert.notNull(mvcResult.getModelAndView().getModel().get("repaymentinformation"), "should contain a dto"))
                .andExpect(mvcResult -> Assert.notNull(mvcResult.getModelAndView().getModel().get("repaymentSchedule"), "should contain a repayment schedule"));
    }
}
