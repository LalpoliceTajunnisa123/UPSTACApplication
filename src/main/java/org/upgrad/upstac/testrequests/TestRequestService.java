package org.upgrad.upstac.testrequests;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.users.User;

@Service
public class TestRequestService {
	
	@Autowired
	TestRequestRepository testRequestRepository;

	public TestRequest createTestRequest(User user, CreateTestRequest createTestRequest) {
		
		
   if(validateTestRequestCanBeSaved(createTestRequest)==false)
	   throw new AppException("A request with same email or phone number already exists");
	   
   
		TestRequest testRequest = new TestRequest();

        testRequest.setName(createTestRequest.getName());
        testRequest.setCreated(LocalDate.now());
        testRequest.setRequestStatus(RequestStatus.INITIATED);
        testRequest.setAge(createTestRequest.getAge());
        testRequest.setEmail(createTestRequest.getEmail());
        testRequest.setPhoneNumber(createTestRequest.getPhoneNumber());
        testRequest.setPinCode(createTestRequest.getPinCode());
        testRequest.setAddress(createTestRequest.getAddress());
        testRequest.setGender(createTestRequest.getGender());

        testRequest.setCreatedBy(user);
        return testRequestRepository.save(testRequest);
	}
    
	public boolean validateTestRequestCanBeSaved(CreateTestRequest createTestRequest) {
		
		List<TestRequest> testRequests=testRequestRepository.findByEmailOrPhoneNumber(createTestRequest.getEmail(), createTestRequest.getPhoneNumber());
	  
        for(TestRequest testRequest:testRequests) {
        	if(testRequest.getRequestStatus().equals(RequestStatus.COMPLETED)==false) {
        		return false;
        	}
        }
        return true;
	}

	public List<TestRequest> getHistoryFor(User user) {
		return testRequestRepository.findByCreatedBy(user);
	}
	
}
