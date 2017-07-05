package com.embedler.moon.graphql.boot.sample.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TimeEntry {

    static final String DATE_FORMAT = "yyyy-MM-dd";
    static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    @ApiModelProperty(hidden = true)
    private String id;

    @ApiModelProperty(hidden = true)
    @NotNull
    private String mboId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDate date;

    @ApiModelProperty(hidden = true)
    private String projectId;
    @ApiModelProperty(hidden = true)
    private ChargeCode chargeCode;
    @ApiModelProperty(hidden = true)
    private Status status;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "24")
    private Double amount;

    @ApiModelProperty(hidden = true)
    private Unit unit;
    private String note;
    @ApiModelProperty(hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime lastChanged;

    @ApiModelProperty(hidden = true)
    private boolean submitted;

    private boolean editable;

    @ApiModelProperty(hidden = true)
    private Map<String, Object> metadata = new HashMap<>();
}