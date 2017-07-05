package com.embedler.moon.graphql.boot.sample.schema.objecttype;

import com.embedler.moon.graphql.boot.sample.model.ChargeCode;
import com.embedler.moon.graphql.boot.sample.model.Status;
import com.embedler.moon.graphql.boot.sample.model.Unit;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLField;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLIgnore;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@GraphQLObject("TimeEntry")
public class TimeEntryObjectType {

    @GraphQLIgnore
    static final String DATE_FORMAT = "yyyy-MM-dd";
    @GraphQLIgnore
    static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private String id;
    private String mboId;
    private Double amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date date;
}