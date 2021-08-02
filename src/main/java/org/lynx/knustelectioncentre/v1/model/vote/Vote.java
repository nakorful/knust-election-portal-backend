package org.lynx.knustelectioncentre.v1.model.vote;

import org.lynx.knustelectioncentre.v1.domain.Position;
import org.lynx.knustelectioncentre.v1.model.candidate.Candidate;
import org.lynx.knustelectioncentre.v1.model.voter.Voter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Vote {

    @Id
    private Long id;

    @ManyToOne
    private Voter voter;

    @Enumerated(EnumType.STRING)
    private Position position;

    @ManyToOne
    private Candidate candidate;

    @CreatedDate
    private ZonedDateTime createdOn;

    @LastModifiedDate
    private ZonedDateTime updatedOn;
}
