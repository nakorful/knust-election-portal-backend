package org.lynx.knustelectioncentre.v1.model.voter;

import lombok.Getter;
import lombok.Setter;
import org.lynx.knustelectioncentre.v1.model.vote.Vote;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Voter {

    @Id
    private String studentId;

    private String name;

    private String password;

    private String username;

    private String avatar;

    private Boolean hasVoted = false;

    @OneToMany
    private Set<Vote> votes = new HashSet<>();

    @CreatedDate
    private ZonedDateTime createdOn;

    @LastModifiedDate
    private ZonedDateTime updatedOn;
}
