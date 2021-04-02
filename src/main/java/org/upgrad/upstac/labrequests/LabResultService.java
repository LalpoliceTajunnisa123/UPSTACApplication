package org.upgrad.upstac.labrequests;

import java.time.LocalDate;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.users.User;

@Service
public class LabResultService {

	@Autowired
    private LabResultRepository labResultRepository;


    private static Logger logger = LoggerFactory.getLogger(LabResultService.class);
    
    public LabResult assignForLabTest(TestRequest testRequest, User tester) {

        return createLabResult(tester, testRequest);


    }
	
	private LabResult createLabResult(User tester, TestRequest testRequest) {
        LabResult labResult = new LabResult();
        labResult.setTester(tester);
        labResult.setRequest(testRequest);
        return labResultRepository.save(labResult);
    }


	public LabResult updateLabTest(TestRequest testRequest, @Valid CreateLabResult createLabResult) {

        LabResult result = labResultRepository.findByRequest(testRequest)
        		.orElseThrow(()-> new AppException("Invalid Request"));
        		
        result.setBloodPressure(createLabResult.getBloodPressure());
        result.setComments(createLabResult.getComments());
        result.setHeartBeat(createLabResult.getHeartBeat());
        result.setOxygenLevel(createLabResult.getOxygenLevel());
        result.setTemperature(createLabResult.getTemperature());
        result.setResult(createLabResult.getResult());
        result.setUpdatedOn(LocalDate.now());

        return labResultRepository.save(result);
	}


}
