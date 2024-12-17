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
import com.example.book_n_go.enums.Role;
import com.example.book_n_go.model.Location;
import com.example.book_n_go.model.User;
import com.example.book_n_go.model.Workspace;
import com.example.book_n_go.repository.WorkspaceRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(WorkspaceController.class)
@Import(TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class WorkspaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkspaceRepo workspaceRepo;

    private Workspace workspace;
    private User user;
    private Location location;

    public void setUp() {
        location = new Location(1L, 1, 1, "Alexandria");
        user = new User(1L, "ahmad@gmail.com", "password", "Ahmad", "0123456789", Role.ADMIN);
        workspace = new Workspace(1L, location, user);
    }

    @Test
    public void testGetWorkspaces() throws Exception {
        setUp();
        when(workspaceRepo.findAll()).thenReturn(List.of(workspace));

        mockMvc.perform(get("/workspaces"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].providerId").value(101));
    }

    @Test
    public void testGetWorkspacesEmpty() throws Exception {
        when(workspaceRepo.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/workspaces"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetWorkspacesInternalServerError() throws Exception {
        doThrow(new RuntimeException("Database error")).when(workspaceRepo).findAll();
        mockMvc.perform(get("/workspaces"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetWorkspaceById() throws Exception {
        setUp();
        when(workspaceRepo.findById(1L)).thenReturn(Optional.of(workspace));

        mockMvc.perform(get("/workspaces/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.providerId").value(101));
    }

    @Test
    public void testGetWorkspaceByIdNotFound() throws Exception {
        when(workspaceRepo.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/workspaces/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateWorkspace() throws Exception {
        setUp();
        when(workspaceRepo.save(any(Workspace.class))).thenReturn(workspace);

        mockMvc.perform(post("/workspaces")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(workspace)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.providerId").value(101));
    }

    @Test
    public void testCreateWorkspaceInternalServerError() throws Exception {
        setUp();
        doThrow(new RuntimeException("Database error")).when(workspaceRepo).save(any(Workspace.class));
        mockMvc.perform(post("/workspaces")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(workspace)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testUpdateWorkspace() throws Exception {
        setUp();
        when(workspaceRepo.findById(1L)).thenReturn(Optional.of(workspace));
        when(workspaceRepo.save(any(Workspace.class))).thenReturn(workspace);

        mockMvc.perform(put("/workspaces/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(workspace)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.providerId").value(101));
    }

    @Test
    public void testUpdateWorkspaceNotFound() throws Exception {
        setUp();
        when(workspaceRepo.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/workspaces/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(workspace)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteWorkspace() throws Exception {
        doNothing().when(workspaceRepo).deleteById(1L);

        mockMvc.perform(delete("/workspaces/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteWorkspaceInternalServerError() throws Exception {
        doThrow(new RuntimeException()).when(workspaceRepo).deleteById(1L);

        mockMvc.perform(delete("/workspaces/{id}", 1))
                .andExpect(status().isInternalServerError());
    }
}