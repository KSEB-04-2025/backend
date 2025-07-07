package com.backend.test.Repository;

import com.backend.test.Service.Inspection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InspectionRepository extends MongoRepository<Inspection, String> { }
