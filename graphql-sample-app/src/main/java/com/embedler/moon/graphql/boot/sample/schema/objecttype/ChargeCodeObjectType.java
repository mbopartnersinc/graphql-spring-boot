package com.embedler.moon.graphql.boot.sample.schema.objecttype;

import com.oembedler.moon.graphql.engine.stereotype.GraphQLObject;
import lombok.Data;

@Data
@GraphQLObject
public class ChargeCodeObjectType {
    private String id;
    private String name;
    private String description;
    private String projectId;
    private long order;
}

