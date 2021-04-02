package org.upgrad.upstac.testrequests;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.labrequests.LabRequestsController;
import org.upgrad.upstac.users.User;

@RestController
public class TestRequestController {
	
	@Autowired
	private UserLoggedInService userLoggedInService;
	
	@Autowired
	TestRequestService testRequestService;
	
	@Autowired
	TestRequestQueryService testRequestQueryService;
	
	Logger log = LoggerFactory.getLogger(LabRequestsController.class);

	@PostMapping("/api/testrequests")
    public TestRequest createRequest(@RequestBody CreateTestRequest createTestRequest) {
		try {
            User user = userLoggedInService.getLoggedInUser();
            return testRequestService.createTestRequest(user, createTestRequest);
		}  catch (AppException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } 
        
    }
	
	@GetMapping("/api/testrequests")
    public List<TestRequest> requestHistory() {

        User user = userLoggedInService.getLoggedInUser();
        return testRequestService.getHistoryFor(user);


    }

	@GetMapping("/api/testrequests/{id}")
    public Optional<TestRequest> getById(@PathVariable Long id) {


        return testRequestQueryService.getTestRequestById(id);


    }
}
