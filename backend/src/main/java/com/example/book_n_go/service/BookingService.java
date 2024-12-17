package com.example.book_n_go.service;

import com.example.book_n_go.dto.BookingCreateRequest;
import com.example.book_n_go.dto.BookingUpdateDurationRequest;
import com.example.book_n_go.dto.BookingUpdateStatusRequest;
import com.example.book_n_go.enums.Day;
import com.example.book_n_go.enums.Status;
import com.example.book_n_go.model.Booking;
import com.example.book_n_go.model.Hall;
import com.example.book_n_go.model.HallSchedule;
import com.example.book_n_go.model.Period;
import com.example.book_n_go.model.Workday;
import com.example.book_n_go.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookingService {
    
    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private HallRepo hallRepo;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private WorkspaceRepo workspaceRepo;

    @Autowired
    private WorkdayRepo workdayRepo;
    
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepo.findByUserId(userId);
    }
    
    public List<Booking> getBookingsByHallId(Long hallId) {
        return bookingRepo.findByHallId(hallId);
    }
    
    public List<Booking> getBookingsByWorkspaceId(Long workspaceId) {
        return bookingRepo.findByWorkspaceId(workspaceId);
    }
    
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }
    
    public HallSchedule getHallSchedules(Long hallId, LocalDateTime startTime) {
        
        if(isHallExists(hallId)) {
            throw new IllegalArgumentException("Hall with id " + hallId + " does not exist");
        }

        Long workSpaceId = hallRepo.findById(hallId).get().getWorkspace().getId();
        List<Workday> workdays = workdayRepo.findByWorkspaceId(workSpaceId);

        List<Booking> bookings = bookingRepo.findByDateBefore(startTime.plus(Duration.ofDays(7)));

        List<Period> bookingPeriods = bookings.stream()
                .map(booking -> new Period(booking.getStartTime(), booking.getEndTime()))
                .collect(Collectors.toList());

        HallSchedule hallSchedule = new HallSchedule(workdays, bookingPeriods);

        return hallSchedule;
    }

    
    public void deleteBooking(Long bookingId) {

        if (!isBookingExists(bookingId)) {
            throw new IllegalArgumentException("Booking with id " + bookingId + " does not exist");
        }

        if (isPastCutOffTime(bookingRepo.findById(bookingId).get().getStartTime())) {
            throw new IllegalArgumentException("Cannot delete booking within 48 hours of the start time");
        }

        bookingRepo.deleteById(bookingId);
    }

    public Booking createBooking (BookingCreateRequest bookingCreateRequest) {

        long hallId = bookingCreateRequest.getHallId();
        long userId = bookingCreateRequest.getUserId();
        long workspaceId = bookingCreateRequest.getWorkspaceId();
        LocalDateTime startTime = bookingCreateRequest.getStartTime();
        LocalDateTime endTime = bookingCreateRequest.getEndTime();

        if (!isWorkspaceExists(workspaceId)) {
            throw new IllegalArgumentException("Workspace with id " + workspaceId + " does not exist");
        }

        if (!isUserExists(userId)) {
            throw new IllegalArgumentException("User with id " + userId + " does not exist");
        }

        if (!isHallExists(bookingCreateRequest.getHallId())) {
            throw new IllegalArgumentException("Hall with id " + bookingCreateRequest.getHallId() + " does not exist");
        }

        if (!isValidDuration(workspaceId, startTime, endTime)) {
            throw new IllegalArgumentException("Invalid duration");
        }

        if (!isHallAvailable(bookingCreateRequest.getHallId(), bookingCreateRequest.getStartTime(), bookingCreateRequest.getEndTime())) {
            throw new IllegalArgumentException("Hall is not available at the requested time");
        }

        Booking booking = new Booking();
        booking.setHall(hallRepo.findById(hallId).get());
        booking.setUser(userRepo.findById(userId).get());
        booking.setWorkspace(workspaceRepo.findById(workspaceId).get());
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        double totalCost = booking.getHall().getPricePerHour() * Duration.between(booking.getStartTime(), booking.getEndTime()).toHours();
        booking.setTotalCost(totalCost);
        booking.setStatus(Status.PENDING);

        return bookingRepo.save(booking);
    }


    public Booking updateBookingDuration (BookingUpdateDurationRequest bookingUpdateRequest) {
        
        Long bookingId = bookingUpdateRequest.getBookingId();
        LocalDateTime startTime = bookingUpdateRequest.getStartTime();
        LocalDateTime endTime = bookingUpdateRequest.getEndTime();
        Long workspaceId = bookingRepo.findById(bookingId).get().getWorkspace().getId();

        if (!isBookingExists(bookingId)) {
            throw new IllegalArgumentException("Booking with id " + bookingId + " does not exist");
        }

        if (isPastCutOffTime(bookingRepo.findById(bookingId).get().getStartTime())) {
            throw new IllegalArgumentException("Cannot update booking duration after 48 hours of the start time");
        }
        
        if (!isValidDuration(workspaceId, startTime, endTime)) {
            throw new IllegalArgumentException("Invalid duration");
        }
        
        if (!isHallAvailable(bookingRepo.findById(bookingId).get().getHall().getId(), startTime, endTime)) {
            throw new IllegalArgumentException("Hall is not available at the requested time");
        }
        
        Booking booking = bookingRepo.findById(bookingId).get();
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        double totalCost = booking.getHall().getPricePerHour() * Duration.between(booking.getStartTime(), booking.getEndTime()).toHours();
        booking.setTotalCost(totalCost);
        
        return bookingRepo.save(booking);
    }


    public Booking updateBookingStatus(BookingUpdateStatusRequest bookingUpdateRequest) {

        Long bookingId = bookingUpdateRequest.getBookingId();
        Status status = bookingUpdateRequest.getStatus();
        
        if (!isBookingExists(bookingId)) {
            throw new IllegalArgumentException("Booking with id " + bookingId + " does not exist");
        }    

        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }    

        Booking booking = bookingRepo.findById(bookingId).get();

        if (booking.getStatus() == Status.REJECTED || booking.getStatus() == Status.CONFIRMED) {
            throw new IllegalArgumentException("Booking is already " + booking.getStatus());
        }

        booking.setStatus(status);
        return bookingRepo.save(booking);
    }


    private boolean isValidDuration(Long workspaceId, LocalDateTime startTime, LocalDateTime endTime) {

        if (startTime.isAfter(endTime)) {
            return false;
        }

        if (startTime.getDayOfWeek() != endTime.getDayOfWeek()) {
            return false;
        }

        Workday workday = workdayRepo.findByWorkspaceIdAndWeekDay(workspaceId, Day.valueOf(startTime.getDayOfWeek().name()));

        if (workday == null) {
            return false;
        }

        LocalTime workdayStartTime = workday.getStartTime().toLocalTime();
        LocalTime workdayEndTime = workday.getEndTime().toLocalTime();

        if (startTime.toLocalTime().isBefore(workdayStartTime)
        || endTime.toLocalTime().isAfter(workdayEndTime)) {
            return false;
        }

        return true;
    }

    private boolean isPastCutOffTime(LocalDateTime startTime) {
        return LocalDateTime.now().isAfter(startTime.minusHours(48));
    }

    private boolean isWorkspaceExists(Long workspaceId) {
        return workspaceRepo.existsById(workspaceId);
    }

    private boolean isUserExists(Long userId) {
        return userRepo.existsById(userId);
    }

    private boolean isBookingExists(Long bookingId) {
        return bookingRepo.existsById(bookingId);
    }

    private boolean isHallExists(Long hallId) {
        return hallRepo.existsById(hallId);
    }

    private boolean isHallAvailable(Long hallId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> bookings = bookingRepo.findConflictingBookings(hallId, startTime, endTime);
        return bookings.isEmpty();
    }

}
