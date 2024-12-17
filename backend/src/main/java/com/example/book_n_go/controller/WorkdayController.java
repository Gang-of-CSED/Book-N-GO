package com.example.book_n_go.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_n_go.model.Workday;
import com.example.book_n_go.model.Workspace;
import com.example.book_n_go.repository.WorkdayRepo;
import com.example.book_n_go.repository.WorkspaceRepo;
import com.example.book_n_go.service.AuthService;

@RestController
@RequestMapping("/workspace/{workspaceId}")
public class WorkdayController {
    @Autowired
    private WorkdayRepo workdayRepo;
    @Autowired
    private WorkspaceRepo workspaceRepo;

    @GetMapping("/workdays")
    public ResponseEntity<List<Workday>> getWorkdays(@PathVariable("workspaceId") long workspaceId) {
        try {
            List<Workday> workdays = new ArrayList<Workday>();
            workdayRepo.findByWorkspaceId(workspaceId).forEach(workdays::add);
            if (workdays.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(workdays, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/workdays/{id}")
    public ResponseEntity<Workday> getWorkdayById(@PathVariable("id") long id) {
        Optional<Workday> workdayData = workdayRepo.findById(id);
        if (workdayData.isPresent()) {
            return new ResponseEntity<>(workdayData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/workdays")
    public ResponseEntity<Workday> createWorkday(@RequestBody Workday workday, @PathVariable("workspaceId") long workspaceId) {
        Workspace workspace = workspaceRepo.findById(workspaceId).get();
        if(workspace.getProvider().getId() != AuthService.getRequestUser().getId()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        workday.setWorkspace(workspace);
        Workday _workday = workdayRepo.save(workday);
        return new ResponseEntity<>(_workday, HttpStatus.CREATED);
    }

    @PutMapping("/workdays/{id}")
    public ResponseEntity<Workday> updateWorkday(@PathVariable("id") long id, @RequestBody Workday workday) {
        Optional<Workday> workdayData = workdayRepo.findById(id);
        if (workdayData.isPresent()) {
            Workday _workday = workdayData.get();
            _workday.setWorkspace(workday.getWorkspace());
            _workday.setStartTime(workday.getStartTime());
            _workday.setEndTime(workday.getEndTime());
            _workday.setWeekDay(workday.getWeekDay());
            return new ResponseEntity<>(workdayRepo.save(_workday), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/workdays/{id}")
    public ResponseEntity<HttpStatus> deleteWorkday(@PathVariable("id") long id) {
        try {
            workdayRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}