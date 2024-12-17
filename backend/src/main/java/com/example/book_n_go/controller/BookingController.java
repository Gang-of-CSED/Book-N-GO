package com.example.book_n_go.controller;

import java.time.LocalDateTime;
import java.util.List;

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

import com.example.book_n_go.dto.BookingCreateRequest;
import com.example.book_n_go.dto.BookingUpdateDurationRequest;
import com.example.book_n_go.dto.BookingUpdateStatusRequest;
import com.example.book_n_go.model.Booking;
import com.example.book_n_go.model.HallSchedule;
import com.example.book_n_go.service.BookingService;


@RestController
@RequestMapping("/bookings")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable("id") Long userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/hall/{id}")
    public ResponseEntity<List<Booking>> getBookingsByHallId(@PathVariable("id") Long hallId) {
        List<Booking> bookings = bookingService.getBookingsByHallId(hallId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/hall/schedule/{id}")
    public ResponseEntity<HallSchedule> getHallSchedules(@PathVariable("id") Long hallId, LocalDateTime startTime) {
        HallSchedule hallSchedule = bookingService.getHallSchedules(hallId, startTime);
        return new ResponseEntity<>(hallSchedule, HttpStatus.OK);
        
    }

    @GetMapping("/workspace/{id}")
    public ResponseEntity<List<Booking>> getBookingsByWorkspaceId(@PathVariable("id") Long workspaceId) {
        List<Booking> bookings = bookingService.getBookingsByWorkspaceId(workspaceId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking (@RequestBody BookingCreateRequest bookingCreateRequest) {
        Booking booking = bookingService.createBooking(bookingCreateRequest);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PutMapping("/updateDuration")
    public ResponseEntity<Booking> updateBookingDuration (@RequestBody BookingUpdateDurationRequest bookingUpdateDurationRequest) {
        Booking booking = bookingService.updateBookingDuration(bookingUpdateDurationRequest);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<Booking> updateBookingStatus (@RequestBody BookingUpdateStatusRequest bookingUpdateStatusRequest) {
        Booking booking = bookingService.updateBookingStatus(bookingUpdateStatusRequest);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteBooking(@PathVariable("id") Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
