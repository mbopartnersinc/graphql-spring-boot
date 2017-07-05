package com.embedler.moon.graphql.boot.sample.schema.objecttype;

import com.mbopartners.boss.project.service.ProjectService;
import com.mbopartners.project.model.Project;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLField;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLIgnore;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLObject;
import graphql.schema.DataFetchingEnvironmentImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@GraphQLObject("Profile")
public class ProfileObjectType {

    @GraphQLIgnore
    @Autowired
    private ProjectService projectService;

    private String mboId;
    private String firstName;
    private String lastName;

    @GraphQLField
    public List<ProjectObjectType> projects(DataFetchingEnvironmentImpl environment) {
        ProfileObjectType source = environment.getSource();

        List<ProjectObjectType> projectObjectTypes = new ArrayList<>();
        List<Project> projects = projectService.getProjectByMboId(source.getMboId());
        projects.forEach(project -> {
            ProjectObjectType projectObjectType = new ProjectObjectType();
            projectObjectType.setMboId(project.getMboId());
            projectObjectType.setId(project.getId());
            projectObjectType.setName(project.getName());
            projectObjectType.setStartDate(Date.from(project.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            projectObjectType.setStartDate(Date.from(project.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            projectObjectTypes.add(projectObjectType);
        });
        return projectObjectTypes;
    }
}
