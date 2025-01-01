package com.example.book_n_go.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.book_n_go.dto.BookingCreateRequest;
import com.example.book_n_go.dto.BookingUpdateDurationRequest;
import com.example.book_n_go.dto.BookingUpdateStatusRequest;
import com.example.book_n_go.dto.HallRequest;
import com.example.book_n_go.enums.Day;
import com.example.book_n_go.enums.Role;
import com.example.book_n_go.enums.Status;
import com.example.book_n_go.model.Aminity;
import com.example.book_n_go.model.Booking;
import com.example.book_n_go.model.Hall;
import com.example.book_n_go.model.HallSchedule;
import com.example.book_n_go.model.Period;
import com.example.book_n_go.model.User;
import com.example.book_n_go.model.Workday;
import com.example.book_n_go.model.Workspace;
import com.example.book_n_go.repository.AminityRepo;
import com.example.book_n_go.repository.BookingRepo;
import com.example.book_n_go.repository.HallRepo;
import com.example.book_n_go.repository.UserRepo;
import com.example.book_n_go.repository.WorkdayRepo;
import com.example.book_n_go.repository.WorkspaceRepo;

// @SpringBootTest
public class HallServiceTest {

  @Mock
  private AuthService authService;

  @Mock
  private BookingRepo bookingRepo;

  @Mock
  private HallRepo hallRepo;

  @Mock
  private UserRepo userRepo;

  @Mock
  private WorkspaceRepo workspaceRepo;

  @Mock
  private WorkdayRepo workdayRepo;
  @Mock
  private AminityRepo aminityRepo;


  @InjectMocks
  private BookingService bookingService;
  @InjectMocks
  private HallsService hallsService;

  private HallRequest hallRequest;

  private Hall hall;
  private User user;
  private User provider;
  private Workspace workspace;
  private Booking booking;
  private BookingCreateRequest bookingCreateRequest;
  private BookingUpdateDurationRequest bookingUpdateDurationRequest;
  private BookingUpdateStatusRequest bookingUpdateStatusRequest;
  private Workday workday;
  private Period period;
  private Aminity aminity;


  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    workspace = new Workspace();
    workspace.setId(1L);
    workspace.setName("Workspace");

    hall = new Hall();
    hall.setId(1L);
    hall.setWorkspace(workspace);
    hall.setCapacity(10);
    hall.setPricePerHour(50);

    user = new User();
    user.setId(1L);
    user.setRole(Role.CLIENT);

    provider = new User();
    provider.setId(2L);
    provider.setRole(Role.PROVIDER);

    workspace.setProvider(provider);

    booking = new Booking();
    booking.setId(1L);
    booking.setStartTime(LocalDateTime.now().plusDays(3));
    booking.setEndTime(LocalDateTime.now().plusDays(3).plusHours(2));
    booking.setHall(hall);

    bookingCreateRequest = new BookingCreateRequest();
    bookingCreateRequest.setHallId(1L);
    bookingCreateRequest.setStartTime(LocalDateTime.now());
    bookingCreateRequest.setEndTime(LocalDateTime.now().plusHours(2));

    bookingUpdateDurationRequest = new BookingUpdateDurationRequest();
    bookingUpdateDurationRequest.setBookingId(1L);
    bookingUpdateDurationRequest.setStartTime(LocalDateTime.now());
    bookingUpdateDurationRequest.setEndTime(LocalDateTime.now().plusHours(2));

    bookingUpdateStatusRequest = new BookingUpdateStatusRequest();
    bookingUpdateStatusRequest.setBookingId(1L);
    bookingUpdateStatusRequest.setStatus(Status.CONFIRMED);

    workday = new Workday();
    workday.setStartTime(LocalDateTime.of(2024, 9, 6, 0, 0));
    workday.setEndTime(LocalDateTime.of(2024, 9, 6, 23, 59));

    period = new Period(booking.getStartTime(), booking.getEndTime());

    SecurityContext securityContext = mock(SecurityContext.class);
    Authentication authentication = mock(Authentication.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
  }

   @Test
  public void testCreateHall() {
    when(workspaceRepo.findById(1L)).thenReturn(Optional.of(workspace));
    when(aminityRepo.findById(1L)).thenReturn(Optional.of(aminity));
    when(hallRepo.save(any(Hall.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Hall createdHall = hallsService.createHall(hallRequest, 1L);

    assertNotNull(createdHall);
    assertEquals("Conference Hall", createdHall.getName());
    assertEquals(100, createdHall.getCapacity());
    assertEquals("A large conference hall", createdHall.getDescription());
    assertEquals(200, createdHall.getPricePerHour());
    assertEquals(1, createdHall.getAminities().size());
    assertEquals(workspace, createdHall.getWorkspace());
  }

  @Test
  public void testCreateHallWorkspaceNotFound() {
    when(workspaceRepo.findById(1L)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> {
      hallsService.createHall(hallRequest, 1L);
    });
  }

  @Test
  public void testCreateHallAminityNotFound() {
    when(workspaceRepo.findById(1L)).thenReturn(Optional.of(workspace));
    when(aminityRepo.findById(1L)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> {
      hallsService.createHall(hallRequest, 1L);
    });
  }
}