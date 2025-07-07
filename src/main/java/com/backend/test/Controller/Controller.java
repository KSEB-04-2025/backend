package com.backend.test.Controller;

import com.backend.test.Repository.InspectionRepository;
import com.backend.test.Service.Inspection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/inspection")
@RequiredArgsConstructor
public class Controller {
    private final InspectionRepository repository;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Inspection i) {
        i.setInspectedAt(LocalDateTime.now());
        return ResponseEntity.ok(repository.save(i));
    }

    @GetMapping
    public List<Inspection> findAll() {
        return repository.findAll();
    }
}
