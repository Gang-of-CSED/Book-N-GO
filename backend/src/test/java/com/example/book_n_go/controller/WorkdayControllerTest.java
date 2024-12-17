package com.example.book_n_go.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.book_n_go.config.TestConfig;
import com.example.book_n_go.enums.Day;
import com.example.book_n_go.enums.Role;
import com.example.book_n_go.model.Location;
import com.example.book_n_go.model.User;
import com.example.book_n_go.model.Workday;
import com.example.book_n_go.model.Workspace;
import com.example.book_n_go.repository.WorkdayRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(WorkdayController.class)
@Import(TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class WorkdayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkdayRepo workdayRepo;
    private Workspace workspace;
    private User user;
    private Location location;

    private Workday workday;

    @BeforeEach
    public void setUp() {
        location = new Location(1L, 1, 1, "Alexandria");
        user = new User(1L, "ahmad@gmail.com", "password", "Ahmad", "0123456789", Role.ADMIN);
        workspace = new Workspace(1L, location, user);
        workday = new Workday(1L, Time.valueOf("09:00:00"), Time.valueOf("17:00:00"), Day.SUNDAY, workspace);
    }

    @Test
    public void testGetAllWorkdays() throws Exception {
        when(workdayRepo.findAll()).thenReturn(List.of(workday));

        mockMvc.perform(get("/workdays"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].startTime").value("09:00:00"))
                .andExpect(jsonPath("$[0].endTime").value("17:00:00"));
    }

    @Test
    public void testGetAllWorkdaysEmpty() throws Exception {
        when(workdayRepo.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/workdays"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllWorkdaysInternalServerError() throws Exception {
        doThrow(new RuntimeException("Database error")).when(workdayRepo).findAll();

        mockMvc.perform(get("/workdays"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetWorkdayById() throws Exception {
        when(workdayRepo.findById(1L)).thenReturn(Optional.of(workday));

        mockMvc.perform(get("/workdays/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime").value("09:00:00"))
                .andExpect(jsonPath("$.endTime").value("17:00:00"));
    }

    @Test
    public void testGetWorkdayByIdNotFound() throws Exception {
        when(workdayRepo.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/workdays/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateWorkday() throws Exception {
        when(workdayRepo.save(any(Workday.class))).thenReturn(workday);

        mockMvc.perform(post("/workdays")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(workday)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.startTime").value("09:00:00"))
                .andExpect(jsonPath("$.endTime").value("17:00:00"));
    }

    @Test
    public void testCreateWorkdayInternalServerError() throws Exception {
        doThrow(new RuntimeException("Database error")).when(workdayRepo).save(any(Workday.class));

        mockMvc.perform(post("/workdays")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(workday)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testUpdateWorkday() throws Exception {
        when(workdayRepo.findById(1L)).thenReturn(Optional.of(workday));
        when(workdayRepo.save(any(Workday.class))).thenReturn(workday);

        workday.setStartTime(Time.valueOf("10:00:00"));
        workday.setEndTime(Time.valueOf("18:00:00"));

        mockMvc.perform(put("/workdays/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(workday)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime").value("10:00:00"))
                .andExpect(jsonPath("$.endTime").value("18:00:00"));
    }

    @Test
    public void testUpdateWorkdayNotFound() throws Exception {
        when(workdayRepo.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/workdays/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(workday)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteWorkday() throws Exception {
        doNothing().when(workdayRepo).deleteById(1L);

        mockMvc.perform(delete("/workdays/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteWorkdayNotFound() throws Exception {
        doThrow(new RuntimeException("Workday not found")).when(workdayRepo).deleteById(1L);

        mockMvc.perform(delete("/workdays/{id}", 1))
                .andExpect(status().isInternalServerError());
    }
}