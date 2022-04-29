package com.example.hrindex.resource;

import com.example.hrindex.model.Citizen;
import com.example.hrindex.util.JwtUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@Validated
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping(value = "/hcp/getCloud", produces = MediaType.APPLICATION_JSON_VALUE)
    public String find(@RequestHeader("Authorization") @NotEmpty(message = "Authorization needed") String auth,
                       @RequestHeader("citizenId") String citizenId) throws Exception {
        jwtUtil.validateEmergencyToken(auth);
        return citizenService.find(citizenId, auth);
    }

    @GetMapping(value = "/citizen/getCloud", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findCitizen(@RequestHeader("Authorization") @NotEmpty(message = "Authorization needed") String auth,
                              @RequestHeader("citizenId") String citizenId) throws Exception {
        jwtUtil.validateAccessToken(auth);
        return citizenService.find(citizenId, auth);
    }

    @GetMapping(value = "/citizen/findCitizen", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findC(@RequestHeader("Authorization") @NotEmpty(message = "Authorization needed") String auth,
                        @RequestHeader("citizenUsername") String citizenUsername,
                        @RequestHeader("cloudUri") String cloudUri) throws Exception {
        jwtUtil.validateAccessToken(auth);
        return citizenService.findByUsername(citizenUsername, cloudUri, auth);
    }


    @PostMapping(value = "/citizen/createCitizen", produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveCitizen(@Valid @RequestHeader("emergencyToken") @NotEmpty(message = "Emergency token should not be empty") String emergencyToken,
                              @Valid @RequestHeader(value="citizenUsername", required=false) @NotEmpty(message = "Wrong parameter name or empty value") String citizenUsername,
                              @Valid @RequestHeader("cloudUri") @NotEmpty(message = "Cloud URI should not be empty") String cloudUri) throws JSONException {
        String citizen = citizenService.create(emergencyToken, citizenUsername, cloudUri);
        return citizen;
    }

    @GetMapping("/findAllCitizens")
    public List<Citizen> getCitizens(@RequestHeader(value = "Authorization", required = true) @NotEmpty(message = "Authorization needed") String auth) throws Exception {
        jwtUtil.privateValidate(auth);
        var citizens = citizenService.getAll();
        return citizens;
    }


    @PutMapping(value = "/citizen/removeData", produces = MediaType.APPLICATION_JSON_VALUE)
    public String delete(@RequestHeader("Authorization") @NotEmpty(message = "Authorization needed") String auth,
                         @RequestHeader("citizenId") String citizenId) throws Exception {
        jwtUtil.validateAccessToken(auth);
        return citizenService.delete(citizenId, auth);
    }

    @DeleteMapping(value = "/deletePerm", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deletePerm(@RequestHeader("Authorization") @NotEmpty(message = "Authorization needed") String auth,
                             @RequestHeader("citizenId") String citizenId) throws Exception {
        jwtUtil.privateValidate(auth);
        return citizenService.deletePerm(citizenId);
    }

    @PutMapping(value = "/citizen/updateCloud", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateCloud(@RequestHeader("citizenId") String citizenId , @RequestHeader("cloudUri") String cloudUri) throws Exception {
        return citizenService.updateCloud(citizenId, cloudUri);
    }

    @PutMapping(value = "/citizen/updateUsername", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateUsername(@RequestHeader("citizenId") String citizenId , @RequestHeader("citizenUsername") String citizenUsername) throws Exception {
        return citizenService.updateUsername(citizenId, citizenUsername);
    }

    @PutMapping(value = "/citizen/updateCitizen", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateCitizen(@RequestHeader("Authorization") @NotEmpty(message = "Authorization needed") String auth,
                                @RequestHeader("citizenId") String citizenId,
                                @RequestHeader("emergencyToken") String emergencyToken,
                                @RequestHeader("citizenUsername") String citizenUsername,
                                @RequestHeader("cloudUri") String cloudUri) throws Exception {
        jwtUtil.validateAccessToken(auth);
        return citizenService.updateCitizen(citizenId, emergencyToken, citizenUsername, cloudUri, auth);
    }

}
