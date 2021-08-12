package org.lynx.knustelectioncentre.v1.model.candidate;

import lombok.Getter;
import lombok.Setter;
import org.lynx.knustelectioncentre.v1.domain.Position;
import org.lynx.knustelectioncentre.v1.model.vote.Vote;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class Candidate {

    @Id
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Position position;

    @OneToMany
    private Set<Vote> votes = new HashSet<>();

    @CreatedDate
    private ZonedDateTime createdOn;

    @LastModifiedDate
    private ZonedDateTime updatedOn;
}