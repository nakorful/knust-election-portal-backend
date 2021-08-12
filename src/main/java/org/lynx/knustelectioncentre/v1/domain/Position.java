package org.lynx.knustelectioncentre.v1.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Position {

    @JsonProperty("SRC PRESIDENT")
    SRC_PRESIDENT("SRC_PRESIDENT"),

    @JsonProperty("SRC FINANCIAL SECRETARY")
    SRC_FINANCIAL_SECRETARY("SRC_FINANCIAL_SECRETARY"),

    @JsonProperty("SRC GENERAL SECRETARY")
    SRC_GENERAL_SECRETARY("SRC_GENERAL_SECRETARY"),

    @JsonProperty("WOMEN'S COMMISSIONER")
    WOMENS_COMMISSIONER("WOMENS_COMMISSIONER");

    private final String type;

    Position(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
