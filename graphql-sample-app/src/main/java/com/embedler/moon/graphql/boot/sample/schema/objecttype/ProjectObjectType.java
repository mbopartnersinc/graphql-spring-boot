package com.embedler.moon.graphql.boot.sample.schema.objecttype;

import com.embedler.moon.graphql.boot.sample.model.ChargeCode;
import com.embedler.moon.graphql.boot.sample.model.TimeEntry;
import com.embedler.moon.graphql.boot.sample.model.TimePeriod;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mbopartners.boss.profile.service.ProfileService;
import com.mbopartners.boss.security.util.AuthenticationUtils;
import com.mbopartners.profile.model.Profile;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLField;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLIgnore;
import com.oembedler.moon.graphql.engine.stereotype.GraphQLObject;
import graphql.schema.DataFetchingEnvironmentImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Data
@GraphQLObject("Project")
public class ProjectObjectType  {

    @Autowired
    @GraphQLIgnore
    private ProfileService profileService;

    private String id;
    private String name;
    private String mboId;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd"
    )
    private Date startDate;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd"
    )
    private Date endDate;

    @GraphQLField
    public ProfileObjectType profile(DataFetchingEnvironmentImpl environment) {
        ProjectObjectType source = environment.getSource();
        Profile profile = profileService.retrieveByMboId(source.getMboId());
        ProfileObjectType profileObjectType = new ProfileObjectType();
        profileObjectType.setFirstName(profile.getFirstName());
        profileObjectType.setLastName(profile.getLastName());
        profileObjectType.setMboId(profile.getMboId());
        return profileObjectType;
    }

    @GraphQLField
    public List<ChargeCodeObjectType> chargeCodes(DataFetchingEnvironmentImpl environment) {
        ProjectObjectType source = environment.getSource();
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        System.out.println("http://aws-east-edge-preprod.mbopartners.com/work-tracking/api/v1/projects/" + source.getId() + "/charge-codes");
        ResponseEntity<List<ChargeCode>> response = restTemplate.exchange("http://aws-east-edge-preprod.mbopartners.com/work-tracking/api/v1/projects/" + source.getId() + "/charge-codes", GET, new HttpEntity<>(AuthenticationUtils.getAuthHeader()),  new ParameterizedTypeReference<List<ChargeCode>>() {});
        List<ChargeCode> chargeCodes = response.getBody();
        List<ChargeCodeObjectType> chargeCodeObjectTypes = new ArrayList<>();
        chargeCodes.forEach(chargeCode -> {
            ChargeCodeObjectType objectType = new ChargeCodeObjectType();
            objectType.setDescription(chargeCode.getDescription());
            objectType.setId(chargeCode.getId());
            objectType.setName(chargeCode.getName());
            objectType.setOrder(chargeCode.getOrder());
            objectType.setProjectId(chargeCode.getProjectId());
            chargeCodeObjectTypes.add(objectType);
        });
        return chargeCodeObjectTypes;
    }

    @GraphQLField
    public List<TimeEntryObjectType> timeEntries(DataFetchingEnvironmentImpl environment) {
        ProjectObjectType source = environment.getSource();
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        System.out.println("http://aws-east-edge-preprod.mbopartners.com/work-tracking/api/v1/projects/" + source.getId() + "/times");
        ResponseEntity<List<TimeEntry>> response = restTemplate.exchange("http://aws-east-edge-preprod.mbopartners.com/work-tracking/api/v1/projects/" + source.getId() + "/times", GET, new HttpEntity<>(AuthenticationUtils.getAuthHeader()),  new ParameterizedTypeReference<List<TimeEntry>>() {});

        List<TimeEntryObjectType> objectTypes = new ArrayList<>();
        response.getBody().forEach(timeEntry -> {
            TimeEntryObjectType objectType = new TimeEntryObjectType();
//            timeEntry.setChargeCode(objectType.getChargeCode());
            objectType.setMboId(timeEntry.getMboId());
            objectType.setAmount(timeEntry.getAmount());
            objectType.setDate(Date.from(timeEntry.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            objectTypes.add(objectType);
        });
        return objectTypes;
    }

    @GraphQLField
    public List<TimePeriodObjectType> timePeriods(DataFetchingEnvironmentImpl environment) {
        ProjectObjectType source = environment.getSource();
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        System.out.println("http://aws-east-edge-preprod.mbopartners.com/work-tracking/api/v1/projects/" + source.getId() + "/time-periods");
        ResponseEntity<List<TimePeriod>> response = restTemplate.exchange("http://aws-east-edge-preprod.mbopartners.com/work-tracking/api/v1/projects/" + source.getId() + "/time-periods", GET, new HttpEntity<>(AuthenticationUtils.getAuthHeader()),  new ParameterizedTypeReference<List<TimePeriod>>() {});
        List<TimePeriodObjectType> objectTypes = new ArrayList<>();
        response.getBody().forEach(timePeriod -> {
            TimePeriodObjectType objectType = new TimePeriodObjectType();
            objectType.setStartDate(Date.from(timePeriod.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            objectType.setEndDate(Date.from(timePeriod.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            objectType.setCurrentPeriod(timePeriod.getCurrentPeriod());
            objectType.setProjectId(timePeriod.getProjectId());
            objectTypes.add(objectType);
        });
        return objectTypes;
    }
}
