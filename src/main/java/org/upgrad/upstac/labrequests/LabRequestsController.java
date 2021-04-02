package org.upgrad.upstac.labrequests;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
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
import static org.upgrad.upstac.exception.UpgradResponseStatusException.asConstraintViolation;

@RestController
@RequestMapping("/api/labrequests")
public class LabRequestsController {
	
	@Autowired
	TestRequestQueryService testRequestQueryService;
	
	@Autowired
	TestRequestUpdateService testRequestUpdateService;
	
	@Autowired
    private UserLoggedInService userLoggedInService;
	
	Logger log = LoggerFactory.getLogger(LabRequestsController.class);
	
	@GetMapping("/to-be-tested")
    @PreAuthorize("hasAnyRole('TESTER')")
    public List<TestRequest> getForTests()  {

       return testRequestQueryService.findBy(RequestStatus.INITIATED);
    }
	
	 @GetMapping
	 @PreAuthorize("hasAnyRole('TESTER')")
	 public List<TestRequest> getForTester()  {

	    User loggedInTester = userLoggedInService.getLoggedInUser(); //getting currently logged in tester
	    return testRequestQueryService.findByCreatedBy(loggedInTester); //getting all test requests assigned to the given tester
	}
	 
	 @PreAuthorize("hasAnyRole('TESTER')")
	 @PutMapping("/assign/{id}")
	 public TestRequest assignForLabTest(@PathVariable Long id) {

	     User tester =userLoggedInService.getLoggedInUser();
	     return testRequestUpdateService.assignForLabTest(id,tester);
	}
	 
	 @PreAuthorize("hasAnyRole('TESTER')")
	    @PutMapping("/update/{id}")
	    public TestRequest updateLabTest(@PathVariable Long id,@RequestBody CreateLabResult createLabResult) {

	        try {
	            User tester=userLoggedInService.getLoggedInUser();
	            return testRequestUpdateService.updateLabTest(id,createLabResult,tester);
	        }catch (AppException e) {
	            throw asBadRequest(e.getMessage());
	        }
	    }
	}
