package com.backend.test.Service;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("inspections")
public class Inspection {
    @Id
    private String id;
    private String productId;
    private String result; // high / low
    private LocalDateTime inspectedAt;
}
