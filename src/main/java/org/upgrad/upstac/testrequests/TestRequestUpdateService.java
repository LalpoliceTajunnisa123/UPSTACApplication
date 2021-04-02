package org.upgrad.upstac.testrequests;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.upstac.consultation.Consultation;
import org.upgrad.upstac.consultation.ConsultationService;
import org.upgrad.upstac.consultation.CreateConsultationRequest;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.labrequests.CreateLabResult;
import org.upgrad.upstac.labrequests.LabResult;
import org.upgrad.upstac.labrequests.LabResultService;
import org.upgrad.upstac.users.User;

@Service
public class TestRequestUpdateService {
	
	@Autowired
	TestRequestRepository testRequestRepository;
	
	@Autowired
	LabResultService labResultService;
	
	@Autowired
	TestRequestFlowService testRequestFlowService;
	
	@Autowired
	ConsultationService consultationService;
	

	 public TestRequest assignForLabTest(Long id, User tester) {
	        TestRequest testRequest = testRequestRepository.findByRequestIdAndRequestStatus(id,RequestStatus.INITIATED).orElseThrow(()-> new AppException("Invalid ID"));
	        LabResult labResult= labResultService.assignForLabTest(testRequest,tester);
	        testRequestFlowService.log(testRequest, RequestStatus.INITIATED, RequestStatus.LAB_TEST_IN_PROGRESS, tester);
	        testRequest.setLabResult(labResult);
	        return updateStatusAndSave(testRequest, RequestStatus.LAB_TEST_IN_PROGRESS);
	    }


	  TestRequest updateStatusAndSave(TestRequest testRequest, RequestStatus status) {
	        testRequest.setRequestStatus(status);
	        return testRequestRepository.save(testRequest);
	    }


	  public TestRequest updateLabTest(Long id,@Valid CreateLabResult createLabResult, User tester) {

	        TestRequest testRequest = testRequestRepository.findByRequestIdAndRequestStatus(id,RequestStatus.LAB_TEST_IN_PROGRESS).orElseThrow(()-> new AppException("Invalid ID or State"));


	        labResultService.updateLabTest(testRequest,createLabResult);
	        testRequestFlowService.log(testRequest, RequestStatus.LAB_TEST_IN_PROGRESS, RequestStatus.LAB_TEST_COMPLETED, tester);
	        return updateStatusAndSave(testRequest, RequestStatus.LAB_TEST_COMPLETED);
	    }


	  public TestRequest assignForConsultation(Long id, User doctor) {
	        TestRequest testRequest = testRequestRepository.findByRequestIdAndRequestStatus(id,RequestStatus.LAB_TEST_COMPLETED).orElseThrow(()-> new AppException("Invalid ID or State"));
	        Consultation consultation =consultationService.assignForConsultation(testRequest,doctor);
	        testRequestFlowService.log(testRequest, RequestStatus.LAB_TEST_COMPLETED, RequestStatus.DIAGNOSIS_IN_PROCESS, doctor);
	        testRequest.setConsultation(consultation);
	        return updateStatusAndSave(testRequest, RequestStatus.DIAGNOSIS_IN_PROCESS);
	    }


	  public TestRequest updateConsultation(Long id, @Valid CreateConsultationRequest createConsultationRequest, User doctor) {

	        TestRequest testRequest = testRequestRepository.findByRequestIdAndRequestStatus(id,RequestStatus.DIAGNOSIS_IN_PROCESS).orElseThrow(()-> new AppException("Invalid ID or State"));
	        consultationService.updateConsultation(testRequest,createConsultationRequest);
	        testRequestFlowService.log(testRequest, RequestStatus.DIAGNOSIS_IN_PROCESS, RequestStatus.COMPLETED, doctor);
	        return updateStatusAndSave(testRequest, RequestStatus.COMPLETED);
	    }


	

}
