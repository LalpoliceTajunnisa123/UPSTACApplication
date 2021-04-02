package org.upgrad.upstac.testrequests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.upstac.users.User;

@Service
public class TestRequestFlowService {
	
	@Autowired
	TestRequestFlowRepository testRequestFlowRepository;

	public void log(TestRequest testRequest, RequestStatus from, RequestStatus to, User changedBy) {
        TestRequestFlow testRequestFlow = new TestRequestFlow();
        testRequestFlow.setChangedBy(changedBy);
        testRequestFlow.setRequest(testRequest);
        testRequestFlow.setFromStatus(from);
        testRequestFlow.setToStatus(to);
        testRequestFlowRepository.save(testRequestFlow);
    }


}
