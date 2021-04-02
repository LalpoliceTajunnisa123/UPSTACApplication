package org.upgrad.upstac.consultation;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.TestRequestQueryService;
import org.upgrad.upstac.testrequests.TestRequestUpdateService;
import org.upgrad.upstac.users.User;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {
	
	Logger log = LoggerFactory.getLogger(ConsultationController.class);
	
	@Autowired
	TestRequestQueryService testRequestQueryService;
	
	@Autowired
    private UserLoggedInService userLoggedInService;
	
	@Autowired
    private TestRequestUpdateService testRequestUpdateService;

	@GetMapping("/in-queue")
	@PreAuthorize("hasAnyRole('DOCTOR')")
	public List<TestRequest> getForConsultations() {

		
		return testRequestQueryService.findBy(RequestStatus.LAB_TEST_COMPLETED); // getting all test requests for which
																					// lab test is completed
	}
	
	 @GetMapping
	    @PreAuthorize("hasAnyRole('DOCTOR')")
	    public List<TestRequest> getForDoctor()  {

	        try {
	            User user = userLoggedInService.getLoggedInUser();
	                  return testRequestQueryService.findByDoctor(user);


	        }catch (AppException e) {
	            throw asBadRequest(e.getMessage());
	        }
	 }
	@PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/assign/{id}")
    public TestRequest assignForConsultation(@PathVariable Long id) {

        try {
            User loggedInDoctor = userLoggedInService.getLoggedInUser(); // getting currently logged in doctor
            return testRequestUpdateService.assignForConsultation(id,loggedInDoctor); // assigning the given test request(by id) to the given doctor for consultation
        }catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }



    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/update/{id}")
    public TestRequest updateConsultation(@PathVariable Long id,@RequestBody CreateConsultationRequest testResult) {

        try {
            User loggedInDoctor = userLoggedInService.getLoggedInUser(); // getting currently logged in doctor
            return testRequestUpdateService.updateConsultation(id,testResult,loggedInDoctor);// updating the given consultation details for given test request(by id) for the given doctor
        }catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }

}
