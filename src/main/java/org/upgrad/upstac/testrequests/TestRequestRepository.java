package org.upgrad.upstac.testrequests;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upgrad.upstac.users.User;

public interface TestRequestRepository extends JpaRepository<TestRequest, Long>{
   
	List<TestRequest> findByEmailOrPhoneNumber(String email,String phoneNUmber);

	List<TestRequest> findByRequestStatus(RequestStatus requestStatus);

	List<TestRequest> findByCreatedBy(User user);

	Optional<TestRequest> findByRequestIdAndRequestStatus(Long requestId, RequestStatus requestStatus);

	Optional<TestRequest> findById(Long id);
}
