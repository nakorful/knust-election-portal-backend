package org.lynx.knustelectioncentre.v1.service;

import org.lynx.knustelectioncentre.v1.model.voter.Voter;
import org.lynx.knustelectioncentre.v1.model.voter.VoterRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class VoterService {

    private final VoterRepository voterRepository;

    public VoterService(VoterRepository voterRepository) {
        Objects.requireNonNull(voterRepository, "voter repository cannot be null");

        this.voterRepository = voterRepository;
    }

    public Voter save(Voter voter) {
        return voterRepository.save(voter);
    }

    public Optional<Voter> findByStudentId(String studentId) {
        return voterRepository.findByStudentId(studentId);
    }
}
