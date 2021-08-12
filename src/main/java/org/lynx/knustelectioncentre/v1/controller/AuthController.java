package org.lynx.knustelectioncentre.v1.controller;

import org.lynx.knustelectioncentre.v1.domain.LoginRequest;
import org.lynx.knustelectioncentre.v1.domain.Position;
import org.lynx.knustelectioncentre.v1.domain.VoteRequest;
import org.lynx.knustelectioncentre.v1.domain.VoteResults;
import org.lynx.knustelectioncentre.v1.model.candidate.Candidate;
import org.lynx.knustelectioncentre.v1.model.vote.Vote;
import org.lynx.knustelectioncentre.v1.model.voter.Voter;
import org.lynx.knustelectioncentre.v1.model.voter.VoterRepository;
import org.lynx.knustelectioncentre.v1.service.CandidateService;
import org.lynx.knustelectioncentre.v1.service.VoteService;
import org.lynx.knustelectioncentre.v1.service.VoterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final VoterService voterService;

    private final CandidateService candidateService;

    private final VoteService voteService;

    public AuthController(VoterService voterService,
                          CandidateService candidateService,
                          VoteService voteService) {
        Objects.requireNonNull(voterService, "voter service cannot be null");
        Objects.requireNonNull(candidateService, "candidate service cannot be null");
        Objects.requireNonNull(voteService, "vote service cannot be null");

        this.voterService = voterService;
        this.candidateService = candidateService;
        this.voteService = voteService;
    }

    @PostMapping("/api/v1/validate/studentId")
    public Voter firstAuthStage(@RequestParam("studentId") String studentId,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        String requestId = request.getSession().getId();

        logger.info("[ " + requestId + " ] about to process request to validate studentId: " + studentId);

        Integer status = 200;

        Optional<Voter> optVoter = voterService.findByStudentId(studentId);
        logger.info("[ " + requestId + " ] is voter with id: " + studentId + " present: " + optVoter.isPresent());

        Voter voter = null;
        if (optVoter.isPresent()) {
            voter = optVoter.get();
        } else {
            logger.info("[ " + requestId + " ] voter with student id: " + studentId + " does not exist");
            status = 404;
        }

        response.setStatus(status);
        return voter;
    }

    @PostMapping("/api/v1/login")
    public Voter loginVoter(@RequestBody LoginRequest loginRequest,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        String requestId = request.getSession().getId();

        logger.info("[ " + requestId + " ] about to process request to login user. request payload: " + ((loginRequest != null) ? loginRequest.toString() : "none"));

        Integer status = 200;
        Voter voter = null;

        if (loginRequest != null) {
            String studentId = loginRequest.getStudentId();
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            if (studentId != null) {
                Optional<Voter> optVoter = voterService.findByStudentId(studentId);
                logger.info("[ " + requestId + " ] is voter with student id: " + studentId + " present: " + optVoter.isPresent());

                if (optVoter.isPresent()) {
                    voter = optVoter.get();

                    byte[] decodedBytes = Base64.getDecoder().decode(voter.getPassword());
                    String decodedPassword = new String(decodedBytes);

                    if (StringUtils.hasText(username) && !username.equalsIgnoreCase(voter.getUsername())) {
                        logger.info("[ " + requestId + " ] invalid username passed");
                        status = 400;

                        return null;
                    }

                    if (StringUtils.hasText(password) && !password.equalsIgnoreCase(decodedPassword)) {
                        logger.info("[ " + requestId + " ] invalid password passed");
                        status = 400;

                        return null;
                    }
                } else {
                    logger.info("[ " + requestId + " ] voter with student id: " + studentId + " does not exist");
                    status = 404;
                }
            } else {
                logger.info("[ " + requestId + " ] invalid or empty student id passed");
                status = 400;
            }
        } else {
            logger.info("[ " + requestId + " ] login request payload is invalid");
            status = 400;
        }

        logger.info("[ " + requestId + " ] status: " + status);

        response.setStatus(status);
        return voter;
    }

    @PostMapping("/api/v1/cast/vote/{voterId}")
    public void castVote(@PathVariable("voterId") String voterId,
                         @RequestBody VoteRequest voteRequest,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        String requestId = request.getSession().getId();

        logger.info("[ " + requestId + " ] about to process request to cast vote for voter with id: " + voterId + ". request payload: " +
                ((voteRequest != null) ? voteRequest.toString() : "none"));

        if (voteRequest != null) {
            // find voter
            Optional<Voter> optVoter = voterService.findByStudentId(voterId);

            logger.info("[ " + requestId + " ] is voter with student id: " + voterId + " present: " + optVoter.isPresent());


            if (optVoter.isPresent()) {
                Voter voter = optVoter.get();
                List<VoteResults> voteResults = voteRequest.getVoteResults();

                if (voter.getHasVoted() != null && !voter.getHasVoted()) {
                    // loop through votes
                    voteResults.forEach(voteResult -> {
                        Position position = voteResult.getPosition();
                        String candidateName = voteResult.getCandidateName();

                        Optional<Candidate> optCandidate = candidateService.findByName(candidateName);
                        logger.info("[ " + requestId + " ] is candidate with name: " + candidateName + " present: " + optCandidate.isPresent());

                        if (optCandidate.isPresent()) {
                            Candidate candidate = optCandidate.get();
                            //get candidate votes
                            Set<Vote> votes = candidate.getVotes();
                            Vote newVote = new Vote();
                            newVote.setVoter(voter);
                            newVote.setCandidate(candidate);
                            newVote.setPosition(position);

                            ZonedDateTime currentDate = ZonedDateTime.now();
                            newVote.setCreatedOn(currentDate);
                            newVote.setUpdatedOn(currentDate);

                            boolean isSuccessful = false;
                            try {
                                newVote = this.voteService.save(newVote);
                                isSuccessful = true;
                            } catch (Exception e) {
                                logger.warn("[ " + requestId + " ] an error occurred while persisting vote. message: " + e.getMessage());
                            }

                            if (isSuccessful) {
                                votes.add(newVote);
                                candidate.setVotes(votes);

                                candidate.setUpdatedOn(currentDate);

                                try {
                                    candidate = candidateService.save(candidate);
                                } catch (Exception e) {

                                }

                                if (newVote != null) {
                                    Voter byWho = newVote.getVoter();
                                    Set<Vote> allVotes = byWho.getVotes();
                                    allVotes.add(newVote);

                                    byWho.setVotes(allVotes);

                                    try {
                                        voterService.save(byWho);
                                    } catch (Exception e) {

                                    }
                                }
                            }
                        }
                    });
                }
            } else {

            }
        }
    }
}
