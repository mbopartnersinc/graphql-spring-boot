package com.embedler.moon.graphql.boot.sample.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.mbopartners.boss.rest.util.jackson.JsonEnum;

public enum Status {
    PENDING,
    APPROVED,
    INVOICED,
    DECLINED,
    NEEDS_WORK;

    @JsonCreator
    public static Status createJson(String value) {
        return JsonEnum.createJson(Status.class, value);
    }
}
