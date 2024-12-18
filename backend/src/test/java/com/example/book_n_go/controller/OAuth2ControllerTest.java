package com.example.book_n_go.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.book_n_go.enums.Role;
import com.example.book_n_go.model.User;
import com.example.book_n_go.repository.UserRepo;
import com.example.book_n_go.service.JwtService;

public class OAuth2ControllerTest {

    private MockMvc mockMvc;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private Authentication authentication;

    @Mock
    private OAuth2User oAuth2User;

    @InjectMocks
    private OAuth2Controller oAuth2Controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(oAuth2Controller).build();
    }

    @Test
    public void testOauth2Success() throws Exception {
        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getAttribute("email")).thenReturn("test@example.com");
        when(oAuth2User.getAttribute("name")).thenReturn("Test User");

        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .name("Test User")
                .phone("")
                .role(Role.CLIENT)
                .build();

        when(userRepo.findByEmail("test@example.com")).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("dummyToken");

        mockMvc.perform(get("/auth/oauth2-success")
                .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("dummyToken"));
    }

    @Test
    public void testOauth2Success_UserNotFound() throws Exception {
        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getAttribute("email")).thenReturn("newuser@example.com");
        when(oAuth2User.getAttribute("name")).thenReturn("New User");

        when(userRepo.findByEmail("newuser@example.com")).thenThrow(new RuntimeException("User not found"));

        User newUser = User.builder()
                .email("newuser@example.com")
                .password("password")
                .name("New User")
                .phone("")
                .role(Role.CLIENT)
                .build();

        when(userRepo.save(any(User.class))).thenReturn(newUser);
        when(jwtService.generateToken(newUser)).thenReturn("newUserToken");

        mockMvc.perform(get("/auth/oauth2-success")
                .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("newUserToken"));
    }
}