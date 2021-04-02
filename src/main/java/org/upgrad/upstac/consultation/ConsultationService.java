package org.upgrad.upstac.consultation;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.users.User;

@Service
public class ConsultationService {
	
	@Autowired
	ConsultationRepository consultationRepository;

	public Consultation assignForConsultation( TestRequest testRequest, User doctor) {
        Consultation consultation = new Consultation();
        consultation.setDoctor(doctor);
        consultation.setRequest(testRequest);

        return consultationRepository.save(consultation);


    }

	public Consultation updateConsultation(TestRequest testRequest , CreateConsultationRequest createConsultationRequest) {
        Consultation consultation = consultationRepository.findByRequest(testRequest).orElseThrow(()-> new AppException("Invalid Request"));

        consultation.setSuggestion(createConsultationRequest.getSuggestion());
        consultation.setComments(createConsultationRequest.getComments());
        consultation.setUpdatedOn(LocalDate.now());

        return consultationRepository.save(consultation);


    }

}
