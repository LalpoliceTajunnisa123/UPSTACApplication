package org.upgrad.upstac.testrequests;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.upgrad.upstac.consultation.Consultation;
import org.upgrad.upstac.consultation.ConsultationRepository;
import org.upgrad.upstac.users.User;

@Service
@Validated
public class TestRequestQueryService {
	
	@Autowired
	TestRequestRepository testRequestRepository;
	
	@Autowired
	ConsultationRepository consultationRepository;
 
	public List<TestRequest> findBy(RequestStatus requestStatus) {
		
		return testRequestRepository.findByRequestStatus(requestStatus);
	}

	public List<TestRequest> findByCreatedBy(User loggedInTester) {
		
		return testRequestRepository.findByCreatedBy(loggedInTester);
	}

	public Optional<TestRequest> getTestRequestById(Long id) {

        return testRequestRepository.findById(id);
    }

	public List<TestRequest> findByDoctor(User user) {
        return  consultationRepository.findByDoctor(user)
                .stream()
                .map(Consultation::getRequest)
                .collect(Collectors.toList());
    }
}
