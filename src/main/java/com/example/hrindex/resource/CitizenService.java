package com.example.hrindex.resource;

import com.example.hrindex.exception.CitizenNotFoundException;
import com.example.hrindex.model.Citizen;
import com.example.hrindex.repository.CitizenRepository;
import com.example.hrindex.util.JwtUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String find(String citizenId, String auth) throws JSONException {
        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new CitizenNotFoundException("ID NOT FOUND  " + citizenId));
        ResponseEntity<Citizen> re =  ResponseEntity.ok().body(citizen);
        String msg = "Citizen found";
        return createJson(citizen, msg, re.getStatusCode().value(), auth);
    }

    public String findByUsername(String citizenUsername, String cloudUri, String auth) throws JSONException {
//        List<Citizen> citizen = citizenRepository.findByCitizenUsername(citizenUsername);
//        if (citizen.isEmpty()) throw new CitizenNotFoundException("USERNAME NOT FOUND  " + citizenUsername);
//        String msg = "Citizen found";
//        ResponseEntity<List<Citizen>> re =  ResponseEntity.ok().body(citizen);
//        return createJson2(citizen, msg, re.getStatusCode().value());

        Citizen citizen = citizenRepository.findFirstByCitizenUsernameAndCloudUri(citizenUsername, cloudUri);
        if (citizen == null) throw new CitizenNotFoundException("USERNAME NOT FOUND  " + citizenUsername);
        String msg = "Citizen found";
        ResponseEntity<Citizen> re =  ResponseEntity.ok().body(citizen);
        System.out.println(citizen);
        String hriEmergencyToken = jwtUtil.generateEmergencyJwt(citizen);
        return createTokenJson(citizen, msg, auth, hriEmergencyToken ,re.getStatusCode().value());
    }

    public String create(String emergencyToken, String citizenUsername, String cloudUri) throws JSONException {

        Citizen c = new Citizen(emergencyToken, citizenUsername, cloudUri);

        String hriAccessToken = jwtUtil.generateAccessJwt(c);
        String hriEmergencyToken = jwtUtil.generateEmergencyJwt(c);

        citizenRepository.save(c);
        String msg = "Citizen created";
        ResponseEntity<Citizen> re = ResponseEntity.ok().body(c);
        return createTokenJson(c, msg, hriAccessToken, hriEmergencyToken, re.getStatusCode().value());
    }

    //Retrieve all private method
    public List<Citizen> getAll() {
        return citizenRepository.findAll();
    }


    public String delete(String citizenId, String auth) throws JSONException {
        Citizen c = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new CitizenNotFoundException("ID NOT FOUND  " + citizenId));

        c.setCitizenUsername("");
        c.setCloudUri("");
        citizenRepository.save(c);
        String msg = "Citizen deleted";
        ResponseEntity<Citizen> re = ResponseEntity.ok().body(c);
        return createJson(c, msg, re.getStatusCode().value(), auth);
    }

    public String deletePerm(String citizenId) throws JSONException {
        Citizen c = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new CitizenNotFoundException("ID NOT FOUND  " + citizenId));
        citizenRepository.deleteById(citizenId);
        String msg = "Citizen deleted perm";
        ResponseEntity<Citizen> re = ResponseEntity.ok().body(c);
        return createJson(c, msg, re.getStatusCode().value(), "privateKey");
    }

    //Update method
    public String updateCloud(String citizenId, String setCloudUri) throws Exception {
        Citizen c = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new CitizenNotFoundException("ID NOT FOUND  " + citizenId));

        c.setCloudUri(setCloudUri);
        citizenRepository.save(c);
        String msg = "Updated cloud";
        ResponseEntity<Citizen> re = ResponseEntity.ok().body(c);
        return createJson(c, msg, re.getStatusCode().value(), "auth");
    }

    public String updateUsername(String citizenId, String citizenUsername) throws Exception {
        Citizen c = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new CitizenNotFoundException("ID NOT FOUND  " + citizenId));

        c.setCitizenUsername(citizenUsername);
        citizenRepository.save(c);
        String msg = "Updated username";
        ResponseEntity<Citizen> re = ResponseEntity.ok().body(c);
        return createJson(c, msg, re.getStatusCode().value(), "auth");
    }

    public String updateCitizen(String citizenId, String emergencyToken, String citizenUsername, String cloudUri, String auth) throws Exception {
        Citizen c = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new CitizenNotFoundException("ID NOT FOUND  " + citizenId));

        c.setEmergencyToken(emergencyToken);
        c.setCitizenUsername(citizenUsername);
        c.setCloudUri(cloudUri);
        citizenRepository.save(c);
        String msg = "Citizen updated";
        ResponseEntity<Citizen> re = ResponseEntity.ok().body(c);
        return createJson(c, msg, re.getStatusCode().value(), auth);
    }

    public String findUsername(String citizenUsername) throws Exception {
        Citizen citizen = citizenRepository.findCitizenByCitizenUsername(citizenUsername);
        if (citizen == null) throw new CitizenNotFoundException("USERNAME NOT FOUND  " + citizenUsername);
        String msg = "Citizen found";
        ResponseEntity<Citizen> re =  ResponseEntity.ok().body(citizen);
        System.out.println(citizen);
        String hriEmergencyToken = jwtUtil.generateEmergencyJwt(citizen);
        String hriAccessToken = jwtUtil.generateAccessJwt(citizen);
        return createTokenJson(citizen,msg,hriAccessToken,hriEmergencyToken,re.getStatusCode().value());
    }

    public String createJson(Citizen citizen, String msg, int code, String auth) throws JSONException {
        String response;
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("citizenUsername", citizen.getCitizenUsername());
        item.put("emergencyToken", citizen.getEmergencyToken());
        item.put("cloudUri", citizen.getCloudUri());
        item.put("citizenId", citizen.getCitizenId());
        array.put(item);
        json.put("data", array);
        json.put("status", code);
        json.put("msg", msg);
        json.put("hriAuthToken", auth);
        response = json.toString();
        return response;
    }

    private String createTokenJson(Citizen citizen, String msg, String hriAccessToken, String hriEmergencyToken, int code) throws JSONException {
        String response;
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("citizenUsername", citizen.getCitizenUsername());
        item.put("emergencyToken", citizen.getEmergencyToken());
        item.put("cloudUri", citizen.getCloudUri());
        item.put("citizenId", citizen.getCitizenId());
        array.put(item);
        json.put("data", array);
        json.put("status", code);
        json.put("msg", msg);
        json.put("hriAccessToken", hriAccessToken);
        json.put("hriEmergencyToken", hriEmergencyToken);
        response = json.toString();
        return response;
    }

}
