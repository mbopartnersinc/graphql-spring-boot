/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 oEmbedler Inc. and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 *  rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 *  persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.embedler.moon.graphql.boot.sample.schema.objecttype;

import com.embedler.moon.graphql.boot.sample.schema.TodoSchema;
import com.mbopartners.boss.profile.service.ProfileService;
import com.mbopartners.boss.project.service.ProjectService;
import com.mbopartners.boss.security.util.AuthenticationUtils;
import com.mbopartners.boss.security.util.BossAuthenticationToken;
import com.mbopartners.profile.model.Profile;
import com.mbopartners.project.model.Project;
import com.oembedler.moon.graphql.engine.relay.RelayNode;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLDescription;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLField;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLID;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLIgnore;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLIn;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLNonNull;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLObject;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingEnvironmentImpl;
import graphql.servlet.GraphQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:java.lang.RuntimeException@gmail.com">oEmbedler Inc.</a>
 */
@GraphQLObject("Root")
public class RootObjectType {

    @Autowired
    @GraphQLIgnore
    private ProjectService projectService;

    @Autowired
    @GraphQLIgnore
    private ProfileService profileService;

    @GraphQLNonNull
    @GraphQLField("version")
    @GraphQLDescription("Root query version number")
    public static final String VERSION = "0.9.0.2";

    @Autowired
    @GraphQLIgnore
    private TodoSchema todoSchema;

    @GraphQLField
    public ProfileObjectType viewer() {
        BossAuthenticationToken token = AuthenticationUtils.getAuthToken();
        token.getToken().getEmployeeNumber();
        Profile profile = profileService.retrieveByMboId(token.getToken().getEmployeeNumber());
        ProfileObjectType profileObjectType = new ProfileObjectType();
        profileObjectType.setMboId(profile.getMboId());
        profileObjectType.setFirstName(profile.getFirstName());
        profileObjectType.setLastName(profile.getLastName());
        return profileObjectType;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<ProjectObjectType> projects() {

        List<Project> projects = projectService.getProjectByMboId("a5123be5-8607-49c3-a248-f4bc6f0c9a3f");
        List<ProjectObjectType> projectObjectTypes = new ArrayList<>();
        projects.forEach(project -> {
            ProjectObjectType objectType = new ProjectObjectType();
            objectType.setId(project.getId());
            objectType.setMboId(project.getMboId());
            objectType.setName(project.getName());
            projectObjectTypes.add(objectType);
        });
        return projectObjectTypes;
    }



    @GraphQLField
    public RelayNode node(@GraphQLID @GraphQLNonNull @GraphQLIn("id") final String id) {
        return new UserObjectType();
    }
}
