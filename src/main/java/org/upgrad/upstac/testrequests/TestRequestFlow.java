package org.upgrad.upstac.testrequests;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.upgrad.upstac.users.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TestRequestFlow {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private TestRequest request;

    private RequestStatus fromStatus ;
    private RequestStatus toStatus ;

    @ManyToOne
    private User changedBy;

    private LocalDate happenedOn=LocalDate.now();

}
