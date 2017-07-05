package com.embedler.moon.graphql.boot.sample.schema.objecttype;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLIgnore;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLObject;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@GraphQLObject("TimePeriod")
public class TimePeriodObjectType {

    @GraphQLIgnore
    static final String DATE_FORMAT = "yyyy-MM-dd";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date endDate;

    private String projectId;

    private Boolean currentPeriod;
}
