package com.example.backend.test.Repository;

import com.example.backend.test.Service.Inspection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InspectionRepository extends MongoRepository<Inspection, String> { }
