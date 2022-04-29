package com.example.hrindex.repository;

import com.example.hrindex.model.Citizen;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitizenRepository extends MongoRepository<Citizen, String>, CrudRepository<Citizen, String> {
       Citizen findFirstByCitizenUsernameAndCloudUri(String citizenUsername, String cloudUri);
}