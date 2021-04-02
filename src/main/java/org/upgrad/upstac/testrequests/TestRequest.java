package org.upgrad.upstac.testrequests;

import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.upgrad.upstac.consultation.Consultation;
import org.upgrad.upstac.labrequests.LabResult;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.models.Gender;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TestRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long requestId;
	
	@ManyToOne
	private User createdBy;
	
    private LocalDate created=LocalDate.now();
    
    RequestStatus requestStatus = RequestStatus.INITIATED;;
    
	private String name;
    private Gender gender;
    private String address;
    private Integer age;
    private String email;
    private String phoneNumber;
    private Integer pinCode;
    
    @OneToOne(mappedBy="request")
    LabResult labResult;
    
    @OneToOne(mappedBy="request")
    Consultation consultation;
}
