package org.lynx.knustelectioncentre.v1.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VoteResults {

    private String candidateName;

    private Position position;
}
