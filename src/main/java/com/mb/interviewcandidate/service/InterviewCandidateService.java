package com.mb.interviewcandidate.service;

import com.mb.interviewcandidate.model.InterviewCandidate;
import com.mb.interviewcandidate.repository.InterviewCandidateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InterviewCandidateService {

    private final InterviewCandidateRepository repository;

    public InterviewCandidateService(InterviewCandidateRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public InterviewCandidate save(InterviewCandidate candidate) {
        return repository.save(candidate);
    }

    @Transactional
    public InterviewCandidate update(Long id, InterviewCandidate candidate) {
        // Find the existing candidate by ID
        InterviewCandidate existingCandidate = repository.findById(id).orElseThrow(() -> new RuntimeException("Candidate not found"));

        // Update the existing candidate's properties with the new candidate data
        if (candidate.getName() != null) {
            existingCandidate.setName(candidate.getName());
        }
        if (candidate.getInterviewDate() != null) {
            existingCandidate.setInterviewDate(candidate.getInterviewDate());
        }


        // Save the updated candidate
        return repository.save(existingCandidate);
    }


    @Transactional(readOnly = true)
    public InterviewCandidate findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Candidate not found"));
    }

    @Transactional(readOnly = true)
    public List<InterviewCandidate> findAll(int page, int size) {
        Page<InterviewCandidate> candidatesPage = repository.findAll(PageRequest.of(page, size));
        return candidatesPage.getContent();
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
