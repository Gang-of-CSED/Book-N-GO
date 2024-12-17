// package com.example.book_n_go.controller;

// import static org.mockito.ArgumentMatchers.*;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Import;
// import org.springframework.http.MediaType;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.test.web.servlet.MockMvc;

// import com.example.book_n_go.config.TestConfig;
// import com.example.book_n_go.enums.Role;
// import com.example.book_n_go.model.Hall;
// import com.example.book_n_go.model.Location;
// import com.example.book_n_go.model.User;
// import com.example.book_n_go.model.Workspace;
// import com.example.book_n_go.repository.HallRepo;
// import com.example.book_n_go.repository.UserRepo;
// import com.example.book_n_go.service.AuthService;
// import com.fasterxml.jackson.databind.ObjectMapper;

// @WebMvcTest(HallController.class)
// @Import(TestConfig.class)
// @AutoConfigureMockMvc(addFilters = false)
// public class HallControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private HallRepo hallRepo;

//     @MockBean
//     private UserRepo userRepository;

//     @MockBean
//     private AuthService authService;


//     private Hall hall;
//     private Workspace workspace;
//     private User user;
//     private Location location;

//     private String token;

//     @BeforeEach
//     public void setUp() {
//         location = new Location(1L, 1, 1, "Alexandria");
//         user = new User(1L, "ahmad@gmail.com", "password", "Ahmad", "0123456789", Role.ADMIN);
//         workspace = new Workspace(1L, location, user);
//         hall = new Hall(1L, 100, "Large Hall", 200.00, workspace, null);
//         SecurityContextHolder.clearContext();
//     }

//     @Test
//     public void testGetAllHalls() throws Exception {
//         when(hallRepo.findAll()).thenReturn(List.of(hall));

//         mockMvc.perform(get("/halls").header("Authorization", "Bearer " + token))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].capacity").value(100))
//                 .andExpect(jsonPath("$[0].description").value("Large Hall"))
//                 .andExpect(jsonPath("$[0].pricePerHour").value(200.00));
//     }

//     @Test
//     public void testGetAllHallsEmpty() throws Exception {
//         when(hallRepo.findAll()).thenReturn(List.of());

//         mockMvc.perform(get("/halls").header("Authorization", "Bearer " + token))
//                 .andExpect(status().isNoContent());
//     }

//     @Test
//     public void testGetAllHallsInternalServerError() throws Exception {
//         doThrow(new RuntimeException("Database error")).when(hallRepo).findAll();

//         mockMvc.perform(get("/halls"))
//                 .andExpect(status().isInternalServerError());
//     }

//     @Test
//     public void testGetHallById() throws Exception {
//         when(hallRepo.findById(1L)).thenReturn(Optional.of(hall));

//         mockMvc.perform(get("/halls/{id}", 1))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.capacity").value(100))
//                 .andExpect(jsonPath("$.description").value("Large Hall"))
//                 .andExpect(jsonPath("$.pricePerHour").value(200.00));
//     }

//     @Test
//     public void testGetHallByIdNotFound() throws Exception {
//         when(hallRepo.findById(1L)).thenReturn(Optional.empty());

//         mockMvc.perform(get("/halls/{id}", 1))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     public void testCreateHall() throws Exception {
//         when(hallRepo.save(any(Hall.class))).thenReturn(hall);

//         mockMvc.perform(post("/halls")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(new ObjectMapper().writeValueAsString(hall)))
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("$.capacity").value(100))
//                 .andExpect(jsonPath("$.description").value("Large Hall"))
//                 .andExpect(jsonPath("$.pricePerHour").value(200.00));
//     }

//     @Test
//     public void testCreateInvalidHall() throws Exception {
//         String invalidJson = "{ \"workspaceId\": 1, \"capacity\": \"invalid\", \"description\": \"Large hall\", \"pricePerHour\": 20.0 }";

//         mockMvc.perform(post("/halls")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(invalidJson))
//                 .andExpect(status().isBadRequest());
//     }

//     @Test
//     public void testCreateHallInternalServerError() throws Exception {
//         doThrow(new RuntimeException("Database error")).when(hallRepo).save(any(Hall.class));

//         mockMvc.perform(post("/halls")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(new ObjectMapper().writeValueAsString(hall)))
//                 .andExpect(status().isInternalServerError());
//     }

//     @Test
//     public void testUpdateHall() throws Exception {
//         when(hallRepo.findById(1L)).thenReturn(Optional.of(hall));
//         when(hallRepo.save(any(Hall.class))).thenReturn(hall);

//         hall.setCapacity(150);
//         hall.setDescription("Updated Hall");
//         hall.setPricePerHour(250.00);

//         mockMvc.perform(put("/halls/{id}", 1)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(new ObjectMapper().writeValueAsString(hall)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.capacity").value(150))
//                 .andExpect(jsonPath("$.description").value("Updated Hall"))
//                 .andExpect(jsonPath("$.pricePerHour").value(250.00));
//     }

//     @Test
//     public void testUpdateHallNotFound() throws Exception {
//         when(hallRepo.findById(1L)).thenReturn(Optional.empty());

//         mockMvc.perform(put("/halls/{id}", 1)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(new ObjectMapper().writeValueAsString(hall)))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     public void testDeleteHall() throws Exception {
//         doNothing().when(hallRepo).deleteById(1L);

//         mockMvc.perform(delete("/halls/{id}", 1))
//                 .andExpect(status().isNoContent());
//     }

//     @Test
//     public void testDeleteHallNotFound() throws Exception {
//         doThrow(new RuntimeException("Hall not found")).when(hallRepo).deleteById(1L);

//         mockMvc.perform(delete("/halls/{id}", 1))
//                 .andExpect(status().isInternalServerError());
//     }
// }