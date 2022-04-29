package com.example.hrindex.model;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document
@Component
public class Citizen {

    @Id
    private String citizenId;

    private String emergencyToken;

    private String citizenUsername;

    private String cloudUri;

    public Citizen(String emergencyToken, String citizenUsername, String cloudUri) {
        this.emergencyToken = emergencyToken;
        this.citizenUsername = citizenUsername;
        this.cloudUri = cloudUri;
    }

    public Citizen() {

    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public String getEmergencyToken() {
        return emergencyToken;
    }

    public void setEmergencyToken(String emergencyToken) {
        this.emergencyToken = emergencyToken;
    }

    public String getCitizenUsername() {
        return citizenUsername;
    }

    public void setCitizenUsername(String citizenUsername) {
        this.citizenUsername = citizenUsername;
    }

    public String getCloudUri() {
        return cloudUri;
    }

    public void setCloudUri(String cloudUri) {
        this.cloudUri = cloudUri;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "citizenId='" + citizenId + '\'' +
                ", emergencyToken='" + emergencyToken + '\'' +
                ", citizenUsername='" + citizenUsername + '\'' +
                ", cloudUri='" + cloudUri + '\'' +
                '}';
    }
}


