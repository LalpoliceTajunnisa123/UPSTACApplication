package org.upgrad.upstac.consultation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.users.User;

public interface ConsultationRepository extends JpaRepository<Consultation, Long>{

	Optional<Consultation> findByRequest(TestRequest testRequest);

	List<Consultation> findByDoctor(User user);

}
