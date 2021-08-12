package org.lynx.knustelectioncentre.v1.service;

import org.lynx.knustelectioncentre.v1.model.vote.Vote;
import org.lynx.knustelectioncentre.v1.model.vote.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VoteService {

    private VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        Objects.requireNonNull(voteRepository, "vote repository cannot be null");

        this.voteRepository = voteRepository;
    }

    public Vote save(Vote vote) {
        return this.voteRepository.save(vote);
    }
}
