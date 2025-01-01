package com.example.book_n_go.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
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
import com.example.book_n_go.enums.Day;
import com.example.book_n_go.enums.Role;
import com.example.book_n_go.enums.Status;
import com.example.book_n_go.model.Booking;
import com.example.book_n_go.model.Hall;
import com.example.book_n_go.model.HallSchedule;
import com.example.book_n_go.model.Period;
import com.example.book_n_go.model.User;
import com.example.book_n_go.model.Workday;
import com.example.book_n_go.model.Workspace;
import com.example.book_n_go.repository.BookingRepo;
import com.example.book_n_go.repository.HallRepo;
import com.example.book_n_go.repository.UserRepo;
import com.example.book_n_go.repository.WorkdayRepo;
import com.example.book_n_go.repository.WorkspaceRepo;

// @SpringBootTest
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
    private User provider;
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
        when(AuthService.getRequestUser()).thenReturn(user);
        when(workspaceRepo.existsById(any(Long.class))).thenReturn(true);
        when(workdayRepo.findByWorkspaceIdAndWeekDay(any(Long.class), any(Day.class))).thenReturn(workday);
        when(bookingRepo.findConflictingBookings(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(Arrays.asList());
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        Booking createdBooking = bookingService.createBooking(bookingCreateRequest);

        verify(bookingRepo).save(any(Booking.class));
        assertEquals(1L, createdBooking.getId());
    }

    @Test
    public void testGetHallAvailability () {
        LocalDateTime checkDay = LocalDateTime.of(2024, 12, 31, 0, 0);
        Workday workday1 = new Workday();
        workday1.setWorkspace(workspace);
        workday1.setWeekDay(Day.TUESDAY);
        workday1.setStartTime(LocalDateTime.of(2024, 12, 31, 9, 0));
        workday1.setEndTime(LocalDateTime.of(2024, 12, 31, 17, 0));

        Booking booking1 = new Booking();
        booking1.setStartTime(LocalDateTime.of(2024, 12, 31, 10, 0));
        booking1.setEndTime(LocalDateTime.of(2024, 12, 31, 12, 0));

        when(hallRepo.existsById(1L)).thenReturn(true);
        when(hallRepo.findById(1L)).thenReturn(Optional.of(hall));
        when(workdayRepo.findByWorkspaceIdAndWeekDay(1L, Day.TUESDAY)).thenReturn(workday1);
        when(bookingRepo.findByEndTimeBefore(any(LocalDateTime.class))).thenReturn(Arrays.asList(booking1));

        List<Period> hallAvailability = bookingService.getHallAvailability(1L, checkDay);

        assertEquals(2, hallAvailability.size());
        assertEquals(LocalDateTime.of(2024, 12, 31, 9, 0), hallAvailability.get(0).getStartTime());
        assertEquals(LocalDateTime.of(2024, 12, 31, 10, 0), hallAvailability.get(0).getEndTime());
        assertEquals(LocalDateTime.of(2024, 12, 31, 12, 0), hallAvailability.get(1).getStartTime());
        assertEquals(LocalDateTime.of(2024, 12, 31, 17, 0), hallAvailability.get(1).getEndTime());
    
    }


    @Test
    public void testUpdateBookingDuration() {
        // Mock SecurityContext and Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(provider);
        SecurityContextHolder.setContext(securityContext);

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
        // Mock SecurityContext and Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(provider);
        SecurityContextHolder.setContext(securityContext);

        when(bookingRepo.existsById(1L)).thenReturn(true);
        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        Booking updatedBooking = bookingService.updateBookingStatus(bookingUpdateStatusRequest);

        verify(bookingRepo).save(booking);
        assertEquals(Status.CONFIRMED, updatedBooking.getStatus());
    }

    @Test
    public void testDeleteBooking() {
        // Mock SecurityContext and Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(provider);
        SecurityContextHolder.setContext(securityContext);

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
