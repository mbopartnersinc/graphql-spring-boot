package com.embedler.moon.graphql.boot.sample.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mbopartners.boss.rest.util.jackson.JsonEnum;

public enum Unit {

    HOURLY,
    TASK,
    WEEK,
    MONTH,
    MILESTONE;

    @JsonCreator
    public static Unit createJson(String value) {
        return JsonEnum.createJson(Unit.class, value);
    }
}
