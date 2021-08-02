package org.lynx.knustelectioncentre.v1.model.voter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoterRepository extends JpaRepository<Voter, String> {

    Optional<Voter> findByStudentId(String studentId);
}
