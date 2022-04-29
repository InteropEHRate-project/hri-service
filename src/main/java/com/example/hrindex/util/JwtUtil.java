package com.example.hrindex.util;

import com.example.hrindex.model.Citizen;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Service
public class JwtUtil {
    private static final String ACCESS_SECRET_KEY = "@ccessKe#y";
    private static final String EMERGENCY_SECRET_KEY = "em#rgency!Key";
    private static final String PRIVATE_SECRET_KEY = "find@citiZens";

    byte[] accessKeySecretBytes = DatatypeConverter.parseBase64Binary(ACCESS_SECRET_KEY);
    byte[] emergencyKeySecretBytes = DatatypeConverter.parseBase64Binary(EMERGENCY_SECRET_KEY);

    public String generateAccessJwt(Citizen citizen){
        Date issuedAt = new Date();
        //claims
        Claims claims = Jwts.claims().setIssuedAt(issuedAt);

        claims.put("tokenName", "hriAccessToken");
        claims.put("citizenUsername", citizen.getCitizenUsername());
        claims.put("cloudUri", citizen.getCloudUri());

        //generate jwt using claims
        return Jwts.builder()
                .setClaims(claims)
                .signWith(HS512, accessKeySecretBytes)
                .compact();
    }

    public String generateEmergencyJwt(Citizen citizen){
        Date issuedAt = new Date();
        //claims
        Claims claims = Jwts.claims().setIssuedAt(issuedAt);

        claims.put("tokenName", "hriEmergencyToken");
        claims.put("citizenUsername", citizen.getCitizenUsername());
        claims.put("cloudUri", citizen.getCloudUri());

        //generate jwt using claims
        return Jwts.builder()
                .setClaims(claims)
                .signWith(HS512, emergencyKeySecretBytes)
                .compact();
    }

    public void validateAccessToken(String auth) throws Exception {
        try {
            Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(ACCESS_SECRET_KEY))
                    .parseClaimsJws(auth)
                    .getBody();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void validateEmergencyToken(String auth) throws Exception {
        try {
            Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(EMERGENCY_SECRET_KEY))
                    .parseClaimsJws(auth)
                    .getBody();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void privateValidate(String auth) throws Exception {
        try {
            Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(PRIVATE_SECRET_KEY))
                    .parseClaimsJws(auth)
                    .getBody();
        }catch (Exception e){
            throw new Exception();
        }
    }

}
