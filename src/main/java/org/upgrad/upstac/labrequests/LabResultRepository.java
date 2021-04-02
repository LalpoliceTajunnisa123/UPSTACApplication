package org.upgrad.upstac.labrequests;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upgrad.upstac.testrequests.TestRequest;


public interface LabResultRepository extends JpaRepository<LabResult, Long>{

	Optional<LabResult> findByRequest(TestRequest testRequest);

}
