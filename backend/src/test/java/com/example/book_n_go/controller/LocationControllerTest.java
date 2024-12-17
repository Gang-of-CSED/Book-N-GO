package com.example.book_n_go.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.book_n_go.config.TestConfig;
import com.example.book_n_go.model.Location;
import com.example.book_n_go.repository.LocationRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LocationController.class)
@Import(TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationRepo locationRepo;

    private Location location;

    public void setUp() {
        location = new Location(1, 101, 200, "New York");
    }

    @Test
    public void testGetAllLocations() throws Exception {
        setUp();
        when(locationRepo.findAll()).thenReturn(List.of(location));

        mockMvc.perform(get("/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("New York"));
    }

    @Test
    public void testGetAllLocationsEmpty() throws Exception {
        when(locationRepo.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/locations"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllLocationsInternalServerError() throws Exception {
        doThrow(new RuntimeException("Database error")).when(locationRepo).findAll();
        mockMvc.perform(get("/locations"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetLocationById() throws Exception {
        setUp();
        when(locationRepo.findById(1L)).thenReturn(Optional.of(location));

        mockMvc.perform(get("/locations/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("New York"));
    }

    @Test
    public void testGetLocationByIdNotFound() throws Exception {
        when(locationRepo.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/locations/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateLocation() throws Exception {
        setUp();
        when(locationRepo.save(any(Location.class))).thenReturn(location);

        mockMvc.perform(post("/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(location)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city").value("New York"));
    }

    @Test
    public void testCreateInvalidLocation() throws Exception {
        String invalidJson = "{\"departmentNumber\": \"Invalid\", \"streetNumber\": 101, \"city\": \"New York\"}";
        mockMvc.perform(post("/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateLocationInternalServerError() throws Exception {
        setUp();
        doThrow(new RuntimeException("Database error")).when(locationRepo).save(any(Location.class));
        mockMvc.perform(post("/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(location)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testUpdateLocation() throws Exception {
        setUp();
        when(locationRepo.findById(1L)).thenReturn(Optional.of(location));
        when(locationRepo.save(any(Location.class))).thenReturn(location);

        mockMvc.perform(put("/locations/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(location)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("New York"));
    }

    @Test
    public void testUpdateLocationNotFound() throws Exception {
        setUp();
        when(locationRepo.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/locations/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(location)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteLocation() throws Exception {
        setUp();
        doNothing().when(locationRepo).deleteById(1L);

        mockMvc.perform(delete("/locations/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteLocationNotFound() throws Exception {
        setUp();
        doThrow(new RuntimeException()).when(locationRepo).deleteById(1L);

        mockMvc.perform(delete("/locations/{id}", 1))
                .andExpect(status().isInternalServerError());
    }
}