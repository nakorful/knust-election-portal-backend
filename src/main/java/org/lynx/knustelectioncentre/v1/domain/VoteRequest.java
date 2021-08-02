package org.lynx.knustelectioncentre.v1.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class VoteRequest {

    @JsonProperty("voteResults")
    private List<VoteResults> voteResults = new ArrayList<>();
}
