package com.example.book_n_go.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_n_go.dto.HallsFilterRequest;
import com.example.book_n_go.model.Hall;
import com.example.book_n_go.model.Workspace;
import com.example.book_n_go.repository.HallRepo;
import com.example.book_n_go.repository.WorkspaceRepo;
import com.example.book_n_go.service.AuthService;
import com.example.book_n_go.service.HallsListFilterService;

@RestController
@RequestMapping("/workspace/{workspaceId}")
public class HallController {
    @Autowired
    private HallRepo hallRepo;
    @Autowired
    private WorkspaceRepo workspaceRepo;

    @GetMapping("/halls")
    public ResponseEntity<List<Hall>> getHalls(@PathVariable("workspaceId") long workspaceId) {
        try {
            List<Hall> halls = new ArrayList<Hall>();
            hallRepo.findByWorkspaceId(workspaceId).forEach(halls::add);
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

    @PostMapping("/halls")
    public ResponseEntity<Hall> createHall(@RequestBody Hall hall, @PathVariable("workspaceId") long workspaceId) {
        Workspace workspace = workspaceRepo.findById(workspaceId).get();
        if(workspace.getProvider().getId() != AuthService.getRequestUser().getId()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        hall.setWorkspace(workspace);
        Hall _hall = hallRepo.save(hall);
        return new ResponseEntity<>(_hall, HttpStatus.CREATED);
    }

    @PutMapping("/halls/{id}")
    public ResponseEntity<Hall> updateHall(@PathVariable("id") long id, @RequestBody Hall hall) {
        Optional<Hall> hallData = hallRepo.findById(id);
        if (hallData.isPresent()) {
            Hall _hall = hallData.get();
            _hall.setWorkspace(hall.getWorkspace());
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
        // try {
            System.out.println("Request: " + request);
            List<Hall> halls = hallsListFilterService.applyCriterias(request);
            
            return new ResponseEntity<>(halls, HttpStatus.OK);
        // } catch (Exception e) {
        //     return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        // }
    }
}