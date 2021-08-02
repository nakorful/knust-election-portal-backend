package org.lynx.knustelectioncentre.v1.service;

import org.lynx.knustelectioncentre.v1.model.candidate.Candidate;
import org.lynx.knustelectioncentre.v1.model.candidate.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        Objects.requireNonNull(candidateRepository, "candidate repository cannot be null");

        this.candidateRepository = candidateRepository;
    }

    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public Optional<Candidate> findById(Long id) {
        return candidateRepository.findById(id);
    }

    public Optional<Candidate> findByName(String name) {
        return candidateRepository.findByNameContainingIgnoreCase(name);
    }
}
