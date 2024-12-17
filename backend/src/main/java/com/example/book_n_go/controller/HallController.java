package com.example.book_n_go.controller;

import com.example.book_n_go.repository.HallRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.book_n_go.model.Hall;
import java.util.*;
import com.example.book_n_go.dto.HallsFilterRequest;
import com.example.book_n_go.service.HallsListFilterService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HallController {
    @Autowired
    private HallRepo hallRepo;

    @GetMapping("/halls")
    public ResponseEntity<List<Hall>> getHalls() {
        try {
            List<Hall> halls = new ArrayList<Hall>();
            hallRepo.findAll().forEach(halls::add);
            if (halls.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(halls, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/halls/{id}")
    public ResponseEntity<Hall> getHallById(@PathVariable("id") long id) {
        Optional<Hall> hallData = hallRepo.findById(id);
        if (hallData.isPresent()) {
            return new ResponseEntity<>(hallData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/workspaces/{workspaceId}/halls")
    public ResponseEntity<List<Hall>> getHallsByWorkspaceId(@PathVariable("workspaceId") long workspaceId) {
        try {
            List<Hall> halls = hallRepo.findByWorkspaceId(workspaceId);
            if (halls.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(halls, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/halls")
    public ResponseEntity<Hall> createHall(@RequestBody Hall hall) {
        try {
            Hall _hall = hallRepo.save(hall);
            return new ResponseEntity<>(_hall, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/halls/{id}")
    public ResponseEntity<Hall> updateHall(@PathVariable("id") long id, @RequestBody Hall hall) {
        Optional<Hall> hallData = hallRepo.findById(id);
        if (hallData.isPresent()) {
            Hall _hall = hallData.get();
            _hall.setWorkspaceId(hall.getWorkspaceId());
            _hall.setCapacity(hall.getCapacity());
            _hall.setDescription(hall.getDescription());
            _hall.setPricePerHour(hall.getPricePerHour());
            return new ResponseEntity<>(hallRepo.save(_hall), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/halls/{id}")
    public ResponseEntity<HttpStatus> deleteHall(@PathVariable("id") long id) {
        try {
            hallRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    private HallsListFilterService hallsListFilterService;
    @PostMapping("/filterHalls")
    public ResponseEntity<List<Hall>> filterHalls(@RequestBody HallsFilterRequest request) {
        try {
            List<Hall> halls = hallsListFilterService.applyCriterias(request);
            
            return new ResponseEntity<>(halls, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
