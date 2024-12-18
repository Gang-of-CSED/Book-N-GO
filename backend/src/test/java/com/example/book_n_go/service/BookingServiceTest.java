package com.example.book_n_go.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.book_n_go.dto.BookingCreateRequest;
import com.example.book_n_go.dto.BookingUpdateDurationRequest;
import com.example.book_n_go.dto.BookingUpdateStatusRequest;
import com.example.book_n_go.enums.Day;
import com.example.book_n_go.enums.Status;
import com.example.book_n_go.model.*;
import com.example.book_n_go.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
public class BookingServiceTest {

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

    @InjectMocks
    private BookingService bookingService;

    private Hall hall;
    private User user;
    private Workspace workspace;
    private Booking booking;
    private BookingCreateRequest bookingCreateRequest;
    private BookingUpdateDurationRequest bookingUpdateDurationRequest;
    private BookingUpdateStatusRequest bookingUpdateStatusRequest;
    private Workday workday;
    private Period period;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        workspace = new Workspace();
        workspace.setId(1L);


        hall = new Hall();
        hall.setId(1L);
        hall.setWorkspace(workspace);

        user = new User();
        user.setId(1L);


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
        workday.setStartTime(java.sql.Time.valueOf(LocalDateTime.now().with(LocalTime.MIN).toLocalTime()));
        workday.setEndTime(java.sql.Time.valueOf(LocalDateTime.now().with(LocalTime.MAX).toLocalTime()));


        period = new Period(booking.getStartTime(), booking.getEndTime());
    }

    @Test
    public void testGetBookingsByUserId() {
        when(bookingRepo.findByUserId(1L)).thenReturn(Arrays.asList(booking));
        List<Booking> bookings = bookingService.getBookingsByUserId(1L);

        verify(bookingRepo).findByUserId(1L);
        assertEquals(1, bookings.size());
        assertEquals(1L, bookings.get(0).getId());
    }

    @Test
    public void testGetBookingsByHallId() {
        when(bookingRepo.findByHallId(1L)).thenReturn(Arrays.asList(booking));
        List<Booking> bookings = bookingService.getBookingsByHallId(1L);

        verify(bookingRepo).findByHallId(1L);
        assertEquals(1, bookings.size());
        assertEquals(1L, bookings.get(0).getId());
    }

    @Test
    public void testGetAllBookings() {
        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));
        List<Booking> bookings = bookingService.getAllBookings();

        verify(bookingRepo).findAll();
        assertEquals(1, bookings.size());
        assertEquals(1L, bookings.get(0).getId());
    }

    @Test
    public void testGetHallSchedules() {
        when(hallRepo.existsById(1L)).thenReturn(true);
        when(hallRepo.findById(1L)).thenReturn(Optional.of(hall));
        when(workdayRepo.findByWorkspace(any(Workspace.class))).thenReturn(Arrays.asList(workday));
        when(bookingRepo.findByEndTimeBefore(any(LocalDateTime.class))).thenReturn(Arrays.asList(booking));

        HallSchedule hallSchedule = bookingService.getHallSchedules(1L, LocalDateTime.now().plusDays(1));

        assertEquals(1, hallSchedule.getBookingPeriods().size());
        assertEquals(period, hallSchedule.getBookingPeriods().get(0));
    }

    @Test
    public void testCreateBooking() {
        // Mock SecurityContext and Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);


        when(hallRepo.existsById(1L)).thenReturn(true);
        when(hallRepo.findById(1L)).thenReturn(Optional.of(hall));
        when(userRepo.existsById(1L)).thenReturn(true);
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(authService.getRequestUser()).thenReturn(user);
        when(workspaceRepo.existsById(any(Long.class))).thenReturn(true);
        when(workdayRepo.findByWorkspaceIdAndWeekDay(any(Long.class), any(Day.class))).thenReturn(workday);
        when(bookingRepo.findConflictingBookings(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(Arrays.asList());
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        Booking createdBooking = bookingService.createBooking(bookingCreateRequest);

        verify(bookingRepo).save(any(Booking.class));
        assertEquals(1L, createdBooking.getId());
    }

    @Test
    public void testUpdateBookingDuration() {
        when(bookingRepo.existsById(1L)).thenReturn(true);
        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));
        when(hallRepo.findById(1L)).thenReturn(Optional.of(hall));
        when(workdayRepo.findByWorkspaceIdAndWeekDay(any(Long.class), any(Day.class))).thenReturn(workday);
        when(bookingRepo.findConflictingBookings(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(Arrays.asList());
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        Booking updatedBooking = bookingService.updateBookingDuration(bookingUpdateDurationRequest);

        verify(bookingRepo).save(booking);
        assertEquals(1L, updatedBooking.getId());
    }

    @Test
    public void testUpdateBookingStatus() {
        when(bookingRepo.existsById(1L)).thenReturn(true);
        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        Booking updatedBooking = bookingService.updateBookingStatus(bookingUpdateStatusRequest);

        verify(bookingRepo).save(booking);
        assertEquals(Status.CONFIRMED, updatedBooking.getStatus());
    }

    @Test
    public void testDeleteBooking() {
        when(bookingRepo.existsById(1L)).thenReturn(true);
        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));

        bookingService.deleteBooking(1L);

        verify(bookingRepo, times(1)).deleteById(1L);
    }

    @Test
    public void testCreateBooking_HallNotExists() {
        // Mock SecurityContext and Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);

        when(hallRepo.existsById(1L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(bookingCreateRequest);
        });
    }
}
