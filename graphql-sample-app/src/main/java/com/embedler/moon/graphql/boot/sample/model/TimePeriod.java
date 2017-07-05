package com.embedler.moon.graphql.boot.sample.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TimePeriod {

    static final String DATE_FORMAT = "yyyy-MM-dd";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDate endDate;
    private String projectId;
    private Boolean currentPeriod;

}
