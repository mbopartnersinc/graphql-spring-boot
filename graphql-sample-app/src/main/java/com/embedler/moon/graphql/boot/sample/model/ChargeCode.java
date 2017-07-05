package com.embedler.moon.graphql.boot.sample.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ChargeCode {

    private String id;
    private String name;
    private String description;
    private String projectId;
    private long order;
    private Map<String, Object> metadata = new HashMap<>();

}
