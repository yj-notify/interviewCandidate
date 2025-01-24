package com.mb.interviewcandidate.repository;

import com.mb.interviewcandidate.model.InterviewCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewCandidateRepository extends JpaRepository<InterviewCandidate, Long> {
}
